package org.example;

import java.sql.*;

public class ConnectToOracle {
    Connection conn = null;

    private void release() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
            conn = DriverManager.getConnection(dbURL, "test", "test");
            System.out.println("connect success!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            release();
        }
    }

    public static void main(String[] args) {
        ConnectToOracle connectToOracle = new ConnectToOracle();
        connectToOracle.run();
    }
}
