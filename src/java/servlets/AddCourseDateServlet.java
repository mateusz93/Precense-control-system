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
 * @author Mateusz Wieczorek
 *
 */
public class AddCourseDateServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        int courseID = Integer.parseInt(session.getAttribute("courseID").toString());
        System.out.println("course ID: " + courseID);

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        String username = session.getAttribute("username").toString();
        String date = request.getParameter("date");
        String startTime = request.getParameter("startTime");
        String finishTime = request.getParameter("finishTime");
        try {
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "data";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "root";
            
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            
            pst = conn.prepareStatement("INSERT INTO CourseDates (courseID, startTime, finishTime, date) VALUES (?, ?, ?, ?)");

            pst.setInt(1, courseID);
            pst.setString(2, startTime);
            pst.setString(3, finishTime);
            pst.setString(4, date);

            if (pst.executeUpdate() != 0) {
                System.out.println("Zapisano do bazy!");
            } else {
                System.out.println("Błąd przy zapisie do bazy");
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/addCourseDate.jsp").forward(request, response);
    }
}
