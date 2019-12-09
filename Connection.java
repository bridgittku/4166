package edu.uncc.nbad;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bridgittku
 */
public class Connection implements Serializable {
    
    private String hostName;
    private int userID;
    private String connectionID;
    private String connectionName;
    private String connectionTopic; 
    private String location;
    private String details;
    private String dateTime;

    public Connection() {
        
        hostName = "";
        userID = 0;
        connectionID = "";
        connectionName = "";
        connectionTopic = "";
        details = "";
        dateTime = "";
        location = "";
        
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public String getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(String connectionID) {
        this.connectionID = connectionID;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getConnectionTopic() {
        return connectionTopic;
    }

    public void setConnectionTopic(String connectionTopic) {
        this.connectionTopic = connectionTopic;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    
    
}
