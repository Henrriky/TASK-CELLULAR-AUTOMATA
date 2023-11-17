package WORK;

public class Connection {
	
	private String state;
	private ChangeChance chanceWeight;
	
	public Connection (String state, ChangeChance chanceWeight) {
		this.state = state;
		this.chanceWeight = chanceWeight;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ChangeChance getChanceWeight() {
		return chanceWeight;
	}

	public void setChanceWeight(ChangeChance chanceWeight) {
		this.chanceWeight = chanceWeight;
	}
	
	
}
