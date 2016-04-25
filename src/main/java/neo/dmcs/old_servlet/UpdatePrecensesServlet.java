package neo.dmcs.old_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mateusz Wieczorek
 *
 */
public class UpdatePrecensesServlet {}

/*
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet(name = "UpdatePrecensesServlet", urlPatterns = {"/UpdatePrecensesServlet"})
public class UpdatePrecensesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdatePrecensesServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePrecensesServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        request.setAttribute("message", "Zaaktualizowano listę obecności");
        request.getRequestDispatcher("/checkPrecensesServlet").forward(request, response);
        //request.getRequestDispatcher("/checkPrecense.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        List<Student> students = (ArrayList<Student>) session.getAttribute("students");
        int dateID = (int) session.getAttribute("dateID");
        System.out.println("WWWWWWWW: " + dateID);
        try {
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "data";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "root";
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);

            for (Student s : students) {
                pst = conn.prepareStatement("SELECT * from StudentPrecenses WHERE studentID=? AND courseDateID=?");
                pst.setInt(1, s.getID());
                pst.setInt(2, dateID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    System.out.println("Wszedlem 1");
                    System.out.println(s.getID());
                    System.out.println(dateID);
                    System.out.println(request.getParameter("" + s.getID() + "_precenseStatus"));
                    pst = conn.prepareStatement("UPDATE StudentPrecenses SET status=? WHERE studentID=? AND courseDateID=?");
                    pst.setString(1, request.getParameter("" + s.getID() + "_precenseStatus"));
                    pst.setInt(2, s.getID());
                    pst.setInt(3, dateID);
                    pst.executeUpdate();
                    
                } else {
                    System.out.println("Wszedlem 2");
                    System.out.println(s.getID());
                    System.out.println(dateID);
                    System.out.println(request.getParameter("" + s.getID() + "_precenseStatus"));
                    pst = conn.prepareStatement("INSERT INTO StudentPrecenses (studentID, courseDateID, status) VALUES (?, ?, ?)");
                    pst.setInt(1, s.getID());
                    pst.setInt(2, dateID);
                    pst.setString(3, request.getParameter("" + s.getID() + "_precenseStatus"));
                    pst.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
*/