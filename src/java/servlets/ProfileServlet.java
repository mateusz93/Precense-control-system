/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.RegisterDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mateusz Wieczorek
 */
public class ProfileServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        HttpSession session = request.getSession(false);
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
                System.out.println(e);
                
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (pst != null) {
                    try {
                        pst.close();
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
        RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
        rd.forward(request, response);
        out.close();

    }
}
