/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author ASUS
 */

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

public class SellerDAOTest_GetRouteById {
    
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
    public void testGetRouteByCode_Success() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);
        when(resultSet.getInt(2)).thenReturn(2); // Đảm bảo trainId = 2
        when(resultSet.getString(3)).thenReturn("RT001");
        when(resultSet.getString(4)).thenReturn("Mô tả");
        when(resultSet.getString(5)).thenReturn("HAN");
        when(resultSet.getString(6)).thenReturn("SG");
        when(resultSet.getTimestamp(7)).thenReturn(Timestamp.valueOf("2024-05-01 08:00:00"));
        when(resultSet.getTimestamp(8)).thenReturn(Timestamp.valueOf("2024-05-02 08:00:00"));
        when(resultSet.getInt(9)).thenReturn(1);

        // Sử dụng phương thức getRoutebyCode (chữ b viết thường) của SellerDAO mới
        Route route = sellerDAO.getRoutebyCode(1);

        assertNotNull(route);
        assertEquals(1, route.getId());
        assertEquals(2, route.getTrainId());
        assertEquals("RT001", route.getRouteCode());
        assertEquals("Mô tả", route.getDescription());
        assertEquals("HAN", route.getDepartureStation());
        assertEquals("SG", route.getArrivalStation());
        assertEquals(Timestamp.valueOf("2024-05-01 08:00:00"), route.getDepartureTime());
        assertEquals(Timestamp.valueOf("2024-05-02 08:00:00"), route.getArrivalTime());
        assertEquals(1, route.getStatus());

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void testGetRouteByCode_NotFound() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Route route = sellerDAO.getRoutebyCode(999);
        assertNull(route);

        verify(preparedStatement).setInt(1, 999);
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void testGetRouteByCode_NegativeId() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Route route = sellerDAO.getRoutebyCode(-1);
        assertNull(route);

        verify(preparedStatement).setInt(1, -1);
        verify(preparedStatement).executeQuery();
    }
    
     // 4. Test giả lập ngoại lệ khi prepareStatement ném SQLException
    @Test
    public void testGetRouteByCode_Exception() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test exception"));

        Route route = sellerDAO.getRoutebyCode(1);
        assertNull(route);
    }

}
