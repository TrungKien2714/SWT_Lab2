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
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UserDetail;

/**
 *
 * @author Aus TUF GAMAING
 */
@WebServlet(name = "EditAccount", urlPatterns = {"/admin_page/EditAccount"})
public class EditAccount extends HttpServlet {

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
            out.println("<title>Servlet EditAccount</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditAccount at " + request.getContextPath() + "</h1>");
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
        DAOforAdmin acc_dao = new DAOforAdmin();
        String role = request.getParameter("role");
        int id = Integer.parseInt(request.getParameter("id"));
        UserDetail user = acc_dao.getChoosedUser(role, id);
        request.setAttribute("fullname", user.getFullname());
        request.setAttribute("phonenumber", user.getPhonenumber());
        request.setAttribute("address", user.getAddress());
        HttpSession session = request.getSession();
        session.setAttribute("email", user.getEmail());
        session.setAttribute("id", String.valueOf(id));
        session.setAttribute("role", user.getRole());

        request.getRequestDispatcher("EditAccount.jsp").forward(request, response);
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
        HttpSession session = request.getSession();

        String fullname = request.getParameter("fullname");
        String phonenumber = request.getParameter("phonenumber");
        String role = String.valueOf(session.getAttribute("role"));
        String address = request.getParameter("address");
        int id = Integer.parseInt(String.valueOf(session.getAttribute("id")));

        DAOforAdmin acc_dao = new DAOforAdmin();

        try {
            acc_dao.editAcc(fullname, phonenumber, address, role, id);
        } catch (SQLException ex) {
            Logger.getLogger(EditAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

        session.setAttribute("email", null);
        session.setAttribute("id", null);
        session.setAttribute("role", null);

        response.sendRedirect("AccountList?role=" + role);
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
