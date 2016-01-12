package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "AddSeveralCourseDatesServlet", urlPatterns = {"/AddSeveralCourseDatesServlet"})
public class AddSeveralCourseDatesServlet extends HttpServlet {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    final String url = "jdbc:mysql://localhost:3306/";
    final String dbName = "data";
    final String driver = "com.mysql.jdbc.Driver";
    final String userName = "root";
    final String password = "root";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        int courseID = Integer.parseInt(session.getAttribute("courseID").toString());

        String username = session.getAttribute("username").toString();
        String firstDate = request.getParameter("firstDate");
        String lastDate = request.getParameter("lastDate");
        String startTime = request.getParameter("startTime");
        String finishTime = request.getParameter("finishTime");

        int space = Integer.parseInt(request.getParameter("space"));
        try {

            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            pst = conn.prepareStatement("SELECT * FROM TeacherCourses WHERE ID=?");
            pst.setInt(1, courseID);
            rs = pst.executeQuery();
            rs.next();
            int coursesQuantity = rs.getInt("coursesQuantity");
            System.out.println("Maxymalna liczba terminów: " + coursesQuantity);

            if (coursesQuantity <= checkActualDatesCourseQuantity(courseID)) {
                request.setAttribute("message", "Dodałeś już maksymalną ilość terminów!");
            } else {
                if ("".equals(firstDate) || "".equals(lastDate) || "".equals(startTime) || "".equals(finishTime)) {
                    request.setAttribute("message", "Pole nie może być puste!");
                } else if (!validateDate(firstDate, lastDate)) {
                    request.setAttribute("message", "Data zakończenia musi być późniejsza niż data rozpoczęcia!");
                } else if (!validateTime(startTime, finishTime)) {
                    request.setAttribute("message", "Czas rozpoczącia nie może być późniejszy niż czas zakończenia!");
                }  //                else {
                //                    System.out.println("Błąd przy zapisie do bazy");
                //                    request.setAttribute("message", "Wystąpił błąd podczas dodawania nowego terminu!");
                //                }
                else {
                    while (coursesQuantity > checkActualDatesCourseQuantity(courseID) && validateDate(firstDate, lastDate)) {
                        if (!chechIfADateCourseExsists(courseID, startTime, finishTime, firstDate)) { // jeśli nie istnieje taki termin kursu
                            saveNewCourseDate(courseID, startTime, finishTime, firstDate);
                        }
                        firstDate = generateNextDate(firstDate, space);
                    }
                    request.setAttribute("message", "Dodano nowe terminy zajęć");
                }
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
        request.getRequestDispatcher("/addSeveralCourseDate.jsp").forward(request, response);
    }

    private boolean validateTime(String startTime, String finishTime) {
        int startTimeHH, startTimeMM, startTimeSS, finishTimeHH, finishTimeMM, finishTimeSS;
        startTimeHH = Integer.parseInt(startTime.substring(0, 2));
        startTimeMM = Integer.parseInt(startTime.substring(3, 5));
        startTimeSS = Integer.parseInt(startTime.substring(6, 8));

        finishTimeHH = Integer.parseInt(finishTime.substring(0, 2));
        finishTimeMM = Integer.parseInt(finishTime.substring(3, 5));
        finishTimeSS = Integer.parseInt(finishTime.substring(6, 8));

        Time firstTime = new Time(startTimeHH, startTimeMM, startTimeSS);
        Time secondTime = new Time(finishTimeHH, finishTimeMM, finishTimeSS);

        if (firstTime.compareTo(secondTime) < 0) {
            System.out.println("Walidacja czasu TRUE");
            return true;
        }
        System.out.println("Walidacja czasu FALSE");
        return false;
    }

    private boolean validateDate(String startDate, String finishDate) {
        int startDateYYYY, startDateMM, startDateDD, finishDateYYYY, finishDateMM, finishDateDD;
        startDateYYYY = Integer.parseInt(startDate.substring(0, 4));
        startDateMM = Integer.parseInt(startDate.substring(5, 7));
        startDateDD = Integer.parseInt(startDate.substring(8, 10));

        finishDateYYYY = Integer.parseInt(finishDate.substring(0, 4));
        finishDateMM = Integer.parseInt(finishDate.substring(5, 7));
        finishDateDD = Integer.parseInt(finishDate.substring(8, 10));

        Date firstDate = new Date(startDateYYYY, startDateMM, startDateDD);
        Date secondDate = new Date(finishDateYYYY, finishDateMM, finishDateDD);

        if (firstDate.compareTo(secondDate) < 0) {
            System.out.println("Walidacja daty TRUE");
            return true;
        }
        System.out.println("Walidacja daty FALSE");
        return false;
    }

    private int checkActualDatesCourseQuantity(int courseID) {
        Connection conn1 = null;
        PreparedStatement pst1 = null;
        ResultSet rs1 = null;

        int quantity = 0;
        try {
            Class.forName(driver).newInstance();
            conn1 = DriverManager.getConnection(url + dbName, userName, password);
            pst1 = conn1.prepareStatement("SELECT COUNT(courseID) AS quantity FROM CourseDates WHERE courseID=?");
            pst1.setInt(1, courseID);
            rs1 = pst1.executeQuery();
            if (rs1.next()) {
                return rs1.getInt("quantity");
            }
        } catch (SQLException e) {
            Logger.getLogger(AddSeveralCourseDatesServlet.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddSeveralCourseDatesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AddSeveralCourseDatesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AddSeveralCourseDatesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quantity;
    }

    private String generateNextDate(String date, int space) {
        LocalDate initial = LocalDate.of(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8, 10)));
        System.out.println("Nowa date to: " + initial.plusDays(space * 7).toString());
        return initial.plusDays(space * 7).toString();
    }

    private boolean chechIfADateCourseExsists(int courseID, String startTime, String finishTime, String date) {
        Connection conn2 = null;
        PreparedStatement pst2 = null;
        ResultSet rs2 = null;
        try {
            Class.forName(driver).newInstance();
            conn2 = DriverManager.getConnection(url + dbName, userName, password);
            pst2 = conn2.prepareStatement("SELECT * FROM CourseDates WHERE courseID=? AND startTime=? AND finishTime=? AND date=?");
            pst2.setInt(1, courseID);
            pst2.setString(2, startTime);
            pst2.setString(3, finishTime);
            pst2.setString(4, date);
            rs2 = pst2.executeQuery();
            if (rs2.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddSeveralCourseDatesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AddSeveralCourseDatesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AddSeveralCourseDatesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void saveNewCourseDate(int courseID, String startTime, String finishTime, String date) {
        Connection conn3 = null;
        PreparedStatement pst3 = null;
        ResultSet rs3 = null;
        try {
            Class.forName(driver).newInstance();
            conn3 = DriverManager.getConnection(url + dbName, userName, password);
            pst3 = conn3.prepareStatement("INSERT INTO CourseDates (courseID, startTime, finishTime, date) VALUES (?, ?, ?, ?)");
            pst3.setInt(1, courseID);
            pst3.setString(2, startTime);
            pst3.setString(3, finishTime);
            pst3.setString(4, date);
            if (pst3.executeUpdate() != 0) {
                System.out.println("Zapisano nową datę do bazy");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddSeveralCourseDatesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AddSeveralCourseDatesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AddSeveralCourseDatesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
