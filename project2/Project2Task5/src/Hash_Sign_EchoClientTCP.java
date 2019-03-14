/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xiaoditao
 * This is a TCP client that can do the operation of add or subtract,
 * and get the result back. Also, we will hash the id and sign the message.
 */
import java.net.*;
import java.io.*;
import java.math.BigInteger;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
/**
 * @author xiaoditao
 * This is a TCP client that can do the operation of add or subtract,
 * and get the result back. Also, we will hash the id and sign the message.
 */
public class Hash_Sign_EchoClientTCP {
    /**
     * BigInteger n, e, d is the global variable, which is used when generating public and private key.
     * eString and nString are the String that are converted from e and n.
     */
    BigInteger n; 
    BigInteger e; 
    BigInteger d; 
    String eString;
    String nString;
     /**
     * @param args 
     * This is the main method of the class, it is the entrance and it will 
     * do the operation of add or subtract. Also, we will hash the id and sign the message.
     */
    public static void main(String args[]) throws NoSuchAlgorithmException, Exception {
        System.out.println("Client running");
        Hash_Sign_EchoClientTCP echoClientTCP = new Hash_Sign_EchoClientTCP();
        String publicKey = echoClientTCP.computeKey();
        echoClientTCP.operate(publicKey, echoClientTCP);
    }
    /**
    * 1. Select at random two large prime numbers p and q.
    * 2. Compute n by the equation n = p * q.
    * 3. Compute phi(n)=  (p - 1) * ( q - 1)
    * 4. Select a small odd integer e that is relatively prime to phi(n).
    * 5. Compute d as the multiplicative inverse of e modulo phi(n). A theorem in
    *    number theory asserts that d exists and is uniquely defined.
    * 6. Publish the pair P = (e,n) as the RSA public key.
    * 7. Keep secret the pair S = (d,n) as the RSA secret key.
    * 8. To encrypt a message M compute C = M^e (mod n)
    * 9. To decrypt a message C compute M = C^d (mod n)
    */
    private String computeKey() {
            Random rnd = new Random();
            BigInteger p = new BigInteger(400,100,rnd);
            BigInteger q = new BigInteger(400,100,rnd);
            this.n = p.multiply(q);
            BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
            this.e = new BigInteger ("65537");
            this.d = e.modInverse(phi);
            this.eString = e.toString();
            this.nString = n.toString();
            String publicKey = this.eString + this.nString;
            return publicKey;
    }
     /** 
     * This method will
     * do the operation of add or subtract.
     * and we will hash the id and sign the message
     */ 
    private void operate(String publicKey, Hash_Sign_EchoClientTCP echoClientTCP) throws NoSuchAlgorithmException, Exception{
            Socket clientSocket = null;
        try {
            int serverPort = 7777;
            clientSocket = new Socket("localhost", serverPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
            Scanner sc = new Scanner(System.in);
            String m;      
            String idHashed = echoClientTCP.ComputeSHA_256_as_Hex_String(publicKey);
            System.out.println("Please enter your operation");
            String nextOperation; 
            while ((nextOperation = sc.nextLine()) != null) { 
                // If the operation is view, then it will not ask the client to type message
                if (nextOperation.equals("view")) {
                        m = idHashed + ";" + eString + ";" + nString + ";" + nextOperation;
                    } else {
                        // If the operation is not view, then it will ask the client to type vlue, or the user can choose to quit
                        System.out.println("Please enter your value, or you can quit by entering 'quit'");
                        String nextValue = sc.nextLine();
                        m = idHashed + ";" + eString + ";" + nString + ";"+ nextOperation + ";"  + nextValue;
                    }
            // sign the message and concact it with the message to send to the server
            String sign = echoClientTCP.sign(m);
            String send = m + ";" + sign;
                out.println(send);
                out.flush();
                if (send.contains("quit")) {
                        System.out.println("I am quitting!");
                        break;
                    }
                String data = in.readLine(); 
                System.out.println("Received: " + data);
                System.out.println("Please enter your operation");
            }
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
            }
        }
    }
     /**
     * ComputeSHA_256_as_Hex_String can read any input and compute a SHA-256 has
     * The program then displays 4 hex-digits taken from the 
     * leftmost 20 bytes of the SHA-256 Hash.
     */
    private String ComputeSHA_256_as_Hex_String(String text) { 
    
        try { 
             MessageDigest digest;
             digest = MessageDigest.getInstance("SHA-256");
             // allocate room for the result of the hash
             byte[] hashBytes;
             // perform the hash
             digest.update(text.getBytes("UTF-8"), 0, text.length());
             // collect result
             hashBytes = digest.digest();
             byte[] leastSignificant = new byte[20];
             System.arraycopy(hashBytes, hashBytes.length-21, leastSignificant, 0, 20);
             String res = new String(leastSignificant);
             return res;
        }
        catch (NoSuchAlgorithmException nsa) {
            System.out.println("No such algorithm exception thrown " + nsa);
        }
        catch (UnsupportedEncodingException uee ) {
            System.out.println("Unsupported encoding exception thrown " + uee);
        }
        return null;
    } 
     /**
     * Signing proceeds as follows:
     * 1) Get the bytes from the string to be signed.
     * 2) Compute a SHA-1 digest of these bytes.
     * 3) Copy these bytes into a byte array that is one byte longer than needed.
     *    The resulting byte array has its extra byte set to zero. This is because
     *    RSA works only on positive numbers. The most significant byte (in the 
     *    new byte array) is the 0'th byte. It must be set to zero.
     * 4) Create a BigInteger from the byte array.
     * 5) Encrypt the BigInteger with RSA d and n.
     * 6) Return to the caller a String representation of this BigInteger.
     * @param message a sting to be signed
     * @return a string representing a big integer - the encrypted hash.
     * @throws Exception 
     */
    private String sign(String message) throws Exception {

        byte[] bytesOfMessage = message.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bigDigest = md.digest(bytesOfMessage);
        byte[] messageDigest = new byte[bigDigest.length + 1];
        messageDigest[0] = 0;
        for(int i = 0; i < bigDigest.length; i++) {
            messageDigest[i+1] = bigDigest[i];    
        }
        BigInteger m = new BigInteger(messageDigest);         
        // encrypt the digest with the private key
        BigInteger c = m.modPow(d, n);          
        // return this as a big integer string
        return c.toString();
    }   
}


