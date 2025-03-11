

package Manager_Control;

import dal.ManagerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="UpdateStatusTrain", urlPatterns={"/UpdateStatusTrain"})
public class UpdateStatusTrain extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateStatusTrain</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateStatusTrain at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ManagerDAO dao = new ManagerDAO();       
        int tid = Integer.parseInt(request.getParameter("trainId"));
        int sid = Integer.parseInt(request.getParameter("status"));
        dao.updateTrainStatus(tid, sid);
        response.sendRedirect("listtrain");
        
    } 


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }



}
