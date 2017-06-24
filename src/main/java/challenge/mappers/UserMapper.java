package challenge.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import challenge.pojos.User;

@SuppressWarnings("rawtypes")
public class UserMapper implements RowMapper{
	 public User mapRow(ResultSet rs, int rowNum) throws SQLException {  
		 User user = new User();  
		 user.setPassword(rs.getString("password")); 
		 user.setUsername(rs.getString("username"));
		 user.setEnabled(rs.getInt("enabled"));
		 user.setPerson_id(rs.getLong("person_id"));
		 return user;  
	 }
}
