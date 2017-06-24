package challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import challenge.dao.UserDAOImpl;
import challenge.pojos.User;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired UserDAOImpl userDao;
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers(){
		return (List<User>) userDao.getAllUsers();
	}
	
	@Override 
	public User getUser(String userName){
		User user= (User) userDao.getUser(userName);
		if(user!=null) return user;
		return null;
	}
	
	@Override
	public boolean checkValidUsers(long userId1, long userId2){
		return userDao.checkValidUsers(userId1, userId2);
	}

	@Override
	public User getUserById(int user_id) {
		User user= (User) userDao.getUserById(user_id);
		if(user!=null) return user;
		return null;
	}
	
}
