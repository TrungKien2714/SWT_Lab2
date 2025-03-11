/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author ASUS
 */
public class PasswordDAO extends DBContext {
    public User getUserByEmail(String email){
        String sql="SELECT * FROM User WHERE email = ?";
        try {
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public User getUserById(int userId) {
        String sql = "Select * from User where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                return new User(
                        rs.getInt(1),
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public void updatePassword(String email, String password) {
       String sql = "UPDATE user SET password = ? WHERE email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, email);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
