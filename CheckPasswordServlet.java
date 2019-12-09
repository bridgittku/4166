/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uncc.nbad;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bridgittku
 */
public class CheckPasswordServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //get parameters from the request
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
        String hashedPassword;
        String salt = "";
        String saltedAndHashedPassword;
        try {
            hashedPassword = PasswordUtil.hashPassword(password);
            salt = PasswordUtil.getSalt();
            saltedAndHashedPassword = PasswordUtil.hashAndSaltPassword(password);
        } catch (NoSuchAlgorithmException ex) {
            hashedPassword = ex.getMessage();
            saltedAndHashedPassword = ex.getMessage();
        }
        request.setAttribute("hashedPassword", hashedPassword);
        request.setAttribute("salt", salt);
        request.setAttribute("saltedAndHashedPassword", saltedAndHashedPassword);
        
        String url = "/login.jsp";
        getServletContext().getRequestDispatcher("/login.jsp").forward(request,response);
    }
}
