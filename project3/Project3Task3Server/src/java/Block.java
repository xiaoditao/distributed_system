/*
This class represents a simple Block.
Each Block object has an index - the position of the block on the chain. The first block (the so called Genesis block) has an index of 0.
Each block has a timestamp - a Java Timestamp object, it holds the time of the block's creation.
Each block has a field named data - a String holding the block's single transaction details.
Each block has a String field named previousHash - the SHA256 hash of a block's parent. This is also called a hash pointer.
Each block holds a nonce - a BigInteger value determined by a proof of work routine. This has to be found by the proof of work logic. It has to be found so that this block has
a hash of the proper difficulty. The difficulty is specified by a small integer representing the number of leading hex zeroes the hash must have.
Each block has a field named difficulty - it is an int that specifies the exact number of left most hex digits needed by a proper hash. The hash is represented in hexadecimal.
If, for example, the difficulty is 3, the hash must have three leading hex 0's (or,1 and 1/2 bytes). Each hex digit represents 4 bits.
*/


/**
 *
 * @author xiaoditao
 */



import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONObject;

/*
This class represents a simple Block.
Each Block object has an index - the position of the block on the chain. The first block (the so called Genesis block) has an index of 0.
Each block has a timestamp - a Java Timestamp object, it holds the time of the block's creation.
Each block has a field named data - a String holding the block's single transaction details.
Each block has a String field named previousHash - the SHA256 hash of a block's parent. This is also called a hash pointer.
Each block holds a nonce - a BigInteger value determined by a proof of work routine. This has to be found by the proof of work logic. It has to be found so that this block has
a hash of the proper difficulty. The difficulty is specified by a small integer representing the number of leading hex zeroes the hash must have.
Each block has a field named difficulty - it is an int that specifies the exact number of left most hex digits needed by a proper hash. The hash is represented in hexadecimal.
If, for example, the difficulty is 3, the hash must have three leading hex 0's (or,1 and 1/2 bytes). Each hex digit represents 4 bits.
*/
public class Block extends java.lang.Object{
    /*
    the position of the block on the chain
    */
    private int index;
    /*
    a Java Timestamp object, it holds the time of the block's creation
    */
    private Timestamp timestamp;
    /*
    a String holding the block's single transaction details
    */
    private String data;
    /*
    the SHA256 hash of a block's parent
    */
    private String previousHash;
    /*
    a BigInteger value determined by a proof of work routine
    */
    private BigInteger nonce;
    /*
    the difficulty is specified by a small integer representing the number of leading hex zeroes the hash must hav
    */
    private int difficulty;
    
    /*
    This the Block constructor
    */
    public Block(int index, java.sql.Timestamp timestamp, java.lang.String data, int difficulty) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.difficulty = difficulty;
        previousHash = "";
    }
    /*
    Getter method
    */
    public int getIndex() {
        return index;
    }
    /*
    Setter method
    */
    public void setIndex(int index) {
        this.index = index;
    }
    /*
    Getter method
    */
    public Timestamp getTimestamp() {
        return timestamp;
    }
    /*
    Setter method
    */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    /*
    Getter method
    */
    public String getData() {
        return data;
    }
    /*
    Setter method
    */
    public void setData(String data) {
        this.data = data;
    }
    /*
    Getter method
    */
    public String getPreviousHash() {
        return previousHash;
    }
    /*
    Setter method
    */
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
    /*
    Getter method
    */
    public BigInteger getNonce() {
        return nonce;
    }
    /*
    Setter method
    */
    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }
    /*
    Getter method
    */
    public int getDifficulty() {
        return difficulty;
    }
    /*
    Setter method
    */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    /*
    This method computes a hash of the concatenation of the index, timestamp, data, previousHash, nonce, and difficulty
    */
    public String calculateHash() {
        // Concat the string
        String concatenation = String.valueOf(index) + String.valueOf(timestamp) + data + previousHash + String.valueOf(nonce) + String.valueOf(difficulty); 
        Date date = new Date();
        date.setTime(this.timestamp.getTime());
        MessageDigest messagedigest;
        try {
            //try to do the hash using "SHA-256"
            messagedigest = MessageDigest.getInstance("SHA-256");
            messagedigest.update(concatenation.getBytes());
            byte[] digest = messagedigest.digest();
            String res = DatatypeConverter.printHexBinary(digest);
            return res;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);    
        }
        return null;
    }

    /*
    The method transforms string to Json format in order to print in a well format in client side
    */
    public JSONObject transferJson() {
        // Construct the json object
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("index", index);
        jsonObj.put("time stamp", String.valueOf(timestamp));
        jsonObj.put("Tx ", data);
        jsonObj.put("PrevHash", previousHash);
        jsonObj.put("nonce", nonce);
        jsonObj.put("difficulty", difficulty);
        // Transfer the json object to string
        return jsonObj;
    }
    /*
    The proof of work methods finds a good hash. It increments the nonce until it produces a good hash.
    This method calls calculateHash() to compute a hash of the concatenation of the index,
    timestamp, data, previousHash, nonce, and difficulty. If the hash has the appropriate
    number of leading hex zeroes, it is done and returns that proper hash. If the hash does
    not have the appropriate number of leading hex zeroes, it increments the nonce by 1
    and tries again. It continues this process, burning electricity and CPU cycles, until
    it gets lucky and finds a good hash.
    @return a String with a hash that has the appropriate number of leading hex zeroes. The difficulty value
    is already in the block. This is the number of hex 0's a proper hash must have.
    */
    public String proofOfWork() {
        this.nonce = new BigInteger("0");
        int num = 0;
        String res = "";
        BigInteger one = new BigInteger("1"); 
        // The num means the number of leading hex zeroes
        // If it does not equal to difficulty, then do the hash again
        while(num != this.getDifficulty()) {
            this.nonce = this.nonce.add(one);
            res = this.calculateHash();     
            int proof = 0;
            // Count the number of leading hex zeroes
            // If the first left number is zero, then do substring and decide whether the first left one is zero
            while(true){
                char c = res.charAt(0);
                if(c == '0') {
                    proof = proof + 1;
                    res = res.substring(1);
                }
                if(c != '0') {
                    break;
                }
            }    
            num = proof;
        }
        // Get the original hash string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.getDifficulty(); i++){
            sb = sb.append("0");
        }
        String convert = sb.toString();
        res = convert + res;
        return res;
    }
    /*
    Override Java's toString method
    @return: a JSON representation of all of this block's data is returned
    */
    @Override
    public String toString() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("index", String.valueOf(index));
        jsonObj.put("time stamp", String.valueOf(timestamp));
        jsonObj.put("Tx ", data);
        jsonObj.put("PrevHash", previousHash);
        jsonObj.put("nonce", String.valueOf(nonce));
        jsonObj.put("difficulty", String.valueOf(difficulty));
        String res = jsonObj.toString();
        return res;
    }
    
}

