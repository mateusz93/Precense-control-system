package neo.dmcs.old_servlet;

import java.io.IOException;
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
public class ProfileServlet {}

/*
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String type = request.getParameter("type");
        String ID = request.getParameter("ID");
        String group = request.getParameter("group");
        String phone = request.getParameter("phone");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String username = session.getAttribute("username").toString();
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        request.setAttribute("username", username);

        System.out.println("username: " + username);
        System.out.println("firstName: " + firstName);
        System.out.println("lastName: " + lastName);
        System.out.println("ID: " + ID);
        System.out.println("group: " + group);
        System.out.println("type: " + type);
        System.out.println("password: " + password);
        System.out.println("confirmPassword: " + confirmPassword);
        System.out.println("email: " + email);
        System.out.println("phone: " + phone);
        System.out.println("street: " + street);

        if ("".equals(password) || "".equals(confirmPassword)) {
            request.setAttribute("message", "Nie podałeś hasła!");
        } else if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Hasła są różne!");
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
                pst = conn.prepareStatement("SELECT * from Users WHERE login=?");
                System.out.println("doPost USERNAME: " + username);
                pst.setString(1, username);
                rs = pst.executeQuery();
                rs.next();
                System.out.println("AAAAAAAAAAAA: " + password);
                System.out.println("AAAAAAAAAAAA: " + rs.getString("password"));
                if (!password.equals(rs.getString("password"))) {
                    request.setAttribute("message", "Niepoprawne hasło!");
                } else {
                    pst = conn.prepareStatement("UPDATE Users SET firstName=?, lastName=? WHERE login=?");
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setString(3, username);
                    pst.executeUpdate();

                    PreparedStatement pst3 = null;
                    pst3 = conn.prepareStatement("SELECT * from Users WHERE login=?");
                    pst3.setString(1, username);
                    rs = pst3.executeQuery();
                    rs.next();
                    int userID = rs.getInt("ID");

                    PreparedStatement pst2 = null;
                    pst2 = conn.prepareStatement("UPDATE Contacts SET phone=?, street=?, city=? WHERE userID=?");
                    pst2.setString(1, phone);
                    pst2.setString(2, street);
                    pst2.setString(3, city);
                    pst2.setInt(4, userID);
                    pst2.executeUpdate();
                    request.setAttribute("message", "Zaaktualizowano profil!");
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
                e.printStackTrace();
            }
        }
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");

        if ((session.getAttribute("username") == null) || (session.getAttribute("username") == "")) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

        String username = (String) session.getAttribute("username");
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
            System.out.println("doGet USERNAME: " + username);
            pst3.setString(1, username);
            rs = pst3.executeQuery();
            rs.next();
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String type = rs.getString("type");
            String password = rs.getString("password");
            int userID = rs.getInt("ID");

            PreparedStatement pst2 = null;
            pst2 = conn.prepareStatement("SELECT * from Contacts WHERE userID=?");
            pst2.setInt(1, userID);
            rs = pst2.executeQuery();
            rs.next();
            String group = rs.getString("group");
            String email = rs.getString("Email");
            String phone = rs.getString("phone");
            String street = rs.getString("street");
            String city = rs.getString("city");

            System.out.println("firstName: " + firstName);
            System.out.println("lastName: " + lastName);
            System.out.println("type: " + type);
            System.out.println("password: " + password);
            System.out.println("userID: " + userID);
            System.out.println("group: " + group);
            System.out.println("email: " + email);
            System.out.println("phone: " + phone);
            System.out.println("street: " + street);

            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("type", type);
            request.setAttribute("password", password);
            request.setAttribute("ID", userID);
            request.setAttribute("email", email);
            request.setAttribute("group", group);
            request.setAttribute("phone", phone);
            request.setAttribute("street", street);
            request.setAttribute("city", city);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
}
*/