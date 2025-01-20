package controller;

import dal.UserDBContext;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import model.User;
import model.Role;

public class LoginController extends HttpServlet {

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
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.sendRedirect("RegisterController");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        UserDBContext dao = new UserDBContext();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(password.length() != 64){
            password = dao.hashString(password);
        }
        String rememberme = request.getParameter("rememberme");
        
        HttpSession session = request.getSession();
        if (dao.login(username, password) != null) {
            User c = dao.login(username, password);
            session.setAttribute("customer_id", c.getUserID());
            session.setAttribute("customer", c);
            session.setAttribute("username", username);
            if (rememberme!=null && rememberme.equals("rem")) {
                Cookie user = new Cookie("u", username);
                Cookie pass = new Cookie("p", password);
                user.setMaxAge(5 * 24 * 60 * 60);
                pass.setMaxAge(5 * 24 * 60 * 60);
                response.addCookie(user);
                response.addCookie(pass);
            } else {
                Cookie[] arr = request.getCookies();
                for (Cookie cookie : arr) {
                    if (cookie.getName().equals("u") || cookie.getName().equals("p")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
            if(c.getRole().equals("admin")){
                response.sendRedirect("AdminController");
            }
            response.sendRedirect("HomeController");
        } else {
            request.setAttribute("wrong", "Username or password wrong! Enter again!");
            request.getRequestDispatcher("Views/Register.jsp").forward(request, response);
        }

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
