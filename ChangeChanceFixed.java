package WORK;

public class ChangeChanceFixed implements ChangeChance {
	
	double chance;
	
	public ChangeChanceFixed (double weight) {
		this.chance = weight;
	}
	
	@Override
	public double getChance(Integer numberOfNeighbours) {
		return this.chance;
	}
}
