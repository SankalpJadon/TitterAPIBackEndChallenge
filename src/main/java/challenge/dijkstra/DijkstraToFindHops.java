package challenge.dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import challenge.pojos.User;

@Component
public class DijkstraToFindHops {
	List<Vertex> listOfVertexes;
	
	List<Vertex> nodes;
    List<Edge> edges;
    Set<Vertex> settledNodes;
    Set<Vertex> unSettledNodes;
    Map<Vertex, Vertex> predecessors;
    Map<Vertex, Integer> distance;
    
    public int getHops(Graph graph,List<Vertex> listOfVertexes, User user1, User user2) {
	     // create a copy of the array so that we can operate on this array
	     nodes = new ArrayList<Vertex>(graph.getVertexes());
	     edges = new ArrayList<Edge>(graph.getEdges());
	     
	     //Source vertex
	     Vertex sourceVertex= new Vertex("0","0");
	     //Destination vertex
	     Vertex destinationVertex = new Vertex("1","1");
	     
	     for(Vertex v:listOfVertexes){
	    	 if(v.getName()==user1.getUsername()) sourceVertex=v;
	    	 if(v.getName()==user2.getUsername()) destinationVertex=v;
	     }
	     
	     dijkstrasToFindHops(sourceVertex);
	     
		return distance.get(destinationVertex);
	}
	
    //Main entry point for dijkstras, specify the source vertex in the function parameter
	public void dijkstrasToFindHops(Vertex source) {
       settledNodes = new HashSet<Vertex>();
       unSettledNodes = new HashSet<Vertex>();
       distance = new HashMap<Vertex, Integer>();
       predecessors = new HashMap<Vertex, Vertex>();
       distance.put(source, 0);
       unSettledNodes.add(source);	//To start off, enter the source vertex in the unsettled list, and keep adding when nodes are reached
       while (unSettledNodes.size() > 0) {	// Run algorithm until unsettled list is empty
           Vertex node = getMinimum(unSettledNodes); // get minimum distance of the node
           settledNodes.add(node);	//When minimum distance is found, add the node to the settled node
           unSettledNodes.remove(node);
           findMinimalDistances(node);
       }
   }
	
	//Function to find the minimal distance of the vertex
	private void findMinimalDistances(Vertex node) {
       List<Vertex> adjacentNodes = getNeighbors(node);
       for (Vertex target : adjacentNodes) {
           if (getShortestDistance(target) > getShortestDistance(node)
                   + 1) {
               distance.put(target, getShortestDistance(node)
                       +1);
               predecessors.put(target, node);
               unSettledNodes.add(target);
           }
       }

   }
	

	// helper function to add neighbors of vertex node
   private List<Vertex> getNeighbors(Vertex node) {
       List<Vertex> neighbors = new ArrayList<Vertex>();
       for (Edge edge : edges) {
           if (edge.getSource().equals(node)
                   && !isSettled(edge.getDestination())) {
               neighbors.add(edge.getDestination());
           }
       }
       return neighbors;
   }
   
   // helper function to get the minimum distanced vertex
   private Vertex getMinimum(Set<Vertex> vertexes) {
       Vertex minimum = null;
       for (Vertex vertex : vertexes) {
           minimum=vertex;
           break;
       }
       return minimum;
   }

   //helper function to check if the vertex is settled
   private boolean isSettled(Vertex vertex) {
       return settledNodes.contains(vertex);
   }

   //helper function to get the shortest distance of the each destination vertex
   private int getShortestDistance(Vertex destination) {
       Integer d = distance.get(destination);
       if (d == null) {
           return Integer.MAX_VALUE;
       } else {
           return d;
       }
   }
    
}
