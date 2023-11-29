package br.edu.ifsp.spo.tads.edda.change;

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
