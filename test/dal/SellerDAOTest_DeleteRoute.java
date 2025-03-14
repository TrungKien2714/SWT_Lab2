package dal;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SellerDAOTest_DeleteRoute {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private SellerDAO sellerDAO;

    @Before
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Khởi tạo SellerDAO bằng constructor mặc định và inject connection đã mock vào field connection
        sellerDAO = new SellerDAO();
        sellerDAO.connection = connection; // Giả sử field connection có phạm vi package‑private hoặc protected
    }

    // 1. Trường hợp thành công: xóa thành công khi executeUpdate trả về 1
    @Test
    public void testDeleteRouteByCode_Success() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        int routeId = 1;
        sellerDAO.deleteRouteByCode(routeId);

        verify(preparedStatement).setInt(1, routeId);
        verify(preparedStatement).executeUpdate();
    }

    // 2. Trường hợp với id âm: vẫn gọi setInt và executeUpdate với id âm
    @Test
    public void testDeleteRouteByCode_NegativeId() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        int routeId = -1;
        sellerDAO.deleteRouteByCode(routeId);

        verify(preparedStatement).setInt(1, routeId);
        verify(preparedStatement).executeUpdate();
    }

    // 3. Trường hợp không có bản ghi được cập nhật (executeUpdate trả về 0)
    @Test
    public void testDeleteRouteByCode_NoUpdate() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        int routeId = 999;
        sellerDAO.deleteRouteByCode(routeId);

        verify(preparedStatement).setInt(1, routeId);
        verify(preparedStatement).executeUpdate();
    }

    // 4. Trường hợp xảy ra ngoại lệ: khi connection.prepareStatement ném SQLException, hàm nên bắt ngoại lệ
    @Test
    public void testDeleteRouteByCode_ExceptionHandling() throws Exception {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Test Exception"));

        try {
            sellerDAO.deleteRouteByCode(1);
        } catch (Exception e) {
            fail("Hàm deleteRouteByCode phải bắt ngoại lệ và không ném ra ngoài");
        }
    }
}
