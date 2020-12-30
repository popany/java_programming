package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

    String createTestDataTypeTable(String tableNameSuffix, String typeName) throws SQLException {
        String tableName = "t_test_datatype_" + tableNameSuffix;
        List<String> columnNames = Arrays.asList(new String[]{ "A" });
        List<String> columnTypes = Arrays.asList(new String[]{ typeName });
        dropCreateTable(tableName, columnNames, columnTypes);
        return tableName;
    }

    String createTestDataTypeTable(String typeName) throws SQLException {
        return createTestDataTypeTable(typeName, typeName);
    }

    @Test
    public void testDatatypeTinyint() throws SQLException {
        String tableName = createTestDataTypeTable("TINYINT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.Integer", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.TINYINT", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("TINYINT", columnInfos.get(0).getColumnTypeName());
        assertEquals(3, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    void insertTestValue(String tableName, Object value) throws SQLException {
        PreparedStatement pstmt = mysqlAccessor.getPreparedStatement(String.format("INSERT INTO %s (A) VALUES (?)", tableName));
        pstmt.setObject(1, value);
        pstmt.executeUpdate();
        pstmt.close();
    }

    Object getInsertedTestValue(String tableName) throws SQLException {
        Statement stmt = mysqlAccessor.getStatement();
        ResultSet resultSet = stmt.executeQuery(String.format("select A from %s", tableName));
        resultSet.next();
        return resultSet.getObject(1);
    }

    void insertTestValues(String tableName, List<Object> values) throws SQLException {
        PreparedStatement pstmt = mysqlAccessor.getPreparedStatement(String.format("INSERT INTO %s (A) VALUES (?)", tableName));
        for (Object value : values) {
            pstmt.setObject(1, value);
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        pstmt.close();
    }

    List<Object> getInsertedTestValues(String tableName) throws SQLException {
        Statement stmt = mysqlAccessor.getStatement();
        ResultSet resultSet = stmt.executeQuery(String.format("select A from %s", tableName));

        List<Object> values = new ArrayList<Object>();
        while (resultSet.next()) {
            values.add(resultSet.getObject(1));
        }
        return values;
    }

    @Test
    public void testDatatypeTinyintInsertInt() throws SQLException {
        String tableName = createTestDataTypeTable("TINYINT");

        insertTestValue(tableName, 127);
        assertEquals(127, getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeTinyintInsertString() throws SQLException {
        String tableName = createTestDataTypeTable("TINYINT");

        insertTestValue(tableName, "127");
        assertEquals(127, getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeTinyintInsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("TINYINT");

        insertTestValue(tableName, 128);

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeTinyintInsertValues() throws SQLException {
        String tableName = createTestDataTypeTable("TINYINT");

        List<Object> values = Arrays.asList(new Object[]{ -128, 0, 127 });
        insertTestValues(tableName, values);

        assertEquals(values, getInsertedTestValues(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeSmallint() throws SQLException {
        String tableName = createTestDataTypeTable("SMALLINT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.Integer", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.SMALLINT", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("SMALLINT", columnInfos.get(0).getColumnTypeName());
        assertEquals(5, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeMediumint() throws SQLException {
        String tableName = createTestDataTypeTable("MEDIUMINT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.Integer", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.INTEGER", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("MEDIUMINT", columnInfos.get(0).getColumnTypeName());
        assertEquals(7, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeInt() throws SQLException {
        String tableName = createTestDataTypeTable("INT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.Integer", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.INTEGER", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("INT", columnInfos.get(0).getColumnTypeName());
        assertEquals(10, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeBigint() throws SQLException {
        String tableName = createTestDataTypeTable("BIGINT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.Long", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.BIGINT", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("BIGINT", columnInfos.get(0).getColumnTypeName());
        assertEquals(19, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeBit() throws SQLException {
        String tableName = createTestDataTypeTable("BIT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.Boolean", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.BIT", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("BIT", columnInfos.get(0).getColumnTypeName());
        assertEquals(1, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeBitInsert() throws SQLException {
        String tableName = createTestDataTypeTable("BIT");

        insertTestValue(tableName, 0);
        assertEquals(false, getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeBitInsertBoolean() throws SQLException {
        String tableName = createTestDataTypeTable("BIT");

        insertTestValue(tableName, false);
        assertEquals(false, getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeBitInsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("BIT");

        insertTestValue(tableName, 2);
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeBitInsertStringThrow() throws SQLException {
        String tableName = createTestDataTypeTable("BIT");

        insertTestValue(tableName, "0");
    }


    @Test
    public void testDatatypeFloat() throws SQLException {
        String tableName = createTestDataTypeTable("FLOAT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.Float", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.REAL", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("FLOAT", columnInfos.get(0).getColumnTypeName());
        assertEquals(12, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeDouble() throws SQLException {
        String tableName = createTestDataTypeTable("Double");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.Double", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.DOUBLE", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("DOUBLE", columnInfos.get(0).getColumnTypeName());
        assertEquals(22, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeDecimal() throws SQLException {
        String tableName = createTestDataTypeTable("DECIMAL");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.math.BigDecimal", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.DECIMAL", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("DECIMAL", columnInfos.get(0).getColumnTypeName());
        assertEquals(10, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeChar() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.CHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("CHAR", columnInfos.get(0).getColumnTypeName());
        assertEquals(1, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    public void testDatatypeCharInsert() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR");

        insertTestValue(tableName, "s");
        assertEquals("s", getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeCharInsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR");

        insertTestValue(tableName, "ss");
    }

    @Test
    public void testDatatypeChar10() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR10", "CHAR(10)");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.CHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("CHAR", columnInfos.get(0).getColumnTypeName());
        assertEquals(10, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeChar3Insert() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR", "CHAR(3)");

        insertTestValue(tableName, "abc");
        assertEquals("abc", getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeChar5Insert() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR", "CHAR(5)");

        insertTestValue(tableName, "a");
        assertEquals("a", getInsertedTestValue(tableName));

        //mysqlAccessor.dropTable(tableName);
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeChar3InsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("CHAR", "CHAR(3)");

        insertTestValue(tableName, "abcd");
    }

    @Test
    public void testDatatypeVarchar10() throws SQLException {
        String tableName = createTestDataTypeTable("VARCHAR10", "VARCHAR(10)");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.VARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("VARCHAR", columnInfos.get(0).getColumnTypeName());
        assertEquals(10, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeVarchar3Insert() throws SQLException {
        String tableName = createTestDataTypeTable("VARCHAR", "VARCHAR(3)");

        insertTestValue(tableName, "abc");
        assertEquals("abc", getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeVarchar5Insert() throws SQLException {
        String tableName = createTestDataTypeTable("VARCHAR", "VARCHAR(5)");

        insertTestValue(tableName, "a");
        assertEquals("a", getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeVarchar3InsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("VARCHAR", "VARCHAR(3)");

        insertTestValue(tableName, "abcd");
    }

    @Test
    public void testDatatypeTinytext() throws SQLException {
        String tableName = createTestDataTypeTable("TINYTEXT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.VARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("TINYTEXT", columnInfos.get(0).getColumnTypeName());
        assertEquals(63, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

   @Test
    public void testDatatypeText() throws SQLException {
        String tableName = createTestDataTypeTable("TEXT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.LONGVARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("TEXT", columnInfos.get(0).getColumnTypeName());
        assertEquals(16383, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

   @Test
    public void testDatatypeMediumtext() throws SQLException {
        String tableName = createTestDataTypeTable("MEDIUMTEXT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.LONGVARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("MEDIUMTEXT", columnInfos.get(0).getColumnTypeName());
        assertEquals(4194303, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

   @Test
    public void testDatatypeLongtext() throws SQLException {
        String tableName = createTestDataTypeTable("LONGTEXT");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.LONGVARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("LONGTEXT", columnInfos.get(0).getColumnTypeName());
        assertEquals(536870911, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeJson() throws SQLException {
        String tableName = createTestDataTypeTable("JSON");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("java.lang.String", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.LONGVARCHAR", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("JSON", columnInfos.get(0).getColumnTypeName());
        assertEquals(2147483647, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeBinary() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("[B", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("BINARY", columnInfos.get(0).getColumnTypeName());
        assertEquals(1, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeBinaryInsert() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY");

        insertTestValue(tableName, "a");
        assertArrayEquals(new byte[]{ 'a' }, (byte[])getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeBinaryInsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY");

        insertTestValue(tableName, "ab");
    }

    @Test
    public void testDatatypeBinary3() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY", "BINARY(3)");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("[B", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.BINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("BINARY", columnInfos.get(0).getColumnTypeName());
        assertEquals(3, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeBinary3Insert() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY", "BINARY(3)");

        insertTestValue(tableName, "abc");
        assertArrayEquals(new byte[]{ 'a', 'b', 'c' }, (byte[])getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeBinary3InsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("BINARY", "BINARY(3)");

        insertTestValue(tableName, "abcd");
    }

    @Test
    public void testDatatypeVarbinary3() throws SQLException {
        String tableName = createTestDataTypeTable("VARBINARY", "VARBINARY(3)");
        List<ColumnInfo> columnInfos = mysqlAccessor.getColumnInfos(tableName);

        assertEquals("testdb", columnInfos.get(0).getCatalogName());
        assertEquals("[B", columnInfos.get(0).getColumnClassName());
        assertEquals("A", columnInfos.get(0).getColumnLabel());
        assertEquals("A", columnInfos.get(0).getColumnName());
        assertEquals("java.sql.Types.VARBINARY", getSqlTypeName(columnInfos.get(0).getColumnType()));
        assertEquals("VARBINARY", columnInfos.get(0).getColumnTypeName());
        assertEquals(3, columnInfos.get(0).getPrecision());
        assertEquals(0, columnInfos.get(0).getScale());

        mysqlAccessor.dropTable(tableName);
    }

    @Test
    public void testDatatypeVarbinary3Insert() throws SQLException {
        String tableName = createTestDataTypeTable("VARBINARY", "VARBINARY(3)");

        insertTestValue(tableName, "abc");
        assertArrayEquals(new byte[]{ 'a', 'b', 'c' }, (byte[])getInsertedTestValue(tableName));

        mysqlAccessor.dropTable(tableName);
    }

    @Test(expected = com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    public void testDatatypeVarbinary3InsertThrow() throws SQLException {
        String tableName = createTestDataTypeTable("VARBINARY", "VARBINARY(3)");

        insertTestValue(tableName, "abcd");
    }









}
