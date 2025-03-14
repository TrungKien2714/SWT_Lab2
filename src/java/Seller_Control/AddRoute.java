/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Seller_Control;

import dal.SellerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Train;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import model.Route;

/**
 *
 * @author ASUS
 */
@WebServlet(name="AddRoute", urlPatterns={"/AddRoute"})
public class AddRoute extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddTrip</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddTrip at " + request.getContextPath () + "</h1>");
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
        String trainid = request.getParameter("trainid");
        String rcode = request.getParameter("routecode");
        String des = request.getParameter("description");
        String depDateTime = request.getParameter("departureDateTime");
        String returnDateTime = request.getParameter("returnDateTime");
        String depStation = request.getParameter("departureStation");
        String arrStation = request.getParameter("arrivalStation");
        int id = Integer.parseInt(trainid);
       
        SellerDAO dao = new SellerDAO();
        dao.addRoute(id, rcode, des, depDateTime, returnDateTime, depStation, arrStation);
        response.sendRedirect("viewlistroute");
        
    }


}
