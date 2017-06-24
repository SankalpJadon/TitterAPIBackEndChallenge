package challenge.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import challenge.dijkstra.Edge;
import challenge.dijkstra.Graph;
import challenge.dijkstra.Vertex;
import challenge.pojos.Followers;
import challenge.pojos.User;

@Component
public class DijkstraDAOImpl implements DijkstraDAO {
	
	@Autowired UserDAO userDao;
	
	List<Vertex> listOfVertexes;
	
	
	@Override
	public List<Vertex> setVertexes(List<User> listOfAllUsers){
		listOfVertexes = new ArrayList<Vertex>();
		Graph graph= new Graph();
		for(User user:listOfAllUsers){
			Vertex v= new Vertex(String.valueOf(user.getPerson_id()),user.getUsername());
			listOfVertexes.add(v);
		}
		graph.setVertexes(listOfVertexes);
		return listOfVertexes;
	}
	
	
	@Override
	public List<Edge> setEdges(List<User> listOfAllUsers,List<Followers> listOfAllFollowers){
		List<Edge> listOfEdges= new ArrayList<Edge>();
		int i=1;
		for(Followers follower: listOfAllFollowers){
			int user_id= follower.getPerson_id();
			int follower_id= follower.getFollower_person_id();
			User user1 = userDao.getUserById(user_id);
			User user2 = userDao.getUserById(follower_id);
			Vertex vertex1= getVertex(user1.getUsername());
			Vertex vertex2 = getVertex(user2.getUsername());
			Edge edge= new Edge(String.valueOf(i),vertex2,vertex1,1);
			listOfEdges.add(edge);
			i++;
		}
		if(listOfEdges.isEmpty()||listOfEdges==null) return null;
		return listOfEdges;
	}
	
	
	@Override
	public Vertex getVertex(String name){
		for(Vertex v: listOfVertexes){
			if(v.getName()==name) return v;
		}
		return null;
	}


	@Override
	public Graph setGraph(List<Vertex> listOfVertexes2, List<Edge> listOfEdges) {
		Graph graph = new Graph(listOfVertexes2,listOfEdges);
		return graph;
	}
    
}
