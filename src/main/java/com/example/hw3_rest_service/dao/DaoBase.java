package com.example.hw3_rest_service.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Anna Belousova
 * Класс для установления соединения с БД
 */
public class DaoBase {
    private final String jdbcURL = "jdbc:postgresql://localhost:5434/restdb";
    private final String jdbcUsername = "postgres";
    private final String jdbcPassword = "123";
    private final String DRIVER = "org.postgresql.Driver";
    private static Connection connection = null;

    /**
     * Метод установления соединения
     *
     * @return connection
     */
    public Connection getConnection() {
        if (DaoBase.connection != null) {
            return DaoBase.connection;
        }
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            DaoBase.connection = connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Метод определения connection
     *
     * @param connection
     */
    public void setConnection(Connection connection) {
        DaoBase.connection = connection;
    }
}
