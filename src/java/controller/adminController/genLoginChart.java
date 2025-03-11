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
import java.util.Date;
import java.util.List;
import model.LoginCounter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Aus TUF GAMAING
 */
@WebServlet(name="genLoginChart", urlPatterns={"/admin_page/genLoginChart"})
public class genLoginChart extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet genLoginChart</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet genLoginChart at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
        List<LoginCounter> lc_list = dao.getLoginCounter();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime oneYearAgo = today.minusMonths(11);
        int[] loginCount = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (LoginCounter lc : lc_list) {
            LocalDateTime lcldt = lc.getDate().toLocalDate().atStartOfDay();
            if (lcldt.isAfter(oneYearAgo)) {
                int m = lcldt.getMonthValue()-1;
                System.out.println(m);
                loginCount[m]++;
            }
        }

        // Example Data: Last 12 Months' Visitor Counts
        JSONArray last12Month = new JSONArray();
        JSONArray visitorCounts = new JSONArray();

        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        List<String> monthLable = new ArrayList<>();
        List<Integer> monthData = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            monthLable.add(months[month]);
            monthData.add(loginCount[month]);
            month++;
            if (month > 11) {
                month = 0;
            }
        }
        for (int i = 0; i < months.length; i++) {
            last12Month.put(monthLable.get(i));
            visitorCounts.put(monthData.get(i));
        }

        JSONObject responseData = new JSONObject();
        responseData.put("labels", last12Month);
        responseData.put("data", visitorCounts);

        PrintWriter out = response.getWriter();
        out.print(responseData.toString());
        out.flush();
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
