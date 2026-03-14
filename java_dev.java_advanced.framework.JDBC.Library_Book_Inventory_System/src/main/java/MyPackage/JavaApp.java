package MyPackage;

import java.util.Scanner;

public class JavaApp {
	public static void execution() {
		
		Scanner sc = new Scanner(System.in);
	    int choice;

	    do {
	    	System.out.println("\n===== LIBRARY BOOK INVENTORY =====");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book by ID");
            System.out.println("3. Update Book Price");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // consume newline

	        Book s = new Book();
	        
	        switch (choice) {
	            case 1 :
	            	s.addBook();
	            	break;
	            case 2 :
	            	s.searchBook();
	            	break;
	            case 3 :
	            	s.updatePrice();
	            	break;
	            case 4 : 
	            	s.deleteBook();
	            	break;
	            case 5 :
	            	System.out.println("Exiting...");
	            	break;
	            default :
	            	System.out.println("Invalid Choice! Please enter 1-5.");
	            	break;
	        }
	    } while (choice != 5);

	    sc.close();
	}
}