package br.edu.ifsp.spo.tads.edda.environment;

import br.edu.ifsp.spo.tads.edda.states.StatePossibles;

class Person {
	
	private String state;
	
	Person () {
		
	}
	
	Person (StatePossibles stateEnum) {
		this.state = stateEnum.getStateName();
	}
	
	Person (String stateEnum) {
		this.state = stateEnum;
	}
	
	public String getState() {
		return state;
	} 
	
	public void setState(StatePossibles stateEnum) {	
		this.state = stateEnum.getStateName();
	}
	
	@Override
	public String toString() {
		return this.state;
	}
}