/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2task2;
/**
 *
 * @author xiaoditao
 * This is a UDP server that can do the sum of the first 100 integers,
 * and get the result back.
 */
import java.net.*;
import java.io.*;
/**
 * The class SumEchoClientUDP can do the sum of the first 100 integers,
 * and get the result back.
 */
public class SumEchoServerUDP{
    /**
     * 
     * @param args 
     * This is the main method of the class, it is the entrance and it will 
     * call the add method for 100 times.
     */
	public static void main(String args[]){ 
        // Print that the server is running
        System.out.println("Server running");
        // Set up the socket.
	DatagramSocket aSocket = null;
	byte[] buffer = new byte[9999];
	try{
                // Set up the socket as 6789.
		aSocket = new DatagramSocket(6789);
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);	
                int sum = 0;
                // Set a count to test if the number of the request reached to 100.
                int count = 0;
 		while(count < 100){
                    // Each time it go into the loop, it will add the count by 1
                    count++;
                    aSocket.receive(request);     
                    // Set up the byte array with the correct length
                    byte[] correctBuffer = new byte[request.getLength()];
                    // Copy the array to the array with the right length
                    System.arraycopy(request.getData(),0,correctBuffer,0,request.getLength());
                    String transformed = new String(correctBuffer);
                    int value = Integer.valueOf(transformed);
                    // add up the value
                    sum = sum + value;
                    String transform = Integer.toString(sum);
                    byte [] message = transform.getBytes();
                    // Set up the packet to contain the reply and send it back.
                    DatagramPacket reply = new DatagramPacket(message, 
                    message.length, request.getAddress(), request.getPort());
                    System.out.println("Echoing: "+ transform);
                    // Send back the data.
                    aSocket.send(reply);
		}
        // Handle the exceptions.
	}catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
	}catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
	}finally {if(aSocket != null) aSocket.close();
            }
    }
}
