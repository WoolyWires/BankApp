package services;

import daos.AccountDao;
import models.Account;

public class AccountService {
	AccountDao AccountDao = new AccountDao();
	
	public Account findAccount(int acctId) {
		return AccountDao.findAccount(acctId);
	}
	
	public Account findAccountByUserId(int userId) {
		return AccountDao.findAccountByUserId(userId);
	}
	
	public void update(Account account) {
		AccountDao.updateAccount(account);
	}

	public void delete(int acctId) {
		Account account = findAccount(acctId);
		Account admin = findAccount(1);
		if(account == null) {
			System.out.println("No matching accounts found.");
			return;
		}else if(account.getId() == 1) {
			System.out.println("Cannot delete admin account.");
			return;
		}else if(admin == null) {
			System.out.println("No admin account exists for balance transfer.");
			return;
		}else {
			account.transfer(admin, account.getBalance());
			AccountDao.updateAccount(account);
			AccountDao.updateAccount(admin);
		}
		
		AccountDao.delete(acctId);
	}

	public void insert(Account account) {
		AccountDao.createAccount(account);
	}
	
	public void insertUserAccount(int userId, int acctId) {
		AccountDao.createUserAccount(userId, acctId);
	}
}
