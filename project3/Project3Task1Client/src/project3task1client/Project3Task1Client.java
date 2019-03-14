package project3task1client;

/**
 *
 * @author xiaoditao
 */
import java.util.Scanner;
public class Project3Task1Client {
    
    
    /*
    the main method of client
    aske the user to input what they want to do
    @return void
    */
    public static void main(String[] args) {
        // show the menu
        Scanner sc = new Scanner(System.in);
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
                String difficultyInput = "";
                int difficultyInputInt = 0;
                System.out.println("Enter difficulty > 0");
                Scanner scDifficulty = new Scanner(System.in);
                difficultyInput = scDifficulty.nextLine();
                difficultyInputInt = Integer.valueOf(difficultyInput);
                while(difficultyInputInt <= 0) {
                    System.out.println("Enter difficulty > 0");
                    difficultyInput = scDifficulty.nextLine();
                    difficultyInputInt = Integer.valueOf(difficultyInput);
                }
                System.out.println("Enter transaction");
                String transactionInput = scDifficulty.nextLine();
                // call the inputOne method to calculate the time spent
                long timeAdd = inputOne(transactionInput, difficultyInputInt);
                System.out.println("Total execution time to add this block was " + timeAdd + " milliseconds");
            }
            // if the input is two, verify the chain
            if (input.equals("2")) {
                System.out.println("Verifying entire chain");
                long timeAdd = inputTwoTime();
                // call the getIsValid method to decide if the chain is valid
                Boolean res = getIsValid();
                // print result
                System.out.println("Chain verification:" + res);
                // print result
                System.out.println("Total execution time to verify the chain was " + timeAdd + " milliseconds");
            }
            // if the input is three, view the BlockChain
            if (input.equals("3")) {
                System.out.println("View the BlockChain");
                System.out.println(getString());
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
    if the client input one, the method calls isChainvalid to decide if the chain is valid
    @return boolean, if it is valid
    */
    private static boolean getIsValid() {
        cmu.edu.Project3Task1WebService_Service service = new cmu.edu.Project3Task1WebService_Service();
        cmu.edu.Project3Task1WebService port = service.getProject3Task1WebServicePort();
        return port.getIsValid();
    }
    /*
    get the toString result
    @return string, toString result
    */
    private static String getString() {
        cmu.edu.Project3Task1WebService_Service service = new cmu.edu.Project3Task1WebService_Service();
        cmu.edu.Project3Task1WebService port = service.getProject3Task1WebServicePort();
        return port.getString();
    }
    /*
    if the client input one, the method takes transction and difficulty and initialize the object of block
    @return long, the time spent
    */
    private static long inputOne(java.lang.String arg0, int arg1) {
        cmu.edu.Project3Task1WebService_Service service = new cmu.edu.Project3Task1WebService_Service();
        cmu.edu.Project3Task1WebService port = service.getProject3Task1WebServicePort();
        return port.inputOne(arg0, arg1);
    }
    /*
    if the client input one, the method calls isChainvalid to decide if the chain is valid
    @return long, the time spent
    */
    private static long inputTwoTime() {
        cmu.edu.Project3Task1WebService_Service service = new cmu.edu.Project3Task1WebService_Service();
        cmu.edu.Project3Task1WebService port = service.getProject3Task1WebServicePort();
        return port.inputTwoTime();
    }
}
