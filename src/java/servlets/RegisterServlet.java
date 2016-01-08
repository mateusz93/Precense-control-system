package servlets;

import dao.RegisterDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RegisterServlet extends HttpServlet {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    final String url = "jdbc:mysql://localhost:3306/";
    final String dbName = "data";
    final String driver = "com.mysql.jdbc.Driver";
    final String userName = "root";
    final String pass = "root";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        
        if (request.getParameter("newEmail") != null) {
            request.setAttribute("newEmail", request.getParameter("newEmail"));
        } else {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String type = request.getParameter("type");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String username = generateUsername(firstName, lastName);

            if ("".equals(password) || "".equals(confirmPassword)) {
                request.setAttribute("message", "Nie podałeś hasła!");
            } else if ("".equals(firstName) || "".equals(lastName) || "".equals(email) || "".equals(username)) {
                request.setAttribute("message", "Pole nie może być puste!");
            } else if (!validatePassword(password)) { 
                request.setAttribute("message", "Hasło musi składać się z 8-30 znaków, wielkiej i małej litery, cyfry i znaku specjalnego [!@#$%^&+=]");
            } else if (!"Student".equals(type) && !"Teacher".equals(type)) {
                request.setAttribute("message", "Niepoprawny typ! Dozwolone: Teacher, Student");
            } else if (RegisterDao.accept(firstName, lastName, type, email, username, password, confirmPassword)) {
                if (session != null) {
                    session.setAttribute("username", username);
                }
                request.setAttribute("message", "Konto poprawnie utworzone!");
            } else {
                request.setAttribute("message", "Hasła są różne!");
            }
        }
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
    
    private boolean validatePassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,30}$";
        return password.matches(pattern);
    } 
    
    private String generateUsername(String firstName, String lastName) {
        String username = firstName + lastName;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, pass);
            
            for (int i = 0; i < 100000; ++i) {
                pst = conn.prepareStatement("SELECT * from Users WHERE login=?");
                pst.setString(1, username + "" + i);
                rs = pst.executeQuery();
                if (!rs.next()) {
                   username = username + "" + i;
                   break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username;
    }   
}
