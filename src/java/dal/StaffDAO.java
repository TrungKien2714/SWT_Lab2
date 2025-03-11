package dal;

import model.Staff;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dtam6
 */
public class StaffDAO extends DBContext {

    public Staff getStaffById(int staffId) {
        Staff staff = null;
        String sql = "SELECT id, user_id, phone_number, full_name, address, status FROM Staff WHERE id = ?";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, staffId);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                staff = new Staff(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("phone_number"),
                    rs.getString("full_name"),
                    rs.getString("address"),
                    rs.getInt("status")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return staff;
    }
}
