
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Project3Task3ServerServlet", urlPatterns = {"/Project3Task3ServerServlet/*"})
/*
the server class
*/
public class Project3Task3ServerServlet extends HttpServlet {
    /*
    boolean, wether the chain is valid
    */
    Boolean isValidBoolean;
    /*
    blockChain, initialize a block chain
    */
    BlockChain blockChain = new BlockChain();
    
    /*
    GET returns a string to the client
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Console: doGET visited");
        String result;
        // The name is on the path /name so skip over the '/'
        String input = (request.getPathInfo()).substring(1);
        // if input is 2, call the isChainValid method
        if(input.equals("2")) {
            PrintWriter out = response.getWriter();
            long start = System.currentTimeMillis();
            isValidBoolean = blockChain.isChainvalid();
            long end = System.currentTimeMillis();
            long timeAdd = end - start;
            result = "Chain verification:" + isValidBoolean + " "+
                    "Total execution time to verify the chain was " + timeAdd + " milliseconds";
            // sent back the result string to the client
            out.println(result);
            return;
        }
        // if input is 3, call the toString method
        if(input.equals("3")) {
            PrintWriter out = response.getWriter();
            result = blockChain.toString();
            // sent back the result string to the client
            out.println(result);
            return;
        }
        // if input is null, return 404
        if(input.equals("") || input == null) {
            response.setStatus(401);
            return;
        }
        
        // Things went well so set the HTTP response code to 200 OK
        response.setStatus(200);
        // tell the client the type of the response
        response.setContentType("text/plain;charset=UTF-8");
        
    }
    
    /*
    POST is used to create a new variable
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Console: doPost visited");
        // To look at what the client accepts examine request.getHeader("Accept")
        // We are not using the accept header here.
        // Read what the client has placed in the POST data area
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String data = br.readLine();
        String[] dataSplit = data.split(";");
        String getDifficulty = dataSplit[0];
        String getTransaction = dataSplit[1];
        if(getDifficulty.equals("") || getTransaction.equals("")) {
            // missing input return 401
            response.setStatus(401);
            return;
        }
        // initialize the block and call the add block method
        PrintWriter out = response.getWriter();
        Block blockAdd = new Block(blockChain.list.size(), blockChain.getTime(),getTransaction, Integer.valueOf(getDifficulty));
        blockChain.addBlock(blockAdd);
        return;
    }
    
    /* update variables */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Console: doPut visited");
        // Read what the client has placed in the PUT data area
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String data = br.readLine();
        // extract variable name from request data (variable names are a single character)
        String variableName = "" + data.charAt(0);
        // extract value after the equals sign
        String[] dataSplit = data.split(";");
        String getDifficulty = dataSplit[0];
        String getTransaction = dataSplit[1];
        if(getDifficulty.equals("") || getTransaction.equals("")) {
            // missing input return 401
            response.setStatus(401);
            return;
        }
        PrintWriter out = response.getWriter();
        Block blockAdd = new Block(BlockChain.list.size(), blockChain.getTime(),getTransaction, Integer.valueOf(getDifficulty));
        blockChain.addBlock(blockAdd);
    }  
}
