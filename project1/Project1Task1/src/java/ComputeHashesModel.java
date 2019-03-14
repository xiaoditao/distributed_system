
/**
 *
 * @author xiaoditao
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class ComputeHashesModel {
    // use the method to convert the input
    public String[] doConvert(String stringToConvert,String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        String[] res = new String[2];
        try {
        // use string array to get the 2 results from the converting process
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hashedBytes = digest.digest(stringToConvert.getBytes("UTF-8"));
        res[0] = "Base64:"+"\r\n"+javax.xml.bind.DatatypeConverter.printBase64Binary(hashedBytes);
        res[1] = "hexadecimal:"+"\n"+javax.xml.bind.DatatypeConverter.printHexBinary(hashedBytes);
        return res;
    } catch (NoSuchAlgorithmException ex) {
        System.out.println("There is an error.");
        }
        return null;
    }
}
