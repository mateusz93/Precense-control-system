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
public class AddSubjectServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        HttpSession session = request.getSession(true);
        String username = session.getAttribute("username").toString();
        System.out.println("username: " + username);

        System.out.println("subjectName: " + request.getParameter("subjectName"));
        System.out.println("departmentName: " + request.getParameter("departmentName"));
        System.out.println("type: " + request.getParameter("type"));
        System.out.println("quantity: " + request.getParameter("quantity"));
        System.out.println("min: " + request.getParameter("min"));
        System.out.println("description: " + request.getParameter("description"));
        
        
        String subjectName = request.getParameter("subjectName");
        String departmentName = request.getParameter("departmentName");
        String type = request.getParameter("type");
        int courseQuantity = Integer.parseInt(request.getParameter("quantity"));
        int minPresence = Integer.parseInt(request.getParameter("min"));
        String description = request.getParameter("description");

        System.out.println("subjectName: " + subjectName);
        System.out.println("departmentName: " + departmentName);
        System.out.println("type: " + type);
        System.out.println("courseQuantity: " + courseQuantity);
        System.out.println("minPresence: " + minPresence);
        System.out.println("description: " + description);
        
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            pst = conn.prepareStatement("SELECT Users.ID from Users WHERE login=?");
            pst.setString(1, username);
            rs = pst.executeQuery();
            rs.next();
            int userID = rs.getInt("ID");
            System.out.println("User ID: " + userID);

            pst = conn.prepareStatement("SELECT Departments.ID from Departments WHERE Name=?");
            pst.setString(1, departmentName);
            rs = pst.executeQuery();
            rs.next();
            int departmentID = rs.getInt("ID");
            System.out.println("Department ID: " + departmentID);

            pst = conn.prepareStatement("SELECT Subjects.ID from Subjects WHERE Name=? AND departmentID=?");
            pst.setString(1, subjectName);
            pst.setInt(2, departmentID);
            rs = pst.executeQuery();
            rs.next();
            int subjectID = rs.getInt("ID");
            System.out.println("Subject ID: " + subjectID);

            pst = conn.prepareStatement("INSERT INTO TeacherCourses (subjectID, teacherID, type, coursesQuantity, minPresence, description) \n"
                    + "VALUES (?, ?, ?, ?, ?, ?)");
            pst.setInt(1, subjectID);
            pst.setInt(2, userID);
            pst.setString(3, type);
            pst.setInt(4, courseQuantity);
            pst.setInt(5, minPresence);
            pst.setString(6, description);

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
        request.getRequestDispatcher("/addCourse.jsp").forward(request, response);
    }

}
