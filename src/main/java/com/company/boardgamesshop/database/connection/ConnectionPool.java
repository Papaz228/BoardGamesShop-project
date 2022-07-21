package com.company.boardgamesshop.database.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;
public class ConnectionPool {
    private static final ConnectionPool POOL=new ConnectionPool();
    private final String URL="jdbc:postgresql://localhost:5432/postgres";
    private final String USERNAME="postgres";
    private final String PASSWORD="postgres";
    private int maxConn=10;
    private int curConn=0;
    private Vector<Connection> freeConns=new Vector<Connection>();
    private ConnectionPool(){}
    public int getCurConn() {
        return curConn;
    }
    public static ConnectionPool getConnPool(){
        return POOL;
    }
    public synchronized Connection getConn(){
        Connection conn=null;
        if(freeConns.size()>0){
            conn=freeConns.firstElement();
            freeConns.removeElementAt(0);
            try {
                if(conn.isClosed()){
                    conn=getConn();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(curConn<maxConn){
            conn=newConn();
        }
        if(conn!=null){
            curConn++;
        }

        return conn;
    }
    private Connection newConn(){
        Connection conn=null;
        try {
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
    public  synchronized void freeConn(Connection conn) {
        freeConns.addElement(conn);
        curConn--;
        notifyAll();
    }
    public void freeAllConns(){
        Enumeration<Connection> conns=freeConns.elements();
        while(conns.hasMoreElements()){
            Connection conn=conns.nextElement();
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        freeConns.removeAllElements();

    }
}
