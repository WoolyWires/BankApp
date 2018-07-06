package bankTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import models.Account;

public class AccountTest {
	Account account;
	
	@Before
	public void testSetup() {
		account = new Account(5, 0);
	}
	
	/*  ======== Account Tests ========== */
	
	@Test
	public void testID() {
		assertTrue("Account should have ID equal to set ID", 5 == account.getId() );
	}
	
	@Test
	public void testDeposit() {
		account.deposit(15);

		assertTrue("Account should have balance equal to deposit", 15 == account.getBalance() );
	}
	
	@Test
	public void testWithdraw() {
		account.deposit(10);
		account.withdraw(5);
		double balance = account.getBalance();

		assertTrue("Account should have balance equal to deposit - withdraw", 5 == balance );
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDeposit() throws Exception {
		account.deposit(-10);
		
		fail("Negative deposit should throw exception.");
	}
	
}
