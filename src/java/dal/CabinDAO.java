package dal;

import model.Cabin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dtam6
 */
public class CabinDAO extends DBContext {

    public Cabin getCabinById(int cabinId) {
        Cabin cabin = null;
        String sql = "SELECT id, name, class, numseat, img_url, train_id FROM Cabin WHERE id = ?";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, cabinId);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                cabin = new Cabin(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("class"),
                    rs.getInt("numseat"),
                    rs.getString("img_url"),
                    rs.getInt("train_id")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(CabinDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cabin;
    }
}
