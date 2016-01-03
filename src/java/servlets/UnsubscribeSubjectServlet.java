package servlets;

import java.io.IOException;
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
 * @author mateusz
 */
public class UnsubscribeSubjectServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("message", "Wypisano z kursu");  // Will be available as ${message} in JSP
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        HttpSession session = request.getSession(true);
        
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            String studentName = session.getAttribute("username").toString();

            pst = conn.prepareStatement("SELECT ID from Users WHERE login=?");
            pst.setString(1, studentName);
            rs = pst.executeQuery();
            rs.next();
            int studentId = rs.getInt("ID");
            int courseId = Integer.parseInt(request.getParameter("unsubscribe"));
            
            pst = conn.prepareStatement("DELETE FROM StudentCourses WHERE teacherCourseID=? AND studentID=?");
            pst.setInt(1, courseId);
            pst.setInt(2, studentId);
            pst.executeUpdate();
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }

        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/coursesServlet").forward(request, response);
    }
}
