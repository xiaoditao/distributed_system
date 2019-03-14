/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2task3;

/**
 *
 * @author xiaoditao
 * This is a UDP server that can do the operation of add or subtract,
 * and get the result back.
 */
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
/**
 * The class SumEchoServerUDP can do the sum of the first 100 integers,
 * and get the result back.
 */
public class OperateEchoServerUDP{
        /**
         * @param args 
         * This is the main method of the class, it is the entrance and it will 
         * do the operation of add or subtract.
         */    
	public static void main(String args[]){ 
        // Print that the client is running
        System.out.println("Server running");
        // Set up the socket.
	DatagramSocket aSocket = null;
	byte[] buffer = new byte[9999];
        Map<String,Integer> map = new HashMap<>();
	try{
                // Set up the socket as 6789.
		aSocket = new DatagramSocket(6789);
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);	
                int sum;
                // If the socket continouly get the request, it will do the operation
 		while(true){
                    aSocket.receive(request);     
                    byte[] correctBuffer = new byte[request.getLength()];
                    // Copy the array to the array with the right length
                    System.arraycopy(request.getData(),0,correctBuffer,0,request.getLength());
                    String requestInfo = new String(correctBuffer);
                    // If the client want to quit,then quit the server side
                    if(requestInfo.contains("quit")) {
                        System.out.println("I am quitting!");
                        break;
                    }
                    // Split the message
                    // Check if the id can be found in the map, if it is in the map, then get the sum
                    // if not, set the sum as 0
                    String[] infoSplit = requestInfo.split(";");
                    if (map.containsKey(infoSplit[0])) {
                        sum = map.get(infoSplit[0]);
                    }else{
                        sum = 0;
                    }
                    String transform;
                    // Switch the operation, if it is add or subtract, it will send "OK"
                    // Or send the result back
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
                    // Set the packet and send data back to the client
                    byte [] m = transform.getBytes();
                    DatagramPacket reply = new DatagramPacket(m, 
                    m.length, request.getAddress(), request.getPort());
                    System.out.println("Echoing: "+ transform);
                    aSocket.send(reply);
		}
        // Catch exceptions       
	}catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
	}catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
	}finally {
            if(aSocket != null) aSocket.close();}
	}
}
