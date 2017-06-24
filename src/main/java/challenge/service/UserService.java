package challenge.service;

import java.util.List;

import challenge.pojos.User;

public interface UserService {
	public List<User> getAllUsers();

	public User getUser(String username);
	
	public boolean checkValidUsers(long userId1, long userId2);
	
	public User getUserById(int user_id);
}
