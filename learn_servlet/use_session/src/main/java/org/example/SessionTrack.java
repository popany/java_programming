package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SessionTrack")
public class SessionTrack extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        Date createTime = new Date(session.getCreationTime());
        Date lastAccessTime = new Date(session.getLastAccessedTime());
         
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    
        String title = "Servlet Use Session";
        Integer visitCount = Integer.valueOf(0);
        String visitCountKey = new String("visitCount");
        String userIDKey = new String("userID");
        String userID = new String("user-0");
        if(session.getAttribute(visitCountKey) == null) {
            session.setAttribute(visitCountKey, Integer.valueOf(0));
        }

    
        if (session.isNew()){
            session.setAttribute(userIDKey, userID);
        } else {
             visitCount = (Integer)session.getAttribute(visitCountKey);
             visitCount = visitCount + 1;
             userID = (String)session.getAttribute(userIDKey);
        }
        session.setAttribute(visitCountKey,  visitCount);
    
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    
        String docType = "<!DOCTYPE html>\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                 "<h2 align=\"center\">Session Info</h2>\n" +
                "<table border=\"1\" align=\"center\">\n" +
                "<tr bgcolor=\"#949494\">\n" +
                "  <th>Session Info</th><th>Value</th></tr>\n" +
                "<tr>\n" +
                "  <td>id</td>\n" +
                "  <td>" + session.getId() + "</td></tr>\n" +
                "<tr>\n" +
                "  <td>Create Time</td>\n" +
                "  <td>" +  df.format(createTime) + 
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>Last visit time</td>\n" +
                "  <td>" + df.format(lastAccessTime) + 
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>User ID</td>\n" +
                "  <td>" + userID + 
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>Visit Count</td>\n" +
                "  <td>" + visitCount + "</td></tr>\n" +
                "</table>\n" +
                "</body></html>"); 
    }
}
