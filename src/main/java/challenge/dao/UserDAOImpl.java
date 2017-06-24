package challenge.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import challenge.mappers.PeopleMapper;
import challenge.mappers.UserMapper;
import challenge.pojos.People;
import challenge.pojos.User;

@Component

public class UserDAOImpl implements UserDAO {

	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private String GET_ALL_USERS = "select * from user";
	// Set the data source for the namedParameterJdbcTemplate.
	@Override
	public void setDataSource(DataSource dataSource) {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getAllUsers(){
        List<User> usersList = namedParameterJdbcTemplate.query(GET_ALL_USERS, new UserMapper());
		return usersList;
	}
	
	// Method to get the user by passing the username.
	@SuppressWarnings("unchecked")
	@Override
	public Object getUser(String userName){
		User user;
		String sql= "select * from user where username = :userName";
		try{
			SqlParameterSource namedParameters = new MapSqlParameterSource("userName", userName);
			user = (User) namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new UserMapper());
		}catch(Exception e){
			return null;
		}
		return user;
	}
	
	// Method to check if the two users are valid or not. It is used to check if the users are distinct (by 
	// returning the count as 2. Otherwise an exception is thrown in the controller.
	@Override
	public boolean checkValidUsers(long userId1, long userId2){
		SqlParameterSource namedParameters = new MapSqlParameterSource("userId1", userId1).
                addValue("userId2", userId2);
		String sql= "select count(*) from user where person_id = :userId1 OR person_id =:userId2";
        int countUsers = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
        return countUsers == 2;
	}
	
	// Test method to check if all people are fetched from the database.
	@Override
	public List<Map<String,Object>> findAllPeople() {
		Map<String, String> paramMap = new HashMap<String, String>();
	    String sql = "select * from People";
	    List<Map<String,Object>> list= this.namedParameterJdbcTemplate.queryForList(sql, paramMap);
	    return list;
	}
	
	// Method to get the person ID by using the handle name.
	@Override
	public int getPersonId(String handle){
		String sql= "select * from people where handle=:handle";
		SqlParameterSource namedParameters = new MapSqlParameterSource("handle",handle);
		@SuppressWarnings("unchecked")
		People person = (People) namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new PeopleMapper());
		return person.getId();
	}
	
	@Override
	public User getUserById(int user_id){
		String sql= "select * from user where person_id= :user_id";
		SqlParameterSource namedParameters = new MapSqlParameterSource("user_id",user_id);
		@SuppressWarnings("unchecked")
		User user = (User) namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new UserMapper());
		return user;
	}
	

}