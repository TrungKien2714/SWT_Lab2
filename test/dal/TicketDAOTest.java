/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import model.TicketDetail;
import java.sql.Timestamp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
/**
 *
 * @author ASUS
 */
public class TicketDAOTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private TicketDAO ticketDAO;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

//Test để lấy toàn bộ thông tin TicketDetail
    @Test
public void testGetAllTicketDetails_success() throws SQLException {
    when(resultSet.next()).thenReturn(true, true, false);

    // Record 1
    when(resultSet.getInt("id")).thenReturn(1);
    when(resultSet.getInt("status")).thenReturn(0);
    when(resultSet.getInt("route_id")).thenReturn(10);
    when(resultSet.getInt("seat_id")).thenReturn(101);
    when(resultSet.getInt("staff_id")).thenReturn(2);
    when(resultSet.getInt("transaction_id")).thenReturn(100);
    when(resultSet.getInt("luggage_type")).thenReturn(1);
    when(resultSet.getBigDecimal("amount_paid")).thenReturn(new BigDecimal("50.00"));
    when(resultSet.getString("phone_number")).thenReturn("0123456789");
    when(resultSet.getString("departure_station")).thenReturn("Station A");
    when(resultSet.getString("arrival_station")).thenReturn("Station B");
    when(resultSet.getTimestamp("departure_time")).thenReturn(Timestamp.valueOf("2024-05-01 08:00:00"));
    when(resultSet.getTimestamp("arrival_time")).thenReturn(Timestamp.valueOf("2024-05-01 10:00:00"));

    List<TicketDetail> tickets = ticketDAO.getAllTicketDetails();

    assertEquals(2, tickets.size());

    // Kiểm tra record đầu tiên
    TicketDetail firstTicket = tickets.get(0);
    assertEquals(1, firstTicket.getId());
    assertEquals(2, firstTicket.getStaffId());
    assertEquals(new BigDecimal("50.00"), firstTicket.getAmountPaid());

    verify(preparedStatement).executeQuery();
}

    //Test để lấy ticketDetail dựa theo ID thành công
    @Test
public void testGetTicketDetailById_Success() throws SQLException {
    // Mock ResultSet
    when(resultSet.next()).thenReturn(true);
    when(resultSet.getInt("id")).thenReturn(1);
    when(resultSet.getInt("status")).thenReturn(0);
    when(resultSet.getInt("route_id")).thenReturn(10);
    when(resultSet.getInt("seat_id")).thenReturn(20);
    when(resultSet.getInt("staff_id")).thenReturn(5);
    when(resultSet.getInt("transaction_id")).thenReturn(100);
    when(resultSet.getInt("luggage_type")).thenReturn(2);
    when(resultSet.getBigDecimal("amount_paid")).thenReturn(new BigDecimal("50.00"));
    when(resultSet.getString("phone_number")).thenReturn("0123456789");
    when(resultSet.getString("full_name")).thenReturn("Nguyen Van A");
    when(resultSet.getString("address")).thenReturn("Ha Noi");
    when(resultSet.getString("departure_station")).thenReturn("Station A");
    when(resultSet.getString("arrival_station")).thenReturn("Station B");
    when(resultSet.getTimestamp("departure_time")).thenReturn(Timestamp.valueOf("2024-05-01 08:00:00"));
    when(resultSet.getTimestamp("arrival_time")).thenReturn(Timestamp.valueOf("2024-05-01 12:00:00"));

    TicketDetail ticket = ticketDAO.getTicketDetailById(1);

    assertNotNull(ticket);
    assertEquals(1, ticket.getId());
    assertEquals(0, ticket.getStatus());
    assertEquals(10, ticket.getRouteId());
    assertEquals(20, ticket.getSeatId());
    assertEquals(5, ticket.getStaffId());
    assertEquals(100, ticket.getTransactionId());
    assertEquals(2, ticket.getLuggageType());
    assertEquals(new BigDecimal("50.00"), ticket.getAmountPaid());
    assertEquals("0123456789", ticket.getPhoneNumber());
    assertEquals("Nguyen Van A", ticket.getFullName());
    assertEquals("Ha Noi", ticket.getAddress());
    assertEquals("Station A", ticket.getDepartureStation());
    assertEquals("Station B", ticket.getArrivalStation());
    assertEquals(Timestamp.valueOf("2024-05-01 08:00:00"), ticket.getDepartureTime());
    assertEquals(Timestamp.valueOf("2024-05-01 12:00:00"), ticket.getArrivalTime());

    verify(preparedStatement).setInt(1, 1);
    verify(preparedStatement).executeQuery();
}

//Test đổi trạng thái vé thành công
    @Test
public void testChangeTicketStatus_success() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenReturn(1);

    boolean result = ticketDAO.changeTicketStatus(1);

    assertTrue(result);

    verify(preparedStatement).setInt(1, 1);
    verify(preparedStatement).executeUpdate();
}

//Test update ticketdetail thành công
  @Test
public void testUpdateTicketDetail_Success() throws SQLException {
    // Giả lập kết quả executeUpdate trả về 1 (cập nhật thành công)
    when(preparedStatement.executeUpdate()).thenReturn(1);

    // Gọi hàm update
    ticketDAO.updateTicketDetail(1, 2, "0123456789", "Nguyen Van A", "Ha Noi");

    // Kiểm tra các tham số đã set đúng chưa
    verify(preparedStatement).setInt(1, 2); // luggage_type
    verify(preparedStatement).setString(2, "0123456789"); // phone_number
    verify(preparedStatement).setString(3, "Nguyen Van A"); // full_name
    verify(preparedStatement).setString(4, "Ha Noi"); // address
    verify(preparedStatement).setInt(5, 1); // ticketId

    // Kiểm tra executeUpdate đã được gọi
    verify(preparedStatement).executeUpdate();
}


//Test insert thêm ticketdetail    
    @Test
public void testInsertTicketDetail_Success() throws SQLException {
    // Giả lập kết quả executeUpdate trả về 1 (insert thành công)
    when(preparedStatement.executeUpdate()).thenReturn(1);

    // Tạo đối tượng TicketDetail để test
    TicketDetail ticketDetail = new TicketDetail();
    ticketDetail.setStatus(1);
    ticketDetail.setLuggageType(2);
    ticketDetail.setRouteId(10);
    ticketDetail.setSeatId(20);
    ticketDetail.setStaffId(5);
    ticketDetail.setTransactionId(100);

    // Gọi hàm insertTicketDetail
    boolean result = ticketDAO.insertTicketDetail(ticketDetail);

    // Kiểm tra kết quả trả về
    assertTrue(result);

    // Kiểm tra các lệnh đã gọi đúng chưa
    verify(preparedStatement).setInt(1, 1);    // status
    verify(preparedStatement).setInt(2, 2);    // luggage_type
    verify(preparedStatement).setInt(3, 10);   // route_id
    verify(preparedStatement).setInt(4, 20);   // seat_id
    verify(preparedStatement).setInt(5, 5);    // staff_id
    verify(preparedStatement).setInt(6, 100);  // transaction_id
    verify(preparedStatement).executeUpdate(); // thực thi lệnh insert
}

//Test xóa ticketDetail thành công
@Test
public void testDeleteTicketDetail_Success() throws SQLException {
    when(preparedStatement.executeUpdate()).thenReturn(1);
    // Gọi hàm cần test
    boolean result = ticketDAO.deleteTicketDetail(1); // Giả sử xóa ticket có id = 1

    // Kiểm tra kết quả trả về
    assertTrue(result);

    // Kiểm tra các lệnh đã được gọi đúng
    verify(preparedStatement).setInt(1, 1); // Kiểm tra setInt đúng tham số
    verify(preparedStatement).executeUpdate(); // Kiểm tra executeUpdate có được gọi
}

//Test fail case xóa TicketDetail không tồn tại
@Test
public void testDeleteTicketDetail_Fail() throws SQLException {
    // Giả lập kết quả executeUpdate trả về 0
    when(preparedStatement.executeUpdate()).thenReturn(0);
    // Gọi hàm cần test
    boolean result = ticketDAO.deleteTicketDetail(999); //  ID không tồn tại

    // Kiểm tra kết quả trả về false
    assertFalse(result);

    // Kiểm tra các lệnh đã được gọi đúng
    verify(preparedStatement).setInt(1, 999);
    verify(preparedStatement).executeUpdate();
}

}
