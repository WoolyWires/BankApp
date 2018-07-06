package services;

import daos.UserDao;
import models.User;

public class UserService {

	UserDao userDao = new UserDao();
	
	public User findUser(int userId) {
		return userDao.findUser(userId);
	}
	
	public User findUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}
	
	public User findUserByLogin(String username, String password) {
		return userDao.findUserByLogin(username, password);
	}

	public void update(User user) {
		userDao.updateUser(user);
	}

	public void delete(int userId) {
		userDao.delete(userId);
	}

	public void insert(User user) {
		if(user.getUsername().length() == 0) {
			System.out.println("Invalid username.");
			return;
		}
		if(user.getPassword().length() < 6) {
			System.out.println("Invalid password.");
			return;
		}
		
		userDao.createUser(user);
	}
}
