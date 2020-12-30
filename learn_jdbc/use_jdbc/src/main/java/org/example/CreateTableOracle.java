package org.example;

import java.sql.*;

public class CreateTableOracle {
    Statement stmt = null;
    Connection conn = null;

    private void release() {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
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

            stmt = conn.createStatement();

            String sql = "CREATE TABLE test.t_test1 " +
                    "(id INTEGER not NULL, " +
                    " column1 VARCHAR2(255), " +
                    " column2 VARCHAR2(255), " +
                    " column3 VARCHAR2(255), " +
                    " column4 VARCHAR2(255), " +
                    " column5 VARCHAR2(255), " +
                    " column6 VARCHAR2(255), " +
                    " column7 VARCHAR2(255), " +
                    " column8 VARCHAR2(255), " +
                    " column9 VARCHAR2(255), " +
                    " column10 VARCHAR2(255), " +
                    " column11 VARCHAR2(255), " +
                    " column12 VARCHAR2(255), " +
                    " column13 VARCHAR2(255), " +
                    " column14 VARCHAR2(255), " +
                    " column15 VARCHAR2(255), " +
                    " column16 VARCHAR2(255), " +
                    " column17 VARCHAR2(255), " +
                    " column18 VARCHAR2(255), " +
                    " column19 VARCHAR2(255), " +
                    " column20 VARCHAR2(255))";

            stmt.executeUpdate(sql);
            System.out.println("Created table success!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            release();
        }
    }

    public static void main(String[] args) {
        CreateTableOracle createTableOracle = new CreateTableOracle();
        createTableOracle.run();
    }
}
