package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppMysqlAccessorTest {
    MysqlAccessor mysqlAccessor = new MysqlAccessor();

    @Before
    public void setup() throws ClassNotFoundException, SQLException, IOException {
        Properties prop = new Properties();
        prop.setProperty("db.url", "jdbc:mysql://mysql8_host/ testdb");
        prop.setProperty("db.user", "root");
        prop.setProperty("db.password", "abc");
        mysqlAccessor.init(prop);
    }

    @After
    public void tearDown() {
        mysqlAccessor.close();
    }

    @Test
    public void assertMysqlVersion() throws SQLException {
        assertEquals("8.0.22", mysqlAccessor.getMysqlVersion());
    }

    @Test
    public void testCreateTable() throws SQLException
    {
        String tableName = "t_test_create_table";
        if (mysqlAccessor.checkExistTable(tableName)) {
            mysqlAccessor.dropTable(tableName);
        }

        List<String> columnNames = Arrays.asList(new String[]{ "id", "name" });
        List<String> columnTypes = Arrays.asList(new String[]{ "INT", "VARCHAR(20)" });
        mysqlAccessor.createTable(tableName, columnNames, columnTypes);

        assertEquals(columnNames, mysqlAccessor.getColumnNames(tableName));
    }

    void dropCreateTable(String tableName, List<String> columnNames, List<String> columnTypes) throws SQLException {
        if (mysqlAccessor.checkExistTable(tableName)) {
            mysqlAccessor.dropTable(tableName);
        }
        mysqlAccessor.createTable(tableName, columnNames, columnTypes);
    }

    String getSqlTypeName(int sqlType) {
        switch (sqlType) {
            case java.sql.Types.BIT:
                return "java.sql.Types.BIT";
            case java.sql.Types.TINYINT:
                return "java.sql.Types.TINYINT";
            case java.sql.Types.SMALLINT:
                return "java.sql.Types.SMALLINT";
            case java.sql.Types.INTEGER:
                return "java.sql.Types.INTEGER";
            case java.sql.Types.BIGINT:
                return "java.sql.Types.BIGINT";
            case java.sql.Types.FLOAT:
                return "java.sql.Types.FLOAT";
            case java.sql.Types.REAL:
                return "java.sql.Types.REAL";
            case java.sql.Types.DOUBLE:
                return "java.sql.Types.DOUBLE";
            case java.sql.Types.NUMERIC:
                return "java.sql.Types.NUMERIC";
            case java.sql.Types.DECIMAL:
                return "java.sql.Types.DECIMAL";
            case java.sql.Types.CHAR:
                return "java.sql.Types.CHAR";
            case java.sql.Types.VARCHAR:
                return "java.sql.Types.VARCHAR";
            case java.sql.Types.LONGVARCHAR:
                return "java.sql.Types.LONGVARCHAR";
            case java.sql.Types.DATE:
                return "java.sql.Types.DATE";
            case java.sql.Types.TIME:
                return "java.sql.Types.TIME";
            case java.sql.Types.TIMESTAMP:
                return "java.sql.Types.TIMESTAMP";
            case java.sql.Types.BINARY:
                return "java.sql.Types.BINARY";
            case java.sql.Types.VARBINARY:
                return "java.sql.Types.VARBINARY";
            case java.sql.Types.LONGVARBINARY:
                return "java.sql.Types.LONGVARBINARY";
            case java.sql.Types.NULL:
                return "java.sql.Types.NULL";
            case java.sql.Types.OTHER:
                return "java.sql.Types.OTHER";
            case java.sql.Types.JAVA_OBJECT:
                return "java.sql.Types.JAVA_OBJECT";
            case java.sql.Types.DISTINCT:
                return "java.sql.Types.DISTINCT";
            case java.sql.Types.STRUCT:
                return "java.sql.Types.STRUCT";
            case java.sql.Types.ARRAY:
                return "java.sql.Types.ARRAY";
            case java.sql.Types.BLOB:
                return "java.sql.Types.BLOB";
            case java.sql.Types.CLOB:
                return "java.sql.Types.CLOB";
            case java.sql.Types.REF:
                return "java.sql.Types.REF";
            case java.sql.Types.DATALINK:
                return "java.sql.Types.DATALINK";
            case java.sql.Types.BOOLEAN:
                return "java.sql.Types.BOOLEAN";
            case java.sql.Types.ROWID:
                return "java.sql.Types.ROWID";
            case java.sql.Types.NCHAR:
                return "java.sql.Types.NCHAR";
            case java.sql.Types.NVARCHAR:
                return "java.sql.Types.NVARCHAR";
            case java.sql.Types.LONGNVARCHAR:
                return "java.sql.Types.LONGNVARCHAR";
            case java.sql.Types.NCLOB:
                return "java.sql.Types.NCLOB";
            case java.sql.Types.SQLXML:
                return "java.sql.Types.SQLXML";
            case java.sql.Types.REF_CURSOR:
                return "java.sql.Types.REF_CURSOR";
            case java.sql.Types.TIME_WITH_TIMEZONE:
                return "java.sql.Types.TIME_WITH_TIMEZONE";
            case java.sql.Types.TIMESTAMP_WITH_TIMEZONE:
                return "java.sql.Types.TIMESTAMP_WITH_TIMEZONE";
            default:
                return "unknown";
        }
    }

    @Test
    public void testDatatypeTinyint() throws SQLException {
        String tableName = "test_datatype_tinyint";
        List<String> columnNames = Arrays.asList(new String[]{ "A" });
        List<String> columnTypes = Arrays.asList(new String[]{ "TINYINT" });
        dropCreateTable(tableName, columnNames, columnTypes);
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.Integer", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.TINYINT", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("TINYINT", columnInfos.get(0).getColumnTypeName());
        assertEquals(3, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());
    }
}
