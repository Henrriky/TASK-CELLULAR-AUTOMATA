package br.edu.ifsp.spo.tads.edda.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import br.edu.ifsp.spo.tads.edda.change.ChangeChance;
import br.edu.ifsp.spo.tads.edda.change.ChangeChanceDynamic;
import br.edu.ifsp.spo.tads.edda.change.ChangeChanceFixed;

public class States {

	private Map<String, List<Connection>> graph;
	
	public States () {
		this.graph = new HashMap<>();
		this.addFixStates();
	}
	
	private void addFixStates () {
		this.addEdge(StatePossibles.SUSCETIVEL, StatePossibles.RECUPERADO, new ChangeChanceFixed(0.03));
		this.addEdge(StatePossibles.SUSCETIVEL, StatePossibles.INFECTADO, new ChangeChanceDynamic());
		this.addEdge(StatePossibles.INFECTADO, StatePossibles.RECUPERADO, new ChangeChanceFixed(0.6));
		this.addEdge(StatePossibles.INFECTADO, StatePossibles.SUSCETIVEL, new ChangeChanceFixed(0.01));
		this.addEdge(StatePossibles.RECUPERADO, StatePossibles.SUSCETIVEL, new ChangeChanceFixed(0.1));
	}
	

	private void addVertex(StatePossibles valor) {

		graph.put(valor.getStateName(), new ArrayList<Connection>());
	}
	

	private void addEdge (StatePossibles origin, StatePossibles destiny, ChangeChance chanceWeight) {
		if (!graph.containsKey(origin.getStateName())) {
			addVertex(origin);
		}
		if (!graph.containsKey(destiny.getStateName())) {
			addVertex(destiny);
		}
		Connection conn = new Connection(destiny, chanceWeight);
		graph.get(origin.getStateName()).add(conn);
	}
    
    public Map<String, List<Connection>> getVertexs () {
    	return graph;
    }
    


    public List<Connection> getEdgesFromVertex(StatePossibles vertex) {
        return graph.get(vertex.getStateName());
    }
    
    public List<Connection> getEdgesFromVertex(String vertex) {
        return graph.get(vertex);
    }
}
