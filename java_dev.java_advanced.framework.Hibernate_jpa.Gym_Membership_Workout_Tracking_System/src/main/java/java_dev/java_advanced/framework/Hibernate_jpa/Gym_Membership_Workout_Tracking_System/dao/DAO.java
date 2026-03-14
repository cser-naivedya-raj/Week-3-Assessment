package java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.dao;

import java.time.LocalDate;
import java.util.Scanner;

import jakarta.persistence.*;
import java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.entity.Member;
import java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.entity.Workout;

public class DAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Gym");
    private static final Scanner sc = new Scanner(System.in);

    // Add Member with Workout
    public static void addMember() {
        try {
            System.out.print("Enter Member ID: ");
            int memberId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter Membership Type: ");
            String type = sc.nextLine().trim();

            System.out.print("Enter Workout ID: ");
            int workoutId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Exercise Name: ");
            String exercise = sc.nextLine().trim();

            System.out.print("Enter Duration (minutes): ");
            int duration = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Workout Date (yyyy-MM-dd): ");
            String dateStr = sc.nextLine().trim();

            if (duration <= 0) {
                System.out.println("Duration must be positive.");
                return;
            }

            LocalDate workoutDate;
            try { workoutDate = LocalDate.parse(dateStr); }
            catch (Exception e) { System.out.println("Invalid date format."); return; }

            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();

            try {
                tx.begin();
                Member member = new Member(memberId, name, type);
                Workout workout = new Workout(workoutId, exercise, duration, workoutDate);
                workout.setMember(member);
                member.getWorkouts().add(workout);
                em.persist(member);
                tx.commit();
                System.out.println("Member added successfully!");
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Error adding member: " + e.getMessage());
            } finally { em.close(); }
        } catch (Exception e) { System.out.println("Invalid input!"); sc.nextLine(); }
    }

    // Search Member by ID
    public static void searchMember() {
        try {
            System.out.print("Enter Member ID: ");
            int memberId = sc.nextInt();
            sc.nextLine();

            EntityManager em = emf.createEntityManager();
            try {
                Member member = em.find(Member.class, memberId);
                if (member == null) { System.out.println("Member not found."); return; }

                System.out.println("ID: " + member.getId());
                System.out.println("Name: " + member.getName());
                System.out.println("Membership: " + member.getMembershipType());
                System.out.println("Workouts:");
                for (Workout w : member.getWorkouts()) {
                    System.out.println("  Workout ID: " + w.getId());
                    System.out.println("  Exercise: " + w.getExerciseName());
                    System.out.println("  Duration: " + w.getDuration());
                    System.out.println("  Date: " + w.getWorkoutDate());
                    System.out.println("-------------------");
                }
            } finally { em.close(); }
        } catch (Exception e) { System.out.println("Invalid input!"); sc.nextLine(); }
    }

    // Update Workout Duration
    public static void updateWorkoutDuration() {
        try {
            System.out.print("Enter Member ID: ");
            int memberId = sc.nextInt();
            System.out.print("Enter Workout ID: ");
            int workoutId = sc.nextInt();
            System.out.print("Enter New Duration: ");
            int newDuration = sc.nextInt();
            sc.nextLine();

            if (newDuration <= 0) { System.out.println("Duration must be positive."); return; }

            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();

            try {
                tx.begin();
                Member member = em.find(Member.class, memberId);
                if (member == null) { System.out.println("Member not found."); return; }

                Workout workout = null;
                for (Workout w : member.getWorkouts()) {
                    if (w.getId() == workoutId) { workout = w; break; }
                }
                if (workout == null) { System.out.println("Workout ID not found."); return; }

                workout.setDuration(newDuration);
                em.merge(member);
                tx.commit();
                System.out.println("Workout updated successfully!");
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Error updating workout: " + e.getMessage());
            } finally { em.close(); }
        } catch (Exception e) { System.out.println("Invalid input!"); sc.nextLine(); }
    }

    // Delete Member
    public static void deleteMember() {
        try {
            System.out.print("Enter Member ID: ");
            int memberId = sc.nextInt();
            sc.nextLine();

            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();

            try {
                tx.begin();
                Member member = em.find(Member.class, memberId);
                if (member == null) { System.out.println("Member not found."); return; }

                em.remove(member);
                tx.commit();
                System.out.println("Member deleted successfully!");
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Error deleting member: " + e.getMessage());
            } finally { em.close(); }
        } catch (Exception e) { System.out.println("Invalid input!"); sc.nextLine(); }
    }

    // Close EntityManagerFactory
    public static void closeFactory() {
        if (emf.isOpen()) { emf.close(); }
    }
}