package models;

/**
 * Employee extension of class User.
 * EMployees have the role ID 1.
 * 
 * @author Aisha Peters
 *
 */

public final class Employee extends User {
	private static final long serialVersionUID = 3L;
	public Employee() {
		super(1, "employee", "employee", 1);
	}
}
