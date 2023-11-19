package WORK;

public class ChangeChanceDynamic implements ChangeChance {

	@Override
	public double getChance(Integer numberOfNeighboursInfected) {
		double result = (1 - Math.pow(Math.E, -numberOfNeighboursInfected));
		return result;
	}
}
