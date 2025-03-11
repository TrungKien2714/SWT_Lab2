/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.customerController;

import dal.CustomerDAO;
import dal.TransactionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Customer;
import model.Transaction;
import model.User;

@WebServlet(name = "TransactionHistory", urlPatterns = {"/transaction"})
public class TransactionHistory extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");

        if (account != null) {
            try {
                Customer customer = customerDAO.getCustomerById(account.getId());

                List<Transaction> transactions = transactionDAO.getTransactionByCustomerId(customer.getId());

                request.setAttribute("transactions", transactions);

                request.getRequestDispatcher("transactionOfCustomer.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID khách hàng không hợp lệ!");
                request.getRequestDispatcher("transactionOfCustomer.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Vui lòng nhập ID khách hàng!");
            request.getRequestDispatcher("transactionOfCustomer.jsp").forward(request, response);
        }
    }

}
