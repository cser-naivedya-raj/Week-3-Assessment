package MyPackage;

import java.util.Scanner;

public class JavaApp {
	public static void execution() {
		
		Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== GYM MEMBERSHIP MANAGEMENT =====");
            System.out.println("1. Add Member");
            System.out.println("2. Search Member by ID");
            System.out.println("3. Update Membership Fee");
            System.out.println("4. Delete Member");
            System.out.println("5. Exit");
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            Member m = new Member();
            switch (choice) {
                case 1 : 
                	m.addMember();
                	break;
                	
                case 2 :
                	m.searchMember();
                	break;
                case 3 :
                	m.updateFee();
                	break;
                case 4 : 
                	m.deleteMember();
                	break;
                case 5 :
                	System.out.println("Exiting...");
                	break;
                default :
                	System.out.println("Invalid Choice!");
                	break;
            }
        } while (choice != 5);

        sc.close();
    }
}
