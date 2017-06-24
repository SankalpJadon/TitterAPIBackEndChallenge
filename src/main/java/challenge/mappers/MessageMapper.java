package challenge.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import challenge.pojos.Message;

@SuppressWarnings("rawtypes")
public class MessageMapper implements RowMapper{
	 public Message mapRow(ResultSet rs, int rowNum) throws SQLException {  
		 Message message = new Message();  
		 message.setId(rs.getInt("id"));
		 message.setContent(rs.getString("content")); 
		 message.setPerson_id(rs.getInt("person_id"));
		 return message;  
	 }
}
