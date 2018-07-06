package models;

import java.io.Serializable;

/**
 * Class to create users for bank accounts.
 * Each User has a username and password affiliated with their account,
 * as well as a role ID to identify their privileges in the bank.
 * 
 * @author Aisha Peters
 *
 */

public abstract class User implements Serializable {
	private static final long serialVersionUID = 3L;
	int id;
	String username;
	String password;
	int roleId;
	
	/* ============== Constructors ============= */
	
	public User(int iD, String name, String pass, int role) {
		id = iD;
		username = name;
		password = pass;
		roleId = role;
	}
	
	/*  ======== Getters & Setters ========== */
	
	public int getId() {
		return id;
	}

	public void setId(int iD) {
		id = iD;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleiD) {
		this.roleId = roleiD;
	}
	
	/*  ======== Overrides ========== */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + roleId;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roleId != other.roleId)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", roleId=" + roleId + "]";
	}

}
