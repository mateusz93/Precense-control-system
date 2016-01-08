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
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        
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

            if ("".equals(password) || "".equals(confirmPassword)) {
                request.setAttribute("message", "Nie podałeś hasła!");
            } else if ("".equals(firstName) || "".equals(lastName) || "".equals(email) || "".equals(username)) {
                request.setAttribute("message", "Pole nie może być puste!");
            } else if (!validatePassword(password)) { 
                request.setAttribute("message", "Hasło musi składać się z 8-30 znaków, wielkiej i małej litery, cyfry i znaku specjalnego [!@#$%^&+=]");
            } else if (!"Student".equals(type) && !"Teacher".equals(type)) {
                request.setAttribute("message", "Niepoprawny typ! Dozwolone: Teacher, Student");
            } else if (RegisterDao.accept(firstName, lastName, type, email, username, password, confirmPassword)) {
                if (session != null) {
                    session.setAttribute("username", username);
                }
                request.setAttribute("message", "Konto poprawnie utworzone!");
            } else {
                request.setAttribute("message", "Hasła są różne!");
            }
        }
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
    
    private boolean validatePassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,30}$";
        return password.matches(pattern);
    } 
}
