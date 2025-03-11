/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import dal.DAOforAdmin;
import java.util.List;

/**
 *
 * @author Aus TUF GAMAING
 */
public class DAOforAdminTest {

    private DAOforAdmin dao;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {
        // Mocking Connection and PreparedStatement
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        // Create DAO and inject mock connection
        dao = new DAOforAdmin(mockConnection); // Assuming you create a constructor that accepts Connection
    }

    @Test
    public void testAddNewAccount_Success() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);

        boolean result = dao.addNewAcc("John Doe", "john@example.com", "123456789", "password123", "admin");
        assertTrue(result);

        verify(mockConnection).commit();
    }

    @Test(expected = SQLException.class)
    public void testAddNewAccount_DuplicateEmail_ShouldRollback() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        doThrow(new SQLException("Duplicate email")).when(mockPreparedStatement).executeUpdate();

        dao.addNewAcc("John Doe", "john@example.com", "123456789", "password123", "admin");

        verify(mockConnection).rollback();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewAccount_InvalidRole_ShouldFail() throws SQLException {
        dao.addNewAcc("John Doe", "john@example.com", "123456789", "password123", "hacker");
    }

    // ✅ Test: Null fields
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewAccount_NullFullName_ShouldThrowException() throws SQLException {
        dao.addNewAcc(null, "john@example.com", "123456789", "password123", "admin");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewAccount_NullEmail_ShouldThrowException() throws SQLException {
        dao.addNewAcc("John Doe", null, "123456789", "password123", "admin");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewAccount_NullPhone_ShouldThrowException() throws SQLException {
        dao.addNewAcc("John Doe", "john@example.com", null, "password123", "admin");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewAccount_NullPassword_ShouldThrowException() throws SQLException {
        dao.addNewAcc("John Doe", "john@example.com", "123456789", null, "admin");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewAccount_NullRole_ShouldThrowException() throws SQLException {
        dao.addNewAcc("John Doe", "john@example.com", "123456789", "password123", null);
    }

    // ✅ Test: Empty fields
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewAccount_EmptyFullName_ShouldThrowException() throws SQLException {
        dao.addNewAcc("   ", "john@example.com", "123456789", "password123", "admin");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewAccount_EmptyEmail_ShouldThrowException() throws SQLException {
        dao.addNewAcc("John Doe", "   ", "123456789", "password123", "admin");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewAccount_EmptyPhone_ShouldThrowException() throws SQLException {
        dao.addNewAcc("John Doe", "john@example.com", "   ", "password123", "admin");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewAccount_EmptyPassword_ShouldThrowException() throws SQLException {
        dao.addNewAcc("John Doe", "john@example.com", "123456789", "   ", "admin");
    }

    // ✅ Test 1: Normal case with multiple emails
    @Test
    public void testGetAllEmail_MultipleEmails() throws SQLException {
        // Mock result set to return 2 emails
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("email")).thenReturn("user1@example.com", "user2@example.com");

        // Call DAO method
        List<String> emails = dao.getAllEmail();

        // Assertions
        assertNotNull(emails);
        assertEquals(2, emails.size());
        assertEquals("user1@example.com", emails.get(0));
        assertEquals("user2@example.com", emails.get(1));

        // Verify interaction
        verify(mockConnection).prepareStatement("Select email from user");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(3)).next(); // true, true, false
        verify(mockResultSet, times(2)).getString("email");
    }

    // ✅ Test 2: Empty table (no emails)
    @Test
    public void testGetAllEmail_EmptyTable() throws SQLException {
        // Mock result set to return no data
        when(mockResultSet.next()).thenReturn(false);

        // Call DAO method
        List<String> emails = dao.getAllEmail();

        // Assertions
        assertNotNull(emails);
        assertTrue(emails.isEmpty()); // List should be empty

        // Verify interaction
        verify(mockConnection).prepareStatement("Select email from user");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(1)).next(); // Only checked once, returned false
    }

    // ✅ Test 3: SQLException occurs (DB error)
    @Test
    public void testGetAllEmail_SQLException() throws SQLException {
        // Simulate SQLException when executing query
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Call DAO method
        List<String> emails = dao.getAllEmail();

        // Assertions
        assertNotNull(emails);
        assertTrue(emails.isEmpty()); // Should return empty list on error

        // Verify interaction
        verify(mockConnection).prepareStatement("Select email from user");
        verify(mockPreparedStatement).executeQuery();
    }

    // ✅ Test 4: Database returns only one email
    @Test
    public void testGetAllEmail_OneEmail() throws SQLException {
        // Mock result set to return one email and then stop
        when(mockResultSet.next()).thenReturn(true, false); // One row
        when(mockResultSet.getString("email")).thenReturn("singleuser@example.com");

        // Call DAO method
        List<String> emails = dao.getAllEmail();

        // Assertions
        assertNotNull(emails);
        assertEquals(1, emails.size());
        assertEquals("singleuser@example.com", emails.get(0));

        // Verify interaction
        verify(mockConnection).prepareStatement("Select email from user");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(2)).next(); // true (one row), false (end)
        verify(mockResultSet, times(1)).getString("email"); // get email once
    }

    // ✅ Test 1: Successful update
    @Test
    public void testEditAcc_SuccessfulUpdate() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // 1 row updated

        boolean result = dao.editAcc("John Doe", "123456789", "123 Street", "admin", 1);

        assertTrue(result);
        verify(mockPreparedStatement).executeUpdate();
    }

    // ✅ Test 2: No rows updated (user ID not found)
    @Test
    public void testEditAcc_NoRowUpdated() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No row updated

        boolean result = dao.editAcc("John Doe", "123456789", "123 Street", "admin", 1);

        assertFalse(result);
        verify(mockPreparedStatement).executeUpdate();
    }

    // ✅ Test 3: Invalid role provided
    @Test(expected = IllegalArgumentException.class)
    public void testEditAcc_InvalidRole() throws SQLException {
        dao.editAcc("John Doe", "123456789", "123 Street", "hacker", 1);
    }

    // ✅ Test 4: Null fullname
    @Test(expected = IllegalArgumentException.class)
    public void testEditAcc_NullFullname() throws SQLException {
        dao.editAcc(null, "123456789", "123 Street", "admin", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditAcc_NullPhonenumber() throws SQLException {
        dao.editAcc("John Doe", null, "123 Street", "admin", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditAcc_NullAddress() throws SQLException {
        dao.editAcc("John Doe", "123456789", null, "admin", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditAcc_EmptyFullname() throws SQLException {
        dao.editAcc(" ", "123456789", "123 Street", "admin", 1);
    }

    // ✅ Test 5: Empty phone number
    @Test(expected = IllegalArgumentException.class)
    public void testEditAcc_EmptyPhoneNumber() throws SQLException {
        dao.editAcc("John Doe", "  ", "123 Street", "admin", 1);
    }

    // ✅ Test 6: Empty address
    @Test(expected = IllegalArgumentException.class)
    public void testEditAcc_EmptyAddress() throws SQLException {
        dao.editAcc("John Doe", "123456789", " ", "admin", 1);
    }

    // ✅ Test 7: SQLException thrown by DB
    @Test(expected = SQLException.class)
    public void testEditAcc_SQLException() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("DB error"));

        dao.editAcc("John Doe", "123456789", "123 Street", "admin", 1);
    }
    
        // ✅ Test 1: Successful deletion when user exists
    @Test
    public void testDeleteUserAccount_Success() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // simulate 1 row affected for both deletes

        boolean result = dao.deleteUserAccount(1, "admin");

        assertTrue(result);
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).commit();
        verify(mockConnection).setAutoCommit(true);
        verify(mockPreparedStatement, times(2)).executeUpdate();
    }

    // ✅ Test 2: Valid ID, but user does not exist (0 rows affected)
    @Test
    public void testDeleteUserAccount_UserNotFound() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected

        boolean result = dao.deleteUserAccount(99, "admin"); // Assuming ID 99 doesn't exist

        assertTrue(result); // Still returns true as per function logic (both deletions succeed even if 0 rows)
        verify(mockConnection).setAutoCommit(false);
        verify(mockConnection).commit();
        verify(mockConnection).setAutoCommit(true);
        verify(mockPreparedStatement, times(2)).executeUpdate();
    }

    // ✅ Test 3: Invalid role (SQL injection prevention)
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserAccount_InvalidRole() throws SQLException {
        dao.deleteUserAccount(1, "hacker"); // Invalid role, should throw exception
    }

    // ✅ Test 4: SQLException during role-specific table deletion
    @Test(expected = SQLException.class)
    public void testDeleteUserAccount_SQLExceptionOnRoleTable() throws SQLException {
        when(mockConnection.prepareStatement(contains("DELETE FROM admin"))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("DB error in role table"));

        try {
            dao.deleteUserAccount(1, "admin");
        } catch (SQLException e) {
            verify(mockConnection).rollback(); // Ensure rollback is called
            throw e; // Rethrow to match expected exception in test
        }
    }

    // ✅ Test 5: SQLException during user table deletion
    @Test(expected = SQLException.class)
    public void testDeleteUserAccount_SQLExceptionOnUserTable() throws SQLException {
        PreparedStatement mockRoleDelete = mock(PreparedStatement.class);
        PreparedStatement mockUserDelete = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(contains("DELETE FROM admin"))).thenReturn(mockRoleDelete);
        when(mockConnection.prepareStatement(contains("DELETE FROM user"))).thenReturn(mockUserDelete);

        when(mockRoleDelete.executeUpdate()).thenReturn(1); // Role deletion successful
        when(mockUserDelete.executeUpdate()).thenThrow(new SQLException("DB error in user table"));

        try {
            dao.deleteUserAccount(1, "admin");
        } catch (SQLException e) {
            verify(mockConnection).rollback(); // Ensure rollback is called
            throw e; // Rethrow to match expected exception in test
        }
    }
}
