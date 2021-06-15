package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppMysqlAccessor {
    static Properties readProperties() throws IOException {
        Properties prop = new Properties();
        try (InputStream input = AppMysqlAccessor.class.getResourceAsStream("/config.properties")) {
            prop.load(input);
            return prop;
        }
    }

    public static void main( String[] args )
    {
        MysqlAccessor mysqlAccessor = new MysqlAccessor();
        try {
            mysqlAccessor.init(readProperties());
            mysqlAccessor.printResult("show tables");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysqlAccessor.close();
        }
    }
}
