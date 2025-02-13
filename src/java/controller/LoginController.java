package controller;

import dal.UserDBContext;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

public class LoginController extends HttpServlet {

    // Create an instance of UserDBContext to interact with the database
    private UserDBContext userDBContext = new UserDBContext();

    // Handle GET requests, typically used to display the login page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to the login page
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }

    // Handle POST requests for processing the login
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get username and password from the form submission
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDBContext db = new UserDBContext();
        User user = db.getUserByUsername(username, password);

        if (user != null) {
            // If login is successful, store the user in the session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            String roleID = db.getRoleIDByUsernameAndPassword(username, password);

            session.setAttribute("roleID", roleID); // Lưu roleID vào session

            // Redirect to the welcome page or a user dashboard after login
            response.sendRedirect(request.getContextPath() + "/homepage");

        } else {
            // If login fails, set an error message and return to the login page
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
        }
    }
}
