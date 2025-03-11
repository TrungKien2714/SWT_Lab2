    package dal;

import model.Train;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dtam6
 */
public class TrainDAO extends DBContext {

    public Train getTrainById(int trainId) {
        Train train = null;
        String sql = "SELECT id, train_model, total_seats, numcabin, status, owner FROM Train WHERE id = ?";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, trainId);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                train = new Train(
                    rs.getInt("id"),
                    rs.getString("train_model"),
                    rs.getInt("total_seats"),
                    rs.getInt("numcabin"),
                    rs.getInt("status"),
                    rs.getInt("owner")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return train;
    }
}
