package challenge.service;

import java.util.List;

import challenge.pojos.Message;

public interface MessageService {
	public List<Message> getMessagesByUserIdAndSearchKey(long userId, String searchKey);
	public List<Message> getMessagesByUser(long userId);

}
