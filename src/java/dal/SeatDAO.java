package dal;

import model.Seat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dtam6
 */
public class SeatDAO extends DBContext {

    public Seat getSeatById(int seatId) {
        Seat seat = null;
        String sql = "SELECT id, name, price, cabin_id, status FROM Seat WHERE id = ?";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, seatId);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                seat = new Seat(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getLong("price"),
                    rs.getInt("cabin_id"),
                    rs.getInt("status")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return seat;
    }
}
