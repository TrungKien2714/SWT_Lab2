package Manager_Control;

import dal.ManagerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Cabin;
import model.Train;

@WebServlet(name="DetailsTrain", urlPatterns={"/DetailsTrain"})
public class DetailsTrain extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DetailsTrain</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailsTrain at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ManagerDAO dao = new ManagerDAO();
        String tid = request.getParameter("id");
        int a = Integer.parseInt(tid);
        List<Cabin> list = dao.getCabinByTrainId(a);
        Train tr = dao.getTrainById(a);
        request.setAttribute("train", tr);
        request.setAttribute("cabinlist", list);
        request.getRequestDispatcher("Manager_DetailsTrain.jsp").forward(request, response);
    } 


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }



}
