package services;

import java.util.Scanner;

public class HelperService {
	
	static Scanner scan = new Scanner(System.in);

	public static int getUserInput(int maxValue) {
		int value;
		while (true) {
			String str = scan.nextLine();
			try {
				value = Integer.parseInt(str);
				if (value > maxValue || value <= 0)
					throw new NumberFormatException();
				return value;
			} catch (NumberFormatException e) {
				System.out.println("Please enter an integer value between 1 and " + maxValue);
			}
		}
	}
	
	public static int getUserInput() {
		int value;
		while (true) {
			String str = scan.nextLine();
			try {
				value = Integer.parseInt(str);
				if (value <= 0)
					throw new NumberFormatException();
				return value;
			} catch (NumberFormatException e) {
				System.out.println("Please enter an integer value greater than 0");
			}
		}
	}
}
