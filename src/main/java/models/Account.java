package models;

import java.io.Serializable;
import org.apache.log4j.Logger;

/**
 * Class to create accounts for class Bank to manage.
 * Each account has an affiliated ID and the current balance.
 * Account balance can be altered using methods withdraw, deposit, and transfer.
 * 
 * 
 * @author Aisha Peters
 *
 */

public class Account implements Serializable {
	private static final long serialVersionUID = 3L;
	private static Logger log = Logger.getRootLogger();
	protected int id;
	protected double balance;
	
	/* ============== Constructors ============= */
	
	public Account(int Id, double Balance) {
		log.debug("New account created.");
		id = Id;
		balance = Balance;
	}
	
	/*  ======== Getters & Setters ========== */
	
	public double getBalance() {
		return balance;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int Id) {
		id = Id;
	}
	
	/*  ======== Methods ========== */
	
	public void deposit(double amount) {
		log.info("Depositing amount: $" + amount);
		balance += amount;
	}
	
	public void withdraw(double amount) throws IllegalArgumentException{
		log.info("Withdrawing amount: $" + amount );
		if(amount > balance || amount < 0)
			throw new IllegalArgumentException();
		
		balance -= amount;
	}
	
	public void transfer(Account other, double amount) {
		this.withdraw(amount);
		other.deposit(amount);
	}
	
	/*  ======== Overrides ========== */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + "]";
	}
	
}
