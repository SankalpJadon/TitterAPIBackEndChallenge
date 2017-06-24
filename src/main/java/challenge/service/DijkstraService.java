package challenge.service;

public interface DijkstraService {
	
	public void populateGraph();
	
	public int findHops(String userName1, String userName2);
	
}
