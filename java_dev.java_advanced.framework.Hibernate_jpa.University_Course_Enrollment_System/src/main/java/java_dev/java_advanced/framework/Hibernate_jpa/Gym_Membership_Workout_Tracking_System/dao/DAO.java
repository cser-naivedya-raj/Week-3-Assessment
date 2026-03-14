package java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.dao;

import jakarta.persistence.*;
import java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.entity.Course;
import java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.entity.Enrollment;

import java.util.List;

public class DAO {

    private EntityManagerFactory emf;

    public DAO() {
        emf = Persistence.createEntityManagerFactory("Course");
    }

    // ADD Course with Enrollment
    public void addCourse(String courseName, String instructor,
                          String studentName, String enrollmentDate) {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Course course = new Course(courseName, instructor);
        em.persist(course);

        Enrollment enrollment = new Enrollment(studentName, enrollmentDate, course);
        em.persist(enrollment);

        em.getTransaction().commit();
        System.out.println("Course added successfully");

        printCourse(em, course.getId());

        em.close();
    }

    // SEARCH Course by ID
    public void searchCourse(int id) {
        EntityManager em = emf.createEntityManager();
        printCourse(em, id);
        em.close();
    }

    // UPDATE Enrollment Date
    public void updateEnrollmentDate(int courseId, int enrollmentId, String newDate) {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Course course = em.createQuery(
                "SELECT c FROM Course c WHERE c.id = :id", Course.class)
                .setParameter("id", courseId)
                .getResultStream().findFirst().orElse(null);

        if (course == null) {
            System.out.println("Course not found");
            em.getTransaction().rollback();
            em.close();
            return;
        }

        Enrollment enrollment = em.createQuery(
                "SELECT e FROM Enrollment e WHERE e.id = :id", Enrollment.class)
                .setParameter("id", enrollmentId)
                .getResultStream().findFirst().orElse(null);

        if (enrollment == null) {
            System.out.println("Enrollment not found");
            em.getTransaction().rollback();
            em.close();
            return;
        }

        enrollment.setEnrollmentDate(newDate);
        em.merge(course);

        em.getTransaction().commit();
        System.out.println("Enrollment updated successfully");
        em.close();
    }

    // DELETE Course
    public void deleteCourse(int id) {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Course course = em.createQuery(
                "SELECT c FROM Course c WHERE c.id = :id", Course.class)
                .setParameter("id", id)
                .getResultStream().findFirst().orElse(null);

        if (course == null) {
            System.out.println("Course not found");
            em.getTransaction().rollback();
            em.close();
            return;
        }

        em.remove(course);
        em.getTransaction().commit();
        System.out.println("Course deleted successfully");
        em.close();
    }

    // Helper: print course details
    private void printCourse(EntityManager em, int id) {

        Course course = em.createQuery(
                "SELECT c FROM Course c WHERE c.id = :id", Course.class)
                .setParameter("id", id)
                .getResultStream().findFirst().orElse(null);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        List<Enrollment> enrollments = em.createQuery(
                "SELECT e FROM Enrollment e WHERE e.course.id = :id", Enrollment.class)
                .setParameter("id", id)
                .getResultList();

        System.out.println("ID: " + course.getId());
        System.out.println("Course: " + course.getCourseName());
        System.out.println("Instructor: " + course.getInstructor());
        System.out.println("Enrollments:");
        for (Enrollment e : enrollments) {
            System.out.println("  Student Name: " + e.getStudentName());
            System.out.println("  Enrollment Date: " + e.getEnrollmentDate());
        }
    }

    public void close() {
        emf.close();
    }
}