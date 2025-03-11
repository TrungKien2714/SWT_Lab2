package controller.customerController;

import dal.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;
import model.User;

@WebServlet(name = "CustomerProfileServlet", urlPatterns = {"/profile"})
public class CustomerProfileServlet extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        User acc = (User) session.getAttribute("account");
        if (acc != null) {
            int a = acc.getId();
            Customer customer = customerDAO.getCustomerById(a);
            request.setAttribute("cus", customer);
        }
        request.getRequestDispatcher("customerProfile.jsp").forward(request, response);
    }
}
