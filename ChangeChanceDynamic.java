package WORK;

public class ChangeChanceDynamic implements ChangeChance {

	@Override
	public double getChance(Integer numberOfNeighboursInfected) {
//		double result = (1 - Math.pow(Math.E, -numberOfNeighboursInfected));
		double result = 1 - Math.pow(2.71828, -numberOfNeighboursInfected);
		return result;
	}
}
