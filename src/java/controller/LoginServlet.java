package controller;

import dal.DAOforAdmin;
import dal.UserDAO;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet({"/admin/login", "/customer/login", "/manager/login", "/seller/login", "/staff/login"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getRequestURI().split("/")[2];
        request.setAttribute("role", role);

        String savedEmail = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (("email_" + role).equals(cookie.getName())) {
                    savedEmail = cookie.getValue();
                    break;
                }
            }
        }
        request.setAttribute("savedEmail", savedEmail);
        request.getRequestDispatcher("../login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getRequestURI().split("/")[2];
        String remember = request.getParameter("remember");

        request.setAttribute("role", role);

        try {
            User user = userDAO.Login(email, password);
            if (user != null && user.getRole().equalsIgnoreCase(role)) {
                HttpSession session = request.getSession();
                session.setAttribute("account", user);
                session.setMaxInactiveInterval(1800);

                Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
                sessionCookie.setMaxAge(7 * 24 * 60 * 60);
                sessionCookie.setPath("/");
                response.addCookie(sessionCookie);

                if ("on".equals(remember)) {
                    Cookie emailCookie = new Cookie("email_" + role, email);
                    emailCookie.setMaxAge(7 * 24 * 60 * 60);
                    emailCookie.setPath("/");
                    response.addCookie(emailCookie);
                } else {
                    Cookie emailCookie = new Cookie("email_" + role, "");
                    emailCookie.setMaxAge(0);
                    emailCookie.setPath("/");
                    response.addCookie(emailCookie);
                }

                if (role.equalsIgnoreCase("customer")) {
                    DAOforAdmin dao = new DAOforAdmin();
                    dao.loginCounted(user.getId());
                    response.sendRedirect("/tratra");
                }
                if (role.equalsIgnoreCase("seller")) {
                    response.sendRedirect(request.getContextPath() + "/liststation");
                }
                if (role.equalsIgnoreCase("manager")) {
                    response.sendRedirect(request.getContextPath() + "/ManagerHomePage.jsp");
                }

                if (role.equalsIgnoreCase("staff")) {
                    response.sendRedirect(request.getContextPath() + "/staff_page/staffDashboard");
                }
                if (role.equalsIgnoreCase("admin")) {
                    response.sendRedirect(request.getContextPath() + "/admin_page/AdminDashboard");
                }

            } else {
                request.setAttribute("errorMessage", "Invalid email, password, or role.");
                request.getRequestDispatcher("../login.jsp").forward(request, response);
            }
        } catch (ServletException | IOException e) {
            request.setAttribute("errorMessage", "Database error. Please try again.");
            request.getRequestDispatcher("../login.jsp").forward(request, response);
        }
    }
}