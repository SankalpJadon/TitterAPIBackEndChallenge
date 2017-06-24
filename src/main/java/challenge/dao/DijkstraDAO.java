package challenge.dao;

import java.util.List;

import challenge.dijkstra.Edge;
import challenge.dijkstra.Graph;
import challenge.dijkstra.Vertex;
import challenge.pojos.Followers;
import challenge.pojos.User;

public interface DijkstraDAO {
	public List<Vertex> setVertexes(List<User> listOfAllUsers);
	public List<Edge> setEdges(List<User> listOfAllUsers,List<Followers> listOfAllFollowers);
	public Vertex getVertex(String name);
	public Graph setGraph(List<Vertex> listOfVertexes2, List<Edge> listOfEdges);
}
