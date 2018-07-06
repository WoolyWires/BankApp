package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Customer;
import models.User;
import services.AccountService;
import services.UserService;
import utilities.ConnectionUtility;

public class RequestDao {
	
	UserService userService = new UserService();
	AccountService accountService = new AccountService();
	
	public User extractRequest(ResultSet rs) throws SQLException {
		int id = rs.getInt("request_id");
		String username = rs.getString("username");
		String password = rs.getString("hash");
		return new Customer(id, username, password);
	}
	
	public List<User> findRequest(int reqId) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "SELECT id, request_id, username, hash, date_created " 
				+ "FROM bank.requests WHERE request_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setInt(1, reqId);

			ResultSet rs = ps.executeQuery();
			List<User> matches = new ArrayList<>();
			while (rs.next()) {
				matches.add(extractRequest(rs));
			}
			return matches;

		} catch (SQLException e) {
			System.out.println("Error from SQL Server.");
		}

		return null;
	}
	
	public List<User> findRequest() {
		Connection conn = ConnectionUtility.getConnection();
		String query = "SELECT * FROM bank.requests";
		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ResultSet rs = ps.executeQuery();
			List<User> requests = new ArrayList<>();
			while (rs.next()) {
				requests.add(extractRequest(rs));
			}
			return requests;

		} catch (SQLException e) {
			System.out.println("Error from SQL Server.");
		}

		return null;
	}
	
	public User findRequestByUsername(String username) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "SELECT id, request_id, username, hash, date_created " 
				+ "FROM bank.requests WHERE username = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// extract and return request user
				return extractRequest(rs);
			}
			// if no request found
			return null;

		} catch (SQLException e) {
			System.out.println("Error from SQL Server.");
		}

		return null;
	}
	
	public void delete(int reqId) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "DELETE FROM bank.requests WHERE request_id = ?";
		try(PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setInt(1, reqId);
			
			int deleted = ps.executeUpdate();
			
			if(deleted > 0) {
				System.out.println(deleted + " requests deleted.");
			} else {
				System.out.println("No matching request found.");
			}
		} catch(SQLException e) {
			System.out.println("Error from SQL Server.");
		}
	}
	
	public void approve(int reqId) {
		List<User> requests = findRequest(reqId);
		if(requests != null) {
			try {
				Account newAccount = new Account(0, 0.0);
				accountService.insert(newAccount);
				System.out.println("Created account with ID: " +newAccount.getId());
				
				for(User user : requests) {
					userService.insert(user);
					System.out.println("Created user with ID: " +user.getId());
					accountService.insertUserAccount(user.getId(), newAccount.getId());
				}
				delete(reqId);
				
			} catch (Exception e) {
				System.out.println("Error from SQL Server.");
			}
		}
		else {
			System.out.println("No matching request found.");
		}
	}
	
	public void createRequest(int reqID, String username, String password) {
		
		Connection conn = ConnectionUtility.getConnection();
		
		String query = "INSERT INTO bank.requests (request_id, username, hash) "
				+ "VALUES (?, ?, ?)";
		try(PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, reqID);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.executeUpdate();
			System.out.println("Request submitted.");
			
		} catch(SQLException e) {
			System.out.println("Failed to submit request.");
		}
	}

}
