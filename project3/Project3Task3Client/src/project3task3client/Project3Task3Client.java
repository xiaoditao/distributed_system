
package project3task3client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
/**
 *
 * @author xiaoditao
 */

/*
class Result, it has a member variable value that can get value from server
*/
class Result {
    /*
    String that is gotten from server
    */
    String value;
    /*
    getter, get the value
    */
    public String getValue() {
        return value;
    }
    /*
    setter, set the value
    */
    public void setValue(String value) {
        this.value = value;
    }
}
/*
class of client
*/
public class Project3Task3Client {
    /*
    the main method of client
    aske the user to input what they want to do
    @return void
    */
    public static void main(String[] args) throws Exception{
        // print the menu
        Scanner sc = new Scanner(System.in);
        System.out.println("Block Chain Menu");
        System.out.println("1. Add a transaction to the blockchain.");
        System.out.println("2. Verify the blockchain.");
        System.out.println("3. View the blockchain.");
        System.out.println("6. Exit.");
        // continously print the menu, until the user quits
        while(sc.hasNextLine()) {          
            String input = sc.nextLine();         
            if (input.equals("1")) {
                String difficultyInput;
                System.out.println("Enter difficulty > 0");
                Scanner scDifficulty = new Scanner(System.in);
                difficultyInput = scDifficulty.nextLine();  
                int intDiff = Integer.valueOf(difficultyInput);
                // if input of difficulty is smaller than zero, ask the user to input again
                while(intDiff <= 0) {
                    System.out.println("Enter difficulty > 0");
                    difficultyInput = scDifficulty.nextLine();     
                }
                System.out.println("Enter transaction");
                String transactionInput = scDifficulty.nextLine(); 
                // compute the time spent
                long start = System.currentTimeMillis();
                assignPost(difficultyInput, transactionInput);
                long end = System.currentTimeMillis();
                long timeAdd = end - start;
                System.out.println("Total execution time to add this block was " + timeAdd + " milliseconds");

            }
            // if input 2, user read method to get the result from doGet method
            if (input.equals("2")) {
                System.out.println(readGet("2"));
            }
            // if input 3, user read method to get the result from doGet method
            if (input.equals("3")) {
                System.out.println(readGet("3"));
            }
            // if input 6, quit
            if (input.equals("6")) {
                break;
            }
            System.out.println("1. Add a transaction to the blockchain.");
            System.out.println("2. Verify the blockchain.");
            System.out.println("3. View the blockchain.");
            System.out.println("6. Exit.");
        }
    }
    /*
    assign a string value to a string name (One character names for demo)
    */
    public static boolean assignPost(String difficultyInput, String transactionInput) {
        // We always want to be able to assign so we may need to PUT or POST.
        // Try to PUT, if that fails then try to POST
        if(doPut(difficultyInput,transactionInput) == 200) {
            return true;
        }
        else {
            if(doPost(difficultyInput,transactionInput) == 200) {  
                return true;
            }
        }
        return false;   
    }
    
    /*
    read a value associated with a name from the server
    return either the value read or an error message
    */
    public static String readGet(String inputChoice) {
        Result r = new Result();
        int status = 0;
        if((status = doGet(inputChoice,r)) != 200) return "Error from server "+ status;
        return r.getValue();
    } 

    /*
    Low level routine to make an HTTP POST request
    Note, POST does not use the URL line for its message to the server
    */
    public static int doPost(String difficultyInput, String transactionInput) {
        int status = 0;
        String output;
        // try to connect with the server
         try {
                URL url = new URL("http://localhost:8080/Project3Task3Server/Project3Task3ServerServlet/");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
            try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
                out.write(difficultyInput + ";" + transactionInput);
            }
                status = con.getResponseCode();
                con.disconnect();
         }
         catch (MalformedURLException e) {
         } catch (IOException e) {
         }
         return status;
    }
    /*
    Make an HTTP GET passing the name on the URL line 
    */    
     public static int doGet(String input, Result r) {         
         r.setValue("");
         String response = "";
         HttpURLConnection conn;
         int status = 0;       
         try {      
                // pass the name on the URL line
		URL url = new URL("http://localhost:8080/Project3Task3Server/Project3Task3ServerServlet/" + "//"+input);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
                // tell the server what format we want back
		conn.setRequestProperty("Accept", "text/plain");
 	
                // wait for response
                status = conn.getResponseCode();
                
                // If things went poorly, don't try to read any response, just return.
		if (status != 200) {
                    // not using msg
                    String msg = conn.getResponseMessage();
                    return conn.getResponseCode();
                }
                String output;
                // things went well so let's read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
 		
		while ((output = br.readLine()) != null) {
			response += output;
		}
		conn.disconnect();
 
	    } 
                catch (MalformedURLException e) {
	    }   catch (IOException e) {
            }      
         // return value from server 
         // set the response object
         r.setValue(response);
         // return HTTP status to caller
         return status;
    } 
     
    
     /*
     Low level routine to make an HTTP PUT request
     Note, PUT does not use the URL line for its message to the server
     */
     public static int doPut(String difficultyInput, String transactionInput) {
         int status = 0;
         // try to connect the server
         try {
                URL url = new URL("http://localhost:8080/Project3Task3Server/Project3Task3ServerServlet/");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                //con.setRequestProperty("Accept", "text/xml");
                con.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                // send the message
                out.write(difficultyInput + ";" + transactionInput);
                out.close();
                status = con.getResponseCode();
                con.disconnect();
         }
         catch (MalformedURLException e) {
         } catch (IOException e) {
         }
         return status;
     }     
}
