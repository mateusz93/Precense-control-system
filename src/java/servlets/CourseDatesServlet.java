package servlets;

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
import model.Student;

/**
 *
 * @author Mateusz Wieczorek
 *
 */
public class CourseDatesServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        int courseID = Integer.parseInt(request.getParameter("info"));

        session.setAttribute("courseID", courseID);
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<CourseDates> datesList = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            System.out.println("course ID: " + courseID);
            pst = conn.prepareStatement("SELECT * from CourseDates WHERE courseID=?");
            pst.setInt(1, courseID);
            rs = pst.executeQuery();

            while (rs.next()) {
                CourseDates date = new CourseDates();
                date.setDate(rs.getDate("date"));
                date.setStartTime(rs.getTime("startTime"));
                date.setFinishTime(rs.getTime("finishTime"));
                date.setId(rs.getInt("ID"));
                datesList.add(date);
            }
            request.setAttribute("courseID", courseID);
            request.setAttribute("datesList", datesList);
            
            pst = conn.prepareStatement("SELECT studentID from StudentCourses WHERE teacherCourseID=?");
            pst.setInt(1, courseID);
            rs = pst.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setID(rs.getInt("studentID"));
                students.add(student);
            }
            
            for (Student s : students) {
                pst = conn.prepareStatement("SELECT firstName, lastName from Users WHERE ID=?");
                pst.setInt(1, s.getID());
                rs = pst.executeQuery();
                while (rs.next()) {
                    s.setFirstName(rs.getString("firstName"));
                    s.setLastName(rs.getString("lastName"));
                }
            }
            session.setAttribute("students", students);
            //request.setAttribute("students", students);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();

        }
        request.getRequestDispatcher("/checkPrecenses.jsp").forward(request, response);
    }
}
