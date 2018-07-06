package services;

import java.util.List;

import daos.RequestDao;
import models.User;

public class RequestService {
	RequestDao requestDao = new RequestDao();
	
	public List<User> findRequest(int reqId) {
		return requestDao.findRequest(reqId);
	}
	
	public List<User> findRequest() {
		return requestDao.findRequest();
	}
	
	public User findRequestByUsername(String username) {
		return requestDao.findRequestByUsername(username);
	}

	public void insert(int reqId, String username, String password) {		
		requestDao.createRequest(reqId, username, password);
	}
	
	public void delete(int reqId) {
		requestDao.delete(reqId);
	}
	
	public void approve(int reqId) {
		requestDao.approve(reqId);
	}
	
}
