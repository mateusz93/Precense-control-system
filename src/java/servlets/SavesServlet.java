package servlets;

import model.Save;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
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
@WebServlet
public class SavesServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("ISO-8859-2");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(true);
        String studentName = session.getAttribute("username").toString();
        System.out.println(studentName);
        System.out.println(request.getParameter("ID"));
        int courseID = Integer.parseInt(request.getParameter("ID"));

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String pass = "root";

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, pass);
            pst = conn.prepareStatement("SELECT id from Users WHERE login=?");
            pst.setString(1, studentName);
            rs = pst.executeQuery();
            int studentID;
            rs.next();
            studentID = rs.getInt("ID");

            pst = conn.prepareStatement("INSERT INTO StudentCourses (teacherCourseID, studentID, saveTime) VALUES (?, ?, NOW())");
            pst.setInt(1, courseID);
            pst.setInt(2, studentID);
            int result = pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        out.print("<br><br><br>");
        out.print("<div class=\"container\">");
        out.print("<div class=\"alert alert-success fade in\">");
        out.print("<a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>");
        out.print("<strong>Sukces</strong> Zapisano na kurs!");
        out.print("</div>");
        out.print("</div>");

        doGet(request, response);
        out.close();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "data";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String pass = "root";

        try {
            List<Save> subjectList = new ArrayList<Save>();

            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, pass);
            pst = conn.prepareStatement("SELECT TeacherCourses.ID, Subjects.Name, concat(Departments.Name, '') AS departmentName, TeacherCourses.type, TeacherCourses.coursesQuantity, concat(Users.firstName, ' ', Users.lastName) AS teacherName, Subjects.description FROM TeacherCourses\n"
                    + "	JOIN Subjects\n"
                    + "	ON Subjects.ID=TeacherCourses.subjectID\n"
                    + "	JOIN Departments\n"
                    + "	ON Departments.ID=Subjects.departmentID\n"
                    + "	JOIN Users\n"
                    + "	ON Users.ID=TeacherCourses.teacherID");
            rs = pst.executeQuery();
            while (rs.next()) {
                Save save = new Save();
                save.setSubjectName(rs.getString("Name"));
                save.setTeacherName(rs.getString("teacherName"));
                save.setDepartmentName(rs.getString("departmentName"));
                save.setCoursesQuantity(rs.getInt("coursesQuantity"));
                save.setType(rs.getString("type"));
                save.setDescription(rs.getString("description"));
                save.setId(rs.getInt("ID"));
                subjectList.add(save);
            }
            for(Save s : subjectList) {
                System.out.println("Nazwa: " + s.getSubjectName());
                System.out.println("Prowadzący: " + s.getTeacherName());
                System.out.println("Wydział: " + s.getDepartmentName());
                System.out.println("Ilość: " + s.getCoursesQuantity());
                System.out.println("Typ: " + s.getType());
                System.out.println("Opis: " + s.getDescription());
                
            }
            
            
            request.setAttribute("subjectList", subjectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("message", "co jest z tobą");  // Will be available as ${message} in JSP
        request.getRequestDispatcher("/saves.jsp").forward(request, response);
    }
}
