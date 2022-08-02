package com.company.boardgamesshop.database.connection;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static ConnectionPool instance = null;
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private final Properties PROPERTIES = getProperties();
    private final int MAX_CONNECTION = Integer.parseInt(PROPERTIES.getProperty("db.maxConnection"));
    private final BlockingQueue<Connection> FREE_CONNECTIONS = new ArrayBlockingQueue<>(MAX_CONNECTION);
    private String url;
    private String user;
    private String password;
    private String driverDB;

    private ConnectionPool() {
        init();
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void init() {
        setDataForConnection();
        loadDrivers();
        createConnections();
    }

    private void setDataForConnection() {
        this.url = PROPERTIES.getProperty("db.url");
        this.password = PROPERTIES.getProperty("db.password");
        this.user = PROPERTIES.getProperty("db.user");
        this.driverDB = PROPERTIES.getProperty("db.driver");
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream("connectionPool.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return properties;
    }

    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class.forName(driverDB).newInstance();
        } catch (InstantiationException | ClassNotFoundException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LOGGER.warn(e);
        }
    }

    public synchronized Connection getConnection() {
        Connection connection = null;
        try {
            connection = FREE_CONNECTIONS.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void createConnections() {
        Connection connection;
        while (FREE_CONNECTIONS.size() < MAX_CONNECTION) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                FREE_CONNECTIONS.put(connection);
            } catch (InterruptedException | SQLException e) {
                LOGGER.warn(e);
                e.printStackTrace();
            }
        }
    }

    public synchronized void returnConnection(Connection connection) {
        if ((connection != null) && (FREE_CONNECTIONS.size() <= MAX_CONNECTION)) {
            try {
                FREE_CONNECTIONS.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}