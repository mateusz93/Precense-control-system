package servlets;

import model.Course;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class TeacherCoursesServlet extends HttpServlet {

    private final static Logger log = Logger.getLogger(TeacherCoursesServlet.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("TUTAJ!!!");
        if (log.isInfoEnabled()) {
            log.info("doPost function envocked");
        }

        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Course> courseList = new ArrayList<Course>();

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            //get ID from studentName
            HttpSession session = request.getSession(true);
            String studentName = session.getAttribute("username").toString();
            pst = conn.prepareStatement("SELECT ID from Users WHERE login=?");
            pst.setString(1, studentName);
            rs = pst.executeQuery();
            rs.next();
            int teacherID = rs.getInt("ID");
            System.out.println("TUTAJ!!!");
            if (log.isInfoEnabled()) {
                log.info("doPost function envoked");
            }
            System.out.println("teacherID = " + teacherID);

            pst = conn.prepareStatement("SELECT Subjects.Name AS subjectName, Departments.Name AS departmentName, TeacherCourses.type, TeacherCourses.coursesQuantity FROM TeacherCourses\n"
                    + "	JOIN Subjects\n"
                    + "	ON Subjects.ID=TeacherCourses.subjectID\n"
                    + "	JOIN Departments\n"
                    + "	ON Subjects.departmentID=Departments.ID\n"
                    + "	JOIN Users\n"
                    + "	ON Users.ID=TeacherCourses.teacherID\n"
                    + "	WHERE Users.ID = ?;");
            pst.setInt(1, teacherID);
            rs = pst.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setSubjectName(rs.getString("subjectName"));
                course.setDepartmentName(rs.getString("departmentName"));
                course.setType(rs.getString("type"));
                course.setQuantity(rs.getInt("coursesQuantity"));
                courseList.add(course);
            }
            request.setAttribute("coursesList", courseList);

        } catch (Exception e) {
            e.printStackTrace();

        }
        request.getRequestDispatcher("/teacherCoursesList.jsp").forward(request, response);
    }
}
