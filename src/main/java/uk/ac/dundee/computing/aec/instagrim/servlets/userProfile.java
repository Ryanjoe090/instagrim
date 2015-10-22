/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
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

        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
        rd.forward(request, response);
    }
}
