package views;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import models.User;
import services.HelperService;
import services.RequestService;
import services.UserService;

public class RequestView {
	
	static Scanner scan = new Scanner(System.in);
	RequestService requestService = new RequestService();
	UserService userService = new UserService();
	
	public void options() {
		int option = 0;
		
		while(option != 4) {
			System.out.println("-----------------------------------\n");
			System.out.println("Please choose an option: ");
			System.out.println("1. View Requests");
			System.out.println("2. Approve Request");
			System.out.println("3. Deny Request");
			System.out.println("4. Exit");
			
			option = HelperService.getUserInput(4);
			
			switch(option) {
				case 1: 
					viewRequests();
					break;
				case 2: 
					approveRequest();
					break;
				case 3:
					denyRequest();
					break;
				case 4:
					return;
			}
		}
	}

	public void createRequest() {
		System.out.println("-----------------------------------\n");
		System.out.println("How many users on the account?: ");
		int count = HelperService.getUserInput();
		
		int reqID = generateID();
		
		for(int i = 1; i <= count; i++) {
			System.out.println("User " +i);
			System.out.println("Please enter your desired username: " );
			String username = scan.nextLine();
			String password = "";
			
			// check for username availability
			do {
				while(!validUsername(username)) {
					System.out.println("Username unavailable. Please try again: " );
					username = scan.nextLine();
				}
			} while(username.length() == 0);
			
			do{
				System.out.println("Please enter your desired password (must be at least 6 characters): " );
				password = scan.nextLine();
			}while(password.length() < 6);
			
			requestService.insert(reqID, username, password);
		}
	}

	// returns a randomly generated positive integer that doesn't exist in requests table
	private int generateID() {
		int reqId = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
		while (true) {
		    // generate new number if already used
			if (requestService.findRequest(reqId) == null)
				reqId = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
			else
				return reqId;
		}
	}

	// returns false if username exists in requests or users table
	private boolean validUsername(String username) {
		if(userService.findUserByUsername(username) != null || requestService.findRequestByUsername(username) != null)
			return false;
		return true;
	}
	
	private void viewRequests() {
		List<User> requests = requestService.findRequest();
		
		if (requests != null) {
			int i = 0;
			requests.forEach(u -> System.out.println(i + ": " + u));
		}
		
	}
	
	private void approveRequest() {
		System.out.println("-----------------------------------\n");
		System.out.println("Enter the ID of the request to approve: ");
		int reqId = HelperService.getUserInput();
		requestService.approve(reqId);
	}
	
	private void denyRequest() {
		System.out.println("-----------------------------------\n");
		System.out.println("Enter the ID of the request to deny: ");
		int reqId = HelperService.getUserInput();
		requestService.delete(reqId);
	}
}
