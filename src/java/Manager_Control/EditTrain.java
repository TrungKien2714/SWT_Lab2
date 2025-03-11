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
import java.util.ArrayList;
import java.util.List;
import model.Cabin;
import model.Seller;
import model.Train;

/**
 *
 * @author ASUS
 */
@WebServlet("/editTrain")
public class EditTrain extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ManagerDAO dao;
    
    @Override
    public void init() throws ServletException {
        dao = new ManagerDAO();
    }
    
    // Hiển thị form chỉnh sửa với dữ liệu hiện có
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int trainId = Integer.parseInt(request.getParameter("id"));
        Train train = dao.getTrainById(trainId);
        List<Cabin> cabins = dao.getCabinByTrainId(trainId);
        // Giả sử SellerDAO có phương thức getAllSellers() để lấy danh sách seller
        List<Seller> sellers = dao.getAllSeller();
        
        request.setAttribute("train", train);
        request.setAttribute("cabins", cabins);
        request.setAttribute("listseller", sellers);
        request.getRequestDispatcher("Manager_EditTrain.jsp").forward(request, response);
    }
    
    // Xử lý cập nhật dữ liệu chỉnh sửa
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int trainId = Integer.parseInt(request.getParameter("trainId")); // hidden field
        String trainModel = request.getParameter("train_model");
        int totalSeats = Integer.parseInt(request.getParameter("total_seats"));
        int numCabin = Integer.parseInt(request.getParameter("numcabin"));
        int owner = Integer.parseInt(request.getParameter("owner"));
        
        // Lấy thông tin các cabin từ form
        String[] cabinNames = request.getParameterValues("cabinName");
        String[] cabinClasses = request.getParameterValues("cabinClass");
        String[] cabinNumseatStr = request.getParameterValues("cabinNumseat");
        String[] cabinImgUrls = request.getParameterValues("cabinImgUrl");
        
        // Kiểm tra tổng số ghế của các cabin
        int sum = 0;
        for (int i = 0; i < cabinNumseatStr.length; i++) {
            sum += Integer.parseInt(cabinNumseatStr[i]);
        }
        if (sum != totalSeats) {
            request.setAttribute("errorMessage", "Tổng số ghế của các toa (" + sum + ") không bằng với Total Seats (" + totalSeats + ").");
            // Nạp lại dữ liệu để hiển thị form chỉnh sửa
            Train train = dao.getTrainById(trainId);
            List<Cabin> cabins = dao.getCabinByTrainId(trainId);
            List<Seller> sellers = dao.getAllSeller();
            request.setAttribute("train", train);
            request.setAttribute("cabins", cabins);
            request.setAttribute("listseller", sellers);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Manager_EditTrain.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        // Cập nhật thông tin train
        boolean trainUpdated = dao.updateTrain(trainId, trainModel, totalSeats, numCabin, owner);
        
        // Chuẩn bị danh sách Cabin mới từ dữ liệu form
        List<Cabin> cabinList = new ArrayList<>();
        for (int i = 0; i < cabinNames.length; i++) {
            Cabin cabin = new Cabin();
            cabin.setCabinName(cabinNames[i]);
            cabin.setType(cabinClasses[i]);
            cabin.setNumSeat(Integer.parseInt(cabinNumseatStr[i]));
            cabin.setImgUrl(cabinImgUrls[i]);
            cabinList.add(cabin);
        }
        
        boolean cabinsUpdated = dao.updateCabins(trainId, cabinList);
        
        if (trainUpdated && cabinsUpdated) {
            response.sendRedirect("listtrain");
        } else {
            request.setAttribute("errorMessage", "Cập nhật thất bại, vui lòng thử lại.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Manager_EditTrain.jsp");
            dispatcher.forward(request, response);
        }
    }
}
