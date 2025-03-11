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

public class SellerDAOTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private SellerDAO sellerDAO;

    @Before
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class); // ⚠️ Quan trọng!

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        sellerDAO = new SellerDAO(connection); // Inject connection qua constructor
    }


    @Test // JUnit 4
    public void testAddRoute_Success() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        int trainId = 1;
        String routeCode = "RT001";
        String description = "Chuyến đi Hà Nội - Sài Gòn";
        String departureDateTime = "2024-05-01T08:00";
        String arrivalDateTime = "2024-05-02T08:00";
        String departureStation = "HAN";
        String arrivalStation = "SG";

        sellerDAO.addRoute(trainId, routeCode, description, departureDateTime, arrivalDateTime, departureStation, arrivalStation);

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
        verify(preparedStatement).executeUpdate();
    }
    
    
    @Test
public void testGetRouteByCode_Success() throws Exception {
    // Giả lập
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    when(resultSet.getInt(1)).thenReturn(1);
    when(resultSet.getInt(2)).thenReturn(1);
    when(resultSet.getString(3)).thenReturn("RT001");
    when(resultSet.getString(4)).thenReturn("Mô tả");
    when(resultSet.getString(5)).thenReturn("HAN");
    when(resultSet.getString(6)).thenReturn("SG");
    when(resultSet.getTimestamp(7)).thenReturn(Timestamp.valueOf("2024-05-01 08:00:00"));
    when(resultSet.getTimestamp(8)).thenReturn(Timestamp.valueOf("2024-05-02 08:00:00"));
    when(resultSet.getInt(9)).thenReturn(1);

    // Gọi hàm DAO
    Route route = sellerDAO.getRouteByCode(1);

    // Kiểm tra kết quả
    assertNotNull(route);
    assertEquals("RT001", route.getRouteCode());
    assertEquals("HAN", route.getDepartureStation());
    assertEquals("SG", route.getArrivalStation());

    // Verify
    verify(preparedStatement).setInt(1, 1);
    verify(preparedStatement).executeQuery();
}



@Test
public void testUpdateRoute_Success() throws Exception {
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
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

@Test
public void testDeleteRouteByCode_Success() throws Exception {
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenReturn(1);

    int routeId = 1;
    sellerDAO.deleteRouteByCode(routeId);

    verify(preparedStatement).setInt(1, routeId);
    verify(preparedStatement).executeUpdate();
}


}
