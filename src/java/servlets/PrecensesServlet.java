package servlets;

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
import model.Course;

/**
 *
 * @author Mateusz Wieczorek
 * 
 */
public class PrecensesServlet extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
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
            String studentName = null;
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            if (session.getAttribute("username") != null) {
                studentName = session.getAttribute("username").toString();
            }
            pst = conn.prepareStatement("SELECT ID from Users WHERE login=?");
            pst.setString(1, studentName);
            rs = pst.executeQuery();
            rs.next();
            int studentID = rs.getInt("ID");

            pst = conn.prepareStatement("SELECT Subjects.Name AS subjectName, Departments.Name AS departmentName, TeacherCourses.type, TeacherCourses.ID AS courseID, TeacherCourses.coursesQuantity, concat(Users.firstName, ' ', Users.lastName) AS teacherName FROM StudentCourses\n"
                    + "	JOIN TeacherCourses\n"
                    + "	ON TeacherCourses.ID=StudentCourses.teacherCourseID\n"
                    + "	JOIN Users\n"
                    + "	ON Users.ID=TeacherCourses.teacherID\n"
                    + "	JOIN Subjects\n"
                    + "	ON Subjects.ID=TeacherCourses.subjectID\n"
                    + "	JOIN Departments\n"
                    + "	ON Departments.ID=Subjects.departmentID \n"
                    + "	WHERE StudentCourses.studentID=?;");
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
//            for (int i = 0; i < courseList.size(); i++) {
//                System.out.println(courseList.get(i).getId());
//                System.out.println(courseList.get(i).getSubjectName());
//                System.out.println(courseList.get(i).getDepartmentName());
//                System.out.println(courseList.get(i).getType());
//                System.out.println(courseList.get(i).getQuantity());
//                System.out.println(courseList.get(i).getTeacherName());
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/precenses.jsp").forward(request, response);
    }
}
