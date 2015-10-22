/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
import uk.ac.dundee.computing.aec.instagrim.stores.Profile;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "userProfile", urlPatterns = {"/userProfile","/userProfile/*","/userThumb/*","/userPic/*"})
@MultipartConfig

/**
 *
 * @author Think
 */
public class userProfile extends HttpServlet {

    Cluster cluster = null;
    private HashMap CommandsMap = new HashMap();
    
    public userProfile() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("userPic", 1);
        CommandsMap.put("userProfile", 2);
        CommandsMap.put("userThumb", 3);

    }

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
        String args[] = Convertors.SplitRequestPath(request);
        /*if (lg != null) {
            //String userName = lg.getUsername();
            Profile profile = user.getDetials(userName);
        }*/
        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            System.out.println("This one?");
            error("Bad Operator", response);
            return;
        }
        switch (command) {
            case 1:
                DisplayImage(Convertors.DISPLAY_PROCESSED,args[2], response);
                break;
            case 2:
                displayProfilePic(args[2], request, response);
                break;
            case 3:
                DisplayImage(Convertors.DISPLAY_THUMB,args[2],  response);
                break;
            default:
                error("Bad Operator", response);
        }
        Profile profile = user.getDetials(userName);
        //displayProfilePic(args[2], request, response);
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
    
    private void displayProfilePic(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        //Pic pic = tm.getProfilePic(User);
        java.util.LinkedList<Pic> lsPics = tm.getProfilePic(User);
        System.out.println("Got here");
        request.setAttribute("profilePic", lsPics);
        //RequestDispatcher rd = request.getRequestDispatcher("/UsersPics.jsp");
        
    }
    
    private void DisplayImage(int type,String Image, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
  
        
        Pic p = tm.getUserPic(type,java.util.UUID.fromString(Image));
        
        OutputStream out = response.getOutputStream();

        response.setContentType(p.getType());
        response.setContentLength(p.getLength());
        //out.write(Image);
        InputStream is = new ByteArrayInputStream(p.getBytes());
        BufferedInputStream input = new BufferedInputStream(is);
        byte[] buffer = new byte[8192];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            out.write(buffer, 0, length);
        }
        out.close();
    }
    
    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a na error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
        return;
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
                int pro = 1; //Tells me if profile pic, in this case aye
                String uuid = tm.insertPic(b, type, filename, username, pro);

                is.close();
                
             response.sendRedirect("/Instagrim/");
            }
            
           // RequestDispatcher rd = request.getRequestDispatcher("/upload.jsp");
           //  rd.forward(request, response);
        }

    }
}
