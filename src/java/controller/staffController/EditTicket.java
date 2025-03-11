/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.staffController;

import dal.TicketDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TicketDetail;
import java.sql.Timestamp;  

/**
 *
 * @author ASUS
 */
@WebServlet(name="EditTicket", urlPatterns={"/staff_page/editTicket"})
public class EditTicket extends HttpServlet {
   
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
            out.println("<title>Servlet EditTicket</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditTicket at " + request.getContextPath () + "</h1>");
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
        TicketDAO tdao =new TicketDAO();
         String id_raw = request.getParameter("id");
         int id=-1;
         try {
            id= Integer.parseInt(id_raw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TicketDetail ticket =tdao.getTicketDetailById(id);
        request.getSession().setAttribute("ticket", ticket);
        request.getRequestDispatcher("EditTicket.jsp").forward(request, response);
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
    try {
        System.out.println("Received POST request to edit ticket");

        // Lấy dữ liệu từ request
        String idRaw = request.getParameter("id");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String luggageTypeRaw = request.getParameter("luggageType");

        System.out.println("Raw parameters:");
        System.out.println("id: " + idRaw);
        System.out.println("fullName: " + fullName);
        System.out.println("phoneNumber: " + phoneNumber);
        System.out.println("address: " + address);
        System.out.println("luggageType: " + luggageTypeRaw);

        if (idRaw == null || luggageTypeRaw == null) {
            System.err.println("Error: Missing required parameters!");
            response.sendRedirect("../staff_page/ticketList");
            return;
        }

        int ticketId = Integer.parseInt(idRaw);
        int luggageType = Integer.parseInt(luggageTypeRaw);

        TicketDAO t_dao = new TicketDAO();
        // Cập nhật thông tin trong database
        t_dao.updateTicketDetail(ticketId, luggageType, phoneNumber, fullName, address);

        // 🌟 Cập nhật lại session sau khi update
        TicketDetail updatedTicket = t_dao.getTicketDetailById(ticketId);
        request.getSession().setAttribute("ticket", updatedTicket);

        // Chuyển hướng về danh sách vé
        response.sendRedirect("../staff_page/ticketList");
    } catch (Exception e) {
        e.printStackTrace();
    }
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
