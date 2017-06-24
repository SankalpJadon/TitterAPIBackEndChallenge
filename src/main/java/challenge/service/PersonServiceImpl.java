package challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import challenge.dao.PersonDAO;

@Component
public class PersonServiceImpl implements PersonService{
	
	@Autowired private PersonDAO personDao;

	@Override
	public int getPersonId(String handle){
		return personDao.getPersonId(handle);
	}
}
