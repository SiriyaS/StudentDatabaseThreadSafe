/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import model.*;

/**
 *
 * @author siriya_s
 */
public class StudentManager {
    public static void insertStudent(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        Double gpa = Double.parseDouble(request.getParameter("gpa"));
        Student stud = new Student(id, name, BigDecimal.valueOf(gpa));
        
        StudentTable.insertStudent(stud);
    }
    
    public static void updateStudent(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        Double gpa = Double.parseDouble(request.getParameter("gpa"));
        
        StudentTable.updateStudent(id, name, BigDecimal.valueOf(gpa));
    }
    
    public static void removeStudent(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        
        StudentTable.removeStudent(id);
    }
    
    public static List<Student> findStudent(HttpServletRequest request) {
        String radio = request.getParameter("filterBy");
        String filter = request.getParameter("filter");
        List<Student> result = new ArrayList<Student>();
        
        // check radio button value
        if(radio.equals("All")) {
            result = StudentTable.findAllStudent();
        }
        else if(radio.equals("ID")) {
            int id = Integer.parseInt(filter);
            result.add(StudentTable.findStudentById(id));
        }
        else if(radio.equals("Name")) {
            result = StudentTable.findStudentByName(filter);
        }
        else if(radio.equals("GPA")) {
            Double gpa = Double.parseDouble(filter);
            result = StudentTable.findStudentByGPA(BigDecimal.valueOf(gpa));
        }
        
        printAllStudent(result);
        return result;
    }
    
    public static void printAllStudent(List<Student> studentList) {
        for(Student stud : studentList) {
            System.out.print(stud.getId() + " ");
            System.out.print(stud.getName() + " ");
            System.out.println(stud.getGPA() + " ");
        }
    }
}
