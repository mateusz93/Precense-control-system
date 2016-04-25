package neo.dmcs.old_servlet;

import java.io.IOException;


/**
 *
 * @author Mateusz Wieczorek
 * 
 */
public class TeacherStatsServlet {}
/*

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TeacherStatsServlet extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        
        doGet(request, response);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        
        request.getRequestDispatcher("/teacherStats.jsp").forward(request, response);
    }
}
*/