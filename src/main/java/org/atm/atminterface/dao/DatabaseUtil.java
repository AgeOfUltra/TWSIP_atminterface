package org.atm.atminterface.dao;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseUtil {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/task2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456789";//uer password

    static DataSource dSource;

    static {
        dSource = createDataSource();
    }

    private static DataSource createDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(JDBC_URL);
        ds.setUsername(USERNAME);
        ds.setPassword(PASSWORD);
        ds.setDriverClassName("com.mysql.jdbc.Driver");

        return ds;
    }


}
