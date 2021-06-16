// wikipedia - Isolation (database systems) - https://en.wikipedia.org/wiki/Isolation_(database_systems)
// MySQL 8.0 Reference Manual - 15.7.4 Phantom Rows - https://dev.mysql.com/doc/refman/8.0/en/innodb-next-key-locking.html

package org.example.read_phenomena.phantom_reads;

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

    static private void insertInOtherTransaction() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                Connection conn = getConnection();
                conn.setAutoCommit(false);
                Statement stat = conn.createStatement();
                stat.executeUpdate("insert into t_test(id, name) values (2, 'baz')");
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

        INSERT t_test (id, NAME) VALUES(1, "foo");
        INSERT t_test (id, NAME) VALUES(3, "bar");
    */
    public static void main( String[] args ) {
        try {
            Connection conn = getConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            conn.setAutoCommit(false);
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select id, name from t_test where id between 1 and 3");
            printResultSet(rs); // print: "1, foo" + "3, bar"

            insertInOtherTransaction();

            rs = stat.executeQuery("select id, name from t_test where id between 1 and 3");
            printResultSet(rs); // print: "1, foo" + "3, bar", phantom read not happen, different with the read phenomena in wikipedia-Isolation(the ANSI/ISO standard SQL 92)
            try {
                stat.executeUpdate("insert into t_test(id, name) values (2, 'baz')"); // throws - java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '2' for key 't_test.PRIMARY'
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

            conn.commit(); // transaction will be closed after commit
            rs = stat.executeQuery("select id, name from t_test where id between 1 and 3");
            printResultSet(rs); // print: "1, foo" + "2, baz" + "3, bar"
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
