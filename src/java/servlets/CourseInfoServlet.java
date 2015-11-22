package servlets;

import dao.Course;
import db.CourseDates;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class CourseInfoServlet extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<CourseDates> datesList = new ArrayList<CourseDates>();

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";
        
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            HttpSession session = request.getSession(true);
            int courseID = Integer.parseInt(request.getParameter("info"));
            
            pst = conn.prepareStatement("SELECT * from CourseDates WHERE courseID=?");
            pst.setInt(1, courseID);
            rs = pst.executeQuery();

            while (rs.next()) {
                CourseDates date = new CourseDates();
                date.setDate(rs.getDate("date"));
                date.setStartTime(rs.getTime("startTime"));
                date.setFinishTime(rs.getTime("finishTime"));
                datesList.add(date);
            }
             request.setAttribute("datesList", datesList);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();

        }
        
        doGet(request, response);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
         request.getRequestDispatcher("/courseDates.jsp").forward(request, response);
    }
}
