package models;

/**
 * Admin extension of class User.
 * Admin have the role ID 2.
 * 
 * @author Aisha Peters
 *
 */

public final class Admin extends User {
	private static final long serialVersionUID = 3L;
	public Admin() {
		super(0, "admin", "admin", 2);
	}

}
