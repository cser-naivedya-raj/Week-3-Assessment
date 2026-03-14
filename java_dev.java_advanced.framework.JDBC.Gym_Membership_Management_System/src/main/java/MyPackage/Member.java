package MyPackage;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Member {
	Scanner sc = new Scanner(System.in);
	public void createDatabase() {
		try {
			String url = "jdbc:mysql://localhost:3306/";
			String userName = "root";
			String password = "anant6350";

			Connection conn = DriverManager.getConnection(url, userName, password);

			Statement stm = conn.createStatement();

			String query = "create database gym_db";
			stm.execute(query);
			System.out.println("Database created Successfully ");

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createTable() {
		try {
			String url = "jdbc:mysql://localhost:3306/gym_db";
			String userName = "root";
			String password = "anant6350";

			Connection conn = DriverManager.getConnection(url, userName, password);

			Statement stm = conn.createStatement();

			String query = "create table member (id INT PRIMARY KEY,\r\n"
					+ "    name VARCHAR(50) NOT NULL,\r\n"
					+ "    membershipType ENUM('Basic','Standard','Premium') NOT NULL,\r\n"
					+ "    fee DOUBLE NOT NULL CHECK(fee > 0))";

			stm.execute(query);
			System.out.println("Table created Successfully ");

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isValidMembershipType(String type) {
        return type.equalsIgnoreCase("Basic") || type.equalsIgnoreCase("Standard") || type.equalsIgnoreCase("Premium");
    }
	
	private static boolean idExists(int id) {
        try {
            
        	String url = "jdbc:mysql://localhost:3306/gym_db";
			String userName = "root";
			String password = "anant6350";

			Connection conn = DriverManager.getConnection(url, userName, password);
			String query = "SELECT id FROM member WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking ID: " + e.getMessage());
            return false;
        }
    }
	
	
	public void addMember() {
	    try {
	        String url = "jdbc:mysql://localhost:3306/gym_db";
	        String userName = "root";
	        String password = "anant6350";

	        Connection conn = DriverManager.getConnection(url, userName, password);

	        System.out.print("Enter Id: ");
	        int nid = sc.nextInt();
	        sc.nextLine(); // consume newline
	        System.out.print("Enter Name: ");
	        String name = sc.nextLine().trim();
	        System.out.print("Enter Membership Type (Basic/Standard/Premium): ");
	        String type = sc.nextLine().trim();
	        System.out.print("Enter Fee: ");
	        double fee = sc.nextDouble();
	        sc.nextLine(); // consume newline

	        if (name.isEmpty() || !isValidMembershipType(type) || fee <= 0) {
	            System.out.println("Invalid input! Please check the constraints.");
	            return;
	        }

	        String query = "INSERT INTO member(id, name, membershipType, fee) VALUES (?, ?, ?, ?)";
	        PreparedStatement pstm = conn.prepareStatement(query);
	        pstm.setInt(1, nid);
	        pstm.setString(2, name);
	        pstm.setString(3, type);
	        pstm.setDouble(4, fee);

	        int rows = pstm.executeUpdate();
	        if (rows > 0) {
	            System.out.println("Member added successfully");
	            System.out.println("ID: " + nid);
	            System.out.println("Name: " + name);
	            System.out.println("Membership Type: " + type);
	            System.out.println("Fee: " + fee);
	        }

	        conn.close();
	    } catch (SQLException e) {
	        System.out.println("Error adding member: " + e.getMessage());
	    }
	}

	public void searchMember() {
		try {
			String url = "jdbc:mysql://localhost:3306/gym_db";
			String userName = "root";
			String password = "anant6350";

			Connection conn = DriverManager.getConnection(url, userName, password);

			Statement stm = conn.createStatement();

			System.out.print("Enter Member ID: ");
	        int id = sc.nextInt();
	        sc.nextLine();
	        if (!idExists(id)) {
	            System.out.println("Member ID not found.");
	            return;
	        }
	        
	        String query = "SELECT * FROM member WHERE id = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Membership Type: " + rs.getString("membershipType"));
                System.out.println("Fee: " + rs.getDouble("fee"));
            }
			
			conn.close();
		} catch (SQLException e) {
            System.out.println("Error searching member: " + e.getMessage());
        }
	}

	public void updateFee() {
		try {
			String url = "jdbc:mysql://localhost:3306/gym_db";
			String userName = "root";
			String password = "anant6350";

			Connection conn = DriverManager.getConnection(url, userName, password);

			System.out.print("Enter Member ID: ");
	        int id = sc.nextInt();
	        if (!idExists(id)) {
	            System.out.println("Member ID not found.");
	            sc.nextLine(); // Consume newline
	            return;
	        }
	        System.out.print("Enter New Fee: ");
	        double newFee = sc.nextDouble();
	        sc.nextLine();
	        if (newFee <= 0) {
	            System.out.println("Fee must be positive.");
	            return;
	        }
			
	        String updateQuery = "UPDATE member SET fee = ? WHERE id = ?";

			PreparedStatement pstm = conn.prepareStatement(updateQuery);
			
			pstm.setDouble(1, newFee);
            pstm.setInt(2, id);
            int rows = pstm.executeUpdate();
            if (rows > 0) {
                System.out.println("Membership fee updated successfully");
            }
			conn.close();
		} catch (SQLException e) {
            System.out.println("Error updating fee: " + e.getMessage());
        }
	}
	
	
	public void deleteMember() {
		try {
		String url = "jdbc:mysql://localhost:3306/gym_db";
		String userName = "root";
		String password = "anant6350";

		Connection conn = DriverManager.getConnection(url, userName, password);

		System.out.print("Enter Member ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (!idExists(id)) {
            System.out.println("Member ID not found.");
            return;
        }
        
		String deleteQuery  = "DELETE FROM member WHERE id = ?";

		PreparedStatement pstm = conn.prepareStatement(deleteQuery);
		pstm.setInt(1, id);
        int rows = pstm.executeUpdate();
        if (rows > 0) {
            System.out.println("Member deleted successfully");
        }
		conn.close();
	} catch (SQLException e) {
        System.out.println("Error deleting member: " + e.getMessage());
    }

	}

}
