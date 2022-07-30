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

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private String url;
    private String user;
    private String password;
    private String driverDB;
    private final Properties PROPERTIES = getProperties();
    private final int maxConnection = Integer.parseInt(PROPERTIES.getProperty("db.maxConnection"));
    private static ConnectionPool instance = null;
    private final BlockingQueue<Connection> FREE_CONNECTIONS = new ArrayBlockingQueue<>(maxConnection);

    private ConnectionPool() {
        init();
    }

    private void init(){
        setDataForConnection();
        loadDrivers();
        createConnections();
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void setDataForConnection(){
        this.url = PROPERTIES.getProperty("db.url");
        this.password = PROPERTIES.getProperty("db.password");
        this.user = PROPERTIES.getProperty("db.user");
        this.driverDB = PROPERTIES.getProperty("db.driver");
    }

    private Properties getProperties(){
        Properties properties = new Properties();
        InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream("connectionPool.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error(e);
        }
        return properties;
    }

    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class.forName(driverDB).newInstance();
        } catch (InstantiationException | ClassNotFoundException e) {
            log.warn(e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log.warn(e);
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

    private void createConnections(){
        Connection connection;
        while(FREE_CONNECTIONS.size() < maxConnection){
            try {
                connection = DriverManager.getConnection(url, user, password);
                FREE_CONNECTIONS.put(connection);
            } catch (InterruptedException | SQLException e) {
                log.warn(e);
                e.printStackTrace();
            }
        }
    }

    public synchronized void returnConnection(Connection connection){
        if ( (connection != null) && (FREE_CONNECTIONS.size()<= maxConnection)) {
            try {
                FREE_CONNECTIONS.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}