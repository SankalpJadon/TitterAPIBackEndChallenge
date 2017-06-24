package challenge.dao;

import java.util.List;
import java.util.Map;

import challenge.pojos.User;

public interface UserDAO extends SimpleDAO{
	public int getPersonId(String handle);
	public Object getUser(String userName);
	public boolean checkValidUsers(long userId1, long userId2);
	public List<Map<String,Object>> findAllPeople();
	public Object getAllUsers();
	public User getUserById(int user_id);
}
