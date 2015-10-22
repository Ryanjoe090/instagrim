/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Profile;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "userProfile", urlPatterns = {"/userProfile","/userProfile/*"})
/**
 *
 * @author Think
 */
public class userProfile extends HttpServlet {

    Cluster cluster = null;

    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String args[] = Convertors.SplitRequestPath(request);
        displayProfile(args[2], request, response);
        
    }
    
    private void displayProfile(String userName, HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        User user = new User();
        user.setCluster(cluster);
        HttpSession session = request.getSession();
        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
        /*if (lg != null) {
            //String userName = lg.getUsername();
            Profile profile = user.getDetials(userName);
        }*/
        Profile profile = user.getDetials(userName);
        request.setAttribute("username", userName);
        request.setAttribute("firstName", profile.getFirstName());
        request.setAttribute("secondName", profile.getSecondName());
        request.setAttribute("email", profile.getEmail());
        request.setAttribute("street", profile.getStreet());
        request.setAttribute("city", profile.getCity());
        request.setAttribute("postCode", profile.getPostCode());
        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
        rd.forward(request, response);
    }
    
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        for (Part part : request.getParts()) {
            System.out.println("Part Name " + part.getName());

            String type = part.getContentType();
            String filename = part.getSubmittedFileName();
            
            InputStream is = request.getPart(part.getName()).getInputStream();
            int i = is.available();
            HttpSession session=request.getSession();
            LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
            String username="majed";
            if (lg.getlogedin()){
                username=lg.getUsername();
            }
            if (i > 0) {
                byte[] b = new byte[i + 1];
                is.read(b);
                System.out.println("Length : " + b.length);
                PicModel tm = new PicModel();
                tm.setCluster(cluster);
                String uuid = tm.insertPic(b, type, filename, username);

                is.close();
                
             response.sendRedirect("/Instagrim/Image/"+uuid);
            }
            
           // RequestDispatcher rd = request.getRequestDispatcher("/upload.jsp");
           //  rd.forward(request, response);
        }

    }
}
