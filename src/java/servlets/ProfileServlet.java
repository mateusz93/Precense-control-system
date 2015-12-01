package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mateusz Wieczorek
 *
 */
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("ISO-8859-2");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String pesel = request.getParameter("PESEL");
        String email = request.getParameter("email");
        String type = request.getParameter("type");
        String index = request.getParameter("index");
        String phone = request.getParameter("phone");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String username = request.getParameter("login");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        request.setAttribute("username", username);

        System.out.println("doPost");
        System.out.println("firstName: " + firstName);
        System.out.println("lastName: " + lastName);
        System.out.println("index: " + index);
        System.out.println("type: " + type);
        System.out.println("password: " + password);
        System.out.println("email: " + email);
        System.out.println("pesel: " + pesel);
        System.out.println("phone: " + phone);
        System.out.println("street: " + street);

        HttpSession session = request.getSession(true);
        if (password == null || confirmPassword == null) {
            out.print("<div class=\"container\">");
            out.print("<div class=\"alert alert-danger fade in\">");
            out.print("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
            out.print("<strong>Błąd!</strong> Nie podales hasła");
            out.print("</div>");
            out.print("</div>");
        } else if (!password.equals(confirmPassword)) {
//             String message = "Example source code of Servlet to JSP communication."; 
//             request.setAttribute("message", message); 
        } else {
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;

            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "data";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String pass = "root";

            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, pass);
                pst = conn.prepareStatement("UPDATE Users SET firstName=?, lastName=?, `index`=? WHERE login=?");
                pst.setString(1, firstName);
                pst.setString(2, lastName);
                pst.setString(3, index);
                pst.setString(4, username);
                pst.executeUpdate();

                PreparedStatement pst3 = null;
                pst3 = conn.prepareStatement("SELECT * from Users WHERE login=?");
                pst3.setString(1, username);
                rs = pst3.executeQuery();
                rs.next();
                int userID = rs.getInt("ID");

                PreparedStatement pst2 = null;
                pst2 = conn.prepareStatement("UPDATE Contacts SET PESEL=?, phone=?, street=?, city=? WHERE userID=?");
                pst2.setString(1, pesel);
                pst2.setString(2, phone);
                pst2.setString(3, street);
                pst2.setString(4, city);
                pst2.setInt(5, userID);
                pst2.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            out.print("<div class=\"container\">");
            out.print("<div class=\"alert alert-success fade in\">");
            out.print("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
            out.print("<strong>Gotowe!</strong> Dane zaaktualizowane");
            out.print("</div>");
            out.print("</div>");

        }
        out.close();
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        
        if ((session.getAttribute("username") == null) || (session.getAttribute("username") == "")) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

        String username = session.getAttribute("username").toString();

        System.out.println("username: " + username);

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String pass = "root";

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, pass);

            PreparedStatement pst3 = null;
            pst3 = conn.prepareStatement("SELECT * from Users WHERE login=?");
            pst3.setString(1, username);
            rs = pst3.executeQuery();
            rs.next();
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            int index = rs.getInt("index");
            String type = rs.getString("type");
            String password = rs.getString("password");
            int userID = rs.getInt("ID");

            PreparedStatement pst2 = null;
            pst2 = conn.prepareStatement("SELECT * from Contacts WHERE userID=?");
            pst2.setInt(1, userID);
            rs = pst2.executeQuery();
            rs.next();
            String email = rs.getString("Email");
            String pesel = rs.getString("PESEL");
            String phone = rs.getString("phone");
            String street = rs.getString("street");
            String city = rs.getString("city");

            System.out.println("doGet");
            System.out.println("firstName: " + firstName);
            System.out.println("lastName: " + lastName);
            System.out.println("index: " + index);
            System.out.println("type: " + type);
            System.out.println("password: " + password);
            System.out.println("userID: " + userID);
            System.out.println("email: " + email);
            System.out.println("pesel: " + pesel);
            System.out.println("phone: " + phone);
            System.out.println("street: " + street);

            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("index", index);
            request.setAttribute("type", type);
            request.setAttribute("password", password);
            request.setAttribute("userID", userID);
            request.setAttribute("email", email);
            request.setAttribute("pesel", pesel);
            request.setAttribute("phone", phone);
            request.setAttribute("street", street);
            request.setAttribute("city", city);

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
}
