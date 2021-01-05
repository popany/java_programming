// https://www.tutorialspoint.com/jdbc/jdbc-db-connections.htm
// https://mkyong.com/jdbc/how-to-connect-to-mysql-with-jdbc-driver-java/

package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MysqlAccessor {

    Connection conn = null;

    public void init(Properties prop) throws SQLException, ClassNotFoundException {
        Properties info = new Properties( );
        info.put("user", prop.getProperty("db.user"));
        info.put("password", prop.getProperty("db.password"));

        // https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html#package.description
        // auto java.sql.Driver discovery -- no longer need to load a java.sql.Driver class via Class.forName
        conn = DriverManager.getConnection(prop.getProperty("db.url"), info);
    }

    public void close() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMysqlVersion() throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            ResultSet resultSet = stmt.executeQuery("select @@version as version");
            resultSet.next();
            return resultSet.getString(1);
        } finally {
            stmt.close();
        }
    }

    public void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();

        int columnCount = rsmd.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(rsmd.getColumnName(i + 1));
        }
        System.out.println("");

        while (resultSet.next()) {
            for (int i = 0; i < columnCount; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                String columnValue = resultSet.getString(i + 1);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
    }

    public void printResult(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            printResultSet(resultSet);
        } finally {
            stmt.close();
        }
    }

    public void dropTable(String tableName) throws SQLException {
        System.out.println("DROP TABLE " + tableName);
        Statement stmt = conn.createStatement();
        try {
            stmt.execute(String.format("DROP TABLE %s", tableName));
        } finally {
            stmt.close();
        }
    }

    boolean checkExistTable(String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});
        return resultSet.next();
    }

    List<String> getColumns(String tableName, String info) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getColumns(null, null, tableName, null);
        ArrayList<String> columnNames = new ArrayList<String>();

        while (resultSet.next()) {
            String columnName = resultSet.getString(info);
            columnNames.add(columnName);
        }
        return columnNames;
    }

    public List<String> getColumnNames(String tableName) throws SQLException {
        return getColumns(tableName, "COLUMN_NAME");
    }

    public List<String> getColumnTypes(String tableName) throws SQLException {
        return getColumns(tableName, "TYPE_NAME");
    }

    void createTable(String tableName, List<String> columnNames, List<String> columnTypes) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE `" + tableName + "` (");
        for (int i = 0; i < columnNames.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("\n");
            sb.append("    `" + columnNames.get(i) + "` " + columnTypes.get(i));
        }
        sb.append(")");
        System.out.println(sb.toString());

        Statement stmt = conn.createStatement();
        try {
            stmt.execute(sb.toString());
        } finally {
            stmt.close();
        }
    }

    public List<ColumnInfo> getColumnInfos(String tableName) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            ResultSet res = stmt.executeQuery(String.format("select * from %s where 1<0", tableName));
            ResultSetMetaData rsmd=res.getMetaData();

            List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();

            int columnCount = rsmd.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setCatalogName(rsmd.getCatalogName(i + 1));
                columnInfo.setColumnClassName(rsmd.getColumnClassName(i + 1));
                columnInfo.setColumnLabel(rsmd.getColumnLabel(i + 1));
                columnInfo.setColumnName(rsmd.getColumnName(i + 1));
                columnInfo.setColumnType(rsmd.getColumnType(i + 1));
                columnInfo.setColumnTypeName(rsmd.getColumnTypeName(i + 1));
                columnInfo.setPrecision(rsmd.getPrecision(i + 1));
                columnInfo.setScale(rsmd.getScale(i + 1));  // Gets the designated column's number of digits to right of the decimal point. 0 is returned for data types where the scale is not applicable.

                columnInfos.add(columnInfo);
            }
            return columnInfos;
        } finally {
            stmt.close();
        }
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public Statement getStatement() throws SQLException {
        return conn.createStatement();
    }
}
