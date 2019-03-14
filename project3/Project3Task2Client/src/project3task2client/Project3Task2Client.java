
package project3task2client;

import java.util.Scanner;

/**
 *
 * @author xiaoditao
 */
public class Project3Task2Client {
    
    /*
    the main method of client
    aske the user to input what they want to do
    @return void
    */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // print the menu
        System.out.println("Block Chain Menu");
        System.out.println("1. Add a transaction to the blockchain.");
        System.out.println("2. Verify the blockchain.");
        System.out.println("3. View the blockchain.");
        System.out.println("6. Exit.");
        // continously print the menu, until the user quits
        while(sc.hasNextLine()) {
            String input = sc.nextLine();
            // if the input is one, add a transaction to the blockchain
            if (input.equals("1")) {
                String difficultyInput;
                System.out.println("Enter difficulty > 0");
                Scanner scDifficulty = new Scanner(System.in);
                difficultyInput = scDifficulty.nextLine();
                int intDiff = Integer.valueOf(difficultyInput);
                while(intDiff <= 0) {
                    System.out.println("Enter difficulty > 0");
                    difficultyInput = scDifficulty.nextLine();
                }
                // send the csv format to server, the "," makes up csv format
                System.out.println("Enter transaction");
                String transactionInput = scDifficulty.nextLine();
                String res = "1" + "," + transactionInput + "," + difficultyInput;
                System.out.println(operation(res));
            }
            // if the input is two, verify the chain
            if (input.equals("2")) {
                // send the csv format to server, the "," makes up csv format
                String res = "2" + "," + " " + "," + " ";
                System.out.println(operation(res));
            }
            // if the input is three, view the BlockChain
            if (input.equals("3")) {
                // send the csv format to server, the "," makes up csv format
                String res = "3" + "," + " " + "," + " ";
                System.out.println(operation(res));
            }
            // if the input is six, quit
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
    the operation method takes the csv from client, decide what client want to do
    @reuturn String, string that shoud be printed
    */    
    private static String operation(java.lang.String arg0) {
        cmu.edu.Project3Task2Service_Service service = new cmu.edu.Project3Task2Service_Service();
        cmu.edu.Project3Task2Service port = service.getProject3Task2ServicePort();
        return port.operation(arg0);
    }
}
