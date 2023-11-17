package celularautomaton;

public class Cell {
	private int state; // 0 susceptible, 1 infected, 2 recovered

	Cell north;
	Cell south;
	Cell east;
	Cell west;
	Cell northeast;
	Cell northwest;
	Cell southeast;
	Cell southwest;
	
	public int infectedNeighbors() {
		int amount = 0;
			if(north.getState() == 1) {
				amount++;
			}
			if(south.getState() == 1) {
				amount++;
			}
			if(east.getState() == 1) {
				amount++;
			}
			if(west.getState() == 1) {
				amount++;
			}
			if(northeast.getState() == 1) {
				amount++;
			}
			if(northwest.getState() == 1) {
				amount++;
			}
			if(southeast.getState() == 1) {
				amount++;
			}
			if(southwest.getState() == 1) {
				amount++;
			}
		return amount;
	}

	public Cell getNorth() {
		return north;
	}

	public void setNorth(Cell north) {
		this.north = north;
	}

	public Cell getSouth() {
		return south;
	}

	public void setSouth(Cell south) {
		this.south = south;
	}

	public Cell getEast() {
		return east;
	}

	public void setEast(Cell east) {
		this.east = east;
	}

	public Cell getWest() {
		return west;
	}

	public void setWest(Cell west) {
		this.west = west;
	}

	public Cell getNortheast() {
		return northeast;
	}

	public void setNortheast(Cell northeast) {
		this.northeast = northeast;
	}

	public Cell getNorthwest() {
		return northwest;
	}

	public void setNorthwest(Cell northwest) {
		this.northwest = northwest;
	}

	public Cell getSoutheast() {
		return southeast;
	}

	public void setSoutheast(Cell southeast) {
		this.southeast = southeast;
	}

	public Cell getSouthwest() {
		return southwest;
	}

	public void setSouthwest(Cell southwest) {
		this.southwest = southwest;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void nei() {
		System.out.print(northwest.state + " ");
		System.out.print(north.state + " ");
		System.out.println(northeast.state + " ");
		System.out.print(west.state + " ");
		System.out.print("  ");
		System.out.println(east.state + " ");
		System.out.print(southwest.state + " ");
		System.out.print(south.state + " ");
		System.out.print(southeast.state + " ");
		System.out.println("\n");
	}

	public String toString() {
		return state + "";
	}
}

/*
 * três estados
 * 
 * 
 * se o numero sorteado for 0,5
 * 
 * 
 * S -> I chanceDeFicarDoente 1-e^(-k) 
 * probabilidadeDeFicarDoente (numero sorteado) 
 * probabilidadeDeFicarDoente < chanceDeFicarDoente 
 * 		fica doente
 * 
 * I -> R
 * 
 * 
 */
