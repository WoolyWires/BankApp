package bankTest;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import models.Admin;
import models.Customer;
import models.Employee;
import models.User;

public class UserTest {
	User customer, employee, admin;
	
	@Before
	public void testSetup() {
		customer = new Customer(4, "Customer", "CustomerPass");
		employee = new Employee();
		admin = new Admin();
	}
	
	/*  ======== Customer Tests ========== */
	
	@Test
	public void testCustomer_getName() {
		assertTrue("Customer should have username 'Customer'", "Customer" == customer.getUsername() );
	}
	
	@Test
	public void testCustomer_setName() {
		customer.setUsername("Name");
		assertTrue("Customer should have username 'Name'", "Name" == customer.getUsername() );
	}
	
	@Test
	public void testCustomer_getPass() {
		assertTrue("Customer should have password 'CustomerPass'", "CustomerPass" == customer.getPassword() );
	}
	
	@Test
	public void testCustomer_setPass() {
		customer.setPassword("Pass");
		assertTrue("Customer should have password 'Pass'", "Pass" == customer.getPassword() );
	}
	
	@Test
	public void testCustomer_getRole() {
		assertTrue("Customer should have role ID 1", 0 == customer.getRoleId() );
	}
	
	/*  ======== Employee Tests ========== */
	
	@Test
	public void testEmployee() {
		assertTrue("Employee should have role ID 1", 1 == employee.getRoleId() );
	}
	
	/*  ======== Admin Tests ========== */
	
	@Test
	public void testAdmin() {
		assertTrue("Employee should have role ID 2", 2 == admin.getRoleId() );
	}
}
