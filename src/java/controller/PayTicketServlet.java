/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

public class PayTicketServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PayTicket</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PayTicket at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Lấy session hiện tại, không tạo mới
        User user = (session != null) ? (User) session.getAttribute("account") : null;
        if (user == null || !"customer".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("customer/login");
            return;
        }
        String amount = request.getParameter("amount");
        String numSeats = request.getParameter("numSeats");
        String selectedSeats = request.getParameter("selectedSeats");
        String routeCode = request.getParameter("routeCode");

        // Lưu dữ liệu vào request để hiển thị trên `pay.jsp`
        request.setAttribute("amount", amount);
        request.setAttribute("numSeats", numSeats);
        request.setAttribute("selectedSeats", selectedSeats);
        request.setAttribute("routeCode", routeCode);

        // Chuyển hướng đến `pay.jsp`
        request.getRequestDispatcher("/pay.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
