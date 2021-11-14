/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Student;

/**
 *
 * @author siriya_s
 */
public class StudentServlet extends HttpServlet {

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
        
        String urlPath = request.getServletPath();
        synchronized(getServletContext()) {
            switch (urlPath) {
                case "/findStudent":
                    List<Student> result = StudentManager.findStudent(request);
                    request.setAttribute("message", "Find student result:");
                    request.setAttribute("list", result);
                    break;
                case "/insertStudent":
                    StudentManager.insertStudent(request);
                    request.setAttribute("message", "Insert student successfully.");
                    break;
                case "/editStudent":
                    int inputID = Integer.parseInt(request.getParameter("id"));
                    // get student id in context
                    List<Integer> lockID = (List<Integer>)getServletContext().getAttribute("lock_id");
                    if(lockID == null) {
                        // set student id in context
                        List<Integer> newList = new ArrayList<Integer>();
                        newList.add(inputID);
                        getServletContext().setAttribute("lock_id", newList);
                    }
                    else {
                        // check inputID and lockID
                        // if not contains -> setAttribute
                        if(!lockID.contains(inputID)) {
                            lockID.add(inputID);
                            getServletContext().setAttribute("lock_id", lockID);
                        }
                        else { // else error cannot edit
                            request.setAttribute("message", "This student record is accessed by another user, you cannot access now.");
                            break;
                        }
                    }

                    StudentManager.updateStudent(request);
                    request.setAttribute("message", "Edit student successfully.");

                    // remove student id from list
                    lockID = (List<Integer>)getServletContext().getAttribute("lock_id");
                    lockID.remove(lockID.indexOf(inputID));
                    getServletContext().setAttribute("lock_id", lockID);
                    break;


                case "/deleteStudent":
                    inputID = Integer.parseInt(request.getParameter("id"));
                    // get student id in context
                    lockID = (List<Integer>)getServletContext().getAttribute("lock_id");
                    if(lockID == null) {
                        // set student id in context
                        List<Integer> newList = new ArrayList<Integer>();
                        newList.add(inputID);
                        getServletContext().setAttribute("lock_id", newList);
                    }
                    else {
                        // check inputID and lockID
                        // if not contains -> setAttribute
                        if(!lockID.contains(inputID)) {
                            lockID.add(inputID);
                            getServletContext().setAttribute("lock_id", lockID);
                        }
                        else { // else error cannot edit
                            request.setAttribute("message", "This student record is accessed by another user, you cannot access now.");
                            break;
                        }
                    }

                    StudentManager.removeStudent(request);
                    request.setAttribute("message", "Delete student successfully.");

                    // remove student id from list
                    lockID = (List<Integer>)getServletContext().getAttribute("lock_id");
                    lockID.remove(lockID.indexOf(inputID));
                    getServletContext().setAttribute("lock_id", lockID);
                    break;

                default:
                    break;
            }

            try (PrintWriter out = response.getWriter()) {
                request.getRequestDispatcher("result.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
        processRequest(request, response);
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
