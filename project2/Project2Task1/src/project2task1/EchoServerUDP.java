/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2task1;
/**
 *
 * @author xiaoditao
 * This is a UDP server that can let user manually send String to the server,
 * and get the same String back, and let the user see what is sent back.
 */
import java.net.*;
import java.io.*;
/**
 * The class EchoServerUDP can let user manually type a String to send to the server,
 * and echo the same String back.
 */
public class EchoServerUDP{
    /**
     * 
     * @param args 
     * This is the main method of the class, it is the entrance and it will get what the user typed
     * and send it back to the client
     */
	public static void main(String args[]){ 
        // Print out that the server is running.
        System.out.println("Server Running");
        // Set up a new datagram socket.
	DatagramSocket aSocket = null;
        // Set up a byte array.
	byte[] buffer = new byte[9999];   
	try{
                // Set the data socket.
		aSocket = new DatagramSocket(6789);
                // Set the datagram packet.
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                // Continouly listen to the request.
 		while(true){
                    // Receive the request.
                    aSocket.receive(request);     
                    // Set the byte array as the same length.
                    byte[] correctBuffer = new byte[request.getLength()];
                    // Copy the byte array to the arrya with the right length.
                    System.arraycopy(request.getData(),0,correctBuffer,0,request.getLength());
                    // Set the reply to  the datagram packet.
                    DatagramPacket reply = new DatagramPacket(correctBuffer, 
                    request.getLength(), request.getAddress(), request.getPort());
                    // Transform the data to String.
                    String requestString = new String(correctBuffer);
                    // If it receives 'quit', do quit.
                    if(requestString.equals("quit!")) break;
                    // Print the echoing word.
                    System.out.println("Echoing: "+requestString);
                    // Send back the echoing word.
                    aSocket.send(reply);
		}
        // Catch the socket exception.
	}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        // Catch the Io exception.
	}catch (IOException e) {System.out.println("IO: " + e.getMessage());
        // If not, go to the finally part.
	}finally {
            // If the socket is not null.
            if(aSocket != null) {
                // Print the message saying it is quit.
                System.out.println("I am quitting!!!!!");
                // Socket is closed.
                aSocket.close();
            }
        }
    }
}
