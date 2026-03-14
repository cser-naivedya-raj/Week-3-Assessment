package java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.main;

import java.util.Scanner;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java_dev.java_advanced.framework.Hibernate_jpa.Gym_Membership_Workout_Tracking_System.dao.DAO;

public class JavaApp {
	public static void execution() {
		Scanner sc = new Scanner(System.in);
		int choice;
	    do {
	        System.out.println("\n===== GYM WORKOUT TRACKING SYSTEM =====");
	        System.out.println("1. Add Member with Workout");
	        System.out.println("2. Search Member by ID");
	        System.out.println("3. Update Workout Duration");
	        System.out.println("4. Delete Member");
	        System.out.println("5. Exit");
	        System.out.print("Enter Choice: ");
	        
			choice = sc.nextInt();
	        sc.nextLine();
	        
	        DAO d = new DAO();
	        switch (choice) {
	            case 1 :
	            	d.addMember();
	            	break;
	            case 2 : 
	            	d.searchMember();
	            	break;
	            case 3 : 
	            	d.updateWorkoutDuration();
	            	break;
	            case 4 : 
	            	d.deleteMember();
	            	break;
	            case 5 : 
	            	System.out.println("Exiting...");
	            	break;
	            default : 
	            	System.out.println("Invalid Choice!");
	            	 break;
	        }
	    } while (choice != 5);

	    
	}
}
