package challenge.dao;

import java.util.List;

import challenge.pojos.Message;

public interface MessageDAO extends SimpleDAO{
	
	public List<Message> getMessagesByUserFilteredByKey(long userId, String search);
	
	public List<Message> getMessageByUser(long userId);

}
