package controller;

import com.google.gson.Gson;
import dal.RouteDAO;
import dal.StationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Station;
import model.Route;

public class Search extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchStation</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchStation at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        StationDAO stationDAO = new StationDAO();
        List<Station> stations = stationDAO.searchStation(query);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(stations));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String departureStation = request.getParameter("departureStation");
        String arrivalStation = request.getParameter("arrivalStation");
        String departureDate = request.getParameter("departureDate");
        String tripType = request.getParameter("tripType");
        String arrivalDate = null;
        if (tripType.equals("roundTrip")) {
            arrivalDate = request.getParameter("arrivalDate");
        }

        RouteDAO routeDAO = new RouteDAO();
        List<Route> routes = routeDAO.searchRoute(departureStation, arrivalStation, departureDate, arrivalDate);

        HttpSession session = request.getSession();
        session.setAttribute("departureStation", departureStation);
        session.setAttribute("arrivalStation", arrivalStation);
        session.setAttribute("departureDate", departureDate);
        session.setAttribute("arrivalDate", arrivalDate);
        session.setAttribute("routes", routes);

        response.sendRedirect("search.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
