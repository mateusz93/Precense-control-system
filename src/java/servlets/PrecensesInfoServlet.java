package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import model.StudentPresence;

/**
 *
 * @author Mateusz Wieczorek
 *
 */
public class PrecensesInfoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet precensesInfoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet precensesInfoServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession(true);
        int courseID = Integer.parseInt(request.getParameter("info"));

        session.setAttribute("courseID", courseID);
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<StudentPresence> datesList = new ArrayList<StudentPresence>();

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
                StudentPresence date = new StudentPresence();
                date.setDate(rs.getDate("date"));
                date.setStartTime(rs.getTime("startTime"));
                date.setFinishTime(rs.getTime("finishTime"));
                
                ResultSet rs2 = null;
                PreparedStatement pst2 = conn.prepareStatement("SELECT StudentPrecenses.status from StudentPrecenses WHERE courseDateID=?");
                System.out.println("courdeDateID: "+ rs.getInt("ID"));
                pst2.setInt(1, rs.getInt("ID"));
                rs2 = pst2.executeQuery();
                if (rs2.next()) {
                    System.out.println("[" + rs2.getString("status") + "]");
                    date.setStatus(rs2.getString("status"));
                }
                
                datesList.add(date);
                System.out.println(date.toString());
            }

            request.setAttribute("courseID", courseID);
            request.setAttribute("datesList", datesList);
           
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();

        }
        request.getRequestDispatcher("/precensesInfo.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //processRequest(request, response);
        HttpSession session = request.getSession(true);
        int courseID = Integer.parseInt(request.getParameter("info"));
        System.out.println("course ID: " + courseID);
        doGet(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
