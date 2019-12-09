/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad;

import edu.uncc.nbad.Connection;
import edu.uncc.nbad.data.ConnectionDB;
import edu.uncc.nbad.data.UserConnectionDB;
import edu.uncc.nbad.data.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class FormControllerServlet extends HttpServlet {

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
            out.println("<title>Servlet FormControllerServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FormControllerServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        ArrayList<User> userCollection = UserDB.getAllUsers();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        ArrayList<Connection> collection = ConnectionDB.getConnections();
        User user = (User) session.getAttribute("theUser");
        if (user==null) {
            out.print("<p id=\"errorMessage\">Please log in to add a new connection</p>");
            RequestDispatcher rd = request.getRequestDispatcher("newConnection.jsp");
            rd.include(request, response);
        } else {
            String taskValue = request.getParameter("task");
            if ("editConnection".equals(taskValue)) /*edit the connection*/ {
                System.out.println("TASK VALUE IS TO EDIT CONNECTION");
                //get connection for specified connectionID
                String connectionID = request.getParameter("connectionID");
                System.out.println("THE CONNECTION ID FROM TASK VALUE IS " + connectionID);
                Connection currentConnection = ConnectionDB.getConnection(connectionID);
                System.out.println("THE CONNECTION NAME FROM TASK VALUE IS " + currentConnection.getConnectionName());
                //set as session attribute
                request.setAttribute("currentConnection", currentConnection);
                //session.setAttribute("currentConnection", currentConnection);
                //forward to editConnection.jsp
                getServletContext().getRequestDispatcher("/editConnection.jsp").forward(request,response);
            }
            
            if ("update".equals(taskValue)) /*update the connection*/ {
                System.out.println("THE TASK IS UPDATE");
                String connectionID = request.getParameter("connectionID");        
                Connection connection = new Connection();
                String hostName = user.getFirstName();
                String connectionName = request.getParameter("connectionName");
                System.out.println("THE CONNECTION NAME FROM UPDATE IS " + connectionName);
                String connectionTopic = request.getParameter("connectionTopic");
                String dateTime = request.getParameter("dateTime");
                String location = request.getParameter("location");
                String details = request.getParameter("details");
            try {
                connectionName = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("connectionName"), "FileName", 200, false);
                connectionTopic = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("connectionTopic"), "FileName", 200, false);
                dateTime = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("dateTime"), "FileName", 200, false);
                location = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("location"), "FileName", 200, false);
                details = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("details"), "FileName", 200, false);
                connection.setHostName(hostName);
                connection.setUserID(user.getUserID());
                connection.setConnectionID(connectionID);
                connection.setConnectionName(request.getParameter("connectionName"));
                System.out.println("THE CONNECTION NAME FROM CONNECTION NAME IS " + connection.getConnectionName());
                connection.setConnectionTopic(request.getParameter("connectionTopic"));
                connection.setDateTime(request.getParameter("dateTime"));
                connection.setLocation(request.getParameter("location"));
                connection.setDetails(request.getParameter("details"));
                ConnectionDB.editConnection(connection);
                collection = ConnectionDB.getConnections();
                request.setAttribute("collection", collection);
                request.setAttribute("userCollection", userCollection);
                System.out.println("THE REQUEST ATTRIBUTE HAS BEEN SET");
                System.out.println("THE CONNECTION ID IS " + connectionID);
                getServletContext().getRequestDispatcher("/ConnectionControllerServlet?connectionID="+connectionID).forward(request,response);
                //getServletContext().getRequestDispatcher("/ConnectionControllerServlet").forward(request,response);
            } catch (IntrusionException e) {
                        System.out.println("THE EXCEPTION IS FOR INTRUSION");
                        Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, e);
                        out.print("<p id=\"errorMessage\">Sorry hostname " + ESAPI.encoder().encodeForHTML(hostName) + " or connection name " + ESAPI.encoder().encodeForHTML(connectionName) + " or connection topic " + ESAPI.encoder().encodeForHTML(connectionTopic) + " or date and time " + ESAPI.encoder().encodeForHTML(dateTime) + " or location " + ESAPI.encoder().encodeForHTML(location) + " or details " + ESAPI.encoder().encodeForHTML(details) + " is not valid</p>");
                                    RequestDispatcher rd = request.getRequestDispatcher("newConnection.jsp");
                                    rd.include(request, response);
                    } catch (ValidationException ex) {
                        System.out.println("THE EXCEPTION IS FOR VALIDATION");
                        Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                        out.print("<p id=\"errorMessage\">Sorry hostname " + ESAPI.encoder().encodeForHTML(hostName) + " or connection name " + ESAPI.encoder().encodeForHTML(connectionName) + " or connection topic " + ESAPI.encoder().encodeForHTML(connectionTopic) + " or date and time " + ESAPI.encoder().encodeForHTML(dateTime) + " or location " + ESAPI.encoder().encodeForHTML(location) + " or details " + ESAPI.encoder().encodeForHTML(details) + " is not valid</p>");
                                    RequestDispatcher rd = request.getRequestDispatcher("newConnection.jsp");
                                    rd.include(request, response);
                    } catch (Exception ex) {
                        System.out.println("THE EXCEPTION IS GENERAL");
                        Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                        out.print("<p id=\"errorMessage\">Sorry hostname " + ESAPI.encoder().encodeForHTML(hostName) + " or connection name " + ESAPI.encoder().encodeForHTML(connectionName) + " or connection topic " + ESAPI.encoder().encodeForHTML(connectionTopic) + " or date and time " + ESAPI.encoder().encodeForHTML(dateTime) + " or location " + ESAPI.encoder().encodeForHTML(location) + " or details " + ESAPI.encoder().encodeForHTML(details) + " is not valid</p>");
                                    RequestDispatcher rd = request.getRequestDispatcher("newConnection.jsp");
                                    rd.include(request, response);
                    }
            }
            if ("submit".equals(taskValue)) /*submit a new connection*/ {
                String nextAvailableConnID = Integer.toString(collection.size() + 1); 
                Connection connection = new Connection();
                connection.setConnectionID(nextAvailableConnID);
                String hostName = user.getFirstName();
                String connectionName = request.getParameter("connectionName");
                String connectionTopic = request.getParameter("connectionTopic");
                String dateTime = request.getParameter("dateTime");
                String location = request.getParameter("location");
                String details = request.getParameter("details");
            try {
                connectionName = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("connectionName"), "FileName", 200, false);
                connectionTopic = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("connectionTopic"), "FileName", 200, false);
                dateTime = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("dateTime"), "FileName", 200, false);
                location = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("location"), "FileName", 200, false);
                details = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("details"), "FileName", 200, false);
                connection.setHostName(hostName);
                connection.setUserID(user.getUserID());
                connection.setConnectionName(request.getParameter("connectionName"));
                connection.setConnectionTopic(request.getParameter("connectionTopic"));
                connection.setDateTime(request.getParameter("dateTime"));
                connection.setLocation(request.getParameter("location"));
                connection.setDetails(request.getParameter("details"));
                UserConnectionDB.addConnection(connection);
                collection = ConnectionDB.getConnections();
                request.setAttribute("collection", collection);
                request.setAttribute("userCollection", userCollection);
                getServletContext().getRequestDispatcher("/connections.jsp").forward(request,response);
            } catch (IntrusionException e) {
                        System.out.println("THE EXCEPTION IS FOR INTRUSION");
                        Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, e);
                        out.print("<p id=\"errorMessage\">Sorry hostname " + ESAPI.encoder().encodeForHTML(hostName) + " or connection name " + ESAPI.encoder().encodeForHTML(connectionName) + " or connection topic " + ESAPI.encoder().encodeForHTML(connectionTopic) + " or date and time " + ESAPI.encoder().encodeForHTML(dateTime) + " or location " + ESAPI.encoder().encodeForHTML(location) + " or details " + ESAPI.encoder().encodeForHTML(details) + " is not valid</p>");
                                    RequestDispatcher rd = request.getRequestDispatcher("newConnection.jsp");
                                    rd.include(request, response);
                    } catch (ValidationException ex) {
                        System.out.println("THE EXCEPTION IS FOR VALIDATION");
                        Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                        out.print("<p id=\"errorMessage\">Sorry hostname " + ESAPI.encoder().encodeForHTML(hostName) + " or connection name " + ESAPI.encoder().encodeForHTML(connectionName) + " or connection topic " + ESAPI.encoder().encodeForHTML(connectionTopic) + " or date and time " + ESAPI.encoder().encodeForHTML(dateTime) + " or location " + ESAPI.encoder().encodeForHTML(location) + " or details " + ESAPI.encoder().encodeForHTML(details) + " is not valid</p>");
                                    RequestDispatcher rd = request.getRequestDispatcher("newConnection.jsp");
                                    rd.include(request, response);
                    } catch (Exception ex) {
                        System.out.println("THE EXCEPTION IS GENERAL");
                        Logger.getLogger(ProfileControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
                        out.print("<p id=\"errorMessage\">Sorry hostname " + ESAPI.encoder().encodeForHTML(hostName) + " or connection name " + ESAPI.encoder().encodeForHTML(connectionName) + " or connection topic " + ESAPI.encoder().encodeForHTML(connectionTopic) + " or date and time " + ESAPI.encoder().encodeForHTML(dateTime) + " or location " + ESAPI.encoder().encodeForHTML(location) + " or details " + ESAPI.encoder().encodeForHTML(details) + " is not valid</p>");
                                    RequestDispatcher rd = request.getRequestDispatcher("newConnection.jsp");
                                    rd.include(request, response);
                    }
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
