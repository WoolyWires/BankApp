package views;

import java.util.Scanner;

import models.Account;
import models.User;
import services.AccountService;
import services.HelperService;
import services.UserService;

public class CustomerView {
	
	static Scanner scan = new Scanner(System.in);
	UserService userService = new UserService();
	AccountService accountService = new AccountService();
	Account userAccount;
	
	public void options(User user) {
		System.out.println("-----------------------------------\n");
		System.out.println("Welcome " +user.getUsername()+ ".");
		userAccount = accountService.findAccountByUserId(user.getId());
		if(userAccount == null) {
			System.out.println("It appears you don't have an account. Please contact customer service.");
			return;
		}
		options(userAccount);
	}

	public void options(Account acct) {
		userAccount = acct;
		int option = 0;

		while(option != 5) {
			System.out.println("-----------------------------------\n");
			System.out.println("Choose an option:");
			System.out.println("1. Check balance");
			System.out.println("2. Deposit");
			System.out.println("3. Withdraw");
			System.out.println("4. Transfer");
			System.out.println("5. Exit");
			
			option = HelperService.getUserInput(5);
			
			switch(option) {
				case 1: 
					balance();
					break;
				case 2: 
					deposit();
					break;
				case 3: 
					withdraw();
					break;
				case 4: 
					transfer();
					break;
				case 5: 
					System.out.println("Thank you.");
					return;
				}
		}
	}
	
	/* ============== Option Methods ============= */
	
	private void balance() {
		System.out.println("-----------------------------------\n");
		System.out.printf("Account balance: $%.2f\n", userAccount.getBalance());
	}
	
	private void deposit() {
		System.out.println("-----------------------------------\n");
		System.out.println("Enter an amount to deposit (0 to exit): ");
		double amount = Double.parseDouble(scan.nextLine());
		if( amount <= 0) return;
		
		userAccount.deposit(amount);
		accountService.update(userAccount);
		
		System.out.printf("$%.2f has been deposited.\n", amount);
	}
	
	private void withdraw() {
		System.out.println("-----------------------------------\n");
		System.out.println("Enter an amount to withdraw (0 to exit): ");
		double amount = Double.parseDouble(scan.nextLine());
		if( amount <= 0) return;
		
		try {
			userAccount.withdraw(amount);
			accountService.update(userAccount);
			System.out.printf("$%.2f has been withdrawn.\n", amount);
			System.out.printf("Remaining balance: $%.2f\n", userAccount.getBalance());
		}
		catch(IllegalArgumentException e) {
			System.out.println("Withdrawal cannot be completed.");
		}
		
	}	
	
	private void transfer() {
		System.out.println("-----------------------------------\n");
		System.out.println("Enter an amount to transfer (0 to exit): ");
		double amount = Double.parseDouble(scan.nextLine());
		if( amount <= 0) return;
		
		System.out.println("Enter account ID to transfer to: ");
		int acctId = HelperService.getUserInput();
		Account otherAccount = accountService.findAccount(acctId);
		if( otherAccount == null) {
			System.out.println("Account not found.");
			return;
		}
		try {
			userAccount.transfer(otherAccount, amount);
			accountService.update(userAccount);
			accountService.update(otherAccount);
		}
		catch(IllegalArgumentException e) {
			System.out.println("Tansfer could not be completed.");
		}
		
	}
	
}

