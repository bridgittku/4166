package edu.uncc.nbad;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.uncc.nbad.data.ConnectionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bridgittku
 */
public class ConnectionControllerServlet extends HttpServlet {

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
            out.println("<title>Servlet ConnectionControllerServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConnectionControllerServlet at " + request.getContextPath() + "</h1>");
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
        //load database of connections
        ArrayList<Connection> collection = ConnectionDB.getConnections();

        //check http request for parameter called "connectionID"
        String value = request.getParameter("connectionID");
        if (!"".equals(value)) /*If there is connectionID*/{
            System.out.println("THERE IS A CONNECTION ID");
            
            String validID = "";
            for (int i = 0; i < collection.size(); i++) {
               String selectedID = collection.get(i).getConnectionID();
               if (value.equals(selectedID)) {
                   validID = selectedID;
               }
            }
            
            if (!"".equals(validID)) /*If connection ID is valid*/ {
                System.out.println("THE CONNECTION ID IS VALID");
                // add object for specified connection to http request object
                Connection selectedConnection = ConnectionDB.getConnection(validID);
                request.setAttribute("connection", selectedConnection);
                //forward request to connection.jsp
                getServletContext().getRequestDispatcher("/connection.jsp").forward(request,response);
            } else /*If connection ID is not valid*/ {
                System.out.println("THE CONNECTION ID IS NOT VALID");
                //display the catalog as if no code had been provided
                request.setAttribute("collection", collection);
                //forward request to connection.jsp
                getServletContext().getRequestDispatcher("/connections.jsp").forward(request,response);
            }
        } else /*dispatch to connections.jsp*/ {
            System.out.println("DISPATCH TO CONNECTIONS.JSP");
            request.setAttribute("collection", collection);
            getServletContext().getRequestDispatcher("/connections.jsp").forward(request,response);
        }
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
        System.out.println("DISPATCHES TO DO POST");
        //load database of connections
        ArrayList<Connection> collection = ConnectionDB.getConnections();

        //check http request for parameter called "connectionID"
        String value = request.getParameter("connectionID");
        if (!"".equals(value)) /*If there is connectionID*/{
            System.out.println("THERE IS A CONNECTION ID");
            
            String validID = "";
            for (int i = 0; i < collection.size(); i++) {
               String selectedID = collection.get(i).getConnectionID();
               if (value.equals(selectedID)) {
                   validID = selectedID;
               }
            }
            
            if (!"".equals(validID)) /*If connection ID is valid*/ {
                System.out.println("THE CONNECTION ID IS VALID");
                // add object for specified connection to http request object
                Connection selectedConnection = ConnectionDB.getConnection(validID);
                request.setAttribute("connection", selectedConnection);
                //forward request to connection.jsp
                getServletContext().getRequestDispatcher("/connection.jsp").forward(request,response);
            } else /*If connection ID is not valid*/ {
                System.out.println("THE CONNECTION ID IS NOT VALID");
                //display the catalog as if no code had been provided
                request.setAttribute("collection", collection);
                //forward request to connection.jsp
                getServletContext().getRequestDispatcher("/connections.jsp").forward(request,response);
            }
        } else /*dispatch to connections.jsp*/ {
            System.out.println("DISPATCH TO CONNECTIONS.JSP");
            request.setAttribute("collection", collection);
            getServletContext().getRequestDispatcher("/connections.jsp").forward(request,response);
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
