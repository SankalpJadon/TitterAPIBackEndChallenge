package challenge.service;

import java.util.List;

import challenge.pojos.People;

public interface FollowerService {
	public boolean startFollowing(long person_id, long unfollow_person_id);
	public boolean unFollow(long person_id, long unfollow_person_id);
	public List<People> findFollowing(int personId);
	public List<People> findFollowers(int personId);
}
