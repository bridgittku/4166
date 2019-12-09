package edu.uncc.nbad.data;


import edu.uncc.nbad.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bridgittku
 */
public class ConnectionDB {
    
    public ArrayList<Connection> connectionSet = new ArrayList<Connection>();
 
    public static ArrayList<Connection> getConnections() {
        //return array of Connection objects of all connections in the database's connection table
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM Connection";
        
        try {
            ps = sqlConnection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Connection> connections = new ArrayList<Connection>();
            Connection connection = null;
            while(rs.next()) {
                connection = new Connection();
                connection.setConnectionID(rs.getString("connectionID"));
                connection.setHostName(rs.getString("hostName"));
                connection.setUserID(rs.getInt("userID"));
                connection.setConnectionName(rs.getString("connectionName"));
                connection.setConnectionTopic(rs.getString("connectionTopic"));
                connection.setDateTime(rs.getString("dateTime"));
                connection.setLocation(rs.getString("location"));
                connection.setDetails(rs.getString("details"));
                connections.add(connection);
            }
            return connections;
        } catch (SQLException e) {
            System.out.println(e);
            return null;  
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }

    }
    
    public static Connection getConnection(String connectionID) {
        //return Connection object for provided connection code
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM Connection " 
                + "WHERE connectionID = ?";
        try {
            ps = sqlConnection.prepareStatement(query);
            ps.setString(1, connectionID);
            rs = ps.executeQuery();
            Connection connection = null;
            if(rs.next()) {
                connection = new Connection();
                connection.setConnectionID(rs.getString("connectionID"));
                connection.setHostName(rs.getString("hostName"));
                connection.setUserID(rs.getInt("userID"));
                connection.setConnectionName(rs.getString("connectionName"));
                connection.setConnectionTopic(rs.getString("connectionTopic"));
                connection.setDateTime(rs.getString("dateTime"));
                connection.setLocation(rs.getString("location"));
                connection.setDetails(rs.getString("details"));
            }
            return connection;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }
    }
    
    public static void editConnection(Connection connection) {
        //edits the connection from the connection table
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query="UPDATE Connection SET "
                + "connectionName = ?, "
                + "connectionTopic = ?, "
                + "dateTime = ?, "
                + "location = ?, "
                + "details = ? "
                + "WHERE connectionID = ?";
        
        try {
            ps = sqlConnection.prepareStatement(query);
            ps.setString(1, connection.getConnectionName());
            System.out.println("THE CONNECTION NAME IN CONNECTION DB is" + connection.getConnectionName());
            ps.setString(2, connection.getConnectionTopic());
            ps.setString(3, connection.getDateTime());
            ps.setString(4, connection.getLocation());
            ps.setString(5, connection.getDetails());
            ps.setString(6, connection.getConnectionID());
            System.out.println("THE CONNECTION ID IN CONNECTION DB IS " + connection.getConnectionID());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }
        
    }
    
    public static void deleteConnection(String connectionID) {
        //deletes the connection from the connection table
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query 
                = "DELETE FROM Connection "
                + "WHERE connectionID = ?";
        
        try {
            ps = sqlConnection.prepareStatement(query);
            ps.setString(1, connectionID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }
    }
    
    public void printConnections() {
        System.out.println("Printing connections here");
        for (int i = 0; i < this.connectionSet.size();i++) {
            System.out.println(this.connectionSet.get(i));
        }
    }
    
}
