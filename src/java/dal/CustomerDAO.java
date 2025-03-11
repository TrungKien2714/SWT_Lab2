
package dal;

import model.Customer;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.CustomLog;
import model.User;

/**
 *
 * @author dtam6
 */
public class CustomerDAO extends DBContext {
    public boolean checkPhoneNumberExist(String phoneNumber) {
        String sql = "SELECT * FROM Customer WHERE phone_number = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
public boolean insertCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (user_id, full_name, phone_number, address, status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, customer.getUser_id());
            ps.setString(2, customer.getFullName());
            ps.setString(3, customer.getPhoneNumber());
            ps.setString(4, customer.getAddress());
            ps.setInt(5, customer.getStatus());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin khách hàng: chỉ cập nhật các trường trong bảng Customer
    public int updateProfileCustomer(Customer customer) {
        int result = 0;
        String sql = "UPDATE Customer SET full_name = ?, phone_number = ?, address = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, customer.getFullName());
            pre.setString(2, customer.getPhoneNumber());
            pre.setString(3, customer.getAddress());
            pre.setInt(4, customer.getId());
            result = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public User Login_Cus(String email, String password) {
        Customer customer = null;
        String sql = "SELECT * from User where email = ? and password = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, password);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4));
            }
        } catch (SQLException e) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    // Cập nhật thông tin khách hàng: chỉ cập nhật các trường trong bảng Customer
    public int updateCustomerProfile(String phone, String fullname, String address, int uid) {
        int n = 0;
        String sql = """
            UPDATE Customer c
            JOIN User u ON  c.user_id = u.id
            SET
                c.phone_number = ?,
                c.full_name = ?,
                c.address = ?
            WHERE c.user_id = ?;
        """;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, phone);
            pre.setString(2, fullname);
            pre.setString(3, address);
            pre.setInt(4, uid);
            n = pre.executeUpdate();
        } catch (Exception e) {
        }
        return n;
    }

    // Lấy thông tin khách hàng theo id từ bảng Customer
    public Customer getCustomerById(int user_id) {
        Customer customer = null;
        String sql = "SELECT *\n"
            + "from Customer\n"
            + "WHERE user_id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, user_id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String phoneNumber = rs.getString("phone_number");
                String fullName = rs.getString("full_name");
                String address = rs.getString("address");
                int status = rs.getInt("status");
                customer = new Customer(id, user_id, phoneNumber, fullName, address, status);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customer;
    }
    // Xóa khách hàng: cập nhật trạng thái của khách hàng thành 0 nếu password khớp trong bảng User
    public int deleteCustomer(int customerId, String password) {
        int n = 0;
        String sql = "UPDATE Customer c JOIN User u ON c.user_id = u.id "
            + "SET c.status = 0 WHERE c.id = ? AND u.password = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, customerId);
            pre.setString(2, password);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Đổi mật khẩu: cập nhật password trong bảng User thông qua join với bảng Customer
    public int changePasswordCustomer(String oldPassword, String newPassword, int id) {
        int n = 0;
        String sql = "UPDATE User u JOIN Customer c ON c.user_id = u.id "
            + "SET u.password = ? WHERE c.id = ? AND u.password = ? AND c.status = 1";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, newPassword);
            pre.setInt(2, id);
            pre.setString(3, oldPassword);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    public boolean checkOldPassword(int userId, String oldPassword) {
        String sql = "SELECT password FROM User WHERE id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, userId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getString("password").equals(oldPassword);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, "Error checking old password", ex);
        }
        return false;
    }

    // Đổi mật khẩu – Chỉ trả về true/false, Controller sẽ xử lý message
    public boolean changePasswordCustomer(int userId, String newPassword) {
        String updatePasswordSQL = "UPDATE User SET password = ? WHERE id = ?";
        boolean success = false;

        try {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // Tiến hành cập nhật mật khẩu
            try (PreparedStatement updateStmt = connection.prepareStatement(updatePasswordSQL)) {
                updateStmt.setString(1, newPassword);
                updateStmt.setInt(2, userId);
                int rowsUpdated = updateStmt.executeUpdate();
                success = rowsUpdated > 0;
            }

            if (success) {
                connection.commit(); // Commit nếu thành công
            } else {
                connection.rollback(); // Rollback nếu thất bại
            }

        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, "Rollback failed!", rollbackEx);
            }
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, "Error updating password", ex);
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit
            } catch (SQLException e) {
                Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, "Failed to reset auto-commit", e);
            }
        }
        return success;
    }

}
