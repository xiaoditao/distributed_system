/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xiaoditao
 * This is a TCP server that can do the operation of add or subtract,
 * and get the result back with the check of the id and the signature of the massage.
 */
import java.net.*;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * The class OperateEchoServerTCP can do the sum of the first 100 integers,
 * and get the result back with the check of the id and the signature of the massage.
 */
public class Hash_Sign_EchoServerTCP {
    public static void main(String args[]) throws Exception {
        Hash_Sign_EchoServerTCP echoServerTCP = new Hash_Sign_EchoServerTCP();
        System.out.println("Server running");
        Socket clientSocket = null;
        try {
            // the server port we are using 
            int serverPort = 7777; 
            // Create a new server socket
            ServerSocket listenSocket = new ServerSocket(serverPort);           
            /*
             * Block waiting for a new connection request from a client.
             * When the request is received, "accept" it, and the rest
             * the tcp protocol handshake will then take place, making 
             * the socket ready for reading and writing.
             */
            clientSocket = listenSocket.accept();
            // If we get here, then we are now connected to a client.           
            // Set up "in" to read from the client socket
            Scanner in;
            in = new Scanner(clientSocket.getInputStream());           
            // Set up "out" to write to the client socket
            PrintWriter out;
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));          
            /* 
             * Forever,
             *   read a line from the socket
             *   print it to the console
             *   echo it (i.e. write it) back to the client
             */   
            Map<String,Integer> map = new HashMap<>();
            int sum;
            // Switch the operation, if it is add or subtract, it will send "OK"
            // Or send the result back
            while (in.hasNext()) {
                String data = in.nextLine();
                // If the client want to quit, the server will quit
                if(data.contains("quit")) {
                        System.out.println("I am quitting!");
                        break;
                    }
                String[] infoSplit = data.split(";");
                int flag = 0;
                String enConcat = infoSplit[1] + infoSplit[2];
                String testId = echoServerTCP.ComputeSHA_256_as_Hex_String(enConcat);
                if(testId.equals(infoSplit[0])) { 
                    flag++;
                }
                // Concat the message and sign it
                String message = "";
                for(int i = 0; i < infoSplit.length-2; i++){
                    message = message  + infoSplit[i] + ";";   
                }
                message = message + infoSplit[infoSplit.length-2];   
                BigInteger big = new BigInteger(infoSplit[1]);   
                BigInteger big2 = new BigInteger(infoSplit[2]);
                if(echoServerTCP.verify(message.trim(), infoSplit[infoSplit.length-1].trim(), big, big2 )){       
                    flag++;
                }
                // If the flag is not 2, there is error
                if(flag != 2) {
                    out.println("Error in request");
                    out.flush();
                    break;
                }else{
                    if (map.containsKey(infoSplit[0])) {
                        sum = map.get(infoSplit[0]);
                    }else{
                        sum = 0;
                    }
                    String transform;
                    switch (infoSplit[3]){
                        case "add":
                            sum = sum + Integer.valueOf(infoSplit[4]);
                            map.put(infoSplit[0], sum);
                            transform = "OK";
                            break;
                        case "subtract": 
                            sum = sum - Integer.valueOf(infoSplit[4]);
                            map.put(infoSplit[0], sum);
                            transform = "OK";
                            break;
                        case "view":
                            transform = Integer.toString(sum);
                            break;
                        default:
                            transform = Integer.toString(sum);
                            break;
                    }                   
                System.out.println("Echoing: " + transform);
                out.println(transform);
                out.flush();
                }
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
    /** For verifying, a SignOrVerify object may be constructed 
    *   with a RSA's e and n. Only e and n are used for signature verification.
    */
    public static boolean verify(String messageToCheck, String encryptedHashStr, BigInteger e, BigInteger n)throws Exception  { 
        // Take the encrypted string and make it a big integer
        BigInteger encryptedHash = new BigInteger(encryptedHashStr);
        // Decrypt it
        BigInteger decryptedHash = encryptedHash.modPow(e, n);  
        // Get the bytes from messageToCheck
        byte[] bytesOfMessageToCheck = messageToCheck.getBytes("UTF-8");
        // compute the digest of the message with SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageToCheckDigest = md.digest(bytesOfMessageToCheck);
        // messageToCheckDigest is a full SHA-256 digest
        // take two bytes from SHA-256 and add a zero byte
        byte[] extraByte = new byte[messageToCheckDigest.length + 1];
        extraByte[0] = 0;
        for(int i = 0; i < messageToCheckDigest.length; i++) {
            extraByte[i+1] = messageToCheckDigest[i];    
        }    
        // Make it a big int
        BigInteger bigIntegerToCheck = new BigInteger(extraByte);
        // inform the client on how the two compare
        if(bigIntegerToCheck.compareTo(decryptedHash) == 0) {    
            return true;
        }
        else {
            return false;
        }
    }   
}

