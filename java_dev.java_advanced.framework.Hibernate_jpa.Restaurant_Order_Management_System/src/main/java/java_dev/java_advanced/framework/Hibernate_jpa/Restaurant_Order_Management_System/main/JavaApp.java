package java_dev.java_advanced.framework.Hibernate_jpa.Restaurant_Order_Management_System.main;

import java.util.Scanner;

import java_dev.java_advanced.framework.Hibernate_jpa.Restaurant_Order_Management_System.dao.DAO;

public class JavaApp {

    public static void execution() {

    	DAO dao = new DAO();
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose operation:");
        System.out.println("1. Add Order with Dish");
        System.out.println("2. Search Order by ID");
        System.out.println("3. Update Dish Quantity");
        System.out.println("4. Delete Order");
        System.out.print("Enter choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1:
                System.out.print("Enter customerName orderDate dishName quantity price: ");
                String line = sc.nextLine().trim();
                String[] parts = line.split(" ");
                String customerName = parts[0];
                String orderDate    = parts[1];
                String dishName     = parts[2];
                int quantity        = Integer.parseInt(parts[3]);
                double price        = Double.parseDouble(parts[4]);
                dao.addOrder(customerName, orderDate, dishName, quantity, price);
                break;

            case 2:
                System.out.print("Enter Order ID: ");
                int searchId = sc.nextInt();
                dao.searchOrder(searchId);
                break;

            case 3:
                System.out.print("Enter orderId dishId newQuantity: ");
                int orderId    = sc.nextInt();
                int dishId     = sc.nextInt();
                int newQty     = sc.nextInt();
                dao.updateDishQuantity(orderId, dishId, newQty);
                break;

            case 4:
                System.out.print("Enter Order ID to delete: ");
                int deleteId = sc.nextInt();
                dao.deleteOrder(deleteId);
                break;

            default:
                System.out.println("Invalid choice");
        }

        dao.close();
        sc.close();
    }
}