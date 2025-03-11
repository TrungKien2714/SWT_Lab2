/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Manager_Control;

import dal.ManagerDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Seller;

/**
 *
 * @author ASUS
 */
@WebServlet(name="AddTrain", urlPatterns={"/AddTrain"})
public class AddTrain extends HttpServlet {
   
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddTrain</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddTrain at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ManagerDAO dao = new ManagerDAO();
        List<Seller> list = dao.getAllSeller();
        request.setAttribute("listseller", list);
        request.getRequestDispatcher("Manager_AddTrain.jsp").forward(request, response);
        
    } 

    @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         ManagerDAO dao = new ManagerDAO();
        // Đảm bảo xử lý charset cho tiếng Việt
        request.setCharacterEncoding("UTF-8");
        
        // Lấy dữ liệu thông tin tàu từ form
        String trainModel = request.getParameter("train_model");
        int totalSeats = Integer.parseInt(request.getParameter("total_seats"));
        int numCabin = Integer.parseInt(request.getParameter("numcabin"));
        int owner = Integer.parseInt(request.getParameter("owner"));
        
        // Sử dụng DAO để thêm train và lấy trainId vừa được tạo
        int trainId = dao.AddTrain(trainModel, totalSeats, numCabin, owner);
        
        if (trainId > 0) {
            // Lấy dữ liệu các toa tàu (cabins) từ form (dạng mảng nếu có nhiều toa)
            String[] cabinNames = request.getParameterValues("cabinName");
            String[] cabinClasses = request.getParameterValues("cabinClass");
            String[] cabinNumseatStr = request.getParameterValues("cabinNumseat");
            String[] cabinImgUrls = request.getParameterValues("cabinImgUrl");
            
            if (cabinNames != null) {
                for (int i = 0; i < cabinNames.length; i++) {
                    int cabinNumseat = Integer.parseInt(cabinNumseatStr[i]);
                    dao.AddCabin(cabinNames[i], cabinClasses[i], cabinNumseat, cabinImgUrls[i], trainId);
                }
            }
            // Sau khi thêm thành công, chuyển hướng đến trang danh sách tàu (trainList.jsp)
            response.sendRedirect("listtrain");
        } else {
            // Nếu có lỗi xảy ra khi thêm tàu, chuyển hướng đến trang thông báo lỗi
            request.setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình thêm tàu.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("listtrain");
            dispatcher.forward(request, response);
        }
    }



}
