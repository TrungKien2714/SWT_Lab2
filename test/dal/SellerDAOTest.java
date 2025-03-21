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
        resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Khởi tạo SellerDAO bằng constructor mặc định và inject connection đã mock vào field connection
        sellerDAO = new SellerDAO();
        sellerDAO.connection = connection; // Giả sử field connection có thể truy cập được trong cùng package
    }

    @Test
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

    // sai dinh dang thoi gian
    @Test
    public void testAddRoute_InvalidDateFormat() throws Exception {
        int trainId = 1;
        String routeCode = "RT002";
        String description = "Chuyến đi HN-SG";
        String departureDateTime = "2024/05/01 08:00"; // sai định dạng
        String arrivalDateTime = "2024/05/02 08:00";   // sai định dạng
        String departureStation = "HAN";
        String arrivalStation = "SG";

        sellerDAO.addRoute(trainId, routeCode, description, departureDateTime, arrivalDateTime, departureStation, arrivalStation);

        verify(preparedStatement, never()).executeUpdate();
    }

    // RouteCode bi null
    @Test
    public void testAddRoute_NullInput() throws Exception {
        int trainId = 1;
        String routeCode = null;
        String description = "abcd";
        String departureDateTime = "2024-05-01T08:00";
        String arrivalDateTime = "2024-05-02T08:00";
        String departureStation = "HAN";
        String arrivalStation = "SG";

        sellerDAO.addRoute(trainId, routeCode, description, departureDateTime, arrivalDateTime, departureStation, arrivalStation);

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Timestamp expectedDepartureTimestamp = new Timestamp(dateTimeFormat.parse(departureDateTime).getTime());
        Timestamp expectedArrivalTimestamp = new Timestamp(dateTimeFormat.parse(arrivalDateTime).getTime());

        verify(preparedStatement).setInt(1, trainId);
        verify(preparedStatement).setString(2, null);
        verify(preparedStatement).setString(3, description);
        verify(preparedStatement).setTimestamp(4, expectedDepartureTimestamp);
        verify(preparedStatement).setTimestamp(5, expectedArrivalTimestamp);
        verify(preparedStatement).setString(6, departureStation);
        verify(preparedStatement).setString(7, arrivalStation);
        verify(preparedStatement).executeUpdate();
    }

    // tat ca empty tru trainId
    @Test
    public void testAddRoute_EmptyStringInput() throws Exception {
        int trainId = 1;
        String routeCode = "";
        String description = "";
        String departureDateTime = "";
        String arrivalDateTime = "";
        String departureStation = "";
        String arrivalStation = "";

        sellerDAO.addRoute(trainId, routeCode, description, departureDateTime, arrivalDateTime, departureStation, arrivalStation);

        verify(preparedStatement, never()).executeUpdate();
    }


}
