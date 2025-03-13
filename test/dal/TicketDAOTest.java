package dal;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.TicketDetail;
import java.sql.Timestamp;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyString;

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

    @Test
    public void testGetTicketDetailById_Success() throws SQLException {
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

    @Test
    public void testGetTicketDetailById_Fail_NotFound() throws SQLException {
        when(resultSet.next()).thenReturn(false);
        TicketDetail ticket = ticketDAO.getTicketDetailById(999);
        assertNull(ticket);
        verify(preparedStatement).setInt(1, 999);
        verify(preparedStatement).executeQuery();
    }

    @Test
    public void testUpdateTicketDetail_Success() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        ticketDAO.updateTicketDetail(1, 2, "0123456789", "Nguyen Van A", "Ha Noi");
        verify(preparedStatement).setInt(1, 2);
        verify(preparedStatement).setString(2, "0123456789");
        verify(preparedStatement).setString(3, "Nguyen Van A");
        verify(preparedStatement).setString(4, "Ha Noi");
        verify(preparedStatement).setInt(5, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateTicketDetail_Fail() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);
        ticketDAO.updateTicketDetail(1, 2, "0123456789", "Nguyen Van A", "Ha Noi");
        verify(preparedStatement).setInt(1, 2);
        verify(preparedStatement).setString(2, "0123456789");
        verify(preparedStatement).setString(3, "Nguyen Van A");
        verify(preparedStatement).setString(4, "Ha Noi");
        verify(preparedStatement).setInt(5, 1);
        verify(preparedStatement).executeUpdate();
    }

    //Test update khi fullname=null
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTicketDetail_FullNameNull_ShouldThrowException() throws SQLException {
        ticketDAO.updateTicketDetail(1, 2, "0123456789", null, "Ha Noi");
    }
//Test update khi phonenumber=null

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTicketDetail_PhoneNumberNull_ShouldThrowException() throws SQLException {
        ticketDAO.updateTicketDetail(1, 2, null, "Nguyen Van A", "Ha Noi");
    }
//Test update khi luggagetype=null

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTicketDetail_LuggageTypeNull_ShouldThrowException() throws SQLException {
        ticketDAO.updateTicketDetail(1, null, "0123456789", "Nguyen Van A", "Ha Noi");
    }
//Test update khi address=null

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateTicketDetail_AddressNull_ShouldThrowException() throws SQLException {
        ticketDAO.updateTicketDetail(1, 2, "0123456789", "Nguyen Van A", null);
    }

//Test insert thành công
    @Test
    public void testInsertTicketDetail_Success() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        TicketDetail ticketDetail = new TicketDetail();
        ticketDetail.setStatus(1);
        ticketDetail.setLuggageType(2);
        ticketDetail.setRouteId(10);
        ticketDetail.setSeatId(20);
        ticketDetail.setStaffId(5);
        ticketDetail.setTransactionId(100);
        boolean result = ticketDAO.insertTicketDetail(ticketDetail);
        assertTrue(result);
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setInt(2, 2);
        verify(preparedStatement).setInt(3, 10);
        verify(preparedStatement).setInt(4, 20);
        verify(preparedStatement).setInt(5, 5);
        verify(preparedStatement).setInt(6, 100);
        verify(preparedStatement).executeUpdate();
    }

    //Test insert khi status âm
    @Test
    public void testInsertTicketDetail_StatusNegative() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        TicketDetail ticketDetail = new TicketDetail();
        ticketDetail.setStatus(-1);
        ticketDetail.setLuggageType(2);
        ticketDetail.setRouteId(10);
        ticketDetail.setSeatId(20);
        ticketDetail.setStaffId(5);
        ticketDetail.setTransactionId(100);

        boolean result = ticketDAO.insertTicketDetail(ticketDetail);

        assertFalse(result);
        verify(preparedStatement).setInt(1, -1);
        verify(preparedStatement).executeUpdate();
    }
//Test insert khi routeid âm

    @Test
    public void testInsertTicketDetail_RouteIdNegative() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        TicketDetail ticketDetail = new TicketDetail();
        ticketDetail.setStatus(1);
        ticketDetail.setLuggageType(2);
        ticketDetail.setRouteId(-10);
        ticketDetail.setSeatId(20);
        ticketDetail.setStaffId(5);
        ticketDetail.setTransactionId(100);

        boolean result = ticketDAO.insertTicketDetail(ticketDetail);

        assertFalse(result);
        verify(preparedStatement).setInt(3, -10);
        verify(preparedStatement).executeUpdate();
    }
//Test insert khi seatid âm

    @Test
    public void testInsertTicketDetail_SeatIdNegative() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        TicketDetail ticketDetail = new TicketDetail();
        ticketDetail.setStatus(1);
        ticketDetail.setLuggageType(2);
        ticketDetail.setRouteId(10);
        ticketDetail.setSeatId(-20);
        ticketDetail.setStaffId(5);
        ticketDetail.setTransactionId(100);

        boolean result = ticketDAO.insertTicketDetail(ticketDetail);

        assertFalse(result);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setInt(2, 2);
        verify(preparedStatement).setInt(3, 10);
        verify(preparedStatement).setInt(4, -20);
        verify(preparedStatement).setInt(5, 5);
        verify(preparedStatement).setInt(6, 100);
        verify(preparedStatement).executeUpdate();
    }

//Test insert khi staffid âm
    @Test
    public void testInsertTicketDetail_StaffIdNegative() throws SQLException {

        when(preparedStatement.executeUpdate()).thenReturn(0);

        TicketDetail ticketDetail = new TicketDetail();
        ticketDetail.setStatus(1);
        ticketDetail.setLuggageType(2);
        ticketDetail.setRouteId(10);
        ticketDetail.setSeatId(20);
        ticketDetail.setStaffId(-5);
        ticketDetail.setTransactionId(100);

        // Gọi hàm insert
        boolean result = ticketDAO.insertTicketDetail(ticketDetail);

        assertFalse(result);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setInt(2, 2);
        verify(preparedStatement).setInt(3, 10);
        verify(preparedStatement).setInt(4, 20);
        verify(preparedStatement).setInt(5, -5);
        verify(preparedStatement).setInt(6, 100);
        verify(preparedStatement).executeUpdate();
    }
//Test insert khi transactionid âm

    @Test
    public void testInsertTicketDetail_TransactionIdNegative() throws SQLException {

        when(preparedStatement.executeUpdate()).thenReturn(0);

        TicketDetail ticketDetail = new TicketDetail();
        ticketDetail.setStatus(1);
        ticketDetail.setLuggageType(2);
        ticketDetail.setRouteId(10);
        ticketDetail.setSeatId(20);
        ticketDetail.setStaffId(5);
        ticketDetail.setTransactionId(-100);

        boolean result = ticketDAO.insertTicketDetail(ticketDetail);

        assertFalse(result);

        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setInt(2, 2);
        verify(preparedStatement).setInt(3, 10);
        verify(preparedStatement).setInt(4, 20);
        verify(preparedStatement).setInt(5, 5);
        verify(preparedStatement).setInt(6, -100);
        verify(preparedStatement).executeUpdate();
    }
//Test insert khi routeid không tồn tại

    @Test
    public void testInsertTicketDetail_InvalidForeignKey() throws SQLException {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Foreign key constraint fails"));

        TicketDetail ticketDetail = new TicketDetail();
        ticketDetail.setStatus(1);
        ticketDetail.setLuggageType(2);
        ticketDetail.setRouteId(9999);
        ticketDetail.setSeatId(20);
        ticketDetail.setStaffId(5);
        ticketDetail.setTransactionId(100);

        boolean result = ticketDAO.insertTicketDetail(ticketDetail);

        assertFalse(result);
        verify(preparedStatement).setInt(3, 9999);
        verify(preparedStatement).executeUpdate();
    }
//Test insert khi seatid đã được đặt

    @Test
    public void testInsertTicketDetail_SeatAlreadyBooked() throws SQLException {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Duplicate entry for seat"));

        TicketDetail ticketDetail = new TicketDetail();
        ticketDetail.setStatus(1);
        ticketDetail.setLuggageType(2);
        ticketDetail.setRouteId(10);
        ticketDetail.setSeatId(20); // Giả sử ghế này đã được đặt
        ticketDetail.setStaffId(5);
        ticketDetail.setTransactionId(100);

        boolean result = ticketDAO.insertTicketDetail(ticketDetail);

        assertFalse(result);
        verify(preparedStatement).setInt(4, 20);
        verify(preparedStatement).executeUpdate();
    }
//Test insert khi transactionid đã tồn tại

    @Test
    public void testInsertTicketDetail_TransactionIdAlreadyExists() throws SQLException {
        when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Duplicate entry for transaction_id"));

        TicketDetail ticketDetail = new TicketDetail();
        ticketDetail.setStatus(1);
        ticketDetail.setLuggageType(2);
        ticketDetail.setRouteId(10);
        ticketDetail.setSeatId(20);
        ticketDetail.setStaffId(5);
        ticketDetail.setTransactionId(100); // Đã tồn tại

        boolean result = ticketDAO.insertTicketDetail(ticketDetail);

        assertFalse(result);
        verify(preparedStatement).setInt(6, 100);
        verify(preparedStatement).executeUpdate();
    }
//Delete ticket thành công

    @Test
    public void testDeleteTicketDetail_Success() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = ticketDAO.deleteTicketDetail(1);

        assertTrue(result);
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeUpdate();
    }
//Delete thất bại do ticketid không tồn tại

    @Test
    public void testDeleteTicketDetail_Fail_NotFound() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = ticketDAO.deleteTicketDetail(999);

        assertFalse(result);
        verify(preparedStatement).setInt(1, 999);
        verify(preparedStatement).executeUpdate();
    }
//Delete ticket fail do ticketid=0

    @Test
    public void testDeleteTicketDetail_Boundary_ZeroId() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = ticketDAO.deleteTicketDetail(0);

        assertFalse(result);
        verify(preparedStatement).setInt(1, 0);
        verify(preparedStatement).executeUpdate();
    }
//Delete ticket fail do ticketid= âm

    @Test
    public void testDeleteTicketDetail_Fail_NegativeId() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = ticketDAO.deleteTicketDetail(-1);

        assertFalse(result);
        verify(preparedStatement).setInt(1, -1);
        verify(preparedStatement).executeUpdate();
    }
}
