package controller;

import dal.RouteDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import model.Cabin;

@WebServlet(name="ViewRoute", urlPatterns={"/ViewRoute"})
public class ViewRoute extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            RouteDAO routeDAO = new RouteDAO();
            Map<String, Object> routeData = routeDAO.getRouteByIdd(id);
            if (!routeData.isEmpty()) {
                Timestamp departureTime = (Timestamp) routeData.get("departureTime");
                Timestamp arrivalTime = (Timestamp) routeData.get("arrivalTime");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String formattedDeparture = sdf.format(departureTime);
                String formattedArrival = sdf.format(arrivalTime);
                List<Cabin> cabins = routeDAO.getCabinFromRouteId(id);
                System.out.println("Cabins data from DAO: " + cabins);
                request.setAttribute("route", routeData);
                request.setAttribute("formattedArrival", formattedArrival);
                request.setAttribute("formattedDeparture", formattedDeparture);
                request.setAttribute("cabins", cabins);
                RequestDispatcher dispatcher = request.getRequestDispatcher("routeInfo.jsp");
                dispatcher.forward(request, response);  // Chuyển tiếp mà không mất dữ liệu
            } else {
                System.out.println("Can't find Route with ID: " + id);
                response.sendRedirect("errorPage.jsp");
            }
        } catch (NumberFormatException e) {
            System.out.println("Bad id: " + e.getMessage());
            response.sendRedirect("errorPage.jsp");
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
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}