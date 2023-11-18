package celularautomaton;

import java.util.Random;

public class World {

	private int size;
	private double suscetibleRate = 0.995;
	private double infectedRate = 1 - suscetibleRate;
	private int infe;
	private int susc;
	private int infectedCells;
	private int recoveredCells;
	private int suscetibleCells;
	
	Cell[][] grid;

	public int getSuscetibleCells() {
		return suscetibleCells;
	}
	
	public int getInfectedCells() {
		return infectedCells;
	}
	
	public int getRecoveredCells() {
		return recoveredCells;
	}
	
	Random randomizer = new Random();

	public World(int size) {
		this.size = size;
		this.infe = (int) (size * size * infectedRate);
		this.susc = size * size - infe;
		grid = new Cell[size][size];
		populateGrid();
//		System.out.println(toString());
//		for (int i = 0; i < generations; i++) {
//			nextGeneration();
//			statistics();
////			System.out.println(toString());
//
//		}
	}

	/*
	 * S -> R: 0.03 S -> I: dinamico
	 * 
	 * I -> R: 0.6 I -> S: 0.01
	 * 
	 * R -> S: 0.1
	 * 
	 */

	public void statistics() {
		System.out.println("Suscetible: " + suscetibleCells);
		System.out.println("Infected: " + infectedCells);
		System.out.println("Recovered: " + recoveredCells + "\n");
	}
	
	public void nextGeneration() {
		int[][] newStates = new int[size][size];
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				switch (grid[column][row].getState()) {
				case 0:
					if (randomizer.nextDouble() < 0.03) {
						newStates[column][row] = 2;
//						grid[column][row].setState(2);
						this.suscetibleCells--;
						this.recoveredCells++;
					} else {
						double infectionChance = 1 - Math.pow(2.71828, -(grid[column][row].infectedNeighbors()));
//						System.out.println(infectionChance);
						if (randomizer.nextDouble() < infectionChance) {
							newStates[column][row] = 1;
//							grid[column][row].setState(1);
							this.suscetibleCells--;
							this.infectedCells++;
						} else {
							newStates[column][row] = 0;
						}
					}
					break;
				case 1:
					if (randomizer.nextDouble() < 0.6) {
						newStates[column][row] = 2;
						this.infectedCells--;
						this.recoveredCells++;
					} else {
						if (randomizer.nextDouble() < 0.01) {
							newStates[column][row] = 0;
							this.infectedCells--;
							this.suscetibleCells++;
						} else {
							newStates[column][row] = 1;
						}
					}
					break;
				case 2:
					if (randomizer.nextDouble() < 0.1) {
						newStates[column][row] = 0;
						recoveredCells--;
						suscetibleCells++;
					} else {
						newStates[column][row] = 2;
					}
					break;
				}
			}
		}

		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				grid[column][row].setState(newStates[column][row]);
			}
		}
	}

	private void populateGrid() {
		if (infe < 1) {
			infe++;
			susc--;
		}
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (randomizer.nextDouble() < suscetibleRate && suscetibleCells < susc) {
					Cell newCell = new Cell();
					newCell.setState(0);
					grid[column][row] = newCell;
					suscetibleCells++;
				} else if (infectedCells < infe) {
					Cell newCell = new Cell();
					newCell.setState(1);
					grid[column][row] = newCell;
					infectedCells++;
				} else {
					Cell newCell = new Cell();
					newCell.setState(0);
					grid[column][row] = newCell;
					suscetibleCells++;
				}
			}
		}

		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				grid[column][row].setNorth(grid[column][calculateIndex(row - 1)]);
				grid[column][row].setSouth(grid[column][calculateIndex(row + 1)]);
				grid[column][row].setEast(grid[calculateIndex(column + 1)][row]);
				grid[column][row].setWest(grid[calculateIndex(column - 1)][row]);
				grid[column][row].setNortheast(grid[calculateIndex(column + 1)][calculateIndex(row - 1)]);
				grid[column][row].setNorthwest(grid[calculateIndex(column - 1)][calculateIndex(row - 1)]);
				grid[column][row].setSoutheast(grid[calculateIndex(column + 1)][calculateIndex(row + 1)]);
				grid[column][row].setSouthwest(grid[calculateIndex(column - 1)][calculateIndex(row + 1)]);
//				grid[column][row].nei();
			}
		}
	}

	public void a() {

		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				grid[column][row].nei();
			}
		}

		System.out.println(suscetibleCells);
		System.out.println(infectedCells);
	}

	private int calculateIndex(int index) {
		if (index < 0) {
			return size - 1;
		}
		if (index == size) {
			return 0;
		}
		return index;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				string.append(grid[column][row] + " ");
			}
			string.append("\n");
		}
		return string.toString();
	}

}
