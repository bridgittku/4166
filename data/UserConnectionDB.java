/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad.data;

import edu.uncc.nbad.Connection;
import edu.uncc.nbad.User;
import edu.uncc.nbad.UserConnection;
import edu.uncc.nbad.UserProfile;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author bridgittku
 */
public class UserConnectionDB {
    
    public static ArrayList <UserConnection> getUserProfile(int userID) {
        //return list of all UserConnection objects saved
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM UserConnection "
                       + "WHERE userID = ?";
                
        try {
            ps = sqlConnection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            
            ArrayList<UserConnection> userConnectionList = new ArrayList<UserConnection>();
            UserConnection userConnection = null;
            
            while (rs.next()) {
                userConnection = new UserConnection();
                userConnection.setConnectionID(rs.getString("connectionID"));
                userConnection.setConnectionName(rs.getString("connectionID"));
                userConnection.setConnectionType(rs.getString("connectionID"));
                userConnection.setUserID(rs.getInt("userID"));
                userConnection.setRsvp(rs.getString("rsvp"));
                userConnectionList.add(userConnection);
            }
            return userConnectionList;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }
    }
    
    public static void addRSVP(String connectionID, int userID, String rsvp) {
        //associated connection to user with userID
        //updates RSVP that user provides for this connection with connection ID
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query 
            = "INSERT INTO UserConnection (connectionID, userID, rsvp) "
                + "VALUES(?, ?, ?)";
        
        try {
            ps = sqlConnection.prepareStatement(query);
            ps.setString(1, connectionID);
            ps.setInt(2, userID);
            ps.setString(3, rsvp);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }
    }
    
    public static void updateRSVP(String connectionID, int userID, String rsvp) {
        //updates rsvp field for user with userID for connection with connectionID
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query 
               = "UPDATE UserConnection "
                 + "SET rsvp = ? "
                 + "WHERE connectionID = ? "
                 + "AND userID = ?";
        try {
            ps = sqlConnection.prepareStatement(query);
            ps.setString(1, rsvp);
            ps.setString(2, connectionID);
            ps.setInt(3, userID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }
    }
    
    public static void deleteRSVP(String connectionID, int userID) {
        //deletes the connection for the specified user
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query
               = "DELETE FROM UserConnection "
                 + "WHERE connectionID = ? "
                +  "AND userID = ?";
        try {
            ps = sqlConnection.prepareStatement(query);
            ps.setString(1, connectionID);
            ps.setInt(2, userID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }
    }
    
    public static void addConnection(Connection connection) {
        //adds new connection to list of connections in the database
        //handles form submission in newConnection view
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query 
            = "INSERT INTO Connection (connectionID, hostName, userID, connectionName, connectionTopic, dateTime, location, details) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            ps = sqlConnection.prepareStatement(query);
            ps.setString(1, connection.getConnectionID());
            ps.setString(2, connection.getHostName());
            ps.setInt(3, connection.getUserID());
            ps.setString(4, connection.getConnectionName());
            ps.setString(5, connection.getConnectionTopic());
            ps.setString(6, connection.getDateTime());
            ps.setString(7, connection.getLocation());
            ps.setString(8, connection.getDetails());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }
    }   
    
    public static void deleteConnection(String connectionID) {
        //deletes the connection from the user connection table 
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query 
                = "DELETE FROM UserConnection "
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
}
