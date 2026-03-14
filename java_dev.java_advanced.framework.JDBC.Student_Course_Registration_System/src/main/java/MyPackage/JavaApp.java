package MyPackage;

import java.util.Scanner;

public class JavaApp {
	public static void execution() {
		
		Scanner sc = new Scanner(System.in);
	    int choice;

	    do {
	        System.out.println("\n===== STUDENT COURSE REGISTRATION =====");
	        System.out.println("1. Add Student");
	        System.out.println("2. Search Student by ID");
	        System.out.println("3. Update Course");
	        System.out.println("4. Delete Student");
	        System.out.println("5. Exit");
	        System.out.print("Enter Choice: ");
	        choice = sc.nextInt();
	        sc.nextLine(); // consume newline

	        Student s = new Student();
	        
	        switch (choice) {
	            case 1 :
	            	s.addStudent();
	            	break;
	            case 2 :
	            	s.searchStudent();
	            	break;
	            case 3 :
	            	s.updateCourse();
	            	break;
	            case 4 : 
	            	s.deleteStudent();
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