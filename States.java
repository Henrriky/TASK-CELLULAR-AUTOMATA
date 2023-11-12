package WORK;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class States {

	Map<String, HashMap<String, Double>> graph;

	public States () {
		this.graph = new HashMap<>();
		this.addFixStates();
	}
	
	private void addFixStates () {
		this.addVertex("S");
		this.addVertex("I");
		this.addVertex("R");
		
		this.addEdge("S", "I", null);
		this.addEdge("I", "R", 0.6);
		this.addEdge("R", "I", 0.3);
	}
	

	private void addVertex(String valor) {

		graph.put(valor, new HashMap<>());
	}
	

	private void addEdge (String origin, String destiny, Double weight) {
		if (!graph.containsKey(origin)) {
			addVertex(origin);
		}
		if (!graph.containsKey(destiny)) {
			addVertex(destiny);
		}
		graph.get(origin).put(destiny, weight);
	}
	
	/*
    public Set<String> getVertices() {
        return graph.keySet();
    }
    */
    
    public Map<String, HashMap<String, Double>> getVertexs () {
    	return graph;
    }
    


    public HashMap<String, Double> getEdgesFromVertex(String vertex) {
        return graph.get(vertex);
    }
}
