package servlets;

import db.Departments;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class AddSubjectServlet extends HttpServlet {

    Logger logger = Logger.getLogger(AddSubjectServlet.class.getName());
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String username = session.getAttribute("username").toString();

        int courseQuantity;
        int minPresence;
        try {
            courseQuantity = Integer.parseInt(request.getParameter("quantity"));
            minPresence = Integer.parseInt(request.getParameter("min"));

            String subjectName = request.getParameter("subjectName");
            int departmentID = Integer.parseInt(request.getParameter("departmentID"));
            String type = "";
            if ("Äwiczenia".equals(request.getParameter("type"))) {
                type = "Cwiczenia";
            } else if ("WykÅad".equals(request.getParameter("type"))) {
                type = "Wyklad";
            } else {
                type = request.getParameter("type");
            }

            String description = request.getParameter("description");

            System.out.println("subjectName: " + subjectName);
            System.out.println("departmentID: " + departmentID);
            System.out.println("type: " + type);
            System.out.println("courseQuantity: " + courseQuantity);
            System.out.println("minPresence: " + minPresence);
            System.out.println("description: " + description);

            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "data";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "root";

            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            pst = conn.prepareStatement("SELECT Users.ID from Users WHERE login=?");
            pst.setString(1, username);
            rs = pst.executeQuery();
            rs.next();
            int userID = rs.getInt("ID");
            System.out.println("User ID: " + userID);

            pst = conn.prepareStatement("SELECT Subjects.ID from Subjects WHERE Name=? AND departmentID=?");
            pst.setString(1, subjectName);
            pst.setInt(2, departmentID);
            rs = pst.executeQuery();
            if (rs.next()) {
                int subjectID = rs.getInt("ID");
                System.out.println("Subject ID: " + subjectID);
                System.out.println("userID: " + userID);
                System.out.println("type: " + type);
                System.out.println("courseQuantity: " + courseQuantity);
                System.out.println("minPresence: " + minPresence);
                System.out.println("description: " + description);

                pst = conn.prepareStatement("INSERT INTO TeacherCourses (subjectID, teacherID, type, coursesQuantity, minPresence, description) \n"
                        + "VALUES (?, ?, ?, ?, ?, ?)");
                pst.setInt(1, subjectID);
                pst.setInt(2, userID);
                pst.setString(3, type);
                pst.setInt(4, courseQuantity);
                pst.setInt(5, minPresence);
                pst.setString(6, description);
                if (pst.executeUpdate() != 0) {
                    request.setAttribute("message", "Dodano nowy kurs");
                } else {
                    request.setAttribute("message", "Wystąpił błąd podczas dodawania nowego terminu!");
                }        
            } else {
                request.setAttribute("message", "Na wybranym wydziale nie znaleziono przedmiotu o podanej nazwie!");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("message", "Pole może zawierać tylko cyfry!");
            logger.log(Level.INFO, e.toString());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.toString());
        } catch (InstantiationException e) {
            logger.log(Level.SEVERE, e.toString());
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, e.toString());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";
        
        List<Departments> departments = new ArrayList<>();
         try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            pst = conn.prepareStatement("SELECT * from Departments");
            rs = pst.executeQuery();
            while(rs.next()) {
                Departments d = new Departments();
                d.setId(rs.getInt("ID"));
                d.setName(rs.getString("Name"));
                d.setDescription(rs.getString("description"));
                departments.add(d);
            } 
            request.setAttribute("departments", departments);
         } catch (Exception e) {
             e.printStackTrace();
         }
        request.getRequestDispatcher("/addCourse.jsp").forward(request, response);
    }

}
