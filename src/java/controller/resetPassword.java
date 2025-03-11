/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.DAOTokenForget;
import dal.PasswordDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.TokenForgetPassword;
import model.User;

/**
 *
 * @author ASUS
 */
@WebServlet(name="resetPassword", urlPatterns={"/resetPassword"})
public class resetPassword extends HttpServlet {
   
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
            out.println("<title>Servlet resetPassword</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet resetPassword at " + request.getContextPath () + "</h1>");
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
        String token = request.getParameter("token");
        HttpSession session = request.getSession();
        DAOTokenForget DAOToken= new DAOTokenForget();
        PasswordDAO pDAO=new PasswordDAO();
        if(token != null) {
            TokenForgetPassword tokenForgetPassword= DAOToken.getTokenPassword(token);
            resetService service = new resetService();
            if(tokenForgetPassword == null) {
                request.setAttribute("mess", "TOKEN INVALID");
                request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
                return;
            }
            if(tokenForgetPassword.isUsed()) {
                request.setAttribute("mess", "TOKEN IS USED");
                request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
                return;
            }
            if(service.isExpireTime(tokenForgetPassword.getExpiryTime())) {
                request.setAttribute("mess", "TOKEN IS EXPIRY TIME");
                request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
                return;
            }
            User user = pDAO.getUserById(tokenForgetPassword.getUser_id());
            request.setAttribute("email", user.getEmail());
            session.setAttribute("token", tokenForgetPassword.getToken());
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
        }
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        //validate password...
        if(!password.equals(confirmPassword)) {
            request.setAttribute("mess", "confirm password must same password");
            request.setAttribute("email", email);
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        String tokenStr = (String) session.getAttribute("token");
        DAOTokenForget DAOToken= new DAOTokenForget();
        TokenForgetPassword tokenForgetPassword = DAOToken.getTokenPassword(tokenStr);
        //check token is valid, of time, of used
        resetService service = new resetService();
        if (tokenForgetPassword == null) {
            request.setAttribute("mess", "token invalid");
            request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
            return;
        }
        if (tokenForgetPassword.isUsed()) {
            request.setAttribute("mess", "token is used");
            request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
            return;
        }
        if (service.isExpireTime(tokenForgetPassword.getExpiryTime())) {
            request.setAttribute("mess", "token is expiry time");
            request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
            return;
        }

        //update is used of token
        tokenForgetPassword.setToken(tokenStr);
        tokenForgetPassword.setUsed(true);
        PasswordDAO pDAO=new PasswordDAO();
        pDAO.updatePassword(email, password);
        DAOToken.updateStatus(tokenForgetPassword);

        //save user in session and redirect to home
        response.sendRedirect(request.getContextPath() + "/customer/login");
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
