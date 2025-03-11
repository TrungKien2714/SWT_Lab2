package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Route;
import model.Station;
import model.Train;

public class SellerDAO {
    private final Connection connection; // Connection sẽ truyền từ ngoài vào

    // Constructor nhận Connection
    public SellerDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Station> getListStation() {
        List<Station> list = new ArrayList<>();
        String sql = "SELECT * FROM vetautratra.station";
        try (PreparedStatement pre = connection.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                list.add(new Station(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Train> getListTrain() {
        List<Train> list = new ArrayList<>();
        String sql = "SELECT * FROM vetautratra.train";
        try (PreparedStatement pre = connection.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                list.add(new Train(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addRoute(int trainId, String routeCode, String describe, String departureDateTime, String arrivalDateTime, String departureStation, String arrivalStation) {
        String sql = "INSERT INTO Route (train_id, route_code, description, departure_time, arrival_time, departure_station, arrival_station) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Timestamp departureTimestamp = new Timestamp(dateTimeFormat.parse(departureDateTime).getTime());
            Timestamp arrivalTimestamp = new Timestamp(dateTimeFormat.parse(arrivalDateTime).getTime());

            pre.setInt(1, trainId);
            pre.setString(2, routeCode);
            pre.setString(3, describe);
            pre.setTimestamp(4, departureTimestamp);
            pre.setTimestamp(5, arrivalTimestamp);
            pre.setString(6, departureStation);
            pre.setString(7, arrivalStation);
            pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Route> getListRoute() {
        List<Route> list = new ArrayList<>();
        String sql = "SELECT * FROM Route WHERE status IN (0, 1)";
        try (PreparedStatement pre = connection.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                list.add(new Route(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getTimestamp(7), rs.getTimestamp(8), rs.getInt(9)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Route> getListRouteDeleted() {
        List<Route> list = new ArrayList<>();
        String sql = "SELECT * FROM Route WHERE status = 2";
        try (PreparedStatement pre = connection.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                list.add(new Route(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getTimestamp(7), rs.getTimestamp(8), rs.getInt(9)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateRoute(int trainId, String routeCode, String describe, String departureDateTime, String arrivalDateTime, String departureStation, String arrivalStation, int id) {
        String sql = "UPDATE Route SET train_id = ?, route_code = ?, `describe` = ?, departure_time = ?, arrival_time = ?, departure_station = ?, arrival_station = ? WHERE id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Timestamp departureTimestamp = new Timestamp(dateTimeFormat.parse(departureDateTime).getTime());
            Timestamp arrivalTimestamp = new Timestamp(dateTimeFormat.parse(arrivalDateTime).getTime());

            pre.setInt(1, trainId);
            pre.setString(2, routeCode);
            pre.setString(3, describe);
            pre.setTimestamp(4, departureTimestamp);
            pre.setTimestamp(5, arrivalTimestamp);
            pre.setString(6, departureStation);
            pre.setString(7, arrivalStation);
            pre.setInt(8, id);
            pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Route getRouteByCode(int routeId) {
        String sql = "SELECT * FROM Route WHERE id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, routeId);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return new Route(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getTimestamp(7), rs.getTimestamp(8), rs.getInt(9));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStatusRoute(String routeCode) {
        String sql = "UPDATE Route SET status = CASE WHEN status = 0 THEN 1 ELSE 0 END WHERE route_code = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setString(1, routeCode);
            pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRouteByCode(int routeId) {
        String sql = "UPDATE Route SET status = 2 WHERE id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, routeId);
            pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
