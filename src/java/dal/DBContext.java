package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    protected Connection connection;

    public DBContext() {

        try {
            String url = "jdbc:mysql://localhost:3306/vetautratra?zeroDateTimeBehavior=CONVERT_TO_NULL";
            String user = "root";
            String password = "123456";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
