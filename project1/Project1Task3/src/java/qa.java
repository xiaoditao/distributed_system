
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author xiaoditao
 */
// set the url pattern to let the jsp come to this servlet
@WebServlet(urlPatterns = {"/submit" ,"/getResults","/aaa"})

public class qa extends HttpServlet {
    // set the instance of the model
    qaModel qm = null;
    @Override
    public void init() {
        qm=new qaModel();
    }
    @Override
    // the doGet method deal with the default condition or the condition that the 
    // user type "/getResults" in the url
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextView;
        String url=request.getServletPath();
        // if the user type "/getResults" then get the results of each choice
        // which stored in the model and clear it
        if(url.equals("/getResults")){
            String a = qm.res[0]+"";
            String b = qm.res[1]+"";
            String c = qm.res[2]+"";
            String d = qm.res[3]+"";
            request.setAttribute("answerA", a);
            request.setAttribute("answerB", b);
            request.setAttribute("answerC", c);
            request.setAttribute("answerD", d);
            nextView="result.jsp";
            qm.clearInt();
        }
        else{
            nextView="submit.jsp";
            request.setAttribute("answer", "");
        }
        // go to the next page that the user choose
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
        
    // if the user click the submit button, then it will go to this doPost method
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get the result from the user, if the user
        // did not input anything, then show "nothing" to the user
        String answer ="";
        if(request.getParameter("choice")==null){
            answer = "nothing";
        }else{
            answer=request.getParameter("choice");
        }
        request.setAttribute("answer", answer);
        // use method in the model to increase the number of the choice
        qm.add(answer); 
        String nextView;
        // after click the submit button, go to the submit page
        nextView="submit.jsp";
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);      
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

