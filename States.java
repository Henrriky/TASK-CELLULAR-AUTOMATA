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
	
	/*
	 * Adicionar mais uma aresta (edge), que é entre R e S, após uma morte
	 * 
	 * */
	private void addFixStates () {
		this.addVertex(StatePossibles.SUSCETIVEL.getStateName());
		this.addVertex(StatePossibles.INFECTADO.getStateName());
		this.addVertex(StatePossibles.REMOVIDO.getStateName());
		
		this.addEdge(StatePossibles.SUSCETIVEL.getStateName(), StatePossibles.INFECTADO.getStateName(), null);
		this.addEdge(StatePossibles.INFECTADO.getStateName(), StatePossibles.REMOVIDO.getStateName(), 0.3);
		this.addEdge(StatePossibles.REMOVIDO.getStateName(), StatePossibles.INFECTADO.getStateName(), 0.6);
		this.addEdge(StatePossibles.REMOVIDO.getStateName(), StatePossibles.SUSCETIVEL.getStateName(), 0.1);
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
