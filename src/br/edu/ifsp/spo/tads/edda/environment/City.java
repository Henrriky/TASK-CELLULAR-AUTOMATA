package br.edu.ifsp.spo.tads.edda.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import br.edu.ifsp.spo.tads.edda.states.Connection;
import br.edu.ifsp.spo.tads.edda.states.StatePossibles;
import br.edu.ifsp.spo.tads.edda.states.States;

public class City {

	private ArrayList<ArrayList<Person>> matrix;
	private int size;
	private States graph;
	private List<HashMap<String, Integer>> generationsStatistics;

	public City(int size) {
		this.size = size;
		this.matrix = new ArrayList<ArrayList<Person>>();
		this.graph = new States();
		this.createPopulation();
	}

	private void createPopulation() {
		double susceptibleRate = 0.95;
		double infectedRate = 1 - susceptibleRate;
		int infectedAmount = (int) (size * size * infectedRate);
		if(infectedAmount == 0)
			infectedAmount++;
		int susceptibleAmount = (int) (size * size * susceptibleRate);
		if(susceptibleAmount + infectedAmount > size * size) 
			susceptibleAmount--;
		int currentSusceptibleAmount = 0;
		int currentInfectedAmount = 0;
		Random randomNumber = new Random();
		
		for (int lin = 0; lin < size; lin++) {
			matrix.add(new ArrayList<Person>());
			for (int col = 0; col < size; col++) {
				Person cell = new Person();
				if (randomNumber.nextDouble() < susceptibleRate && currentSusceptibleAmount < susceptibleAmount) { 
					cell.setState(StatePossibles.SUSCETIVEL);
					matrix.get(lin).add(cell);
					currentSusceptibleAmount++;
				} else if(currentInfectedAmount < infectedAmount){
					cell.setState(StatePossibles.INFECTADO);
					matrix.get(lin).add(cell);
					currentInfectedAmount++;
				} else {
					cell.setState(StatePossibles.SUSCETIVEL);
					matrix.get(lin).add(cell);
					currentSusceptibleAmount++;
				}
			}
		}
		HashMap<String, Integer> stats = new HashMap<String,Integer>();
		stats.put(StatePossibles.SUSCETIVEL.getStateName(), currentSusceptibleAmount);
		stats.put(StatePossibles.INFECTADO.getStateName(), currentInfectedAmount);
		stats.put(StatePossibles.RECUPERADO.getStateName(), 0);
		generationsStatistics = new ArrayList<HashMap<String, Integer>>();
		generationsStatistics.add(0, stats);
	}

	public void startGenerations(int numberOfGenerations) {
		for (int i = 1; i <= numberOfGenerations; i++) {
			applyNextGenerations(i);
		}
	}

	private void applyNextGenerations(Integer currentGenerationCounter) {
		ArrayList<ArrayList<Person>> nextGenerationMatrix = new ArrayList<ArrayList<Person>>();
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		stats.put(StatePossibles.SUSCETIVEL.getStateName(), 0);
		stats.put(StatePossibles.INFECTADO.getStateName(), 0);
		stats.put(StatePossibles.RECUPERADO.getStateName(), 0);
		generationsStatistics.add(currentGenerationCounter, stats);
		HashMap<String, Integer> generationStatistics = generationsStatistics.get(currentGenerationCounter);
		for (int lin = 0; lin < size; lin++) {
			nextGenerationMatrix.add(new ArrayList<Person>());
			for (int col = 0; col < size; col++) {
				int numberOfNeighboursInfected = calculateNeighboursInfected(lin, col);
				Person currentPerson = matrix.get(lin).get(col);
				Person personWithStateModified = applyRule(numberOfNeighboursInfected, currentPerson,
						generationStatistics);
				nextGenerationMatrix.get(lin).add(personWithStateModified);
			}
		}
		matrix = nextGenerationMatrix;
		nextGenerationMatrix = null;
	}

	private Person applyRule(int numberOfNeighboursInfected, Person currentPerson,
			HashMap<String, Integer> currentGenerationStatistics) {
		String currentCellState = currentPerson.getState();
		Random rand = new Random();
		boolean willSwapState;
		Integer quantityPeopleInCurrentState;
		List<Connection> edgesFromVertex = graph.getEdgesFromVertex(currentCellState);
		for (Connection edge : edgesFromVertex) {
			double chanceOfChange = edge.getChanceWeight().getChance(numberOfNeighboursInfected);
			String stateOfCurrentEdge = edge.getState();
			double randomNum = rand.nextDouble();
			willSwapState = (randomNum <= chanceOfChange);
			if (willSwapState) {
				quantityPeopleInCurrentState = currentGenerationStatistics.get(stateOfCurrentEdge);
				currentGenerationStatistics.put(stateOfCurrentEdge, ++quantityPeopleInCurrentState);
				return new Person(stateOfCurrentEdge);
			}
		}
		quantityPeopleInCurrentState = currentGenerationStatistics.get(currentCellState);
		currentGenerationStatistics.put(currentCellState, ++quantityPeopleInCurrentState);
		return currentPerson;
	}

	private int calculateNeighboursInfected(int lin, int col) {
		int numberOfNeighboursInfected = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int indexLin = this.calculateIndexOfNeighbour(lin + i);
				int indexCol = this.calculateIndexOfNeighbour(col + j);
				boolean isCurrentCell = (indexLin == lin && indexCol == col);
				if (!isCurrentCell) {
					Person currentNeighbour = matrix.get(indexLin).get(indexCol);
					boolean isInfected = currentNeighbour.getState().equals(StatePossibles.INFECTADO.getStateName());
					if (isInfected) {
						++numberOfNeighboursInfected;
					}
				}
			}
		}
		return numberOfNeighboursInfected;
	}

	private int calculateIndexOfNeighbour(int index) {
		if (index < 0) {
			return size - 1;
		} else if (index == size) {
			return 0;
		}
		return index;
	}

	public String getStatisticsOfGenerationsToString() {
		StringBuilder builder = new StringBuilder();
		int index = 0;
		for (HashMap<String, Integer> currentInformationsOfGeneration : generationsStatistics) {
			HashMap<String, Integer> statistics = currentInformationsOfGeneration;
			builder.append("Geracao ");
			builder.append(index);
			builder.append(" => ");
			builder.append("Suscetiveis: ");
			builder.append(statistics.get(StatePossibles.SUSCETIVEL.getStateName()));
			builder.append(" | Infectados: ");
			builder.append(statistics.get(StatePossibles.INFECTADO.getStateName()));
			builder.append(" | Recuperados: ");
			builder.append(statistics.get(StatePossibles.RECUPERADO.getStateName()));
			builder.append("\n");
			index++;
		}
		return builder.toString();
	}

	public List<HashMap<String, Integer>> getStatisticsOfGenerations() {
		return this.generationsStatistics;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int lin = 0; lin < size; lin++) {
			builder.append(this.matrix.get(lin));
			builder.append("\n");
		}
		return builder.toString();
	}
}
