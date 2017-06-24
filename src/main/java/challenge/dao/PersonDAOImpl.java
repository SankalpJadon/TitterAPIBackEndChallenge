package challenge.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import challenge.mappers.PeopleMapper;
import challenge.pojos.People;

@Component
public class PersonDAOImpl implements PersonDAO {
	
	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public void setDataSource(DataSource dataSource) {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int getPersonId(String handle){
		People person;
		String sql= "select * from people where handle=:handle";
		SqlParameterSource namedParameters = new MapSqlParameterSource("handle",handle);
		try{
			person = (People) namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new PeopleMapper());
			if(person==null) return -1;
		}catch(Exception e){
			return -1;
		}
		return person.getId();
	}
}
