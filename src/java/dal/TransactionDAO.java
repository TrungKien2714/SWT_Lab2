/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Transaction;

/**
 *
 * @author dtam6
 */
public class TransactionDAO extends DBContext {

    public List<Transaction> getTransactionByCustomerId(int customerID) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transaction WHERE customer_id = ?;";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, customerID);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getString("payment_method"),
                    rs.getInt("payment_status"),
                    rs.getDate("payment_date"),
                    rs.getInt("quantity"),
                    rs.getDouble("amount_paid"),
                    rs.getString("voucher_code"));
                transactions.add(transaction);
            }

        } catch (SQLException ex) {
//            Logger.getLogger(TransactionDAO.class
//                .getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        }
        return transactions;
    }

    public static void main(String[] args) {
        TransactionDAO dao = new TransactionDAO();
        List<Transaction> list = dao.getTransactionByCustomerId(1);
        System.out.println(list);
    }
}
