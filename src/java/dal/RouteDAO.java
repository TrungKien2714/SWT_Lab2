package dal;

import model.Route;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import model.Cabin;

public class RouteDAO extends DBContext {

    public List<Route> getAllRoute() {
        List<Route> list = new ArrayList<>();
        try {
            String sql = "SELECT r.id, r.train_id, r.route_code, r.description, dep.name AS departure_station_name, arr.name AS arrival_station_name, r.departure_time, r.arrival_time, r.status "
                    + " FROM Route r "
                    + " JOIN Station dep ON r.departure_station = dep.station_code "
                    + " JOIN Station arr ON r.arrival_station = arr.station_code;";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new Route(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8),
                        rs.getInt(9)));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public List<Route> searchRoute(String departureStation, String arrivalStation, String departureDate, String arrivalDate) {
        List<Route> list = new ArrayList<>();
        try {
            // Xây dựng câu SQL động
            StringBuilder sql = new StringBuilder("SELECT r.id, r.train_id, r.route_code, r.description, dep.name AS departure_station_name, arr.name AS arrival_station_name, r.departure_time, r.arrival_time, r.status "
                    + " FROM Route r "
                    + " JOIN Station dep ON r.departure_station = dep.station_code "
                    + " JOIN Station arr ON r.arrival_station = arr.station_code WHERE 1=1"); // 1=1 để dễ dàng thêm các điều kiện sau

            // Dùng PreparedStatement để tránh SQL Injection
            if (departureStation != null && !departureStation.trim().isEmpty()) {
                sql.append(" AND departure_station LIKE ?");
            }
            if (arrivalStation != null && !arrivalStation.trim().isEmpty()) {
                sql.append(" AND arrival_station LIKE ?");
            }
            if (departureDate != null && !departureDate.trim().isEmpty()) {
                sql.append(" AND departure_time LIKE ?");
            }
            if (arrivalDate != null && !arrivalDate.trim().isEmpty()) {
                sql.append(" AND arrival_time LIKE ?");
            }

            // Sử dụng PreparedStatement với câu SQL động
            PreparedStatement stm = connection.prepareStatement(sql.toString());

            // Gán các giá trị cho PreparedStatement
            int parameterIndex = 1;
            if (departureStation != null && !departureStation.trim().isEmpty()) {
                stm.setString(parameterIndex++, "%" + departureStation + "%");
            }
            if (arrivalStation != null && !arrivalStation.trim().isEmpty()) {
                stm.setString(parameterIndex++, "%" + arrivalStation + "%");
            }
            if (departureDate != null && !departureDate.trim().isEmpty()) {
                stm.setString(parameterIndex++, "%" + departureDate + "%");
            }
            if (arrivalDate != null && !arrivalDate.trim().isEmpty()) {
                stm.setString(parameterIndex++, "%" + arrivalDate + "%");
            }

            // Thực thi câu truy vấn
            ResultSet rs = stm.executeQuery();

            // Duyệt qua kết quả trả về và tạo đối tượng Route
            while (rs.next()) {
                list.add(new Route(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8),
                        rs.getInt(9)));
            }

            // Đảm bảo đóng ResultSet và PreparedStatement
            rs.close();
            stm.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public Map<String, Object> getRouteByIdd(int id) {
        Map<String, Object> routeData = new HashMap<>();
        String sql = "SELECT "
                + "r.id, r.route_code, r.description, r.departure_time, r.arrival_time, r.status, "
                + "t.id AS train_id, t.train_model AS train_name, "
                + "dep.name AS departure_station_name, arr.name AS arrival_station_name, "
                + "seller.full_name AS seller_name, "
                + "COUNT(DISTINCT c.id) AS total_cabins, "
                + "COUNT(CASE WHEN c.class = 'Business' THEN st.id END) AS Seat_Business_amount, "
                + "COUNT(CASE WHEN c.class = 'Economy' THEN st.id END) AS Seat_Economy_amount, "
                + "MIN(CASE WHEN c.class = 'Business' THEN st.price END) AS Business_price, "
                + "MIN(CASE WHEN c.class = 'Economy' THEN st.price END) AS Economy_price "
                + "FROM Route r "
                + "JOIN Train t ON r.train_id = t.id "
                + "JOIN Seller seller ON t.owner = seller.id "
                + "JOIN Station dep ON r.departure_station = dep.station_code "
                + "JOIN Station arr ON r.arrival_station = arr.station_code "
                + "LEFT JOIN Cabin c ON t.id = c.train_id "
                + "LEFT JOIN Seat st ON c.id = st.cabin_id "
                + "WHERE r.id = ? "
                + "GROUP BY r.id, t.train_model, r.route_code, r.description, dep.name, arr.name, "
                + "r.departure_time, r.arrival_time, r.status, seller.full_name";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                routeData.put("id", rs.getInt("id"));
                routeData.put("trainId", rs.getInt("train_id"));
                routeData.put("trainName", rs.getString("train_name"));
                routeData.put("routeCode", rs.getString("route_code"));
                routeData.put("description", rs.getString("description"));
                routeData.put("departureStation", rs.getString("departure_station_name"));
                routeData.put("arrivalStation", rs.getString("arrival_station_name"));
                routeData.put("departureTime", rs.getTimestamp("departure_time"));
                routeData.put("arrivalTime", rs.getTimestamp("arrival_time"));
                routeData.put("status", rs.getInt("status"));
                routeData.put("owner", rs.getString("seller_name"));
                routeData.put("totalCabins", rs.getInt("total_cabins"));
                routeData.put("businessAmount", rs.getInt("Seat_Business_amount"));
                routeData.put("economyAmount", rs.getInt("Seat_Economy_amount"));
                routeData.put("businessPrice", rs.getBigDecimal("Business_price"));
                routeData.put("economyPrice", rs.getBigDecimal("Economy_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routeData;
    }

    public List<Cabin> getCabinFromRouteId(int routeId) {
        List<Cabin> list = new ArrayList<>();
        try {
            String sql = "SELECT c.id, c.name, c.class, c.numseat, c.img_url, c.train_id FROM Route r "
                    + " JOIN Train t ON r.train_id = t.id "
                    + " JOIN Cabin c ON t.id = c.train_id "
                    + " WHERE r.id = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, routeId); // Truyền ID của Route vào truy vấn
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new Cabin(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getInt(6)));
}
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }
    public Route getRouteById(int id) {
        String sql = " SELECT * FROM Route WHERE id = ?; ";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Route route = new Route(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7),
                        rs.getTimestamp(8),
                        rs.getInt(9));
                return route;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
