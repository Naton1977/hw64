package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SecurityContext {
    private static SecurityContext instance;
    private String URL;
    private String UserSQL;
    private String PasswordSQL;


    private SecurityContext() {

    }

    public static SecurityContext getInstance() {
        if (instance == null) {
            instance = new SecurityContext();
        }
        return instance;
    }


    public Connection connection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, UserSQL, PasswordSQL);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return connection;
    }

    public void initSQl(String URL, String userSQL, String passwordSQL) {
        this.URL = URL;
        this.UserSQL = userSQL;
        this.PasswordSQL = passwordSQL;
    }

}
