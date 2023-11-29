package br.edu.ifsp.spo.tads.edda.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import br.edu.ifsp.spo.tads.edda.states.Connection;
import br.edu.ifsp.spo.tads.edda.states.StatePossibles;
import br.edu.ifsp.spo.tads.edda.states.States;

public class City {

	private ArrayList<ArrayList<Person>> matriz;
	private int size;
	private States graph;
	private List<HashMap<String, Integer>> generationsStatistics;

	public City(int size) {
		this.size = size;
		this.matriz = new ArrayList<ArrayList<Person>>();
		this.graph = new States();
		this.createPopulation();
	}

	private void createPopulation() {

		int probabilitySuscetivel =  (int) Math.round((size * size) * 0.95);
		int probabilityInfectado = (int) Math.round((size * size) * 0.05);
		int qtdSuscetivel = probabilitySuscetivel;
		int qtdInfectado = probabilityInfectado;
		int qtdRemovido = 0;
		Random random = new Random();
		int randomNumber;

		
		for (int lin = 0; lin < size; lin++) {
			matriz.add(new ArrayList<Person>());
			for (int col = 0; col < size; col++) {
					Person cell = new Person();
					randomNumber = random.nextInt(2);
					if (qtdSuscetivel > 0 || qtdInfectado > 0) { 
						if ((random.nextDouble() < 0.95 && qtdSuscetivel > 0) || qtdSuscetivel == 0) {
							cell.setState(StatePossibles.SUSCETIVEL);
							matriz.get(lin).add(cell);
							qtdSuscetivel--;
						} else {
							cell.setState(StatePossibles.INFECTADO);
							matriz.get(lin).add(cell);
							qtdInfectado--;
						} 
					} else {
						cell.setState(StatePossibles.SUSCETIVEL);
						matriz.get(lin).add(cell);
					}
			}
		}
		generationsStatistics = new ArrayList<HashMap<String, Integer>>();
		HashMap<String, Integer> stats = new HashMap<String,Integer>();
		stats.put(StatePossibles.SUSCETIVEL.getStateName(), probabilitySuscetivel);
		stats.put(StatePossibles.INFECTADO.getStateName(), probabilityInfectado);
		stats.put(StatePossibles.RECUPERADO.getStateName(), qtdRemovido);
		generationsStatistics.add(0, stats);
	}

	public void startGenerations(int numberOfGenerations) {
		for (int i = 1; i <= numberOfGenerations; i++) {
			applyNextGenerations(i);
		}
	}
	
	private int calculateNeighboursInfected(int lin, int col) {
		int numberOfNeighboursInfected = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int indexLin = this.calculateIndexOfNeighbour(lin + i);
				int indexCol = this.calculateIndexOfNeighbour(col + j);
				boolean isCurrentCell = (indexLin == lin && indexCol == col);
				if (!isCurrentCell) {
					Person currentNeighbour = matriz.get(indexLin).get(indexCol);
					boolean isInfected = currentNeighbour.getState().equals(StatePossibles.INFECTADO.getStateName());
					if (isInfected) {
						++numberOfNeighboursInfected;
					}
				}
			}
		}
		return numberOfNeighboursInfected;
	}
	
	private void applyNextGenerations(Integer currentGenerationCounter) {
		ArrayList<ArrayList<Person>> nextGeneration = new ArrayList<ArrayList<Person>>();
		//generations.put(currentGenerationCounter, new double[3]);
		HashMap<String, Integer> stats = new HashMap<String,Integer>();
		stats.put(StatePossibles.SUSCETIVEL.getStateName(), 0);
		stats.put(StatePossibles.INFECTADO.getStateName(), 0);
		stats.put(StatePossibles.RECUPERADO.getStateName(), 0);
		generationsStatistics.add(currentGenerationCounter, stats);
		HashMap<String, Integer> generationStatistics = generationsStatistics.get(currentGenerationCounter);
		for (int lin = 0; lin < size; lin++) {
			nextGeneration.add(new ArrayList<Person>());
			for (int col = 0; col < size; col++) {
				int numberOfNeighboursInfected = calculateNeighboursInfected(lin, col);
				Person currentCell = matriz.get(lin).get(col);
				Person personWithStateModified = applyRule(numberOfNeighboursInfected, currentCell, generationStatistics);
				nextGeneration.get(lin).add(personWithStateModified);
			}
		}
		matriz = nextGeneration;
		nextGeneration = null;
	}

	private Person applyRule(
			int numberOfNeighboursInfected, 
			Person currentCell, 
			HashMap<String, Integer> currentGenerationStatistics
	) {
		String currentCellState = currentCell.getState();
		Random rand = new Random();
		boolean willSwapState;
		Integer quantityPeopleInCurrentState;
		List<Connection> edgesFromVertex = graph.getEdgesFromVertex(currentCellState);
		for (Connection edge: edgesFromVertex) {
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
		return currentCell;
	}

	private int calculateIndexOfNeighbour(int indice) {
		if (indice < 0) {
			return size - 1;
		} else if (indice == size) {
			return 0;
		}
		return indice;
	}




	public String getStatisticsOfGenerationsToString() {
		StringBuilder builder = new StringBuilder();
		int index = 0;
		for (HashMap<String, Integer>  currentInformationsOfGeneration : generationsStatistics) {
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
			builder.append(this.matriz.get(lin));
			builder.append("\n");
		}
		return builder.toString();
	}
}
