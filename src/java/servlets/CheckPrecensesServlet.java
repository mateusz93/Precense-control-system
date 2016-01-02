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
public class CheckPrecensesServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        doGet(request, response);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        int courseID = (int) session.getAttribute("courseID");
        int dateID = Integer.parseInt(request.getParameter("dateID"));
        System.out.println("Date ID: " + dateID);
        session.setAttribute("dateID", dateID);
        List<Student> students = (ArrayList<Student>) session.getAttribute("students");
        //List<Student> students = (ArrayList<Student>) request.getAttribute("students");
        
        
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

            System.out.println("course ID dupaaa: " + courseID);
            pst = conn.prepareStatement("SELECT * from CourseDates WHERE courseID=?");
            pst.setInt(1, courseID);
            rs = pst.executeQuery();

            while (rs.next()) {
                CourseDates date = new CourseDates();
                date.setId(rs.getInt("ID"));
                date.setDate(rs.getDate("date"));
                date.setStartTime(rs.getTime("startTime"));
                date.setFinishTime(rs.getTime("finishTime"));
                datesList.add(date);
            }
            
            for (Student s : students) {
                pst = conn.prepareStatement("SELECT status, studentID from StudentPrecenses WHERE courseDateID=?");
                pst.setInt(1, dateID);
                rs = pst.executeQuery();

                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("studentID"));
                    System.out.println("status: " + rs.getString("status"));
                    if (s.getID() == rs.getInt("studentID")) {
                        s.setPrecenseStatus(rs.getString("status"));
                    }
                }
            }
            
            for (Student s : students) {
                System.out.println("EEEE: " + s.toString());
            }
            
            request.setAttribute("courseID", courseID);
            request.setAttribute("datesList", datesList);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();

        }
        request.getRequestDispatcher("/checkPrecense.jsp").forward(request, response);
    }
}
