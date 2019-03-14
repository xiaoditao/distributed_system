
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(urlPatterns = {"/ComputeHashes"})
public class ComputeHashes extends HttpServlet {
    // set instance of the model
    ComputeHashesModel chm =null;
    @Override
    public void init() {
        chm = new ComputeHashesModel();
    }
    // This servlet will reply to HTTP GET requests via this doGet method
     @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        // get the string needed to be converted
        String stringToConvert = request.getParameter("convertString");
        String ua = request.getHeader("User-Agent");
        // determine whether the device is a mobile phone
        boolean mobile;
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            // use the doc type to make it adapt to mobile phones
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }
        String nextView;
        // if the user type things in the text field, convert it, if not, go to the index page
        if (stringToConvert != null) {
            String picSize = (mobile) ? "mobile" : "desktop";
            String algorithm = request.getParameter("convertMethod");
            String[] stringConverted = null;
            try {
                stringConverted = chm.doConvert(stringToConvert, algorithm);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ComputeHashes.class.getName()).log(Level.SEVERE, null, ex);
            }
            // use the method doConvert in the model to convert the String, and return 2 outcomes
            request.setAttribute("stringConverted1",stringConverted[0]);
            request.setAttribute("stringConverted2",stringConverted[1]);
            // if the input is not null, go to the result page
            nextView = "result.jsp";
        } else {
            // if the input is null, go the the index page
            nextView = "index.jsp";
        }
        // go to the correct view
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}
