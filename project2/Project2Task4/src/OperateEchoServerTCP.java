/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xiaoditao
 * This is a TCP server that can do the operation of add or subtract,
 * and get the result back.
 */
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * The class OperateEchoServerTCP can do the sum of the first 100 integers,
 * and get the result back.
 */
public class OperateEchoServerTCP {
        /**
         * @param args 
         * This is the main method of the class, it is the entrance and it will 
         * do the operation of add or subtract.
         */ 
    public static void main(String args[]) {
        Socket clientSocket = null;
        try {
            // the server port we are using
            int serverPort = 7777; 
            // Create a new server socket
            ServerSocket listenSocket = new ServerSocket(serverPort);
             // Block waiting for a new connection request from a client.
             // When the request is received, "accept" it, and the rest
             // the tcp protocol handshake will then take place, making 
             // the socket ready for reading and writing.
            clientSocket = listenSocket.accept();
            // If we get here, then we are now connected to a client.   
            // Set up "in" to read from the client socket
            Scanner in;
            in = new Scanner(clientSocket.getInputStream());
            // Set up "out" to write to the client socket
            PrintWriter out;
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
             //Forever,
             //read a line from the socket
             //print it to the console
             //echo it (i.e. write it) back to the client
            Map<String,Integer> map = new HashMap<>();
            int sum = 0;
            // Switch the operation, if it is add or subtract, it will send "OK"
            // Or send the result back
            while (true) {
                String data = in.nextLine();
                if(data.contains("quit")) {
                        System.out.println("I am quitting!");
                        break;
                    }
                String[] infoSplit = data.split(";");
                    if (map.containsKey(infoSplit[0])) {
                        sum = map.get(infoSplit[0]);
                    }else{
                        sum = 0;
                    }
                    String transform = "";
                    switch (infoSplit[1]){
                        case "add":
                            sum = sum + Integer.valueOf(infoSplit[2]);
                            map.put(infoSplit[0], sum);
                            transform = "OK";
                            break;
                        case "subtract": 
                            sum = sum - Integer.valueOf(infoSplit[2]);
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
                // Print the message we send back 
                System.out.println("Echoing: " + transform);
                out.println(transform);
                out.flush();
            }   
        // Handle exceptions
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());    
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // ignore exception on close
            }
        }
    }
}
