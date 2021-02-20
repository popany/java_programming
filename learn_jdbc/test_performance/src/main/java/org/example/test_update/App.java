package org.example.test_update;

import java.sql.*;

public class App {
    static Connection conn;

    // table structure
    // CREATE TABLE `test_update` (
    //     `id` INT(10,0) NULL DEFAULT NULL,
    //     `count` INT(10,0) NULL DEFAULT NULL,
    //     `elapsed_ms` BIGINT(19,0) NULL DEFAULT NULL
    // )
    static void insertTable(int id, int count, long elapsedMs) {
        try {
            Statement stmt = conn.createStatement();
            String sql = String.format("INSERT INTO test_update (id, count, elapsed_ms) VALUES (%d, %d, %d)", id, count, elapsedMs);
        
            if (stmt.executeUpdate(sql) != 1) {
                System.out.println("Insert failed, count: " + count);
            }
        } catch (Exception e) {
            System.out.println("Exception, count: " + count);
            e.printStackTrace();
        }
    }

    static void updateTable(int id, int count, long elapsedMs) {
        try {
            Statement stmt = conn.createStatement();
            String sql = String.format("UPDATE test_update SET count=%d, elapsed_ms=%d WHERE id = %d", count, elapsedMs, id);
        
            if (stmt.executeUpdate(sql) != 1) {
                System.out.println("Update failed, count: " + count);
            }
        } catch (Exception e) {
            System.out.println("Exception, count: " + count);
            e.printStackTrace();
        }
    }

    static void insertOrUpdateTable(int id, int totalCount) {
        Long startTime = System.nanoTime();
        insertTable(id, 1, 0);
        for (int i = 1; i < totalCount; i++) {
            Long elapsed = System.nanoTime() - startTime;
            elapsed /= 1000000;
            updateTable(id, i + 1, elapsed);
        }
    }

    static void connect() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://mysql_host:3306/test";
        String user = "root";
        String password = "abc";
    
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        try {
            connect();

            int id = 0;
            if (args.length == 1) {
                id = Integer.parseInt(args[0]);
            }
            int totalCount = 10000;
            if (args.length == 2) {
                totalCount = Integer.parseInt(args[1]);
            }
            
            insertOrUpdateTable(id, totalCount);
       } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
