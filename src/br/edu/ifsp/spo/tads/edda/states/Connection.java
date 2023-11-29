package br.edu.ifsp.spo.tads.edda.states;

import br.edu.ifsp.spo.tads.edda.change.ChangeChance;

public class Connection {
	
	private String stateVertex;
	private ChangeChance chanceWeight;
	
	public Connection (StatePossibles state, ChangeChance chanceWeight) {
		this.stateVertex = state.getStateName();
		this.chanceWeight = chanceWeight;
	}

	public String getState() {
		return stateVertex;
	}

	public void setState(StatePossibles state) {
		this.stateVertex = state.getStateName();
	}

	public ChangeChance getChanceWeight() {
		return chanceWeight;
	}

	public void setChanceWeight(ChangeChance chanceWeight) {
		this.chanceWeight = chanceWeight;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{ Estado: ");
		builder.append(stateVertex);
		builder.append(" | Peso: ");
		builder.append(chanceWeight.getChance(0));
		builder.append("}");
		return builder.toString();
	}
	
}
