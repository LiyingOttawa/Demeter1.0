/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import businesslayer.NavigationHelper;
import businesslayer.AuthService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserDTO;

/**
 *
 * @author liyingguo
 */
@WebServlet(name = "AuthControllerServlet", urlPatterns = {"/auth/*"})
public class AuthControllerServlet extends HttpServlet {
    AuthService dataService = new AuthService();

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
        String action = request.getPathInfo();
        switch (action) {
            case "/logout":
                request.getSession().invalidate();
                NavigationHelper.back(request, response);
                break;
            case "/register":
                NavigationHelper.goTo(request, response, "/views/auth/register.jsp");
                break;
            case "/profile":
                NavigationHelper.goTo(request, response, "/views/auth/profile.jsp");
                break;
            case "/users":
                dataService = new AuthService();
                List<UserDTO> users = null;

                try {
                    users = dataService.getAllUsers();
                    request.setAttribute("users", users);
                    NavigationHelper.goTo(request, response, "/views/auth/users.jsp");
                    break;
                } catch (SQLException ex) {
                    NavigationHelper.HandleError(response, ex);
                }

                break;

            case "/login":
            default:
                if(dataService.isLoggedIn(request))
                {
                    String firstPath =  dataService.getFirstPath(request);
                    response.sendRedirect("/"+firstPath+"/");
                }
                else
                {
                    NavigationHelper.goTo(request, response, "/views/auth/login.jsp");
                }
                break;
             
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
        String action = request.getPathInfo();
        switch (action) {
            case "/login":
            default:
                if(dataService.login(request))
                {
                    response.sendRedirect("/");
                }
                else
                {
                    request.setAttribute("message", "Invalid username or password. Please try again.");
                    NavigationHelper.goTo(request, response, "/views/auth/login.jsp");
                }
                break;
            case "/register":
                NavigationHelper.goTo(request, response, "/views/auth/register.jsp");
                break;
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
