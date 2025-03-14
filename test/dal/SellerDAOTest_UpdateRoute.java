/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import model.Route;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 *
 * @author ASUS
 */
public class SellerDAOTest_UpdateRoute {
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private SellerDAO sellerDAO;

    @Before
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Khởi tạo SellerDAO bằng constructor mặc định và inject connection đã mock vào field connection
        sellerDAO = new SellerDAO();
        sellerDAO.connection = connection; // Giả sử field connection có thể truy cập được trong cùng package
    }
    
    @Test
    public void testUpdateRoute_Success() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        
        int trainId = 1;
        String routeCode = "RT001";
        String description = "Mô tả";
        String departureDateTime = "2024-05-01T08:00";
        String arrivalDateTime = "2024-05-02T08:00";
        String departureStation = "HAN";
        String arrivalStation = "SG";
        int id = 1;
        
        sellerDAO.updateRoute(trainId, routeCode, description, departureDateTime, arrivalDateTime, departureStation, arrivalStation, id);
        
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Timestamp expectedDepartureTimestamp = new Timestamp(dateTimeFormat.parse(departureDateTime).getTime());
        Timestamp expectedArrivalTimestamp = new Timestamp(dateTimeFormat.parse(arrivalDateTime).getTime());
        
        verify(preparedStatement).setInt(1, trainId);
        verify(preparedStatement).setString(2, routeCode);
        verify(preparedStatement).setString(3, description);
        verify(preparedStatement).setTimestamp(4, expectedDepartureTimestamp);
        verify(preparedStatement).setTimestamp(5, expectedArrivalTimestamp);
        verify(preparedStatement).setString(6, departureStation);
        verify(preparedStatement).setString(7, arrivalStation);
        verify(preparedStatement).setInt(8, id);
        verify(preparedStatement).executeUpdate();
    }
    
    // sai đinh dan thoi gian
    @Test
    public void testUpdateRoute_InvalidDepartureDate() throws Exception {
        int trainId = 1;
        String routeCode = "RT002";
        String description = "Test invalid departure date";
        String departureDateTime = "2024/05/01 08:00"; // sai định dạng
        String arrivalDateTime = "2024-05-02T08:00";   // hợp lệ
        String departureStation = "HAN";
        String arrivalStation = "SG";
        int id = 2;
        
        sellerDAO.updateRoute(trainId, routeCode, description, departureDateTime, arrivalDateTime, departureStation, arrivalStation, id);
        
        // Nếu parse thất bại, executeUpdate() sẽ không được gọi
        verify(preparedStatement, never()).executeUpdate();
    }
   
    
    // Test với các field khác nhận giá trị null (routeCode, description, departureStation, arrivalStation) nhưng ngày hợp lệ
    @Test
    public void testUpdateRoute_NullOtherFields() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        
        int trainId = 1;
        String routeCode = null;
        String description = null;
        String departureDateTime = "2024-05-01T08:00";
        String arrivalDateTime = "2024-05-02T08:00";
        String departureStation = null;
        String arrivalStation = null;
        int id = 5;
        
        sellerDAO.updateRoute(trainId, routeCode, description, departureDateTime, arrivalDateTime, departureStation, arrivalStation, id);
        
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Timestamp expectedDepartureTimestamp = new Timestamp(dateTimeFormat.parse(departureDateTime).getTime());
        Timestamp expectedArrivalTimestamp = new Timestamp(dateTimeFormat.parse(arrivalDateTime).getTime());
        
        verify(preparedStatement).setInt(1, trainId);
        verify(preparedStatement).setString(2, routeCode);
        verify(preparedStatement).setString(3, description);
        verify(preparedStatement).setTimestamp(4, expectedDepartureTimestamp);
        verify(preparedStatement).setTimestamp(5, expectedArrivalTimestamp);
        verify(preparedStatement).setString(6, departureStation);
        verify(preparedStatement).setString(7, arrivalStation);
        verify(preparedStatement).setInt(8, id);
        verify(preparedStatement).executeUpdate();
    }
    
    // Test với empty string cho date inputs (sẽ gây exception khi parse)
    @Test
    public void testUpdateRoute_EmptyStringDateInputs() throws Exception {
        int trainId = 1;
        String routeCode = "RT005";
        String description = "Test empty date inputs";
        String departureDateTime = "";
        String arrivalDateTime = "";
        String departureStation = "HAN";
        String arrivalStation = "SG";
        int id = 6;
        
        sellerDAO.updateRoute(trainId, routeCode, description, departureDateTime, arrivalDateTime, departureStation, arrivalStation, id);
        
        verify(preparedStatement, never()).executeUpdate();
    }
}
