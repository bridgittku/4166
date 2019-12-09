/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad;

import edu.uncc.nbad.data.ConnectionDB;
import edu.uncc.nbad.data.UserConnectionDB;
import edu.uncc.nbad.data.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author bridgittku
 */
public class ProfileControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProfileControllerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileControllerServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Check session for current user using attribute theUser
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();

        //create UserProfile instance to hold user connections
        ArrayList<UserConnection> connections = new ArrayList<UserConnection>();
        ArrayList<Connection> collection = ConnectionDB.getConnections();
        request.setAttribute("collection", collection);
        //UserProfile profile = new UserProfile(user, connections);
        String actionValue = request.getParameter("action");
        if ("login".equals(actionValue)) /*action is "login"*/ {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
        if ("register".equals(actionValue)) /*action is "register"*/ {
            getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
        }
        if("view".equals(actionValue)) /*Dispatch to view saved connections*/{
            getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request,response);
        }
        if ("remove".equals(actionValue)) /*action is to remove connection*/{
            String connectionID = request.getParameter("connectionID");
            ConnectionDB.deleteConnection(connectionID);
            UserConnectionDB.deleteConnection(connectionID);
            collection = ConnectionDB.getConnections();
            request.setAttribute("collection", collection);
            /**User profileUser = (User) session.getAttribute("theUser");
            UserProfile updatedProfile = new UserProfile(profileUser);
            updatedProfile.setUser(profileUser);
            session.setAttribute("userProfile", updatedProfile);
            ArrayList<UserConnection> userConnections = updatedProfile.getUserConnectionList();
            **/
            getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request,response);
        }
        
        if("registerUser".equals(actionValue)) /*action is "register user"*/ {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            
            //check strength requirements
            String message;
            try {
                PasswordUtil.checkPasswordStrength(password);
                message = "";
            } catch (Exception e) {
                message = e.getMessage();
            }
            request.setAttribute("message", message);
            
            //hash and salt password
            String saltedPassword;
            String hashedPassword;
            String salt = PasswordUtil.getSalt();
            System.out.println("THE SALT VALUE FROM REGISTER is " + salt);
            //String saltedAndHashedPassword;
            try {
                saltedPassword = salt + password;
                hashedPassword = PasswordUtil.hashPassword(saltedPassword);        
                //saltedAndHashedPassword = PasswordUtil.hashAndSaltPassword(password);
            } catch (NoSuchAlgorithmException ex) {
                hashedPassword = ex.getMessage();
                saltedPassword = ex.getMessage();
                //saltedAndHashedPassword = ex.getMessage();
            }
            request.setAttribute("hashedPassword", hashedPassword);
            request.setAttribute("salt", salt);

            try {
                firstName = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("firstName"), "HTTPParameterValue", 200, false);
                lastName = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("lastName"), "HTTPParameterValue", 200, false);
                email = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("email"), "HTTPParameterValue", 200, false);
                password = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("password"), "HTTPParameterValue", 200, false);
                userName = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("userName"), "HTTPParameterValue", 200, false);               
                Register.registerUser(firstName, lastName, email, userName, hashedPassword);
                getServletContext().getRequestDispatcher("/login.jsp").forward(request,response);
            } catch (IntrusionException e) {
                    Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, e);
                    out.print("<p id=\"errorMessage\">Sorry username " + ESAPI.encoder().encodeForHTML(userName) + " or password " + ESAPI.encoder().encodeForHTML(password) + " or first name " + ESAPI.encoder().encodeForHTML(firstName) + " or last name " + ESAPI.encoder().encodeForHTML(lastName) + " or email " + ESAPI.encoder().encodeForHTML(email) + " is not valid</p>");
                                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                                rd.include(request, response);
            } catch (ValidationException ex) {
                System.out.println("THE EXCEPTION FROM REGISTRATION IS VALIDATION");
                    Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    out.print("<p id=\"errorMessage\">Sorry username " + ESAPI.encoder().encodeForHTML(userName) + " or password " + ESAPI.encoder().encodeForHTML(password) + " or first name " + ESAPI.encoder().encodeForHTML(firstName) + " or last name " + ESAPI.encoder().encodeForHTML(lastName) + " or email " + ESAPI.encoder().encodeForHTML(email) + " is not valid</p>");
                                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                                rd.include(request, response);
            } catch (Exception ex) {
                    Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    out.print("<p id=\"errorMessage\">Sorry username " + ESAPI.encoder().encodeForHTML(userName) + " or password " + ESAPI.encoder().encodeForHTML(password) + " or first name " + ESAPI.encoder().encodeForHTML(firstName) + " or last name " + ESAPI.encoder().encodeForHTML(lastName) + " or email " + ESAPI.encoder().encodeForHTML(email) + " is not valid</p>");
                                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                                rd.include(request, response);
            }
        }
        
        User user = new User();
        if ("signIn".equals(actionValue)) /*action is "signIn"*/ {
            //check session for "theUser" attribute
            user = (User) session.getAttribute("theUser");
            if (user != null) /*theUser attribute is set*/ {
                //dispatch to profile view
                getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
            } else /*no valid User bean*/ {
                //check http request for parameter called "username"
                String userName = request.getParameter("userName");
                String password = request.getParameter("password");
                //String salt = request.getParameter("salt");
                String salt = PasswordUtil.getSalt();
                System.out.println("THE VALUE OF THE SALT FROM SIGNIN IS " + salt);
                String saltedPassword = salt + password;
                String hashedPassword;
                try {
                    hashedPassword = PasswordUtil.hashPassword(saltedPassword);
                    System.out.println("The ");
                } catch (NoSuchAlgorithmException ex) {
                    hashedPassword = ex.getMessage();
                }
                try {
                    password = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("password"), "HTTPParameterValue", 200, false);
                    userName = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("userName"), "HTTPParameterValue", 200, false);
                    if (!"".equals(userName)) /*if userName parameter exists*/ {
                        //check http request for parameter called password
                        if (!"".equals(password)) /*there is valid username and password*/ {
                            //verify the username and password match a valid user in the database
                            System.out.println("THE HASHEDPASSWORD IS " + hashedPassword);
                            if (Login.validate(userName, hashedPassword)) /*User credentials are valid*/ {
                                //HTTP session = request.getSession(false);
                                if (session != null) {
                                    //retrieve list of saved connections for user from database
                                    
                                    user = Login.getUser(userName, hashedPassword);
                                    session.setAttribute("theUser", user);
                                    System.out.println("THE USER ID IS " + user.getUserID());
                                    if (UserConnectionDB.getUserProfile(user.getUserID()) != null) {
                                        UserProfile profile = new UserProfile(user);
                                        profile.setUser(user);
                                        session.setAttribute("userProfile", profile);         
                                        ArrayList<UserConnection> userConnections = profile.getUserConnectionList();
                                        //add list of saved connections to session object as "currentProfile"
                                        session.setAttribute("viewConnections", userConnections);
                                    }
                                    //dispatch to profile view
                                    getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
                                }

                            } else {
                                System.out.println("THE USER CREDENTIALS ARE NOT VALID");
                                out.print("<p id=\"errorMessage\">Sorry username " + ESAPI.encoder().encodeForHTML(userName) + " or password " + ESAPI.encoder().encodeForHTML(password) + " is not valid</p>");
                                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                                rd.include(request, response);
                            }
                            out.close();
                        } else /*if password parameter does not exist*/ {
                            //dispatch to login JSP view
                            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                            rd.include(request, response);
                        }
                    } else /*if userName parameter does not exist*/ {
                        //dispatch to login JSP view
                        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                    }
                } catch (IntrusionException e) {
                    System.out.println("INTRUSION EXCEPTION THROWN");
                    Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, e);
                    out.print("<p id=\"errorMessage\">Sorry username " + ESAPI.encoder().encodeForHTML(userName) + " or password " + ESAPI.encoder().encodeForHTML(password) + " is not valid</p>");
                                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                                rd.include(request, response);
                } catch (ValidationException ex) {
                    System.out.println("INTRUSION EXCEPTION THROWN");
                    Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    out.print("<p id=\"errorMessage\">Sorry username " + ESAPI.encoder().encodeForHTML(userName) + " or password " + ESAPI.encoder().encodeForHTML(password) + " is not valid</p>");
                                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                                rd.include(request, response);
                } catch (Exception ex) {
                    System.out.println("INTRUSION EXCEPTION THROWN");
                    Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    out.print("<p id=\"errorMessage\">Sorry username " + ESAPI.encoder().encodeForHTML(userName) + " or password " + ESAPI.encoder().encodeForHTML(password) + " is not valid</p>");
                                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                                rd.include(request, response);
                }
                
            }
        }

        if (user != null) /*there is a user parameter and it is valid*/ {

            //check http request for parameter called "action" or "task"
            UserProfile profile = (UserProfile) session.getAttribute("userProfile");
            System.out.println("THE USER CONNECTION LIST OF THE PROFILE IS " + profile.getUserConnectionList());
            connections = profile.getUserConnectionList();
            if (actionValue == null || actionValue.equals("")) {
                getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
            }
            if (!"".equals(actionValue)) /* if parameter exists*/ {
                //validate that value is one of designated phrases
                if ("save".equals(actionValue) || "updateProfile".equals(actionValue)
                        || "delete".equals(actionValue)
                        || "signout".equals(actionValue) || "signIn".equals(actionValue)) {

                    if ("signout".equals(actionValue)) /*action is "signout"*/ {
                        //clear session and dispatch to home jsp view
                        session.invalidate();
                        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                    }

                    //action is "save", "updateProfile", or "delete"
                    if ("save".equals(actionValue) || "updateProfile".equals(actionValue) || "delete".equals(actionValue)) {
                        //check http request for parameter called "viewConnections"
                        String connectionsValue = request.getParameter("viewConnections");
                        //return list of connection ids that appear in view
                        String[] viewConnectionsList = request.getParameterValues("viewConnections");

                        if (!"".equals(connectionsValue)) /*if parameter exists*/ {
                            //check http request for parameter "connectionID"
                            String idValue = request.getParameter("connectionID");
                            //check that connectionID exists in view list
                            boolean inView = false;
                            for (int i = 0; i < viewConnectionsList.length; i++) {
                                if (idValue.equals(viewConnectionsList[i])) {
                                    //connectionID exists in view list
                                    inView = true;
                                } else {
                                    inView = false;
                                }
                            }
                            if (inView == false) /*connection is not in connection list*/ {
                                //dispatch to profile JSP view
                                getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
                            } else /*connection exists in view list*/ {
                                //action is "save"
                                if ("save".equals(actionValue)) {
                                    //check http request for parameter "rsvp"
                                    String rsvpValue = request.getParameter("rsvp");
                                    //if parameter exists or value matches designated values
                                    if (rsvpValue.equals("yes") || rsvpValue.equals("no") || rsvpValue.equals("maybe")) {
                                        if (connections == null) {
                                            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                                        } else {
                                            //check if connection from idValue exists in profile
                                            boolean idValueExists = false;
                                            int idValueIndex = 0;
                                            for (int i = 0; i < connections.size(); i++) {
                                                if (idValue.equals(connections.get(i).getConnectionID())) /*idValue exists in profile list*/ {
                                                    idValueExists = true;
                                                    idValueIndex = i;
                                                }
                                            }

                                            if (idValueExists == true) /*connection exists in profile*/ {
                                                //update profile with new value for rsvp for that connection
                                                UserConnection newUserConnection = new UserConnection();
                                                int index = 0;
                                                for (int i = 0; i < connections.size(); i++) {
                                                    if (idValue.equals(connections.get(i))) {
                                                        index = i;
                                                    }
                                                }
                                                newUserConnection = connections.get(index);
                                                profile.updateConnection(idValue, rsvpValue);
                                                //update session object with updated user profile
                                                session.setAttribute("userProfile", profile);
                                                //dispatch to profile view
                                                getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
                                            } else /*connection does not exist in profile*/ {
                                                //Create new UserConnection object using rsvp value                             
                                                Connection newConnection = ConnectionDB.getConnection(idValue);
                                                //add UserConnection object to UserProfile 
                                                profile.addConnection(newConnection, rsvpValue);
                                                //update session object with updated user profile
                                                session.setAttribute("userProfile", profile);
                                                //dispatch to profile JSP view
                                                getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
                                            }
                                        } 
                                    } else /*parameter is not valid and value does not match designated values*/ {
                                            //dispatch to profile JSP view
                                            getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);

                                        }

                                }

                                if ("updateProfile".equals(actionValue)) /*action is "updateProfile"*/ {
                                    //check if connection already exists in user profile
                                    boolean idValueExists = false;
                                    int idValueIndex = 0;
                                    for (int i = 0; i < connections.size(); i++) {
                                        if (idValue.equals(connections.get(i).getConnectionID())) /*idValue exists in profile list*/ {
                                            idValueExists = true;
                                            idValueIndex = i;
                                        }
                                    }
                                    if (idValueExists == true) /*Connection exists in user profile*/ {
                                        //get UserConnection object saved in user profile
                                        Connection newConnection = ConnectionDB.getConnection(idValue);
                                        //add UserConnection object to HTTP request as "theConnection"
                                        session.setAttribute("connection", newConnection);

                                        //dispatch to JSP view for connection.jsp
                                        getServletContext().getRequestDispatcher("/connection.jsp").forward(request, response);

                                    } else /*Connection does not exist*/ {
                                        //dispatch to profile JSP view
                                        getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
                                    }
                                }

                                if ("delete".equals(actionValue)) /*Action is "delete"*/ {
                                    //check if connection already exists in user profile
                                    boolean idValueExists = false;
                                    int idValueIndex = 0;

                                    for (int i = 0; i < connections.size(); i++) {
                                        if (idValue.equals(connections.get(i).getConnectionID())) /*idValue exists in profile list*/ {
                                            idValueExists = true;
                                            idValueIndex = i;
                                        }
                                    }

                                    if (idValueExists == true) /*Connection exists in user profile*/ {
                                        //remove connection from user profile
                                        profile.removeConnection(connections.get(idValueIndex).getConnectionID());
                                        //update session object with updated user profile
                                        session.setAttribute("userProfile", profile);
                                        //dispatch to profile JSP view
                                        getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
                                    } else /*Connection doesn't exist*/ {
                                        //dispatch to profile JSP view
                                        getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
                                    }

                                }

                            }

                        } else /*parameter does not exist*/ {
                            //dispatch directly to profile JSP view
                            getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
                        }
                    }
                } else /*Unknown value*/ {
                    //dispatch to profile JSP view
                    getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
                }
            } else /*No action parameter*/ {
                //dispatch to profile JSP view
                getServletContext().getRequestDispatcher("/savedConnections.jsp").forward(request, response);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
