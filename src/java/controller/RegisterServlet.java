package controller;

import dal.CustomerDAO;
import dal.UserDAO;
import model.Customer;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String address = request.getParameter("address");

        UserDAO userDAO = new UserDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        if (userDAO.checkEmailExist(email)) {
            request.setAttribute("registerError", "Email already exists");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (customerDAO.checkPhoneNumberExist(phoneNumber)) {
            request.setAttribute("registerError", "Phone Number already exists");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("registerError", "Password and confirm password do not match");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            request.setAttribute("registerError", "Password must contain both letters and numbers");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!phoneNumber.matches("[0-9]{10}")) {
            request.setAttribute("registerError", "Phone number is not valid");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("Customer");

        int userId = userDAO.insertUser(user);
        if (userId > 0) {
            Customer customer = new Customer();
            customer.setUser_id(userId);
            customer.setFullName(fullName);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);
            customer.setStatus(1);

            if (customerDAO.insertCustomer(customer)) {
                response.sendRedirect("./customer/login");
            } else {
                request.setAttribute("registerError", "Failed to register customer.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("registerError", "Failed to register user.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "RegisterServlet for Customer Registration";
    }
}
