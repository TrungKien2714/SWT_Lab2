package controller.customerController;

import dal.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "CustomerChangePassword", urlPatterns = {"/change-password"})
public class ChangePasswordCustomer extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher("changeCustomerPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        String message = "";

        try {
            String id = request.getParameter("id");
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String renewPassword = request.getParameter("renewPassword");

            // Kiểm tra dữ liệu hợp lệ
            if (id == null || oldPassword == null || newPassword == null || renewPassword == null
                || id.isBlank() || oldPassword.isBlank() || newPassword.isBlank() || renewPassword.isBlank()) {
                throw new Exception("⚠ All fields must be filled!");
            }

            int customerId = Integer.parseInt(id);

            // Kiểm tra mật khẩu cũ có đúng không
            if (!customerDAO.checkOldPassword(customerId, oldPassword)) {
                message = "❌ Incorrect old password!";
            } // Kiểm tra mật khẩu nhập lại có khớp không
            else if (!newPassword.equals(renewPassword)) {
                message = "❌ New password and confirmation do not match!";
            } // Nếu tất cả điều kiện hợp lệ, thực hiện đổi mật khẩu
            else {
                boolean isUpdated = customerDAO.changePasswordCustomer(customerId, newPassword);
                if (isUpdated) {
                    message = "✅ Password changed successfully!";
                    session.removeAttribute("account");
                    response.sendRedirect("/tratra/customer/login");
                } else {
                    message = "❌ Failed to change password. Please try again!";
                    response.sendRedirect("/tratra/change-password");
                }
            }

        } catch (Exception e) {
            message = "⚠ Error: " + e.getMessage();
            session.setAttribute("message", message);
            response.sendRedirect("change-password");
        }

        // Truyền message về JSP
    }
}
