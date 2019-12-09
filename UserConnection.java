/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad;

import edu.uncc.nbad.data.ConnectionDB;
import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @author bridgittku
 */
public class UserConnection implements Serializable {
    
    private Connection connection;
    private String connectionName;
    private String connectionType;
    private String connectionID;
    private int userID;
    private String rsvp;
    
    public UserConnection() {
        
        connection = null;
        rsvp = "";
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionID) {
        /**this.connectionName = connectionName;**/
        ArrayList<Connection> connections = ConnectionDB.getConnections();
        if (connections != null) {
            for (int i = 0; i < connections.size(); i++) {
                if (connections.get(i).getConnectionID().equals(connectionID)) {
                    this.connectionName = ConnectionDB.getConnection(connectionID).getConnectionName();
                }
            }
        }
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionID) {
        /**this.connectionType = connectionType;**/
        ArrayList<Connection> connections = ConnectionDB.getConnections();
        if (connections != null) {
            for (int i = 0; i < connections.size(); i++) {
                if (connections.get(i).getConnectionID().equals(connectionID)) {
                    this.connectionType = ConnectionDB.getConnection(connectionID).getConnectionTopic();
                }
            }
        }
    }
    
    public String getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(String connectionID) {
        this.connectionID = connectionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    
    public String getRsvp() {
        return rsvp;
    }

    public void setRsvp(String rsvp) {
        this.rsvp = rsvp;
    }
    
}
