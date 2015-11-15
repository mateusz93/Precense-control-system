package servlets;

/**
 *
 * @author mateusz
 *
 * http://javaandj2eetutor.blogspot.com/2014/01/login-application-using-jsp-servlet-and.html
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

        String n = request.getParameter("username");
        String p = request.getParameter("userpass");
        String t = request.getParameter("type");

        //HttpSession session = request.getSession(false);
        HttpSession session = request.getSession(true);

        if (LoginDao.validate(n, p, t)) {
            if (session != null) {
                session.setAttribute("username", n);
            }
            //RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
            RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
            rd.forward(request, response);
        } else {
            out.print("<br><br><br>");
            out.print("<div class=\"container\">");
            out.print("<div class=\"alert alert-danger fade in\">");
            out.print("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
            out.print("<strong>Error:</strong> Bledny email lub haslo");
            out.print("</div>");
            out.print("</div>");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.include(request, response);
        }
        out.close();
    }
}
