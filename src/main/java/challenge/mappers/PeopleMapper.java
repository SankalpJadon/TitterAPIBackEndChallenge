package challenge.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import challenge.pojos.People;

@SuppressWarnings("rawtypes")
public class PeopleMapper implements RowMapper{
	 public People mapRow(ResultSet rs, int rowNum) throws SQLException {  
		 People people = new People();  
		 people.setHandle(rs.getString("handle"));
		 people.setId(rs.getInt("id")); 
		 people.setName(rs.getString("name"));
		 return people;  
	 }
}
