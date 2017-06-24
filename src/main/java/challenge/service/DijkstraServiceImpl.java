package challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import challenge.dao.DijkstraDAOImpl;
import challenge.dao.FollowersDAO;
import challenge.dao.UserDAO;
import challenge.dijkstra.DijkstraToFindHops;
import challenge.dijkstra.Edge;
import challenge.dijkstra.Graph;
import challenge.dijkstra.Vertex;
import challenge.pojos.Followers;
import challenge.pojos.User;

@Component
public class DijkstraServiceImpl implements DijkstraService{

	@Autowired DijkstraDAOImpl dijkstraDao;
	@Autowired UserDAO userDao;
	@Autowired FollowersDAO followersDao;
	@Autowired DijkstraToFindHops dijkstra;
	
	Graph graph;
	List<Vertex> listOfVertexes;
	
	@Override
	public void populateGraph(){
		List<User> listOfAllUsers= (List<User>) userDao.getAllUsers();
		listOfVertexes= dijkstraDao.setVertexes(listOfAllUsers);
		List<Followers> listOfAllFollowers = followersDao.getAllFollowers();
		List<Edge> listOfEdges = dijkstraDao.setEdges(listOfAllUsers,listOfAllFollowers);
		graph = dijkstraDao.setGraph(listOfVertexes,listOfEdges);
	}
	
	@Override
	public int findHops(String userName1, String userName2) {
		User user1= (User) userDao.getUser(userName1);
		User user2= (User) userDao.getUser(userName2);
		int hops = dijkstra.getHops(graph,listOfVertexes,user1,user2);
		return hops;
	}
	
}
