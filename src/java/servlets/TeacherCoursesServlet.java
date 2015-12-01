package servlets;

import model.Course;
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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            int studentID = rs.getInt("ID");
            System.out.println("Student ID = " + studentID);

            pst = conn.prepareStatement("SELECT Subjects.Name AS subjectName, Departments.Name AS departmentName, Courses.type, Courses.ID AS courseID, Courses.coursesQuantity, concat(Users.firstName, ' ', Users.lastName) AS teacherName FROM StudentCourses\n"
                    + "	JOIN Courses\n"
                    + "	ON Courses.ID=StudentCourses.courseID\n"
                    + "	JOIN Users_Subjects\n"
                    + "	ON Users_Subjects.courseID=Courses.ID\n"
                    + "	JOIN Users\n"
                    + "	ON Users_Subjects.teacherID=Users.ID\n"
                    + "	JOIN Subjects\n"
                    + "	ON Subjects.ID=Courses.subjectID\n"
                    + "	JOIN Departments\n"
                    + "	ON Departments.ID=Subjects.departmentID WHERE StudentCourses.studentID=?;");
            pst.setInt(1, studentID);
            rs = pst.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("courseID"));
                course.setSubjectName(rs.getString("subjectName"));
                course.setDepartmentName(rs.getString("departmentName"));
                course.setType(rs.getString("type"));
                course.setTeacherName(rs.getString("teacherName"));
                course.setQuantity(rs.getInt("coursesQuantity"));
                courseList.add(course);
            }
            request.setAttribute("coursesList", courseList);

        } catch (Exception e) {
            e.printStackTrace();

        }
        request.getRequestDispatcher("/coursesList.jsp").forward(request, response);
    }
}
