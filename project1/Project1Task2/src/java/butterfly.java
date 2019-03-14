
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
@WebServlet(urlPatterns = {"/butterfly"})
public class butterfly extends HttpServlet {
    // Set the instance of the model
    butterflyModel bm = null;
    @Override
    public void init() {
        bm = new butterflyModel();
    }
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        // if the user input species, get it
        String speciesToSearch = request.getParameter("searchedSpecies");
        // if the user input region, get it
        String regionToSearch = request.getParameter("region");
        request.setAttribute("reg",regionToSearch);
        // if the user input stage, get it
        String stageToSearch = request.getParameter("stage");
        String ua = request.getHeader("User-Agent");
        // decide if the user is using a mobile phone
        boolean mobile;
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }
        // set the nextview to switch to the next view
        String nextView;
        if (regionToSearch != null) {
            String picSize = (mobile) ? "mobile" : "desktop";
             // use the method in the model to get the picture's url
             String pictureURL = bm.doFlickrSearch(speciesToSearch, regionToSearch,stageToSearch,picSize);
             if(pictureURL==null){                
                 pictureURL = (String)null;
             }
             // set attributes of the picture's url
             request.setAttribute("pictureURL",pictureURL);
             String zongURL=bm.zong(speciesToSearch, regionToSearch,stageToSearch,picSize);
             request.setAttribute("zongURL",zongURL);
             nextView = "result.jsp";
        } else {
             nextView = "welcome.jsp";
        }
        // switch the screen based on the right page
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
