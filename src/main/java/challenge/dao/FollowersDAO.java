package challenge.dao;

import java.util.List;

import challenge.pojos.Followers;
import challenge.pojos.People;

public interface FollowersDAO extends SimpleDAO{
	
	public Followers getFollowerByKey(long person_id, long follower_person_id);
	
	public boolean startFollowing(long person_id, long follower_person_id);
	
	public List<People> findFollowers(int person_id);
	
	public List<People> findFollowing(int person_id);
	
	public boolean unfollow(long person_id, long unfollow_person_id);
	
	public List<Followers> getAllFollowers();
}
