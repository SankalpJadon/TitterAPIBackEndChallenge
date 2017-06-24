package challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import challenge.dao.MessageDAOImpl;
import challenge.pojos.Message;

@Component
public class MessageServiceImpl implements MessageService {

	@Autowired private MessageDAOImpl messageDao;
	
	@Override
	public List<Message> getMessagesByUserIdAndSearchKey(long userId, String searchKey) {
        return messageDao.getMessagesByUserFilteredByKey(userId, searchKey);
	}
	
	@Override
	public List<Message> getMessagesByUser(long userId) {
        return messageDao.getMessageByUser(userId);
	}

}
