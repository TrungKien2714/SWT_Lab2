package seller;

import dal.SellerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Route;
import model.Station;
import model.Train;

@WebServlet(name = "UpdateRouteServlet", urlPatterns = {"/updateroute"})
public class UpdateRouteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateRouteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRouteServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("routeid");
        int rouid = Integer.parseInt(id);
        SellerDAO dao = new SellerDAO();
        Route a = dao.getRoutebyCode(rouid);
        request.setAttribute("r", a);
        List<Station> liststation = dao.getListStation();               
        List<Train> listtrain = dao.getListTrain();
        request.setAttribute("station", liststation);
        request.setAttribute("train", listtrain);
        request.getRequestDispatcher("updateRoute.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SellerDAO dao = new SellerDAO();
        String traid = request.getParameter("trainid");
        String roucode = request.getParameter("routecode");
        String descrep = request.getParameter("description");
        String depaDateTime = request.getParameter("departureDateTime");
        String returnnDateTime = request.getParameter("returnDateTime");
        String depaStation = request.getParameter("departureStation");
        String arrvStation = request.getParameter("arrivalStation");
        int id = Integer.parseInt(traid);
        String rid = request.getParameter("routeid");
        int rouid = Integer.parseInt(rid);
        dao.updateRoute(id, roucode, descrep, depaDateTime, returnnDateTime, depaStation, arrvStation, rouid);
        response.sendRedirect("viewlistroute");
    }

}
