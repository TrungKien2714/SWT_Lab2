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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import model.LoginCounter;
import model.Seller;
import model.SellerRevenue;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Aus TUF GAMAING
 */
@WebServlet(name = "genSaleChart", urlPatterns = {"/admin_page/genSaleChart"})
public class genSaleChart extends HttpServlet {

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
            out.println("<title>Servlet genSaleChart</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet genSaleChart at " + request.getContextPath() + "</h1>");
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        DAOforAdmin dao = new DAOforAdmin();
        List<SellerRevenue> sr_list = dao.getSellerRevenue();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime oneYearAgo = today.minusMonths(11);
        List<String> sellers = new ArrayList<>();
        for (SellerRevenue sr : sr_list) {
            if (!sellers.contains(sr.getFull_name())) {
                sellers.add(sr.getFull_name());
            }
        }

        List<List<Integer>> sl_sale_list = new ArrayList<>();
        for (int i = 0; i < sellers.size(); i++) {
            List<Integer> monthlySales = new ArrayList<>(Collections.nCopies(12, 0)); // Create a list of 12 zeros
            sl_sale_list.add(monthlySales);
        }
        int cou = 0;
        for (String s : sellers) {
            for (SellerRevenue sr : sr_list) {
                if (sr.getFull_name().equalsIgnoreCase(s)) {
                    LocalDateTime srldt = sr.getDate().toLocalDate().atStartOfDay();
                    if (srldt.isAfter(oneYearAgo)) {
                        int m = srldt.getMonthValue() - 1;
                        sl_sale_list.get(cou).set(m, sl_sale_list.get(cou).get(m)+(int)sr.getAmount_paid());
                    }
                }
            }
            cou++;
        }

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        List<String> monthLable = new ArrayList<>();
        List<List<Integer>> monthData = new ArrayList<>();
        for (int j = 0; j < sellers.size(); j++) {
            monthData.add(new ArrayList<>()); // Initialize each seller's list
        }

        for (int i = 0; i < 12; i++) {
            monthLable.add(months[month]);
            for (int j = 0; j < sellers.size(); j++) {
                monthData.get(j).add(sl_sale_list.get(j).get(month));
            }
            month++;
            if (month > 11) {
                month = 0;
            }
        }

        // Example Data: Last 12 Months' Visitor Counts
        JSONArray last12Month = new JSONArray();
        JSONArray sellerArray = new JSONArray();

        for (int i = 0; i < months.length; i++) {
            last12Month.put(monthLable.get(i));
        }

        for (int i = 0; i < sellers.size(); i++) {
            JSONObject sellerObj = new JSONObject();
            sellerObj.put("name", sellers.get(i));
            sellerObj.put("sales", monthData.get(i));
            sellerArray.put(sellerObj);
        }

        JSONObject responseData = new JSONObject();
        responseData.put("labels", last12Month);
        responseData.put("data", sellerArray);

        PrintWriter out = response.getWriter();
        out.print(responseData.toString());
        out.flush();
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
