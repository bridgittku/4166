/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad.data;

import edu.uncc.nbad.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author bridgittku
 */
public class UserDB {
    
    public ArrayList<User> userSet = new ArrayList<User>();
    //return set of all users in hardcoded database
    public static ArrayList<User> getAllUsers() {
        //returns a list of all users in the User table in database
        ConnectionPool pool = ConnectionPool.getInstance();
        java.sql.Connection sqlConnection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM User";
        
        try {
            ps = sqlConnection.prepareStatement(query);
            rs = ps.executeQuery();
            
            ArrayList<User> users = new ArrayList<User>();
            User user = null;
            while (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.out.println(e);
            return null;  
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }
    }
    
    //gets particular user in hardcoded database
    public static User getUser(int userID) {
       ConnectionPool pool = ConnectionPool.getInstance();
       java.sql.Connection sqlConnection = pool.getConnection();
       PreparedStatement ps = null;
       ResultSet rs = null;
       
       String query = "SELECT * FROM User "
               + "WHERE userID = ?";
       try {
           ps = sqlConnection.prepareStatement(query);
           ps.setInt(1, userID);
           rs = ps.executeQuery();
           User user = null;
           if (rs.next()) {
               user = new User();
               user.setUserID(rs.getInt("userID"));
               user.setFirstName(rs.getString("firstName"));
               user.setLastName(rs.getString("lastName"));
               user.setEmail(rs.getString("email"));
           }
           return user;
       } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(sqlConnection);
        }
    }
}
