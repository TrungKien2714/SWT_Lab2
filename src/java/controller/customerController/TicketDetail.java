package controller.customerController;

import dal.CabinDAO;
import dal.RouteDAO;
import dal.SeatDAO;
import dal.StaffDAO;
import dal.TicketDAO;
import dal.TrainDAO;
import dal.TransactionDAO;
import dto.TicketDTO;
import model.Ticket;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TicketDetailServlet", urlPatterns = {"/ticket-detail"})
public class TicketDetail extends HttpServlet {

    private final TicketDAO ticketDAO = new TicketDAO();
    private final RouteDAO routeDAO = new RouteDAO();
    private final TrainDAO trainDAO = new TrainDAO();
    private final CabinDAO cabinDAO = new CabinDAO();
    private final SeatDAO seatDAO = new SeatDAO();
    private final StaffDAO staffDAO = new StaffDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String transactionIdStr = request.getParameter("transactionId");
        if (transactionIdStr != null) {
            try {
                int transactionId = Integer.parseInt(transactionIdStr);

                List<Ticket> tickets = ticketDAO.getTicketsByTransactionId(transactionId);

                if (!tickets.isEmpty()) {

                    List<TicketDTO> list = new ArrayList<>();
                    for (Ticket ticket : tickets) {
                        var route = routeDAO.getRouteById(ticket.getRouteId());
                        var seat = seatDAO.getSeatById(ticket.getSeatId());
                        var cabin = cabinDAO.getCabinById(seat.getCabinid());
                        var train = trainDAO.getTrainById(cabin.getTrainId());
                        var staff = staffDAO.getStaffById(ticket.getStaffId());
                        TicketDTO ticketDTO = new TicketDTO(ticket, route, train, cabin, seat, staff);
                        list.add(ticketDTO);
                    }

                    request.setAttribute("tickets", list);
                    request.getRequestDispatcher("ticketDetail.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Ticket not found!");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid transaction ID!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Transaction ID is required!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
