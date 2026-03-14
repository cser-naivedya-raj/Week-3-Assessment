package MyPackage;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Book {
	static Scanner sc = new Scanner(System.in);
	private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root"; // DB username
    private static final String PASSWORD = "anant6350"; // DB password

    private static boolean idExists(int id) {
        String query = "SELECT id FROM book WHERE id = ?";
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

    // Add Book
    static void addBook() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("Enter Title: ");
            String title = sc.nextLine().trim();
            System.out.print("Enter Author: ");
            String author = sc.nextLine().trim();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            sc.nextLine(); // consume newline

            if (title.isEmpty() || author.isEmpty() || price <= 0) {
                System.out.println("Invalid input! Ensure title/author are not empty and price is positive.");
                return;
            }

            String query = "INSERT INTO book(title, author, price) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setDouble(3, price);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Book added successfully");
                    System.out.println("ID: " + id);
                    System.out.println("Title: " + title);
                    System.out.println("Author: " + author);
                    System.out.println("Price: " + price);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    // Search Book
    static void searchBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (!idExists(id)) {
            System.out.println("Book ID not found.");
            return;
        }

        String query = "SELECT * FROM book WHERE id = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Price: " + rs.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println("Error searching book: " + e.getMessage());
        }
    }

    // Update Price
    static void updatePrice() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (!idExists(id)) {
            System.out.println("Book ID not found.");
            return;
        }

        System.out.print("Enter New Price: ");
        double newPrice = sc.nextDouble();
        sc.nextLine();
        if (newPrice <= 0) {
            System.out.println("Price must be positive.");
            return;
        }

        String query = "UPDATE book SET price = ? WHERE id = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, newPrice);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book price updated successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error updating price: " + e.getMessage());
        }
    }

    // Delete Book
    static void deleteBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (!idExists(id)) {
            System.out.println("Book ID not found.");
            return;
        }

        String query = "DELETE FROM book WHERE id = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book deleted successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }
}