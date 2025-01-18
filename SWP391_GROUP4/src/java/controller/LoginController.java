package controller;

import dal.UserDBContext;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.Role;

public class LoginController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        UserDBContext dbContext = new UserDBContext();
        List<Role> roles = dbContext.getRoles();
        
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("view/login.jsp").forward(request, response);
    } 
    

   

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}