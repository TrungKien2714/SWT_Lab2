package dal;

import model.Customer;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private CustomerDAO customerDAO;

    @Before
    public void setUp() throws SQLException {
        // For each test, mock Connection, PreparedStatement, ResultSet
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Create the DAO object and inject the mock Connection
        customerDAO = new CustomerDAO(mockConnection);
    }

    // ---------------- 1) checkPhoneNumberExist ----------------
    @Test
    public void testCheckPhoneNumberExist_Found() throws Exception {
        // Simulate that the ResultSet returns 1 row
        when(mockResultSet.next()).thenReturn(true);

        boolean exists = customerDAO.checkPhoneNumberExist("0123456789");
        assertTrue("Phone number '0123456789' exists but the method returned false!", exists);

        verify(mockConnection).prepareStatement("SELECT * FROM Customer WHERE phone_number = ?");
        verify(mockPreparedStatement).setString(1, "0123456789");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCheckPhoneNumberExist_NotFound() throws Exception {
        // Simulate an empty ResultSet
        when(mockResultSet.next()).thenReturn(false);

        boolean exists = customerDAO.checkPhoneNumberExist("9999999999");
        assertFalse("Phone number '9999999999' does not exist but the method returned true!", exists);

        verify(mockConnection).prepareStatement("SELECT * FROM Customer WHERE phone_number = ?");
        verify(mockPreparedStatement).setString(1, "9999999999");
        verify(mockPreparedStatement).executeQuery();
    }

    // ---------------- 2) insertCustomer ----------------
    @Test
    public void testInsertCustomer_Success() throws Exception {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Customer c = new Customer();
        c.setUser_id(1);
        c.setFullName("Test Insert");
        c.setPhoneNumber("0123000000");
        c.setAddress("Test Address");
        c.setStatus(1);

        boolean inserted = customerDAO.insertCustomer(c);
        assertTrue("Could not insert Customer!", inserted);

        String expectedSql = "INSERT INTO Customer (user_id, full_name, phone_number, address, status) VALUES (?, ?, ?, ?, ?)";
        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).setString(2, "Test Insert");
        verify(mockPreparedStatement).setString(3, "0123000000");
        verify(mockPreparedStatement).setString(4, "Test Address");
        verify(mockPreparedStatement).setInt(5, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testInsertCustomer_Failed() throws Exception {
        // Simulate executeUpdate() returning 0 => insert failed
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        Customer c = new Customer();
        c.setUser_id(999); // Suppose this user_id is invalid
        c.setFullName("Fail Insert");
        c.setPhoneNumber("FailPhone");
        c.setAddress("Fail Address");
        c.setStatus(1);

        boolean inserted = customerDAO.insertCustomer(c);
        assertFalse("Insert should fail but returned true!", inserted);
    }

    // ---------------- 3) updateProfileCustomer(Customer) ----------------
    @Test
    public void testUpdateProfileCustomer() throws Exception {
        // Simulate a successful update
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Customer c = new Customer(1, 8, "0900000008", "Customer One", "Old Address", 1);
        c.setFullName("New Name");
        c.setPhoneNumber("0123456789");
        c.setAddress("New Address");

        int rowsAffected = customerDAO.updateProfileCustomer(c);
        assertEquals("Profile update failed!", 1, rowsAffected);

        String expectedSql = "UPDATE Customer SET full_name = ?, phone_number = ?, address = ? WHERE id = ?";
        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockPreparedStatement).setString(1, "New Name");
        verify(mockPreparedStatement).setString(2, "0123456789");
        verify(mockPreparedStatement).setString(3, "New Address");
        verify(mockPreparedStatement).setInt(4, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    // ---------------- 4) Login_Cus(String, String) ----------------
    @Test
    public void testLogin_Cus_Invalid() throws Exception {
        when(mockResultSet.next()).thenReturn(false);

        User user = customerDAO.Login_Cus("wrong@example.com", "wrong");
        assertNull("Invalid login but did not return null!", user);

        String expectedSql = "SELECT * from User where email = ? and password = ?";
        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockPreparedStatement).setString(1, "wrong@example.com");
        verify(mockPreparedStatement).setString(2, "wrong");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testLogin_Cus_Valid() throws Exception {
        // Simulate a ResultSet with data => valid login
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(8);
        when(mockResultSet.getString(2)).thenReturn("customer1@example.com");
        when(mockResultSet.getString(3)).thenReturn("customer123");
        when(mockResultSet.getString(4)).thenReturn("Customer");

        User user = customerDAO.Login_Cus("customer1@example.com", "customer123");
        assertNotNull("Valid login but returned null!", user);
        assertEquals("customer1@example.com", user.getEmail());

        String expectedSql = "SELECT * from User where email = ? and password = ?";
        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockPreparedStatement).setString(1, "customer1@example.com");
        verify(mockPreparedStatement).setString(2, "customer123");
        verify(mockPreparedStatement).executeQuery();
    }

    // ---------------- 5) updateCustomerProfile(String, String, String, int) ----------------
    @Test
    public void testUpdateCustomerProfile() throws Exception {
        // Simulate executeUpdate() returning 1 => update success
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int uid = 8;
        int rows = customerDAO.updateCustomerProfile("0988111222", "Update Name", "Update Address", uid);
        assertTrue("updateCustomerProfile() failed!", rows > 0);

        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setString(1, "0988111222");
        verify(mockPreparedStatement).setString(2, "Update Name");
        verify(mockPreparedStatement).setString(3, "Update Address");
        verify(mockPreparedStatement).setInt(4, uid);
        verify(mockPreparedStatement).executeUpdate();
    }

    // ---------------- 6) getCustomerById(int) ----------------
    @Test
    public void testGetCustomerById_Found() throws Exception {
        // Simulate a ResultSet with data
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(10);
        when(mockResultSet.getString("phone_number")).thenReturn("0123456789");
        when(mockResultSet.getString("full_name")).thenReturn("Test Name");
        when(mockResultSet.getString("address")).thenReturn("Test Address");
        when(mockResultSet.getInt("status")).thenReturn(1);

        Customer c = customerDAO.getCustomerById(8);
        assertNotNull("Customer with user_id=8 was not found!", c);
        assertEquals(8, c.getUser_id());

        verify(mockConnection).prepareStatement("SELECT *\nfrom Customer\nWHERE user_id = ?");
        verify(mockPreparedStatement).setInt(1, 8);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetCustomerById_NotFound() throws Exception {
        // Simulate an empty ResultSet
        when(mockResultSet.next()).thenReturn(false);

        Customer c = customerDAO.getCustomerById(9999);
        assertNull("user_id=9999 does not exist but did not return null!", c);

        verify(mockConnection).prepareStatement("SELECT *\nfrom Customer\nWHERE user_id = ?");
        verify(mockPreparedStatement).setInt(1, 9999);
        verify(mockPreparedStatement).executeQuery();
    }

    // ---------------- 7) deleteCustomer(int, String) ----------------
    @Test
    public void testDeleteCustomer_FailedWrongPassword() throws Exception {
        // Simulate 0 rows updated => wrong password
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        int rows = customerDAO.deleteCustomer(2, "wrongpassword");
        assertEquals("Used the wrong password but the customer was still deleted!", 0, rows);

        String expectedSql = "UPDATE Customer c JOIN User u ON c.user_id = u.id "
            + "SET c.status = 0 WHERE c.id = ? AND u.password = ?";
        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockPreparedStatement).setInt(1, 2);
        verify(mockPreparedStatement).setString(2, "wrongpassword");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteCustomer_Success() throws Exception {
        // Simulate 1 row updated => successful delete
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int rows = customerDAO.deleteCustomer(10, "correctPass");
        assertEquals("Delete failed even though password is correct!", 1, rows);
    }

    // ---------------- 8) changePasswordCustomer(String, String, int) (Old Version) ----------------
    @Test
    public void testChangePasswordCustomer_OldVersion() throws Exception {
        // Simulate 1 row updated
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int rows = customerDAO.changePasswordCustomer("oldpass", "newpass", 1);
        assertEquals("Failed to change password (old version)!", 1, rows);

        String expectedSql = "UPDATE User u JOIN Customer c ON c.user_id = u.id "
            + "SET u.password = ? WHERE c.id = ? AND u.password = ? AND c.status = 1";
        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockPreparedStatement).setString(1, "newpass");
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).setString(3, "oldpass");
        verify(mockPreparedStatement).executeUpdate();
    }

    // ---------------- 9) checkOldPassword(int, String) ----------------
    @Test
    public void testCheckOldPassword_Match() throws Exception {
        // Simulate resultSet.next()=true and password="abc123"
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("password")).thenReturn("abc123");

        boolean match = customerDAO.checkOldPassword(8, "abc123");
        assertTrue("The correct password did not match!", match);

        verify(mockConnection).prepareStatement("SELECT password FROM User WHERE id = ?");
        verify(mockPreparedStatement).setInt(1, 8);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCheckOldPassword_NotMatch() throws Exception {
        // Simulate resultSet.next()=true but password="abc123"
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("password")).thenReturn("abc123");

        boolean match = customerDAO.checkOldPassword(8, "wrongpass");
        assertFalse("Wrong password but method returned true!", match);
    }

    @Test
    public void testCheckOldPassword_NoRecord() throws Exception {
        // Simulate resultSet.next()=false => no row
        when(mockResultSet.next()).thenReturn(false);

        boolean match = customerDAO.checkOldPassword(999, "whatever");
        assertFalse("No user with ID=999 but method still returned true!", match);

        verify(mockConnection).prepareStatement("SELECT password FROM User WHERE id = ?");
        verify(mockPreparedStatement).setInt(1, 999);
        verify(mockPreparedStatement).executeQuery();
    }

    // ---------------- 10) changePasswordCustomer(int, String) (New Version) ----------------
    @Test
    public void testChangePasswordCustomer_NewVersion_Success() throws Exception {
        // Simulate 1 row updated => success = true
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean success = customerDAO.changePasswordCustomer(8, "newPassword123");
        assertTrue("Failed to change password (new version)!", success);

        String expectedSql = "UPDATE User SET password = ? WHERE id = ?";
        verify(mockConnection).prepareStatement(expectedSql);
        verify(mockPreparedStatement).setString(1, "newPassword123");
        verify(mockPreparedStatement).setInt(2, 8);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testChangePasswordCustomer_NewVersion_Failed() throws Exception {
        // Simulate 0 rows updated => failure => success = false
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        boolean success = customerDAO.changePasswordCustomer(8, "noUpdate");
        assertFalse("Update failed but method returned true!", success);
    }
}
