package challenge.dijkstra;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Graph {
    private List<Vertex> vertexes;
    private List<Edge> edges;

    public Graph(){
    	
    }
    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }
    
    public void setVertexes(List<Vertex> vertexes){
    	this.vertexes=vertexes;
    }
    
    public List<Edge> getEdges() {
        return edges;
    }
    
    public void setEdge(List<Edge> edges){
    	this.edges=edges;
    }

}