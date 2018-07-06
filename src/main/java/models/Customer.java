package models;

/**
 * Customer extension of class User.
 * 
 * @author Aisha Peters
 *
 */

public final class Customer extends User {
	private static final long serialVersionUID = 3L;
	public Customer(int id, String name, String pass) {
		super(id, name, pass, 0);
	}

}
