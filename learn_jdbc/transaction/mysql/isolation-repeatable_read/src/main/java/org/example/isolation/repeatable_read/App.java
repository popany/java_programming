package org.example.isolation.repeatable_read;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App 
{
    static private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://172.21.0.3/db_test", "test", "test");
    }

    static private void printResultSet(ResultSet resultSet) throws SQLException {
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

    static private void updateInOtherTransaction() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                Connection conn = getConnection();
                conn.setAutoCommit(false);
                Statement stat = conn.createStatement();
                stat.executeUpdate("update t_test set name='bar' where id=1");
                conn.commit();
                return 0;
            }
        });
        try {
            future.get(); //wait for a thread to complete
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    /* prerequest:

        CREATE TABLE `t_test` (
        `id` INT NULL,
        `name` VARCHAR(50) NULL
        )
        ENGINE=InnoDB;

        INSERT t_test (id, NAME) VALUES(1, "foo")
    */
    public static void main( String[] args ) {
        try {
            Connection conn = getConnection();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select id, name from t_test");
            printResultSet(rs); // print: "1, foo"

            updateInOtherTransaction();

            rs = stat.executeQuery("select id, name from t_test");
            printResultSet(rs); // print: "1, foo", because this transaction's isolation level is REPEATABLE_READ, can't see the update of name in the transaction

            conn.commit(); // transaction will be closed after commit
            rs = stat.executeQuery("select id, name from t_test");
            printResultSet(rs); // print: "1, bar", because a new transaction was created
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
