package challenge.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import challenge.pojos.Followers;

@SuppressWarnings("rawtypes")
public class FollowersMapper implements RowMapper{
		 public Followers mapRow(ResultSet rs, int rowNum) throws SQLException {  
			 Followers follower = new Followers();  
			 follower.setFollower_person_id(rs.getInt("follower_person_id"));
			 follower.setId(rs.getInt("id")); 
			 follower.setPerson_id(rs.getInt("person_id"));
			 return follower;  
		 }
}
