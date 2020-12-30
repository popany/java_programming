package org.example;

import org.springframework.util.StopWatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WriteToTableOracle {

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

    private List<String> generateSqls(int count, int startId) {
        List<String> sqls = new ArrayList<String>();

        for (int i = 0; i < count; ++i) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO TEST.T_TEST1 (id," +
                    "column1," +
                    "column2," +
                    "column3," +
                    "column4," +
                    "column5," +
                    "column6," +
                    "column7," +
                    "column8," +
                    "column9," +
                    "column10," +
                    "column11," +
                    "column12," +
                    "column13," +
                    "column14," +
                    "column15," +
                    "column16," +
                    "column17," +
                    "column18," +
                    "column19," +
                    "column20" +
                    ") VALUES (");

            int id = startId + i;
            sb.append("'" + id + "'");
            sb.append(",'" + id + "_column1'");
            sb.append(",'" + id + "_column2'");
            sb.append(",'" + id + "_column3'");
            sb.append(",'" + id + "_column4'");
            sb.append(",'" + id + "_column5'");
            sb.append(",'" + id + "_column6'");
            sb.append(",'" + id + "_column7'");
            sb.append(",'" + id + "_column8'");
            sb.append(",'" + id + "_column9'");
            sb.append(",'" + id + "_column10'");
            sb.append(",'" + id + "_column11'");
            sb.append(",'" + id + "_column12'");
            sb.append(",'" + id + "_column13'");
            sb.append(",'" + id + "_column14'");
            sb.append(",'" + id + "_column15'");
            sb.append(",'" + id + "_column16'");
            sb.append(",'" + id + "_column17'");
            sb.append(",'" + id + "_column18'");
            sb.append(",'" + id + "_column19'");
            sb.append(",'" + id + "_column20')");

            sqls.add(sb.toString());
        }
        return sqls;
    }

    private void insertTable(List<String> sqls) {
        Statement stat = null;
        try {
            stat = conn.createStatement();
            for (String sql : sqls) {
                stat.addBatch(sql);
            }
            stat.executeBatch();
            conn.commit();
        } catch (SQLException e) {
             e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void run() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
            conn = DriverManager.getConnection(dbURL, "test", "test");

            int startId = 1;
            int count = 1000;
            StopWatch stopWatch = new StopWatch();
            for (int i = 0; i < 10; ++i) {
                stopWatch.start();
                List<String> sqls = generateSqls(count, startId);
                startId += count;
                insertTable(sqls);
                stopWatch.stop();
                System.out.println(String.format("i: %d, %s(s)", i, stopWatch.getTotalTimeSeconds()));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            release();
        }
    }

    public static void main(String[] args) {
        WriteToTableOracle writeToTableOracle = new WriteToTableOracle();
        writeToTableOracle.run();
    }
}
