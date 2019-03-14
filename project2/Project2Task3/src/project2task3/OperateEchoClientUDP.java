/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2task3;
/**
 *
 * @author xiaoditao
 * This is a UDP client that can do the operation of add or subtract,
 * and get the result back.
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;
public class OperateEchoClientUDP{
    /**
     * @param args 
     * This is the main method of the class, it is the entrance and it will 
     * do the operation of add or subtract.
     */
    public static void main(String args[]){ 
	// Initialize the instance and call the operate method 
        System.out.println("Client running");
        OperateEchoClientUDP echoClientUDP = new OperateEchoClientUDP();
            echoClientUDP.operate();   
    }
    
    private void operate(){
        // Set up the socket
	DatagramSocket aSocket = null;   
	try {
                // Set up the host
		InetAddress aHost = InetAddress.getByName("localhost");
		int serverPort = 6789;
		aSocket = new DatagramSocket();
                // Set the scanner and let the user to type message.
		Scanner sc = new Scanner(System.in);
                System.out.println("Please enter your id");
		while (sc.hasNext()) {
                    // Continously receive the request from the client
                    String pass = "";
                    String nextId = sc.nextLine();
                    // Print the message
                    System.out.println("Please enter your operation");
                    String nextOperation = sc.nextLine();
                    // If the operation is view, then it will not ask the client to type message
                    if (nextOperation.equals("view")) {
                        pass = nextId + ";" + nextOperation;
                    } else {
                    // If the operation is not view, then it will ask the client to type vlue, or the user can choose to quit
                        System.out.println("Please enter your value, or you can quit by entering 'quit'");
                        String nextValue = sc.nextLine();
                        pass = nextId + ";" + nextOperation + ";" + nextValue;
                    }
                    byte [] message = pass.getBytes();	
		    DatagramPacket request = new DatagramPacket(message,  message.length, aHost, serverPort);
  		    aSocket.send(request);
                    // If the client want to quit,then quit the client side
                    if (pass.contains("quit")) {
                        System.out.println("I am quitting!");
                        break;
                    }
                    // Set an array to receive what is sent back by the server.
                    byte[] buffer = new byte[9999];
  		    DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
                    aSocket.receive(reply);
                     // Set a new byte array with the right length
                    byte[] correctBuffer = new byte[reply.getLength()];
                    // Copy the data to the right array
                    System.arraycopy(reply.getData(),0,correctBuffer,0,reply.getLength());
                    String transformed = new String(correctBuffer);
                    System.out.println("Reply: " + transformed);  
                    System.out.println("Please enter your id");
                }
        // catch the exceptions
	}catch (SocketException e) {System.out.println("Socket: " + e.getMessage());
	}catch (IOException e){System.out.println("IO: " + e.getMessage());
	}finally {if(aSocket != null) aSocket.close();}
    }    
}
