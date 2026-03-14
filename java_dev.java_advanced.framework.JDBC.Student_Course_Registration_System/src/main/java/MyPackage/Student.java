package MyPackage;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Student {
	static Scanner sc = new Scanner(System.in);
	private static final String URL = "jdbc:mysql://localhost:3306/university_db";
    private static final String USER = "root"; // DB username
    private static final String PASSWORD = "anant6350"; // DB password

    private static boolean isNumeric(String str) {
        try {
            int n = Integer.parseInt(str);
            return n > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean idExists(int id) {
        String query = "SELECT id FROM student WHERE id = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking ID: " + e.getMessage());
            return false;
        }
    }

    // Add Student
    public void addStudent() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("Enter Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter Course: ");
            String course = sc.nextLine().trim();
            System.out.print("Enter Semester: ");
            String semesterStr = sc.nextLine().trim();

            if (name.isEmpty() || course.isEmpty() || !isNumeric(semesterStr)) {
                System.out.println("Invalid input! Ensure all fields are valid and semester is numeric.");
                return;
            }

            int semester = Integer.parseInt(semesterStr);

            String query = "INSERT INTO student(name, course, semester) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, course);
            ps.setInt(3, semester);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Student added successfully");
                    System.out.println("ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Course: " + course);
                    System.out.println("Semester: " + semester);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    // Search Student
    public void searchStudent() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (!idExists(id)) {
            System.out.println("Student ID not found.");
            return;
        }

        String query = "SELECT * FROM student WHERE id = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Course: " + rs.getString("course"));
                System.out.println("Semester: " + rs.getInt("semester"));
            }
        } catch (SQLException e) {
            System.out.println("Error searching student: " + e.getMessage());
        }
    }

    // Update Course
    public void updateCourse() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (!idExists(id)) {
            System.out.println("Student ID not found.");
            return;
        }

        System.out.print("Enter New Course: ");
        String newCourse = sc.nextLine().trim();
        if (newCourse.isEmpty()) {
            System.out.println("Course cannot be empty.");
            return;
        }

        String query = "UPDATE student SET course = ? WHERE id = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, newCourse);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Course updated successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error updating course: " + e.getMessage());
        }
    }

    // Delete Student
    public void deleteStudent() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (!idExists(id)) {
            System.out.println("Student ID not found.");
            return;
        }

        String query = "DELETE FROM student WHERE id = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Student deleted successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
}