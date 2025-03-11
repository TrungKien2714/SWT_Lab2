package dal;

import model.Station;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class StationDAO extends DBContext {

    public List<Station> getAllStation() {
        List<Station> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Station";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new Station(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public List<Station> searchStation(String query) {
        List<Station> list = new ArrayList<>();
        try {
            // Sử dụng câu lệnh SQL với LIKE để tìm kiếm trạm theo tên
            String sql = "SELECT * FROM Station WHERE name LIKE ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + query + "%");  // Tìm kiếm với từ khóa (query)
            ResultSet rs = stm.executeQuery();

            // Duyệt qua kết quả trả về và tạo đối tượng Station
            while (rs.next()) {
                list.add(new Station(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }
}
