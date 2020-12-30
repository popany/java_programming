package org.example;

import java.sql.*;

public class HiveAccessor {
    private static String hiveJdbcDriverName = "org.apache.hive.jdbc.HiveDriver";
    private static Connection conn;

    private static void loadHiveJdbcDriver() throws ClassNotFoundException
    {
        Class.forName(hiveJdbcDriverName);
    }

    static void printResultSet(ResultSet resultSet) throws java.sql.SQLException
    {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }

    static void showTables()
    {
        ;
    }

    static void showDatabases() throws java.sql.SQLException
    {
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("show databases");
        printResultSet(res);
    }

    static void init() throws ClassNotFoundException, java.sql.SQLException
    {
        loadHiveJdbcDriver();
        conn = DriverManager.getConnection("jdbc:hive2://cdh02:10000/default", "hive", "");
    }

    static public void main(String[] args)
    {
        try {
            init();
            showDatabases();
        }
        catch (Exception ex)
        {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
