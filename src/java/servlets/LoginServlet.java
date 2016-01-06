package servlets;

/**
 *
 * http://javaandj2eetutor.blogspot.com/2014/01/login-application-using-jsp-servlet-and.html
 *
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mateusz Wieczorek
 *
 */
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String e = request.getParameter("email");
        String p = request.getParameter("userpass");
        String t = request.getParameter("type");

        System.out.println("Type: " + t);

        HttpSession session = request.getSession(true);
        session.setAttribute("type", t);

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            pst = conn.prepareStatement("select userID from Contacts where email=?");
            pst.setString(1, e);
            rs = pst.executeQuery();
            rs.next();
            int userID = rs.getInt("userID");

            pst = conn.prepareStatement("select * from Users where ID=? and password=? and type=?");
            pst.setInt(1, userID);
            pst.setString(2, p);
            pst.setString(3, t);
            rs = pst.executeQuery();
            rs.next();
            String username = rs.getString("login");
            
            if (LoginDao.validate(e, p, t)) {
                if (session != null) {
                    session.setAttribute("username", username);
                }
                RequestDispatcher rd = request.getRequestDispatcher("redirect.jsp");
                rd.forward(request, response);
            } else {
                out.print("<div class=\"container\">");
                out.print("<div class=\"alert alert-danger fade in\">");
                out.print("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
                out.print("<strong>Bledny email lub haslo!</strong>");
                out.print("</div>");
                out.print("</div>");
                request.getRequestDispatcher("login.jsp").include(request, response);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException exception) {
            exception.printStackTrace();
        }
        out.close();
        //  response.sendRedirect("/WebApplication/login.jsp");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
