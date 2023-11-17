package WORK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class States {

	Map<String, List<Connection>> graph;
	
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
		this.addVertex(StatePossibles.RECUPERADO.getStateName());
		
		this.addEdge(StatePossibles.SUSCETIVEL.getStateName(), StatePossibles.RECUPERADO.getStateName(), new ChangeChanceFixed(0.03));
		this.addEdge(StatePossibles.SUSCETIVEL.getStateName(), StatePossibles.INFECTADO.getStateName(), new ChangeChanceDynamic());
		this.addEdge(StatePossibles.INFECTADO.getStateName(), StatePossibles.RECUPERADO.getStateName(), new ChangeChanceFixed(0.6));
		this.addEdge(StatePossibles.INFECTADO.getStateName(), StatePossibles.SUSCETIVEL.getStateName(), new ChangeChanceFixed(0.01));
		this.addEdge(StatePossibles.RECUPERADO.getStateName(), StatePossibles.SUSCETIVEL.getStateName(), new ChangeChanceFixed(0.1));
	}
	

	private void addVertex(String valor) {

		graph.put(valor, new ArrayList<Connection>());
	}
	

	private void addEdge (String origin, String destiny, ChangeChance chanceWeight) {
		if (!graph.containsKey(origin)) {
			addVertex(origin);
		}
		if (!graph.containsKey(destiny)) {
			addVertex(destiny);
		}
		Connection conn = new Connection(destiny, chanceWeight);
		graph.get(origin).add(conn);
	}
	
	/*
    public Set<String> getVertices() {
        return graph.keySet();
    }
    */
    
    public Map<String, List<Connection>> getVertexs () {
    	return graph;
    }
    


    public List<Connection> getEdgesFromVertex(String vertex) {
        return graph.get(vertex);
    }
}
