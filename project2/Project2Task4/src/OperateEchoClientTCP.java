/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xiaoditao
 * This is a TCP client that can do the operation of add or subtract,
 * and get the result back.
 */
import java.net.*;
import java.io.*;
/**
 *
 * @author xiaoditao
 * This is a TCP client that can do the operation of add or subtract,
 * and get the result back.
 */
public class OperateEchoClientTCP {
     /**
     * @param args 
     * This is the main method of the class, it is the entrance and it will 
     * do the operation of add or subtract.
     */
    public static void main(String args[]) {
        // Initialize the instance and call the operate method 
        System.out.println("Client running");
        OperateEchoClientTCP echoClientTCP = new OperateEchoClientTCP();
        echoClientTCP.operate();  
    }
     /** 
     * This method will
     * do the operation of add or subtract.
     */           
     private void operate()    {  
        // Set up the socket
        Socket clientSocket = null;
        try {
            // Set up the port
            int serverPort = 7777;
            clientSocket = new Socket("localhost", serverPort);
            // Set the buffer reader and let the user to type message.
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
            String message;
            String nextId;
            System.out.println("Please enter your id");
            // Continously receive the request from the client
            while ((nextId = typed.readLine()) != null) {
                // Print the message
                System.out.println("Please enter your operation");
                String nextOperation = typed.readLine();
                // If the operation is view, then it will not ask the client to type message
                if (nextOperation.equals("view")) {
                        message = nextId + ";" + nextOperation;
                    } else {
                        // If the operation is not view, then it will ask the client to type vlue, or the user can choose to quit
                        System.out.println("Please enter your value, or you can quit by entering 'quit'");
                        String nextValue = typed.readLine();
                        message = nextId + ";" + nextOperation + ";" + nextValue;
                    }  
                out.println(message);
                out.flush();
                // If the client want to quit,then quit the client side
                if (message.contains("quit")) {
                        System.out.println("I am quitting!");
                        break;
                    }
                // Read the data from the server
                String data = in.readLine(); 
                System.out.println("Received: " + data);
                System.out.println("Please enter your id");
            }
        // catch the exceptions
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

}
