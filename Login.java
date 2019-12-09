/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad;

import edu.uncc.nbad.data.UserDB;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.owasp.esapi.ESAPI;

/**
 *
 * @author bridgittku
 */
public class Login {
    public static boolean validate(String Name, String Pass) {
        boolean status = false;
        java.sql.Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "friendsWhoFood";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String password = "Bk/8803252019";
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager
                    .getConnection(url + dbName, userName, password);

            //st = conn.createStatement();
            /**String query = "SELECT * FROM login WHERE user='" + Name + "' and password='" + Pass + "'";**/   
            String query= "SELECT * FROM User WHERE userName = ?" + " AND password = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, Name);
            ps.setString(2, Pass);
            rs = ps.executeQuery();

            /**rs = st.executeQuery(query);**/
            status = rs.next();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

    public static User getUser(String Name, String Pass) {
        int userID = 0;
        User user = new User();
        java.sql.Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "friendsWhoFood";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String password = "Bk/8803252019";
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager
                    .getConnection(url + dbName, userName, password);

            //st = conn.createStatement();
            /**String query = "SELECT * FROM login WHERE user='" + Name + "' and password='" + Pass + "'";

            rs = st.executeQuery(query);**/
            String query= "SELECT * FROM User WHERE userName = ?" + " AND password = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, Name);
            ps.setString(2, Pass);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                userID = rs.getInt("userID");
                user = UserDB.getUser(userID);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }
}
