package servlets;

import db.CourseDates;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mateusz Wieczorek
 *
 */
@WebServlet(name = "EditCourseDateServlet", urlPatterns = {"/EditCourseDateServlet"})
public class EditCourseDateServlet extends HttpServlet {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    final String url = "jdbc:mysql://localhost:3306/";
    final String dbName = "data";
    final String driver = "com.mysql.jdbc.Driver";
    final String userName = "root";
    final String password = "root";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            
            int courseDateID = Integer.parseInt(request.getParameter("editID"));
            System.out.println("courseDateID" + courseDateID);
            
            pst = conn.prepareStatement("SELECT * from CourseDates WHERE ID=?");
            pst.setInt(1, courseDateID);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                request.setAttribute("date", rs.getDate("date"));
                request.setAttribute("startTime", rs.getTime("startTime"));
                request.setAttribute("finishTime", rs.getTime("finishTime"));
                request.setAttribute("ID", rs.getInt("ID"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DeleteCourseDateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteCourseDateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DeleteCourseDateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DeleteCourseDateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("/addCourseDate.jsp").forward(request, response);       
    }
    
}
