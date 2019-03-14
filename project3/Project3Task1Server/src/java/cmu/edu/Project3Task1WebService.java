
package cmu.edu;

import static cmu.edu.BlockChain.list;
import javax.jws.WebService;


/**
 *
 * @author xiaoditao
 */
@WebService(serviceName = "Project3Task1WebService")
public class Project3Task1WebService {
    /*
    boolean, wether the chain is valid
    */
    Boolean isValidBoolean;
    /*
    blockChain, initialize a block chain
    */
    BlockChain blockChain = new BlockChain();
    /*
    if the client input one, the method takes transction and difficulty and initialize the object of block
    @return long, the time spent
    */
    public long inputOne (String transactionInput, int difficultyInputInt){
        Block blockAdd = new Block(list.size(), blockChain.getTime(), transactionInput, difficultyInputInt);
        long start = System.currentTimeMillis();
        blockChain.addBlock(blockAdd);
        // time spent
        long end = System.currentTimeMillis();
        long timeAdd = end - start;
        return timeAdd;
    }
    /*
    if the client input one, the method calls isChainvalid to decide if the chain is valid
    @return long, the time spent
    */
    public long inputTwoTime() {
        long start = System.currentTimeMillis();
        isValidBoolean = blockChain.isChainvalid();
        // time spent
        long end = System.currentTimeMillis();
        long timeAdd = end - start;
        return timeAdd;
    }
    /*
    if the client input one, the method calls isChainvalid to decide if the chain is valid
    @return boolean, if it is valid
    */
    public boolean getIsValid() {
        return isValidBoolean;
    }
    /*
    get the toString result 
    @return string, toString result 
    */
    public String getString() {
        return blockChain.toString();
    }
}
 