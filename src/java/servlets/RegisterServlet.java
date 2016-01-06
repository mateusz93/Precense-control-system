package servlets;

import dao.RegisterDao;
import java.io.IOException;
import java.io.PrintWriter;
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
public class RegisterServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession(true);
        
        if (request.getParameter("newEmail") != null) {
            request.setAttribute("newEmail", request.getParameter("newEmail"));
        } else {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String type = request.getParameter("type");
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            if (RegisterDao.accept(firstName, lastName, type, email, username, password, confirmPassword)) {
                if (session != null) {
                    session.setAttribute("username", username);
                }
                request.setAttribute("message", "Konto poprawnie utworzone!");
            } else {
                request.setAttribute("message", "Hasło niepoprawne!");
            }
        }
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
