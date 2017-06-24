package challenge.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import challenge.mappers.MessageMapper;
import challenge.pojos.Message;

@Component
public class MessageDAOImpl implements MessageDAO {
	
    private final String GET_MSGS = "SELECT * FROM messages " +
            "WHERE (person_id=:user_id OR person_id IN (" +
            "SELECT person_id FROM followers WHERE follower_person_id=:user_id))";
    
	private final String GET_FILTERED_MSG = GET_MSGS + " AND content like :search_key";
		
		@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	// Set the data source for the namedParameterJdbcTemplate.
	@Override
	public void setDataSource(DataSource dataSource) {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// DAO method to get messages as filtered by UserID and search key sent in the request
	@Override
	public List<Message> getMessagesByUserFilteredByKey(long userId, String searchKey) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("user_id", userId).addValue("search_key", "%"+searchKey+"%");
        @SuppressWarnings("unchecked")
		List<Message> message = namedParameterJdbcTemplate.query(GET_FILTERED_MSG, namedParameters, new MessageMapper());
		return message;
	}
 
	// DAO method to get the list of all the messages by userID
	@Override
	public List<Message> getMessageByUser(long userId) {
		SqlParameterSource namedParameters = new MapSqlParameterSource("user_id", userId);
		@SuppressWarnings("unchecked")
		List<Message> message = namedParameterJdbcTemplate.query(GET_MSGS, namedParameters, new MessageMapper());
		return message;
	}

}
