/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.adminController;

import dal.DAOforAdmin;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import model.View;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import model.LoginCounter;
import model.SellerRevenue;
import model.SellerTicketSale;

/**
 *
 * @author Aus TUF GAMAING
 */
@WebServlet(name = "AdminDashboard", urlPatterns = {"/admin_page/AdminDashboard"})
public class AdminDashboard extends HttpServlet {

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
            out.println("<title>Servlet AdminDashboard</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminDashboard at " + request.getContextPath() + "</h1>");
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
        DAOforAdmin dao = new DAOforAdmin();
        List<View> v = dao.getView();
        List<LoginCounter> lc = dao.getLoginCounter();
        List<SellerRevenue> sr = dao.getSellerRevenue();
        List<SellerTicketSale> sts = dao.getSellerTicketSale();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime oneYearAgo = today.minusYears(1);
        LocalDateTime twoYearsAgo = today.minusYears(2);

        List<View> recentViews = new ArrayList<>();
        List<View> olderViews = new ArrayList<>();
        for (View view : v) {
            LocalDateTime viewDate = view.getDate().toLocalDate().atStartOfDay();

            if (viewDate.isAfter(oneYearAgo)) {
                recentViews.add(view); // Within 1 year
            } else if (viewDate.isAfter(twoYearsAgo)) {
                olderViews.add(view); // Between 1-2 years
            }
        }
        double viewPercent;
        if (olderViews.isEmpty()) {
            viewPercent = 100;
        } else {
            viewPercent = ((double) recentViews.size() / olderViews.size() - 1) * 100;
        }
        NumberFormat formatter = new DecimalFormat("#0.0");
        request.setAttribute("view", recentViews.size());
        request.setAttribute("viewPercent", formatter.format(Math.abs(viewPercent)));
        request.setAttribute("viewSign", (viewPercent > 0) ? 1 : 0);
        request.setAttribute("viewDiff", Math.abs(recentViews.size() - olderViews.size()));

        List<LoginCounter> recentLogins = new ArrayList<>();
        List<LoginCounter> olderLogins = new ArrayList<>();
        for (LoginCounter login : lc) {
            LocalDateTime loginDate = login.getDate().toLocalDate().atStartOfDay();

            if (loginDate.isAfter(oneYearAgo)) {
                recentLogins.add(login); // Within 1 year
            } else if (loginDate.isAfter(twoYearsAgo)) {
                olderLogins.add(login); // Between 1-2 years
            }
        }
        double loginPercent;
        if (olderLogins.isEmpty()) {
            loginPercent = 100;
        } else {
            loginPercent = ((double) recentLogins.size() / olderLogins.size() - 1) * 100;
        }
        request.setAttribute("login", recentLogins.size());
        request.setAttribute("loginPercent", formatter.format(Math.abs(loginPercent)));
        request.setAttribute("loginSign", (loginPercent > 0) ? 1 : 0);
        request.setAttribute("loginDiff", Math.abs(recentLogins.size() - olderLogins.size()));

        double recentSale = 0;
        double olderSale = 0;
        for (SellerRevenue sere : sr) {
            LocalDateTime sereldt = sere.getDate().toLocalDate().atStartOfDay();

            if (sereldt.isAfter(oneYearAgo)) {
                recentSale+=sere.getAmount_paid(); // Within 1 year
            } else if (sereldt.isAfter(twoYearsAgo)) {
                olderSale+=sere.getAmount_paid(); // Between 1-2 years
            }
        }
        double revenuePercent;
        if (olderSale==0) {
            revenuePercent = 100;
        } else {
            revenuePercent = ((double) recentSale / olderSale - 1) * 100;
        }
        request.setAttribute("sale", recentSale);
        request.setAttribute("salePercent", formatter.format(Math.abs(revenuePercent)));
        request.setAttribute("saleSign", (revenuePercent > 0) ? 1 : 0);
        request.setAttribute("saleDiff", Math.abs(recentSale - olderSale));
        
        List<SellerTicketSale> recentTicket = new ArrayList<>();
        List<SellerTicketSale> olderTicket = new ArrayList<>();
        for (SellerTicketSale seti : sts) {
            LocalDateTime setildt = seti.getDate().toLocalDate().atStartOfDay();

            if (setildt.isAfter(oneYearAgo)) {
                recentTicket.add(seti); // Within 1 year
            } else if (setildt.isAfter(twoYearsAgo)) {
                olderTicket.add(seti); // Between 1-2 years
            }
        }
        double ticketPercent;
        if (olderTicket.isEmpty()) {
            ticketPercent = 100;
        } else {
            ticketPercent = ((double) recentTicket.size() / olderTicket.size() - 1) * 100;
        }
        request.setAttribute("ticket", recentTicket.size());
        request.setAttribute("ticketPercent", formatter.format(Math.abs(ticketPercent)));
        request.setAttribute("ticketSign", (ticketPercent > 0) ? 1 : 0);
        request.setAttribute("ticketDiff", Math.abs(recentTicket.size() - olderTicket.size()));
        
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
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
        processRequest(request, response);
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
