package java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.main;

import java.util.Scanner;

import java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.dao.DAO;

public class JavaApp {

    public static void execution() {

        DAO dao = new DAO();
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose operation:");
        System.out.println("1. Add Course with Enrollment");
        System.out.println("2. Search Course by ID");
        System.out.println("3. Update Enrollment Date");
        System.out.println("4. Delete Course");
        System.out.print("Enter choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1:
                System.out.print("Enter courseName instructor studentName enrollmentDate: ");
                String line  = sc.nextLine().trim();
                String[] parts        = line.split(" ");
                String courseName     = parts[0];
                String instructor     = parts[1];
                String studentName    = parts[2];
                String enrollmentDate = parts[3];
                dao.addCourse(courseName, instructor, studentName, enrollmentDate);
                break;

            case 2:
                System.out.print("Enter Course ID: ");
                int searchId = sc.nextInt();
                dao.searchCourse(searchId);
                break;

            case 3:
                System.out.print("Enter courseId enrollmentId newDate: ");
                int courseId     = sc.nextInt();
                int enrollmentId = sc.nextInt();
                String newDate   = sc.next();
                dao.updateEnrollmentDate(courseId, enrollmentId, newDate);
                break;

            case 4:
                System.out.print("Enter Course ID to delete: ");
                int deleteId = sc.nextInt();
                dao.deleteCourse(deleteId);
                break;

            default:
                System.out.println("Invalid choice");
        }

        dao.close();
        sc.close();
    }
}