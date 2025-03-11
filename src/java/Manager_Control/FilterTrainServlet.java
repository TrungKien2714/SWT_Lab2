/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Manager_Control;

import dal.ManagerDAO;
import dal.SellerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Train;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "FilterTrainServlet", urlPatterns = {"/FilterTrain"})
public class FilterTrainServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FilterTrainServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilterTrainServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số lọc
        String trainModel = request.getParameter("trainModel");
        String owner = request.getParameter("owner");
        
        ManagerDAO dao = new ManagerDAO();
        List<Train> filteredTrains = dao.getFilteredTrains(trainModel, owner);
        
        // Xây dựng sellerMap: key = seller id, value = seller full name
        Map<Integer, String> sellerMap = new HashMap<>();
        for (Train t : filteredTrains) {
            String sellerName = dao.getSellerNameById(t.getOwner());
            sellerMap.put(t.getOwner(), sellerName);
        }
        
        request.setAttribute("alltrain", filteredTrains);
        request.setAttribute("sellerMap", sellerMap);
        request.getRequestDispatcher("Manager_manageTrain.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
