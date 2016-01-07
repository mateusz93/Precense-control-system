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
public class AddCourseDateServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        int courseID = Integer.parseInt(session.getAttribute("courseID").toString());
        System.out.println("course ID: " + courseID);

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String username = session.getAttribute("username").toString();
        String date = request.getParameter("date");
        String startTime = request.getParameter("startTime");
        String finishTime = request.getParameter("finishTime");
        try {
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "data";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "root";

            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            pst = conn.prepareStatement("SELECT COUNT(courseID) AS quantity FROM CourseDates WHERE courseID=?");
            pst.setInt(1, courseID);
            rs = pst.executeQuery();
            rs.next();
            int quantity = rs.getInt("quantity");
            System.out.println("Aktualna liczba terminów: " + quantity);

            pst = conn.prepareStatement("SELECT * FROM TeacherCourses WHERE ID=?");
            pst.setInt(1, courseID);
            rs = pst.executeQuery();
            rs.next();
            int coursesQuantity = rs.getInt("coursesQuantity");
            System.out.println("Maxymalna liczba terminów: " + coursesQuantity);
            if (coursesQuantity >= quantity) {
                if (request.getParameter("courseDateID") != null && !"".equals(request.getParameter("courseDateID"))) {
                    int courseDateID = Integer.parseInt(request.getParameter("courseDateID"));
                    pst = conn.prepareStatement("UPDATE CourseDates SET startTime=?, finishTime=?, date=? WHERE ID=?");
                    pst.setString(1, startTime);
                    pst.setString(2, finishTime);
                    pst.setString(3, date);
                    pst.setInt(4, courseDateID);

                    if (!validateTime(startTime, finishTime)) {
                        request.setAttribute("message", "Czas rozpoczącia nie może być późniejszy niż czas zakończenia!");
                    } else if ("".equals(date) || "".equals(startTime) || "".equals(finishTime)) {
                        request.setAttribute("message", "Pole nie może być puste!");
                    } else if (pst.executeUpdate() != 0) {
                        System.out.println("Zapisano do bazy!");
                        request.setAttribute("message", "Zaaktualizowano termin zajęć");
                    } else {
                        System.out.println("Błąd przy zapisie do bazy");
                        request.setAttribute("message", "Wystąpił błąd podczas edycji terminu zajęć!");
                    }

                } else {
                    pst = conn.prepareStatement("INSERT INTO CourseDates (courseID, startTime, finishTime, date) VALUES (?, ?, ?, ?)");
                    pst.setInt(1, courseID);
                    pst.setString(2, startTime);
                    pst.setString(3, finishTime);
                    pst.setString(4, date);

                    if (!validateTime(startTime, finishTime)) {
                        request.setAttribute("message", "Czas rozpoczącia nie może być późniejszy niż czas zakończenia!");
                    } else if ("".equals(date) || "".equals(startTime) || "".equals(finishTime)) {
                        request.setAttribute("message", "Pole nie może być puste!");
                    } else if (pst.executeUpdate() != 0) {
                        System.out.println("Zapisano do bazy!");
                        request.setAttribute("message", "Dodano nowy termin.");
                    } else {
                        System.out.println("Błąd przy zapisie do bazy");
                        request.setAttribute("message", "Wystąpił błąd podczas dodawania nowego terminu!");
                    }
                }
            } else {
                request.setAttribute("message", "Dodałeś już maksymalną ilość terminów!");
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            request.setAttribute("message", "Niepoprawny format!");
            e.printStackTrace();
        }
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("/addCourseDate.jsp").forward(request, response);
    }
    
    private boolean validateTime(String startTime, String finishTime) {
        int startTimeHH, startTimeMM, startTimeSS, finishTimeHH, finishTimeMM, finishTimeSS;
        startTimeHH = Integer.parseInt(startTime.substring(0, 2));
        startTimeMM = Integer.parseInt(startTime.substring(3, 5));
        startTimeSS = Integer.parseInt(startTime.substring(6, 8));
        
        finishTimeHH = Integer.parseInt(finishTime.substring(0, 2));
        finishTimeMM = Integer.parseInt(finishTime.substring(3, 5));
        finishTimeSS = Integer.parseInt(finishTime.substring(6, 8));
        
        if (startTimeHH > finishTimeHH) {
            return false;
        }
        if (startTimeMM > finishTimeMM) {
            return false;
        }
        if (startTimeSS > finishTimeSS) {
            return false;
        }
        return true;
    }
}
