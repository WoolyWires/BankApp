package views;

import java.util.Scanner;
import models.User;
import services.HelperService;
import services.UserService;

public class UserView {
	
	static Scanner scan = new Scanner(System.in);
	UserService userService = new UserService();
	RequestView request = new RequestView();
	AdminView admin = new AdminView();
	EmployeeView employee = new EmployeeView();
	CustomerView customer = new CustomerView();

	public void options() {
		System.out.println("-----------------------------------\n");
		System.out.println("Hello. Welcome to CoinSnatchers Bank.");
		System.out.println("Please choose an option: ");
		System.out.println("1. Login");
		System.out.println("2. Request Account");
		System.out.println("3. Exit");
		
		int option = HelperService.getUserInput(3);
		
		switch(option) {
			case 1: 
				userLogin();
				break;
			case 2: 
				request.createRequest();
				break;
			case 3:
				System.out.println("-----------------------------------\n");
				System.out.println("Goodbye.");
				System.out.println("-----------------------------------\n");
				System.exit(0);
		}
		options();
	}
	
	private void userLogin() {
		System.out.println("-----------------------------------\n");
		// check for valid account using user input
		System.out.println("Username: " );
		String username = scan.nextLine();
		System.out.println("Password: " );
		String password = scan.nextLine();
		User user = userService.findUserByLogin(username, password);
		if(user == null) {
			System.out.println("Account not found. Exiting...\n");
			options();
		}
		
		int role = user.getRoleId();
		
		// redirect to view according to user role
		if(role == 2) admin.options();
		else if(role == 1) employee.options();
		else customer.options(user);
	}

}
