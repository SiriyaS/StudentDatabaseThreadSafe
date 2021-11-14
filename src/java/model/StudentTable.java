/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author siriya_s
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author siriya_s
 */
public class StudentTable {
    
    public static void insertStudent(Student stud) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentDatabaseThreadSafePU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(stud);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static void updateStudent(Integer id, String name, BigDecimal gpa) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentDatabaseThreadSafePU");
        EntityManager em = emf.createEntityManager();
        Student fromDb = em.find(Student.class, id);
        fromDb.setName(name);
        fromDb.setGPA(gpa);
        em.getTransaction().begin();
        try {
            em.persist(fromDb);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public static void removeStudent(Integer id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentDatabaseThreadSafePU");
        EntityManager em = emf.createEntityManager();
        Student fromDb = em.find(Student.class, id);
        em.getTransaction().begin();
        try {
            em.remove(fromDb);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static List<Student> findAllStudent() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentDatabaseThreadSafePU");
        EntityManager em = emf.createEntityManager();
        List<Student> studList = (List<Student>) em.createNamedQuery("Student.findAll").getResultList();
        em.close();
        return studList;
    }

    public static Student findStudentById(Integer id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentDatabaseThreadSafePU");
        EntityManager em = emf.createEntityManager();
        Student stud = em.find(Student.class, id);
        em.close();
        return stud;
    }

    public static List<Student> findStudentByName(String name) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentDatabaseThreadSafePU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Student.findByName");
        query.setParameter("name", name);
        List<Student> studList = (List<Student>) query.getResultList();
        em.close();
        return studList;
    }

    public static List<Student> findStudentByGPA(BigDecimal gpa) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("StudentDatabaseThreadSafePU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Student.findByGpa");
        query.setParameter("gpa", gpa);
        List<Student> studList = (List<Student>) query.getResultList();
        em.close();
        return studList;
    }
}
