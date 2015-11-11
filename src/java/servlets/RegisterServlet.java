/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.RegisterDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mateusz
 */
public class RegisterServlet extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String firstName = request.getParameter("firstName");  
        String lastName = request.getParameter("lastName");  
        String type = request.getParameter("type");  
        String email = request.getParameter("email");
        String username = request.getParameter("username");    
        String password = request.getParameter("password"); 
        String confirmPassword = request.getParameter("confirmPassword");  

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.setAttribute("username", username);
        }
       
        if(RegisterDao.accept(firstName, lastName, type, email, username, password, confirmPassword)) {
            out.print("<div class=\"container\">");
            out.print("<div class=\"alert alert-success fade in\">");
            out.print("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
            out.print("<strong>Success!</strong> Konto utworzone");
            out.print("</div>");
            out.print("</div>");
            
        } else {
            out.print("<div class=\"container\">");
            out.print("<div class=\"alert alert-danger fade in\">");
            out.print("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
            out.print("<strong>Error:</strong> Bledny email lub haslo");
            out.print("</div>");
            out.print("</div>");
        }
        RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
        rd.include(request, response);
        out.close();
    
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    }
}
