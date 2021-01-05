package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public void testCreateTable() throws SQLException {
        String tableName = "t_test_create_table";
        if (mysqlAccessor.checkExistTable(tableName)) {
            mysqlAccessor.dropTable(tableName);
        }

        List<String> columnNames = Arrays.asList(new String[] { "id", "name" });
        List<String> columnTypes = Arrays.asList(new String[] { "INT", "VARCHAR(20)" });
        mysqlAccessor.createTable(tableName, columnNames, columnTypes);
        try {
            assertEquals(columnNames, mysqlAccessor.getColumnNames(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
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

    String createTestDataTypeTable(String tableNameSuffix, String typeName) throws SQLException {
        String tableName = "t_test_datatype_" + tableNameSuffix;
        List<String> columnNames = Arrays.asList(new String[] { "A" });
        List<String> columnTypes = Arrays.asList(new String[] { typeName });
        dropCreateTable(tableName, columnNames, columnTypes);
        return tableName;
    }

    String createTestDataTypeTable(String typeName) throws SQLException {
        return createTestDataTypeTable(typeName, typeName);
    }

    @Test
    public void testDatatypeTinyint() throws SQLException {
        String tableName = createTestDataTypeTable("TINYINT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.Integer", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.TINYINT", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("TINYINT", columnInfos.get(0).getColumnTypeName());
            assertEquals(3, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    void insertTestValue(String tableName, Object value) throws SQLException {
        PreparedStatement pstmt = mysqlAccessor
                .getPreparedStatement(String.format("INSERT INTO %s (A) VALUES (?)", tableName));
        try {
            pstmt.setObject(1, value);
            pstmt.executeUpdate();
        } finally {
            pstmt.close();
        }
    }

    Object getInsertedTestValue(String tableName) throws SQLException {
        Statement stmt = mysqlAccessor.getStatement();
        try {
            ResultSet resultSet = stmt.executeQuery(String.format("select A from %s", tableName));
            resultSet.next();
            return resultSet.getObject(1);
        } finally {
            stmt.close();
        }
    }

    void insertTestValues(String tableName, List<Object> values) throws SQLException {
        PreparedStatement pstmt = mysqlAccessor
                .getPreparedStatement(String.format("INSERT INTO %s (A) VALUES (?)", tableName));
        try {
            for (Object value : values) {
                pstmt.setObject(1, value);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } finally {
            pstmt.close();
        }
    }

    List<Object> getInsertedTestValues(String tableName) throws SQLException {
        Statement stmt = mysqlAccessor.getStatement();
        try {
            ResultSet resultSet = stmt.executeQuery(String.format("select A from %s", tableName));

            List<Object> values = new ArrayList<Object>();
            while (resultSet.next()) {
                values.add(resultSet.getObject(1));
            }
            return values;
        } finally {
            stmt.close();
        }
    }

    @Test
    public void testDatatypeTinyintInsertInt() throws SQLException {
        String tableName = createTestDataTypeTable("TINYINT");
        try {
            insertTestValue(tableName, 127);
            assertEquals(127, getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeTinyintInsertString() throws SQLException {
        String tableName = createTestDataTypeTable("TINYINT");
        try {
            insertTestValue(tableName, "127");
            assertEquals(127, getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeTinyintInsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("TINYINT");

        try {
            insertTestValue(tableName, 128);
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeTinyintInsertValues() throws SQLException {
        String tableName = createTestDataTypeTable("TINYINT");

        try {
            List<Object> values = Arrays.asList(new Object[] { -128, 0, 127 });
            insertTestValues(tableName, values);

            assertEquals(values, getInsertedTestValues(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeSmallint() throws SQLException {
        String tableName = createTestDataTypeTable("SMALLINT");

        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.Integer", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.SMALLINT", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("SMALLINT", columnInfos.get(0).getColumnTypeName());
            assertEquals(5, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeMediumint() throws SQLException {
        String tableName = createTestDataTypeTable("MEDIUMINT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.Integer", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.INTEGER", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("MEDIUMINT", columnInfos.get(0).getColumnTypeName());
            assertEquals(7, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeInt() throws SQLException {
        String tableName = createTestDataTypeTable("INT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.Integer", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.INTEGER", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("INT", columnInfos.get(0).getColumnTypeName());
            assertEquals(10, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeBigint() throws SQLException {
        String tableName = createTestDataTypeTable("BIGINT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.Long", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BIGINT", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("BIGINT", columnInfos.get(0).getColumnTypeName());
            assertEquals(19, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeBit() throws SQLException {
        String tableName = createTestDataTypeTable("BIT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.Boolean", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BIT", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("BIT", columnInfos.get(0).getColumnTypeName());
            assertEquals(1, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeBitInsert() throws SQLException {
        String tableName = createTestDataTypeTable("BIT");
        try {
            insertTestValue(tableName, 0);
            assertEquals(false, getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeBitInsertBoolean() throws SQLException {
        String tableName = createTestDataTypeTable("BIT");
        try {
            insertTestValue(tableName, false);
            assertEquals(false, getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeBitInsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("BIT");
        try {
            insertTestValue(tableName, 2);
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeBitInsertStringThrow() throws SQLException {
        String tableName = createTestDataTypeTable("BIT");
        try {
            insertTestValue(tableName, "0");
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeFloat() throws SQLException {
        String tableName = createTestDataTypeTable("FLOAT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.Float", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.REAL", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("FLOAT", columnInfos.get(0).getColumnTypeName());
            assertEquals(12, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeFloatInsertDouble() throws SQLException {
        String tableName = createTestDataTypeTable("FLOAT");
        try {
            insertTestValue(tableName, Double.valueOf(1.0));
            assertEquals(Float.valueOf(1.0f), getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeFloatInsertBigDecimal() throws SQLException {
        String tableName = createTestDataTypeTable("FLOAT");
        try {
            insertTestValue(tableName, BigDecimal.valueOf(1.0));
            assertEquals(Float.valueOf(1.0f), getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeDouble() throws SQLException {
        String tableName = createTestDataTypeTable("DOUBLE");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.Double", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.DOUBLE", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("DOUBLE", columnInfos.get(0).getColumnTypeName());
            assertEquals(22, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeDoubleInsertBigDecimal() throws SQLException {
        String tableName = createTestDataTypeTable("DOUBLE");
        try {
            insertTestValue(tableName, BigDecimal.valueOf(1.0));
            assertEquals(Double.valueOf(1.0f), getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeDecimal() throws SQLException {
        String tableName = createTestDataTypeTable("DECIMAL");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.math.BigDecimal", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.DECIMAL", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("DECIMAL", columnInfos.get(0).getColumnTypeName());
            assertEquals(10, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeChar() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.CHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("CHAR", columnInfos.get(0).getColumnTypeName());
            assertEquals(1, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    public void testDatatypeCharInsert() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR");
        try {
            insertTestValue(tableName, "s");
            assertEquals("s", getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeCharInsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR");
        try {
            insertTestValue(tableName, "ss");
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeChar10() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR10", "CHAR(10)");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.CHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("CHAR", columnInfos.get(0).getColumnTypeName());
            assertEquals(10, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeChar3Insert() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR", "CHAR(3)");
        try {
            insertTestValue(tableName, "abc");
            assertEquals("abc", getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeChar5Insert() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR", "CHAR(5)");
        try {
            insertTestValue(tableName, "a");
            assertEquals("a", getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeChar3InsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR", "CHAR(3)");
        try {
            insertTestValue(tableName, "abcd");
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeVarchar10() throws SQLException {
        String tableName = createTestDataTypeTable("VARCHAR10", "VARCHAR(10)");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.VARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("VARCHAR", columnInfos.get(0).getColumnTypeName());
            assertEquals(10, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeVarchar3Insert() throws SQLException {
        String tableName = createTestDataTypeTable("VARCHAR", "VARCHAR(3)");
        try {
            insertTestValue(tableName, "abc");
            assertEquals("abc", getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeVarchar5Insert() throws SQLException {
        String tableName = createTestDataTypeTable("VARCHAR", "VARCHAR(5)");
        try {
            insertTestValue(tableName, "a");
            assertEquals("a", getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeVarchar3InsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("VARCHAR", "VARCHAR(3)");
        try {
            insertTestValue(tableName, "abcd");
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeTinytext() throws SQLException {
        String tableName = createTestDataTypeTable("TINYTEXT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.VARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("TINYTEXT", columnInfos.get(0).getColumnTypeName());
            assertEquals(63, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeText() throws SQLException {
        String tableName = createTestDataTypeTable("TEXT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.LONGVARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("TEXT", columnInfos.get(0).getColumnTypeName());
            assertEquals(16383, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeMediumtext() throws SQLException {
        String tableName = createTestDataTypeTable("MEDIUMTEXT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.LONGVARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("MEDIUMTEXT", columnInfos.get(0).getColumnTypeName());
            assertEquals(4194303, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeLongtext() throws SQLException {
        String tableName = createTestDataTypeTable("LONGTEXT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.LONGVARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("LONGTEXT", columnInfos.get(0).getColumnTypeName());
            assertEquals(536870911, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeJson() throws SQLException {
        String tableName = createTestDataTypeTable("JSON");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.LONGVARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("JSON", columnInfos.get(0).getColumnTypeName());
            assertEquals(2147483647, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeBinary() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("BINARY", columnInfos.get(0).getColumnTypeName());
            assertEquals(1, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeBinaryInsert() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY");
        try {
            insertTestValue(tableName, "a");
            assertArrayEquals(new byte[] { 'a' }, (byte[]) getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeBinaryInsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY");
        try {
            insertTestValue(tableName, "ab");
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeBinary3() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY", "BINARY(3)");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("BINARY", columnInfos.get(0).getColumnTypeName());
            assertEquals(3, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeBinary3Insert() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY", "BINARY(3)");
        try {
            insertTestValue(tableName, "abc");
            assertArrayEquals(new byte[] { 'a', 'b', 'c' }, (byte[]) getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeBinary3InsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY", "BINARY(3)");
        try {
            insertTestValue(tableName, "abcd");
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeVarbinary3() throws SQLException {
        String tableName = createTestDataTypeTable("VARBINARY", "VARBINARY(3)");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.VARBINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("VARBINARY", columnInfos.get(0).getColumnTypeName());
            assertEquals(3, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeVarbinary3Insert() throws SQLException {
        String tableName = createTestDataTypeTable("VARBINARY", "VARBINARY(3)");
        try {
            insertTestValue(tableName, "abc");
            assertArrayEquals(new byte[] { 'a', 'b', 'c' }, (byte[]) getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeVarbinary3InsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("VARBINARY", "VARBINARY(3)");
        try {
            insertTestValue(tableName, "abcd");
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeTinyblob() throws SQLException {
        String tableName = createTestDataTypeTable("TINYBLOB");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.VARBINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("TINYBLOB", columnInfos.get(0).getColumnTypeName());
            assertEquals(255, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeTinyblobInsertString() throws SQLException {
        String tableName = createTestDataTypeTable("TINYBLOB");
        try {
            insertTestValue(tableName, "abc");
            assertArrayEquals(new byte[] { 'a', 'b', 'c' }, (byte[]) getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeTinyblobInsertArray() throws SQLException {
        String tableName = createTestDataTypeTable("TINYBLOB");
        try {
            insertTestValue(tableName, new byte[] { 'a', 'b', 'c' });
            assertArrayEquals(new byte[] { 'a', 'b', 'c' }, (byte[]) getInsertedTestValue(tableName));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeMediumblob() throws SQLException {
        String tableName = createTestDataTypeTable("MEDIUMBLOB");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.LONGVARBINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("MEDIUMBLOB", columnInfos.get(0).getColumnTypeName());
            assertEquals(16777215, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeLongblob() throws SQLException {
        String tableName = createTestDataTypeTable("LONGBLOB");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.LONGVARBINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("LONGBLOB", columnInfos.get(0).getColumnTypeName());
            assertEquals(2147483647, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeDate() throws SQLException {
        String tableName = createTestDataTypeTable("DATE");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.sql.Date", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.DATE", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("DATE", columnInfos.get(0).getColumnTypeName());
            assertEquals(10, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeDateInsert() throws SQLException, ParseException {
        String tableName = createTestDataTypeTable("DATE");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date d = sdf.parse("2020-01-01 00:00:00");
            insertTestValue(tableName, d);
            assertEquals(d, getInsertedTestValue(tableName));
            assertEquals("2020-01-01", getInsertedTestValue(tableName).toString());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = java.sql.SQLDataException.class)
    public void testDatatypeDateThrow() throws SQLException, ParseException {
        String tableName = createTestDataTypeTable("DATE");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date d = sdf.parse("2020-01-01 00:00:00");
            insertTestValue(tableName, d);

            Statement stmt = mysqlAccessor.getStatement();
            try {
                ResultSet resultSet = stmt.executeQuery(String.format("select A from %s", tableName));
                resultSet.next();
                resultSet.getInt(1);
            } finally {
                stmt.close();
            }
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeTime() throws SQLException {
        String tableName = createTestDataTypeTable("TIME");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.sql.Time", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.TIME", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("TIME", columnInfos.get(0).getColumnTypeName());
            assertEquals(10, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeTimeInsert() throws SQLException, ParseException {
        String tableName = createTestDataTypeTable("TIME");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date d = sdf.parse("2020-00-00 12:01:02");
            insertTestValue(tableName, d);
            assertEquals(new java.sql.Time(12, 1, 2), getInsertedTestValue(tableName));
            assertEquals("12:01:02", getInsertedTestValue(tableName).toString());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeYear() throws SQLException {
        String tableName = createTestDataTypeTable("YEAR");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.sql.Date", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.DATE", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("YEAR", columnInfos.get(0).getColumnTypeName());
            assertEquals(4, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeYearInsert() throws SQLException, ParseException {
        String tableName = createTestDataTypeTable("YEAR");
        try {
            insertTestValue(tableName, 2020);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date d = sdf.parse("2020-01-01 00:00:00");
            assertEquals(d, getInsertedTestValue(tableName));
            assertEquals("2020-01-01", getInsertedTestValue(tableName).toString());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test(expected = java.sql.SQLException.class)
    public void testDatatypeYearInsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("YEAR");
        try {
            insertTestValue(tableName, new java.sql.Date(0));
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeDatetime() throws SQLException {
        String tableName = createTestDataTypeTable("DATETIME");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.sql.Timestamp", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.TIMESTAMP", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("DATETIME", columnInfos.get(0).getColumnTypeName());
            assertEquals(19, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeDatetimeInsert() throws SQLException, ParseException {
        String tableName = createTestDataTypeTable("DATETIME");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date d = sdf.parse("2020-01-02 12:01:02");
            insertTestValue(tableName, d);

            assertEquals(d, getInsertedTestValue(tableName));
            assertEquals("2020-01-02 12:01:02.0", getInsertedTestValue(tableName).toString());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeTimestamp() throws SQLException {
        String tableName = createTestDataTypeTable("TIMESTAMP");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.sql.Timestamp", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.TIMESTAMP", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("TIMESTAMP", columnInfos.get(0).getColumnTypeName());
            assertEquals(19, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeTimestampInsert() throws SQLException, ParseException {
        String tableName = createTestDataTypeTable("TIMESTAMP");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date d = sdf.parse("2020-01-02 12:01:02");
            insertTestValue(tableName, d);

            assertEquals(d, getInsertedTestValue(tableName));
            assertEquals("2020-01-02 12:01:02.0", getInsertedTestValue(tableName).toString());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }


    @Test
    public void testDatatypePoint() throws SQLException {
        String tableName = createTestDataTypeTable("POINT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("GEOMETRY", columnInfos.get(0).getColumnTypeName());
            assertEquals(2147483647, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeLinestring() throws SQLException {
        String tableName = createTestDataTypeTable("LINESTRING");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("GEOMETRY", columnInfos.get(0).getColumnTypeName());
            assertEquals(2147483647, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypePolygon() throws SQLException {
        String tableName = createTestDataTypeTable("POLYGON");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("GEOMETRY", columnInfos.get(0).getColumnTypeName());
            assertEquals(2147483647, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeGeometry() throws SQLException {
        String tableName = createTestDataTypeTable("GEOMETRY");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("GEOMETRY", columnInfos.get(0).getColumnTypeName());
            assertEquals(2147483647, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeMultipoint() throws SQLException {
        String tableName = createTestDataTypeTable("MULTIPOINT");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("GEOMETRY", columnInfos.get(0).getColumnTypeName());
            assertEquals(2147483647, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeMultilinestring() throws SQLException {
        String tableName = createTestDataTypeTable("MULTILINESTRING");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("GEOMETRY", columnInfos.get(0).getColumnTypeName());
            assertEquals(2147483647, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeMultipolygon() throws SQLException {
        String tableName = createTestDataTypeTable("MULTIPOLYGON");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("GEOMETRY", columnInfos.get(0).getColumnTypeName());
            assertEquals(2147483647, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeGeometrycollection() throws SQLException {
        String tableName = createTestDataTypeTable("GEOMETRYCOLLECTION");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("[B", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("GEOMETRY", columnInfos.get(0).getColumnTypeName());
            assertEquals(2147483647, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeEnum() throws SQLException {
        String tableName = createTestDataTypeTable("ENUM", "ENUM('a', 'bc', '11')");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.CHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("CHAR", columnInfos.get(0).getColumnTypeName());
            assertEquals(2, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }

    @Test
    public void testDatatypeSet() throws SQLException {
        String tableName = createTestDataTypeTable("SET", "SET('a', 'bc', '11')");
        try {
            List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);
            assertEquals("testdb", columnInfos.get(0).getCatalogName());
            assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
            assertEquals("A", columnInfos.get(0).getColumnLabel());
            assertEquals("A", columnInfos.get(0).getColumnName());
            assertEquals("java.sql.Types.CHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
            assertEquals("CHAR", columnInfos.get(0).getColumnTypeName());
            assertEquals(7, columnInfos.get(0).getPrecision());
            assertEquals(0, columnInfos.get(0).getScale());
        } finally {
            mysqlAccessor.dropTable(tableName);
        }
    }
}
