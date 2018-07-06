package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilities.ConnectionUtility;
import models.Admin;
import models.Customer;
import models.Employee;
import models.User;

public class UserDao {

	public User extractUser(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String username = rs.getString("username");
		String password = rs.getString("hash");
		int role = rs.getInt("role");
		if(role == 2)
			return new Admin();
		else if (role == 1)
			return new Employee();
		else
			return new Customer(id, username, password);
	}
	
	public User findUser(int userId) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "SELECT id, username, hash, role " 
				+ "FROM bank.users WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// extract and return user
				return extractUser(rs);
			}
			// if no user found
			return null;

		} catch (SQLException e) {
			System.out.println("Error from SQL Server.");
		}

		return null;
	}
	
	public User findUserByUsername(String username) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "SELECT id, username, hash, role " 
				+ "FROM bank.users WHERE username = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// extract and return user
				return extractUser(rs);
			}
			// if no user found
			return null;

		} catch (SQLException e) {
			System.out.println("Error from SQL Server.");
		}

		return null;
	}
	
	public User findUserByLogin(String name, String hash) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "SELECT id, username, hash, role " 
				+ "FROM bank.users WHERE username = ? AND hash = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, name);
			ps.setString(2, hash);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// extract and return user
				return extractUser(rs);
			}
			// if no user found
			return null;

		} catch (SQLException e) {
			System.out.println("Error from SQL Server.");
		}

		return null;
	}
	
	public void updateUser(User user) {
		Connection conn = ConnectionUtility.getConnection();
		String query = "UPDATE bank.users SET username = ?, "
				+ "hash = ? WHERE id = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getId());
			
			int altered = ps.executeUpdate();
			if(altered == 1) {
				System.out.println("Updated user.");
			} else {
				System.out.println("Could not find user.");
			}
		} catch(SQLException e) {
			System.out.println("Failed to update user.");
		}
	}

	public void delete(int userId) {
		if (userId == 1) {
			System.out.println("Cannot delete admin.");
			return;
		} else if (userId == 2) {
			System.out.println("Cannot delete employee.");
			return;
		}
		Connection conn = ConnectionUtility.getConnection();
		String query = "DELETE FROM bank.users WHERE id = ?";
		try(PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, userId);
			int deleted = ps.executeUpdate();
			if(deleted > 0) {
				System.out.println(deleted + " users deleted.");
			} else {
				System.out.println("No matching users found.");
			}
		} catch(SQLException e) {
			System.out.println("Failed to delete user.");
		}
	}

	public void createUser(User user) {
		Connection conn = ConnectionUtility.getConnection();
		
		String query = "INSERT INTO bank.users (username, hash, role) "
				+ "VALUES (?, ?, ?) RETURNING id";
		try(PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getRoleId());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				user.setId(rs.getInt("id"));
			}
			
		} catch(SQLException e) {
			System.out.println("Failed to insert user.");
		}
		
	}
}
