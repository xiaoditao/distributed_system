/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2task2;
/**
 *
 * @author xiaoditao
 * This is a UDP client that can do the sum of the first 100 integers,
 * and get the result back.
 */
import java.net.*;
import java.io.*;
/**
 * The class SumEchoClientUDP can do the sum of the first 100 integers,
 * and get the result back.
 */
public class SumEchoClientUDP{
    /**
     * 
     * @param args 
     * This is the main method of the class, it is the entrance and it will 
     * call the add method for 100 times.
     */
    public static void main(String args[]){ 
        System.out.println("Client running");
        // call the add method for 100 times, send each time the value to sum up
        SumEchoClientUDP echoClientUDP = new SumEchoClientUDP();
        for (int i = 1; i < 101; i++) {
            echoClientUDP.add(i);
        }    
    }
    /**
     * This method will get an Integer as input, send the integer to the server 
     * in order to sum it up
     * @param i is the value we send it to the server to do the sum
     */
    private void add(int i){
        // Set up the socket.
	DatagramSocket aSocket = null;
	try {
                // Set up the socket, host and port as 6789.
		InetAddress aHost = InetAddress.getByName("localhost");
		int serverPort = 6789;
		aSocket = new DatagramSocket();
                String transform = Integer.toString(i);
                // Put the value into byte array.
                byte [] message = transform.getBytes();
                DatagramPacket request = new DatagramPacket(message,  message.length, aHost, serverPort);
                // Send the data to the server.
                aSocket.send(request);
                // Set an array to receive what is sent back by the server.
                byte[] buffer = new byte[9999];
  		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
                aSocket.receive(reply);
                // Set a new byte array with the right length
                byte[] correctBuffer = new byte[reply.getLength()];
                // Copy the data to the right array
                System.arraycopy(reply.getData(),0,correctBuffer,0,reply.getLength());
                String transformed = new String(correctBuffer);
                System.out.println("Sum: " + transformed);   
        // catch the exceptions
	}catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
	}catch (IOException e){
            System.out.println("IO: " + e.getMessage());
	}finally {
            if(aSocket != null) aSocket.close();
        }
    }    
}
