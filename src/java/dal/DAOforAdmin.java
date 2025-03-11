/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LoginCounter;
import model.SellerRevenue;
import model.SellerTicketSale;
import model.UserDetail;
import model.View;

/**
 *
 * @author Aus TUF GAMAING
 */
public class DAOforAdmin extends DBContext {

    public List<UserDetail> getAllUserByRole(String role) {
        List<UserDetail> users = new ArrayList<>();
        String sql = "select * from User\n"
                + " join " + role + " on User.id = " + role + ".user_id";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String address = rs.getString("address");
                String phone_number = rs.getString("phone_number");
                String full_name = rs.getString("full_name");
                int status = rs.getInt("status");
                UserDetail user = new UserDetail(id, email, password, role, full_name, phone_number, address, status);
                users.add(user);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    public List<String> getAllEmail() {
        List<String> emails = new ArrayList<>();
        String sql = "Select email from user";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                emails.add(email);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return emails;
    }

    public UserDetail getChoosedUser(String role, int id) {
        UserDetail user = new UserDetail();
        String sql = "select * from User\n"
                + " join " + role + " on User.id = " + role + ".user_id"
                + " where user_id = " + id;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phoneNumber = rs.getString("phone_number");
                String address = rs.getString("address");
                int status = rs.getInt("status");

                user = new UserDetail(id, email, password, role, fullName, phoneNumber, address, status);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public void changeStatus(int id, String role) {
        String sql = "UPDATE " + role + "\n"
                + "SET status = CASE WHEN status = 0 THEN 1 ELSE 0 END\n"
                + "WHERE user_id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            pre.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editAcc(String fullname, String phonenumber, String address, String role, int id) {
        String sql = "UPDATE " + role + "\n"
                + "SET full_name = ?, phone_number = ?, address = ?\n"
                + "WHERE user_id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, fullname);
            pre.setString(2, phonenumber);
            pre.setString(3, address);
            pre.setInt(4, id);

            pre.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addNewAcc(String fullname, String email, String phonenumber, String password, String role) {
        String sql;
        sql = "INSERT INTO user (email, password, role) VALUES (?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);

            pre.setString(1, email);
            pre.setString(2, password);
            pre.setString(3, role);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        sql = "select id from user where email = '" + email + "'";
        int id = 0;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        sql = "INSERT INTO " + role + " (user_id, phone_number, full_name, address, status) VALUES (?, ?, ?, ?, 1)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);

            pre.setInt(1, id);
            pre.setString(2, phonenumber);
            pre.setString(3, fullname);
            pre.setString(4, "");
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UserDetail getUserDetail(int id, String role) {
        UserDetail user = new UserDetail();
        String sql = "select * from User\n"
                + " join " + role + " on User.id = " + role + ".user_id\n"
                + " where user_id = " + id;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                String address = rs.getString("address");
                String phone_number = rs.getString("phone_number");
                String full_name = rs.getString("full_name");
                int status = rs.getInt("status");
                user = new UserDetail(id, email, password, role, full_name, phone_number, address, status);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public void viewCounted() {
        String sql;
        sql = "INSERT INTO View (date) values (?)";
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = myDateObj.format(myFormatObj);
        try {
            PreparedStatement pre = connection.prepareStatement(sql);

            pre.setString(1, formattedDate);

            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<View> getView() {
        List<View> views = new ArrayList<>();
        String sql = "select * from View";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");

                View v = new View(id, date);
                views.add(v);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return views;
    }

    public void loginCounted(int id) {
        String sql;
        sql = "INSERT INTO Logincounter (date, user_id) values (?, ?)";
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, formattedDate);
            pre.setInt(2, id);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<LoginCounter> getLoginCounter() {
        List<LoginCounter> lcs = new ArrayList<>();
        String sql = "select * from Logincounter";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                int user_id = rs.getInt("user_id");
                LoginCounter lc = new LoginCounter(id, date, user_id);
                lcs.add(lc);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lcs;
    }

    public List<SellerRevenue> getSellerRevenue() {
        List<SellerRevenue> sr_list = new ArrayList<>();
        String sql = "SELECT full_name, amount_paid, payment_date FROM ticket\n"
                + "join transaction on ticket.transaction_id = transaction.id\n"
                + "join route on ticket.route_id = route.id\n"
                + "join train on route.train_id = train.id\n"
                + "join seller on seller.id = train.owner";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String name = rs.getString("full_name");
                Date date = rs.getDate("payment_date");
                double amount = rs.getDouble("amount_paid");
                SellerRevenue sr = new SellerRevenue(name, date, amount);
                sr_list.add(sr);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sr_list;
    }

    public List<SellerTicketSale> getSellerTicketSale() {
        List<SellerTicketSale> sts_list = new ArrayList<>();
        String sql = "SELECT full_name, booking_date FROM ticket\n"
                + "join route on ticket.route_id = route.id\n"
                + "join train on route.train_id = train.id\n"
                + "join seller on seller.id = train.owner";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String name = rs.getString("full_name");
                Date date = rs.getDate("booking_date");
                SellerTicketSale sts = new SellerTicketSale(name, date);
                sts_list.add(sts);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOforAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sts_list;
    }
}
