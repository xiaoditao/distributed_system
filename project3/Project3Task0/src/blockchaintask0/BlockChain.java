package blockchaintask0;
/**
 *
 * @author xiaoditao
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/*
This class represents a simple BlockChain
*/
public class BlockChain {
    /*
    an ArrayList to hold Blocks
    */
    static List<Block> list;
    /*
    a chain hash to hold a SHA256 hash of the most recently added Block
    */
    static String chainHash;
    /*
    constructor, initialize the instance members
    */
    public BlockChain () {
        BlockChain.list = new ArrayList<>();
    }
    /*
    A new Block is being added to the BlockChain. This new block's previous hash must hold the hash of the most recently added block.
    After this call on addBlock, the new block becomes the most recently added block on the BlockChain. The SHA256 hash of every block
    must exhibit proof of work, i.e., have the requisite number of leftmost 0's defined by its difficulty. Suppose our new block is x.
    And suppose the old blockchain was a <-- b <-- c <-- d then the chain after addBlock completes is a <-- b <-- c <-- d <-- x.
    Within the block x, there is a previous hash field. This previous hash field holds the hash of the block d. The block d is called the parent of x.
    The block x is the child of the block d. It is important to also maintain a hash of the most recently added block in a chain hash.
    Let's look at our two chains again. a <-- b <-- c <-- d. The chain hash will hold the hash of d. After adding x, we have a <-- b <-- c <-- d <-- x.
    The chain hash now holds the hash of x. The chain hash is not defined within a block but is defined within the block chain. The arrows are used to
    describe these hash pointers. If b contains the hash of a then we write a <-- b.
    @parameter: newBlock - is added to the BlockChain as the most recent block
    */
    public void addBlock(Block newBlock) {
        newBlock.setPreviousHash(chainHash);
        chainHash = newBlock.proofOfWork();
        list.add(newBlock);
    }
    /*
    get the size of the chain
    @returns
    the size of the chain in blocks
    */
    public int getChainSize() {
        return list.size();
    }
    /*
    get the last block in the chain
    @returns
    a reference to the most recently added Block
    */
    public Block getLatestBlock() {
        return list.get(list.size()-1);
    }
    /*
    get the current system time
    @returns
    the current system time
    */
    public Timestamp getTime() {
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        Instant instant = Instant.from(zdt);
        Timestamp timestamp = Timestamp.from(instant);
        return timestamp;
    }
    /**
     * If the chain only contains one block, the genesis block at position 0, this routine
     * computes the hash of the block and checks that the hash has the requisite number of
     * leftmost 0's (proof of work) as specified in the difficulty field. It also checks
     * that the chain hash is equal to this computed hash. If either check fails, return false.
     * Otherwise, return true.
     * If the chain has more blocks than one, begin checking from block one.
     * Continue checking until you have validated the entire chain. The first check will involve a
     * computation of a hash in Block 0 and a comparison with the hash pointer in Block 1. If they
     * match and if the proof of work is correct, go and visit the next block in the chain. At the end,
     * check that the chain hash is also correct.
     * @return boolean, if the chain is valid
     */
    public Boolean isChainvalid() {
        // the condition when the chain is empty, return true
        if (BlockChain.list.isEmpty()) return true;
        // the condition when there is only one block in the chain
        if (BlockChain.list.size() == 1) {
            Block firstBlock = BlockChain.list.get(0);
            String hash = firstBlock.calculateHash();
            int proof = 0;
            // compute the number of leftmost 0's
            while(true){
                char c = hash.charAt(0);
                if(c == '0') {
                    // If the left is zero, count is added
                    proof = proof + 1;
                    hash = hash.substring(1);
                }
                if(c != '0') {
                    break;
                }
            }
            String proofString = String.valueOf(proof);
            String diffString = String.valueOf(firstBlock.getDifficulty());
            // if the number of leftmost 0's does not equal difficulty, return false
            if (!proofString.equals(diffString)) {
                System.out.println("There is one block in the chain, and the hash does not have requisite number of leftmost 0's");
                return false;
            }
            // if the previous hash of the next block does not equal to the hash of this block, return false
            if (! BlockChain.chainHash.equals(firstBlock.calculateHash())) {
                System.out.println("The chain hash is not equal to it's computed hash");
                return false;
            }
        }
        // the condition when the chain has more than one block
        if (BlockChain.list.size() != 1) {
            // check each block in the chain
            for (int i = 1; i < BlockChain.list.size(); i++) {
                Block thisBlock = BlockChain.list.get(i);
                Block previousBlock = BlockChain.list.get(i - 1);
                // if the previous hash of the next block does not equal to the hash of this block, return false
                if (!previousBlock.calculateHash().equals(thisBlock.getPreviousHash())) {
                    System.out.println("..Improper hash on node " + i + ", previous hash does not equal to previous block's hash");
                    return false;
                }
                Block b = BlockChain.list.get(i);
                String hash2 = b.calculateHash();
                int proof2 = 0;
                while(true){
                    char c = hash2.charAt(0);
                    if(c == '0') {
                        proof2 = proof2 + 1;
                        hash2 = hash2.substring(1);
                    }
                    if(c != '0') {
                        break;
                    }
                }
                // if the number of leftmost 0's does not equal difficulty, return false
                if (proof2 != b.getDifficulty()) {
                    StringBuilder s = new StringBuilder();
                    for (int k = 0; k < b.getDifficulty(); k++) {
                        s = s.append("0");
                    }
                    s.toString();
                    System.out.println("..Improper hash on node " + i + " Does not begin with " + s);
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * This routine acts as a test driver for your Blockchain. It will begin by creating a BlockChain object
     * and then adding the Genesis block to the chain. The Genesis block will be created with an empty string
     * as the pervious hash and a difficulty of 2.
     * All blocks added to the Blockchain will have a difficulty passed in to the program by the user at run
     * time. All hashes will have the proper number of zero hex digits representing the most significant
     * nibbles in the hash. A nibble is 4 bits. If the difficulty is specified as three, then all hashes
     * will begin with 3 or more zero hex digits (or 3 nibbles, or 12 zero bits).
     * @param args
     */
    public static void main(String[] args) {
        // initialize the block chain
        BlockChain blockChain = new BlockChain();
        // initialize the first block in the chain
        Block newBlock = new Block(0, blockChain.getTime(), "Genesis", 2);
        newBlock.setPreviousHash("");
        chainHash = newBlock.proofOfWork();
        list.add(newBlock);
        Scanner sc = new Scanner(System.in);
        // print the menu
        System.out.println("Block Chain Menu");
        System.out.println("0. View basic blockchain status.");
        System.out.println("1. Add a transaction to the blockchain.");
        System.out.println("2. Verify the blockchain.");
        System.out.println("3. View the blockchain.");
        System.out.println("4. Corrupt the chain.");
        System.out.println("5. Hide the corruption by repairing the chain.");
        System.out.println("6. Exit.");
        // continously print the menu, until the user quits
        while(sc.hasNextLine()) {
            String input = sc.nextLine();
            // if the input is zero, view basic blockchain status
            if (input.equals("0")) {
                System.out.println("Current size of chain:" + list.size());
                System.out.println("Current hashes per second by this machine:" + blockChain.hashesPerSecond());
                System.out.println("Difficulty of most recent block:" + list.get(list.size()-1).getDifficulty());
                System.out.println("Nonce for most recent block:" + list.get(list.size()-1).getNonce());
                System.out.println("Chain hash:");
                System.out.println(chainHash);
            }
            // if the input is one, add a transaction to the blockchain
            if (input.equals("1")) {
                String difficultyInput = "";
                int difficultyInputInt = 0;
                System.out.println("Enter difficulty > 0");
                Scanner scDifficulty = new Scanner(System.in);
                difficultyInput = scDifficulty.nextLine();
                difficultyInputInt = Integer.valueOf(difficultyInput);
                while(difficultyInputInt <= 0) {
                    System.out.println("Enter difficulty > 0");
                    difficultyInput = scDifficulty.nextLine();
                    difficultyInputInt = Integer.valueOf(difficultyInput);
                }
                System.out.println("Enter transaction");
                String transactionInput = scDifficulty.nextLine();
                Block blockAdd = new Block(list.size(), blockChain.getTime(), transactionInput, difficultyInputInt);
                long start = System.currentTimeMillis();
                blockChain.addBlock(blockAdd);
                long end = System.currentTimeMillis();
                long timeAdd = end - start;
                System.out.println("Total execution time to add this block was " + timeAdd + " milliseconds");
            }
            // if the input is two, verify the chain
            if (input.equals("2")) {
                System.out.println("Verifying entire chain");
                long start = System.currentTimeMillis();
                boolean res = blockChain.isChainvalid();
                System.out.println("Chain verification:" + res);
                long end = System.currentTimeMillis();
                long timeAdd = end - start;
                System.out.println("Total execution time to verify the chain was " + timeAdd + " milliseconds");
            }
            // if the input is three, view the BlockChain
            if (input.equals("3")) {
                System.out.println("View the BlockChain");
                System.out.println(blockChain);
            }
            // if the input is four,corrupt the chain
            if (input.equals("4")) {
                System.out.println("Corrupt the Blockchain");
                System.out.println("Enter block ID of block to Corrupt");
                Scanner scFour = new Scanner(System.in);
                String inputID = scFour.nextLine();
                int inputIDInt = Integer.valueOf(inputID);
                System.out.println("Enter new data for block " + inputID);
                String newData = scFour.nextLine();
                blockChain.list.get(inputIDInt).setData(newData);
                System.out.println("Block" + inputID + " now holds " + newData);
            }
            // if the input is five, repair the chain
            if (input.equals("5")) {
                System.out.println("Reparing the entire chain");
                long start = System.currentTimeMillis();
                blockChain.repairChain();
                long end = System.currentTimeMillis();
                long timeAdd = end - start;
                System.out.println("Total execution time to verify the chain was " + timeAdd + " milliseconds");
                
            }
            // if the input is six, quit
            if (input.equals("6")) {
                break;
            }
            System.out.println("0. View basic blockchain status.");
            System.out.println("1. Add a transaction to the blockchain.");
            System.out.println("2. Verify the blockchain.");
            System.out.println("3. View the blockchain.");
            System.out.println("4. Corrupt the chain.");
            System.out.println("5. Hide the corruption by repairing the chain.");
            System.out.println("6. Exit.");
        }
    }
    /*
    comput the count of hashes per second of the computer holding this chain. It uses a simple string - "00000000" to hash
    */
    public int hashesPerSecond() {
        long time = System.currentTimeMillis();
        long end = time + 1000;
        int count = 0;
        // while it is inside one second
        while(System.currentTimeMillis() < end) {
            count++;
            String hashZero = "00000000";
            MessageDigest messagedigest;
            try {
                messagedigest = MessageDigest.getInstance("SHA-256");
                messagedigest.update(hashZero.getBytes());
                byte[] digest = messagedigest.digest();
                String res = DatatypeConverter.printHexBinary(digest);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;
    }
    /*
    This routine repairs the chain. It checks the hashes of each block and ensures that any illegal hashes are recomputed. 
    After this routine is run, the chain will be valid. The routine does not modify any difficulty values. It computes 
    new proof of work based on the difficulty specified in the Block.
    */
    public void repairChain() {
        // if there is only one block in the chain, give the hash to chianHash
        if(BlockChain.list.size() == 1) {
            Block block = BlockChain.list.get(0);
            BlockChain.chainHash = block.proofOfWork();
        }
        // if not, for each block, compute the hash, give it to chianHash and make next block's previous hash 
        // to be the chianHash
        else{
            for(int i = 0; i < BlockChain.list.size()-1; i++) {
                Block block = BlockChain.list.get(i);
                BlockChain.chainHash = block.proofOfWork();
                Block nextBlock = BlockChain.list.get(i + 1);
                nextBlock.setPreviousHash(BlockChain.chainHash);
            }
            Block b = BlockChain.list.get(list.size()-1);
            b.proofOfWork();
        }
    }
    /*
    this method uses the toString method defined on each individual block.
    */
    @Override
    public String toString() {
        JSONObject objJson = new JSONObject();
        JSONArray arrayJson = new JSONArray();
        for (Block block : list){
            arrayJson.add(block.transferJson());
        }
        objJson.put("ds_chain", arrayJson);
        objJson.put("chainHash", BlockChain.chainHash);
        String ans = objJson.toJSONString();
        return ans;
    }
}
