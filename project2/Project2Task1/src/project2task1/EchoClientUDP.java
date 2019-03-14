/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2task1;
/**
 *
 * @author xiaoditao
 * This is a UDP client that can let user manually send String to the server,
 * and get the same String back, and let the user see what is sent back.
 */
import java.net.*;
import java.io.*;
/**
 * The class EchoClientUDP can let user manually type a String to send to the server,
 * and get the same String back.
 */
public class EchoClientUDP{
    /**
     * 
     * @param args 
     * This is the main method of the class, it is the entrance and it will let the user 
     * to type what they want to send to the server
     */
    public static void main(String args[]){ 
        // Print out that the client is running.
        System.out.println("Client Running");
        // Set up a new datagram socket.
	DatagramSocket aSocket = null;
	try {
                // Set up a host.
		InetAddress aHost = InetAddress.getByName("localhost");
                // Set the port as 6789.
		int serverPort = 6789;
                // Set up a datagram socket.
		aSocket = new DatagramSocket();
                // Set up a String to receive the next line the user typed.
		String nextLine;
                // Set up a buffered reader.
		BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
                // While the user type something, it will continously send it to the server.
		while ((nextLine = typed.readLine()) != null) {
                    // If the user enter "quit!", the client will quit.
                    if(nextLine.equals("quit!")) {
                        // Set up a byte array to contain the message the user typed.
                        byte [] message = nextLine.getBytes();
                        // Set up a data gram packet to contian the request.
                        DatagramPacket request = new DatagramPacket(message,  message.length, aHost, serverPort);
                        // Send the request to the server.
                        aSocket.send(request);
                        // Quit the loop.
                        break;
                    }else{
                        // Set up a byte array to contain the message the user typed.
                        byte [] message = nextLine.getBytes();	
                        // Set up a data gram packet to contian the request.
                        DatagramPacket request = new DatagramPacket(message,  message.length, aHost, serverPort);
                        // Send the request to the server.
                        aSocket.send(request);
                        // Set the byte array as the exact size as what is sent back.
                        byte[] buffer = new byte[message.length];
                        // Set data gram packet to receive the reply.
                        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
                        // Let the socket to receive the reply.
                        aSocket.receive(reply);
                        // Print out the reply.
                        System.out.println("Reply: " + new String(reply.getData()));
                    }
            }
        // Catch the socket exception.
	}catch (SocketException e) {System.out.println("Socket: " + e.getMessage());
        // Catch the Io exception.
	}catch (IOException e){System.out.println("IO: " + e.getMessage());
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
