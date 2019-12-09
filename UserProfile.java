/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad;
import edu.uncc.nbad.data.ConnectionDB;
import edu.uncc.nbad.data.UserConnectionDB;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author bridgittku
 */
public class UserProfile implements Serializable {
    
    User user;
    ArrayList<UserConnection> userConnectionList;
    
    public UserProfile(User user) {
        this.userConnectionList = UserConnectionDB.getUserProfile(user.getUserID());
    }
    
    //getters and setters
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<UserConnection> getUserConnectionList() {
        //return userConnectionList from UserConnectionDB
        int userID = user.getUserID();
        return UserConnectionDB.getUserProfile(userID);
    }

    public void setUserConnectionList() {
        int userID = user.getUserID();
        userConnectionList = UserConnectionDB.getUserProfile(userID);
    }
    
    public void addConnection(Connection connection, String rsvp) {
            //add UserConnection for this connection to the user profile
            UserConnectionDB.addRSVP(connection.getConnectionID(), user.getUserID(), rsvp);
    }
    
    public void removeConnection(String connectionID) { 
        UserConnectionDB.deleteRSVP(connectionID, user.getUserID());
    }
    
    public void updateConnection(String connectionID, String rsvp) {
        //updates a Userconnection data (rsvp), sets RSVP to true
        UserConnectionDB.updateRSVP(connectionID, user.getUserID(), rsvp);
    }
    
    public void emptyProfile() {
        //clear entire profile contents
        for (int i = 0; i < this.userConnectionList.size(); i++) {
            this.userConnectionList.remove(i);
        }
    }
}
