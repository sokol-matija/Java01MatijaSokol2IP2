package hr.algebra.dal.sql;

import java.util.Properties;

public class DataSourceSingleton {

    private static final String PATH = "/config/db.properties";

    private static final String SERVER_NAME = "SERVER_NAME";
    private static final String DATABASE_NAME = "DATABASE_NAME";
    private static final String USER = "USER";
    private static final String PASSWORD = "PASSWORD";

    private static final Properties PROPERTIES = new Properties();
}
