package WORK;

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
	
	
	/*
	private HashMap<String, Double> state;
	
	public HashMap<String, Double> getState() {
		return state;
	} 
	
	public void setState(HashMap<String, Double> state) {	
		this.state = state;
	}
	*/
}