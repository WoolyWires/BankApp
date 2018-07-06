package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Account;
import utilities.ConnectionUtility;

public class AccountDao {

	public Account extractAccount(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		double balance = rs.getDouble("balance");
		return new Account(id, balance);
	}
	
	public Account findAccount(int acctId) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "SELECT id, balance " 
				+ "FROM bank.accounts WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setInt(1, acctId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// extract and return account
				return extractAccount(rs);
			}
			// if no account found
			return null;

		} catch (SQLException e) {
			System.out.println("Error from SQL Server.");
		}

		return null;
	}
	
	public Account findAccountByUserId(int userId) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "SELECT user_id, account_id " 
				+ "FROM bank.user_accounts WHERE user_id = ?";
		
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// extract, find, and return account
				int acctId = rs.getInt("account_id");
				return findAccount(acctId);
			}
			// if no account found
			return null;

		} catch (SQLException e) {
			System.out.println("Error from SQL Server.");
		}

		return null;
	}
	

	public void updateAccount(Account account) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "UPDATE bank.accounts SET balance = ? "
				+ "WHERE id = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setDouble(1, account.getBalance());
			ps.setInt(2, account.getId());
			
			int altered = ps.executeUpdate();
			if(altered == 1) {
				System.out.println("Updated account.");
			} else {
				System.out.println("Could not find account.");
			}
		} catch(SQLException e) {
			System.out.println("Failed to update account.");
		}
	}

	public void delete(int acctId) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "DELETE FROM bank.accounts WHERE id = ?";
		try(PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, acctId);
			
			int deleted = ps.executeUpdate();
			
			if(deleted > 0) {
				System.out.println(deleted + " account deleted.");
			} else {
				System.out.println("No matching accounts found.");
			}
		} catch(SQLException e) {
			System.out.println("Failed to delete account.");
		}
	}

	public void createAccount(Account account) {
		//Update account instead if the ID already exists
		if(account.getId() > 0) {
			updateAccount(account);
			return;
		}
		
		Connection conn = ConnectionUtility.getConnection();
		
		String query = "INSERT INTO bank.accounts (balance) "
				+ "VALUES (?) RETURNING id";
		try(PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setDouble(1, account.getBalance());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				account.setId(rs.getInt("id"));
			}
			
		} catch(SQLException e) {
			System.out.println("Failed to insert account.");
		}
	}
	
	public void createUserAccount(int userId, int acctId) {
		Connection conn = ConnectionUtility.getConnection();
		
		String query = "INSERT INTO bank.user_accounts (user_id, account_id) "
				+ "VALUES (?, ?)";
		try(PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setDouble(1, userId);
			ps.setDouble(2, acctId);
			ps.executeUpdate();
			
			System.out.println("Account linked to user.");
		} catch(SQLException e) {
			System.out.println("Failed to insert user account.");
		}
	}
	
}
