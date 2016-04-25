package neo.dmcs.old_servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Mateusz Wieczorek
 * 
 */
public class DeleteCourseDateServlet {}
/*
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "DeleteCourseDateServlet", urlPatterns = {"/DeleteCourseDateServlet"})
public class DeleteCourseDateServlet extends HttpServlet {

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
            
            int courseDateID = Integer.parseInt(request.getParameter("deleteID"));
            System.out.println("courseDateID: " + courseDateID);
            pst = conn.prepareStatement("DELETE FROM StudentPrecenses WHERE courseDateID=?");
            pst.setInt(1, courseDateID);
            pst.executeUpdate();
            pst = conn.prepareStatement("DELETE FROM CourseDates WHERE ID=?");
            pst.setInt(1, courseDateID);
            if (pst.executeUpdate() != 0) {
                request.setAttribute("message", "Usunięto termin kursu i związane z nim obecności");
            }
            request.setAttribute("subjectName", request.getParameter("subjectName"));
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
        request.getRequestDispatcher("/courseInfoServlet").forward(request, response);
    }

}
*/