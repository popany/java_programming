package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HelloForm")
public class HelloForm extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public HelloForm() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Cookie name = new Cookie("name", URLEncoder.encode(request.getParameter("name"), "UTF-8"));
        Cookie url = new Cookie("url", request.getParameter("url"));
        
        name.setMaxAge(60*60*24); 
        url.setMaxAge(60*60*24); 
        
        response.addCookie( name );
        response.addCookie( url );
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        String title = "Set Cookie Instance";
        String docType = "<!DOCTYPE html>\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>Site Name：</b>："
                + request.getParameter("name") + "\n</li>" +
                "  <li><b>Site URL：</b>："
                + request.getParameter("url") + "\n</li>" +
                "</ul>\n" +
                "</body></html>");
        }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
