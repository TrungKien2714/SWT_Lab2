package dal;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ManagerDAOTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private ManagerDAO managerDAO;

    @Before
    public void setUp() throws Exception {
        // Khởi tạo mock
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Khi gọi prepareStatement thì trả về mockPreparedStatement
        when(mockConnection.prepareStatement(any(String.class), anyInt())).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        // Gán connection giả lập vào ManagerDAO
        managerDAO = new ManagerDAO();
        managerDAO.connection = mockConnection;
    }

    @Test
    public void testAddTrain_ValidInput_ReturnNewId() throws Exception {
        // Arrange
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // 1 row affected
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Có ID sinh ra
        when(mockResultSet.getInt(1)).thenReturn(10); // ID giả lập

        // Act
        int result = managerDAO.AddTrain("Model X", 100, 5, 1);

        // Assert
        assertEquals(10, result); // Kỳ vọng ID là 10
        verify(mockPreparedStatement).setString(1, "Model X");
        verify(mockPreparedStatement).setInt(2, 100);
        verify(mockPreparedStatement).setInt(3, 5);
        verify(mockPreparedStatement).setInt(4, 1);
        verify(mockPreparedStatement).executeUpdate();
        verify(mockPreparedStatement).getGeneratedKeys();
    }

    @Test
    public void testAddTrain_InvalidOwner_ReturnMinusOne() throws Exception {
        // Arrange - Giả lập khi insert thì bị lỗi khóa ngoại
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLIntegrityConstraintViolationException("Foreign key constraint fails"));

        // Act - Gọi hàm AddTrain với owner không tồn tại
        int result = managerDAO.AddTrain("Model X", 100, 5, 999); // 999 là owner không tồn tại

        // Assert - Kiểm tra kết quả phải là -1 khi bị lỗi khóa ngoại
        assertEquals(-1, result);

        // Verify - Kiểm tra đã truyền đúng các tham số
        verify(mockPreparedStatement).setString(1, "Model X");
        verify(mockPreparedStatement).setInt(2, 100);
        verify(mockPreparedStatement).setInt(3, 5);
        verify(mockPreparedStatement).setInt(4, 999);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testAddTrain_NullModel_ReturnMinusOne() throws Exception {
        // Arrange
        doThrow(new NullPointerException("Model is null")) // ✅ Dùng doThrow cho void
                .when(mockPreparedStatement).setString(1, null);

        // Act
        int result = managerDAO.AddTrain(null, 150, 6, 1); // Gọi hàm với model null

        // Assert
        assertEquals(-1, result); // Kỳ vọng return -1 do lỗi
    }

    @Test
    public void testAddCabin_ValidInput_Success() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Giả lập thêm thành công

        managerDAO.AddCabin("Cabin A", "Luxury", 20, "img.url", 0);

        verify(mockPreparedStatement).setString(1, "Cabin A");
        verify(mockPreparedStatement).setString(2, "Luxury");
        verify(mockPreparedStatement).setInt(3, 20);
        verify(mockPreparedStatement).setString(4, "img.url");
        verify(mockPreparedStatement).setInt(5, 0);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testAddCabin_NullCabinName_ThrowsSQLException() throws Exception {
        // Giả lập lỗi khi cabinName null (constraint từ DB)
        doThrow(new SQLException("Column 'name' cannot be null"))
                .when(mockPreparedStatement).setString(1, null);

        managerDAO.AddCabin(null, "Luxury", 20, "img.url", 0);

        verify(mockPreparedStatement).setString(1, null);
    }

    @Test
    public void testAddCabin_NullCabinClass_ThrowsSQLException() throws Exception {
        // Giả lập lỗi khi cabinClass null
        doThrow(new SQLException("Column 'class' cannot be null"))
                .when(mockPreparedStatement).setString(2, null);

        managerDAO.AddCabin("Cabin A", null, 20, "img.url", 0);

        verify(mockPreparedStatement).setString(2, null);
    }

    @Test
    public void testAddCabin_NullTrainId_ThrowsSQLException() throws Exception {
        // Giả lập lỗi SQL khi trainId null
        doThrow(new SQLException("TrainId cannot be null"))
                .when(mockPreparedStatement).setInt(5, 0); // Giả sử null mapping thành 0

        managerDAO.AddCabin("Cabin A", "Luxury", 20, "img.url", 0);
    }

    @Test
    public void testAddCabin_InvalidTrainId_SQLException() throws Exception {
        // Giả lập lỗi khóa ngoại khi trainId không tồn tại
        doThrow(new SQLIntegrityConstraintViolationException("Foreign key constraint fails"))
                .when(mockPreparedStatement).executeUpdate();

        managerDAO.AddCabin("Cabin A", "Luxury", 20, "img.url", 999); // 999 không tồn tại
    }

    @Test
    public void testUpdateTrain_ValidInput_Success() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = managerDAO.updateTrain(1, "Model Y", 200, 8, 5);

        assertTrue(result); // Cập nhật thành công
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateTrain_TrainIdNotExist_NoRecordUpdate() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Không cập nhật dòng nào

        boolean result = managerDAO.updateTrain(999, "Model Y", 200, 8, 5);

        assertFalse(result); // Không cập nhật
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateTrain_OwnerIdNotExist_SQLException() throws Exception {
        doThrow(new SQLException("Foreign key constraint fails")).when(mockPreparedStatement).executeUpdate();

        managerDAO.updateTrain(1, "Model Y", 200, 8, 99); // OwnerId không tồn tại

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateTrain_ModelNull_SQLException() throws Exception {
        doThrow(new SQLException("Model cannot be null")).when(mockPreparedStatement).setString(1, null);

        managerDAO.updateTrain(1, null, 200, 8, 5);

        verify(mockPreparedStatement).setString(1, null);
    }

    @Test
    public void testUpdateTrain_TotalSeatsNull_SQLException() throws Exception {
        doThrow(new SQLException("TotalSeats cannot be null")).when(mockPreparedStatement).setInt(2, 0);

        managerDAO.updateTrain(1, "Model Y", 0, 8, 5); // totalSeats null giả lập 0

        verify(mockPreparedStatement).setInt(2, 0);
    }

    @Test
    public void testUpdateTrain_OwnerNull_SQLException() throws Exception {
        doThrow(new SQLException("Owner cannot be null")).when(mockPreparedStatement).setInt(4, 0);

        managerDAO.updateTrain(1, "Model Y", 200, 8, 0); // owner null giả lập 0

        verify(mockPreparedStatement).setInt(4, 0);
    }

    @Test
    public void testUpdateTrainStatus_ValidInput_Success() throws Exception {
        // Arrange
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Update thành công

        // Act
        boolean result = managerDAO.updateTrainStatus(1, 1); // trainId = 1, status = 1 (hợp lệ)

        // Assert
        assertTrue(result); // Thành công
        verify(mockPreparedStatement).setInt(1, 1); // status
        verify(mockPreparedStatement).setInt(2, 1); // trainId
        verify(mockPreparedStatement).executeUpdate();
    }

// 2. Test Case: Train ID không tồn tại => Không cập nhật (0 rows)
    @Test
    public void testUpdateTrainStatus_TrainIdNotExist_NoRecordUpdate() throws Exception {
        // Arrange
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Không có bản ghi nào

        // Act
        boolean result = managerDAO.updateTrainStatus(99, 1); // trainId = 99 (không tồn tại)

        // Assert
        assertFalse(result); // Không cập nhật
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setInt(2, 99);
        verify(mockPreparedStatement).executeUpdate();
    }

    // 3. Test Case: Status null (giả lập lỗi) => SQLException
    @Test
    public void testUpdateTrainStatus_NullStatus_SQLException() throws Exception {
        // Arrange
        doThrow(new SQLException("Status cannot be null")).when(mockPreparedStatement).setInt(1, 0); // null giả lập 0

        // Act
        managerDAO.updateTrainStatus(2, 0); // status null giả lập 0

        // Assert: Chỉ verify cái đã xảy ra
        verify(mockPreparedStatement).setInt(1, 0);
        // Không cần verify(mockPreparedStatement).setInt(2, 2); vì chưa chạy tới đây
    }

}
