/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu.edu;

import static cmu.edu.BlockChain.list;
import javax.jws.WebService;


/**
 *
 * @author xiaoditao
 */
@WebService(serviceName = "Project3Task2Service")
public class Project3Task2Service {
    /*
    boolean, wether the chain is valid
    */
    Boolean isValidBoolean;
    /*
    blockChain, initialize a block chain
    */
    BlockChain blockChain = new BlockChain();
    /*
    the operation method takes the csv from client, decide what client want to do
    @reuturn String, string that shoud be printed
    */
    public String operation(String input) {
        // split the message sent from the client
        String[] stringSplitted = input.split(",");
        String res;
        res = "";
        // compute the time
        long start;
        long end;
        long timeAdd;
        // switch the different input from the user
        switch(stringSplitted[0]) {
            // if user want to add a block, call addBlock method, return what should be print back to client
            case "1":
                Block blockAdd = new Block(list.size(), blockChain.getTime(), stringSplitted[1], Integer.valueOf(stringSplitted[2]));
                start = System.currentTimeMillis();
                blockChain.addBlock(blockAdd);
                end = System.currentTimeMillis();
                timeAdd = end - start;
                res = "Total execution time to add this block was " + timeAdd + " milliseconds";
                break;
            // if user want to verify the chain, call isChainValid method, return what should be print back to client
            case "2":
                start = System.currentTimeMillis();
                isValidBoolean = blockChain.isChainvalid();
                end = System.currentTimeMillis();
                timeAdd = end - start;
                res = "Verifying entire chain" + "\n" + "Chain verification:" + isValidBoolean + "\n" + 
                        "Total execution time to verify the chain was " + timeAdd + " milliseconds";
                break;
            // if user want to view the chain, call toString method, return what should be print back to client
            case "3": 
                res = "View the BlockChain" + "\n" + blockChain.toString();
                break;
            // if user want to break, the quit
            case "6":
                break;
            default:
                break;
        }
        return res;
    }
}
