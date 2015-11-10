package servlets;

/**
 *
 * @author mateusz
 * 
 *  http://javaandj2eetutor.blogspot.com/2014/01/login-application-using-jsp-servlet-and.html
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.LoginDao;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String n=request.getParameter("username");    
        String p=request.getParameter("userpass"); 

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.setAttribute("name", n);
        }
       
        if (LoginDao.validate(n, p)) {
            RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
            rd.forward(request, response);
        } else {
            out.print("<div class=\"container\">");
            out.print("<div class=\"alert alert-danger fade in\">");
            out.print("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
            out.print("<strong>Error:</strong> Bledny email lub haslo");
            out.print("</div>");
            out.print("</div>");
            
           //out.print("<center><p style=\"color:red\">Sorry username or password error</p></center>");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.include(request, response);
        }
        out.close();
    }
}
