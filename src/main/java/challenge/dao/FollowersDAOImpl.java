package challenge.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import challenge.mappers.FollowersMapper;
import challenge.mappers.PeopleMapper;
import challenge.pojos.Followers;
import challenge.pojos.People;

@Component
public class FollowersDAOImpl implements FollowersDAO{
	
	private String GET_ROW_BY_ID = "select * from followers where person_id= :person_id AND " +
            "follower_person_id= :follower_person_id" ;
	private String GET_ALL_FOLLOWERS= "select * from followers";
    private String INSERT_FOLLOWER = "insert into followers(person_id,follower_person_id) values (:person_id, :follower_person_id)";
	private String GET_FOLLOWERS_BY_PERSON_ID= "select * from Followers where person_id in (:ids)";
	private String GET_PEOPLE_BY_ID= "select * from People where id in (:id)";
	private String GET_FOLLOWERS_BY_FOLLOWER_ID= "select * from Followers where follower_person_id in (:ids)";
	private String DELETE_FOLLOWER = "delete from followers where person_id in (:person_id) and follower_person_id in (:follower_person_id)";

	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	// Set data source for namedParameterJdbcTemplate object.
	@Override
	public void setDataSource(DataSource dataSource) {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	// DAO method to get the follower object for a user which is checked if the user is already following the
	// person or not. If the user is already following, return null, otherwise return the Follower object.
	@Override
	public Followers getFollowerByKey(long person_id, long follower_person_id){
		List<Followers> followers;
		SqlParameterSource namedParameters = new MapSqlParameterSource("person_id", person_id).
		   addValue("follower_person_id", follower_person_id);
		try{
			followers =  namedParameterJdbcTemplate.query(GET_ROW_BY_ID, namedParameters,new FollowersMapper());
		} catch(Exception e){
			return null;
		}
		if(followers.isEmpty()) return null;
		return followers.get(0);
	}

	// DAO method to start following a person. This method adds a record in the followers table. 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean startFollowing(long person_id, long follower_person_id) {
		Map parameters = new HashMap();
	    parameters.put("person_id", person_id);
	    parameters.put("follower_person_id", follower_person_id);
	    try{
	    	namedParameterJdbcTemplate.update(INSERT_FOLLOWER, parameters);
	    }catch(Exception e){
	    	return false;
	    }
	    return true;
	}
	
	// DAO method to find the list of all the people who are following the user.
	@Override
	public List<People> findFollowers(int person_id) {
		Set<Integer> ids= new HashSet<Integer>();
		ids.add(person_id);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		@SuppressWarnings("unchecked")
		List<Followers> following = namedParameterJdbcTemplate.query(GET_FOLLOWERS_BY_PERSON_ID, parameters, new FollowersMapper());	
		List<People> people= new ArrayList<People>();
		for(Followers follower: following){
			int id= follower.getFollower_person_id();
			SqlParameterSource namedParameter = new MapSqlParameterSource("id",id);
			@SuppressWarnings("unchecked")
			People person = (People) namedParameterJdbcTemplate.queryForObject(GET_PEOPLE_BY_ID, namedParameter, new PeopleMapper());
			people.add(person);
		}
		return people;
	}
	
	// DAO method to find the list of all the people the user is following.
	@Override
	public List<People> findFollowing(int person_id) {
		Set<Integer> ids= new HashSet<Integer>();
		List<People> people;
		ids.add(person_id);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		try{
			List<Followers> following = namedParameterJdbcTemplate.query(GET_FOLLOWERS_BY_FOLLOWER_ID, parameters, new FollowersMapper());
			people= new ArrayList<People>();
			for(Followers follower: following){
				int id= follower.getPerson_id();
				SqlParameterSource namedParameter = new MapSqlParameterSource("id",id);
				@SuppressWarnings("unchecked")
				People person = (People) namedParameterJdbcTemplate.queryForObject(GET_PEOPLE_BY_ID, namedParameter, new PeopleMapper());
				people.add(person);
			}
		}catch(Exception e){
			return null;
		}
		return people;	
	}

	// DAO method to unfollow person by specifying person_id and follower_id 
	@SuppressWarnings("unchecked")
	@Override
	public boolean unfollow(long person_id, long follower_person_id) {
		 @SuppressWarnings("rawtypes")
		Map parameters = new HashMap();
		 parameters.put("person_id", person_id);
		 parameters.put("follower_person_id", follower_person_id);
		 try{
			 namedParameterJdbcTemplate.update(DELETE_FOLLOWER, parameters);
		 }catch(Exception e){
			 return false;
		 }
		 return true;
	}

	// DAO utility method to get list of all the followers
	@Override
	public List<Followers> getAllFollowers() {
		List<Followers> followersList;
	    try{
	    	followersList = namedParameterJdbcTemplate.query(GET_ALL_FOLLOWERS, new FollowersMapper());
	    }catch(Exception e){
	    	return null;
	    }
	    return followersList;
	}
		
}
