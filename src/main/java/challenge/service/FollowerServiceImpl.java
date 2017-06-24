package challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import challenge.dao.FollowersDAOImpl;
import challenge.pojos.Followers;
import challenge.pojos.People;

@Component
public class FollowerServiceImpl implements FollowerService{
	
	@Autowired private FollowersDAOImpl followersDao;
		
	@Override
	public boolean startFollowing(long person_id, long follower_person_id){
	        Followers f = followersDao.getFollowerByKey(person_id, follower_person_id);
	        if (f == null){
	            return followersDao.startFollowing(person_id, follower_person_id);
	        }
	        else
	            return false;
	    }

	@Override
	public boolean unFollow(long person_id, long unfollow_person_id) {
		Followers f = followersDao.getFollowerByKey(person_id, unfollow_person_id);
		if(f!=null){
			return followersDao.unfollow(person_id,unfollow_person_id);
		}
		else
			return false;
	}
	
	@Override
	public List<People> findFollowing(int personId){
		List<People> list= followersDao.findFollowing(personId);
		if(list!=null){
			return list;
		}
		else
			return null;
	}
	
	@Override
	public List<People> findFollowers(int personId){
		List<People> list= followersDao.findFollowers(personId);
		if(list!=null){
			return list;
		}
		else
			return null;
	}


}
