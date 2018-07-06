package views;

import java.util.Scanner;

import models.User;
import models.Account;
import services.AccountService;
import services.HelperService;
import services.UserService;

public class AdminView {

	static Scanner scan = new Scanner(System.in);
	UserService userService = new UserService();
	AccountService accountService = new AccountService();
	RequestView requestView = new RequestView();
	CustomerView customerView = new CustomerView();
	
	public void options() {
		int option = 0;
		
		while(option != 6) {
			System.out.println("-----------------------------------\n");
			System.out.println("Welcome to the Admin interface." );
			System.out.println("Choose an option: ");
			System.out.println("1. View Account Status");
			System.out.println("2. View User Information");
			System.out.println("3. Manage Requests");
			System.out.println("4. Manage Account");
			System.out.println("5. Remove Account");
			System.out.println("6. Exit");
			option = HelperService.getUserInput(6);
			switch(option) {
				case 1: 
					System.out.println(findAccount());
					break;
				case 2: 
					System.out.println(findUser());
					break;
				case 3: 
					requestView.options();
					break;
				case 4:
					manageAccount();
					break;
				case 5: 
					removeAccount();
					break;
				case 6: 
					return;
			}
		}
	}
	
	/* ============== Option Methods ============= */

	private void removeAccount() {
		System.out.println("-----------------------------------\n");
		System.out.println("Enter ID of the account to remove: ");
		int acctId = HelperService.getUserInput();
		
		accountService.delete(acctId);
	}

	private void manageAccount() {
		System.out.println("-----------------------------------\n");
		System.out.println("Enter the account ID to manage: ");
		int acctId = HelperService.getUserInput();
		Account acct = accountService.findAccount(acctId);
		if( acct == null)
			System.out.println("No account found.");
		else
			customerView.options(acct);
	}

	private Account findAccount() {
		System.out.println("-----------------------------------\n");
		System.out.println("Find account by:");
		System.out.println("1. Account ID");
		System.out.println("2. User ID");
		
		int choice = HelperService.getUserInput(2);

		if (choice == 1) {
			return getAccountById();
		}
		
		return getAccountByUser();
	}
	
	public Account getAccountById() {
		System.out.println("Enter the ID of the account you would like to find.");
		int acctId = HelperService.getUserInput();
		Account acct = accountService.findAccount(acctId);
		return acct;
	}
	
	public Account getAccountByUser() {
		System.out.println("Enter the user ID whose account you wish to find.");
		int acctId = HelperService.getUserInput();
		Account acct = accountService.findAccountByUserId(acctId);
		return acct;
	}
	
	private User findUser() {
		System.out.println("-----------------------------------\n");
		System.out.println("Find user by:");
		System.out.println("1. User ID");
		System.out.println("2. Username");
		
		int choice = HelperService.getUserInput(2);

		if (choice == 1) {
			return getUserById();
		}
		
		return getUserByUsername();
	}
	
	private User getUserById() {
		System.out.println("Enter the ID of the user you would like to view.");
		int userId = HelperService.getUserInput();
		User user = userService.findUser(userId);
		return user;
	}
	
	private User getUserByUsername() {
		System.out.println("Enter the username of the user you wish to view.");
		String username = scan.nextLine();
		User user = userService.findUserByUsername(username);
		return user;
	}
	
}
