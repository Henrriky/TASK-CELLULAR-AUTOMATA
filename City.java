package WORK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class City {

	private ArrayList<ArrayList<Person>> matriz;
	private int tamanho;
	private States graph;
	private Map<Integer, HashMap<String, Double>> generations;

	public City(int tamanho) {
		this.tamanho = tamanho;
		this.matriz = new ArrayList<ArrayList<Person>>();
		this.graph = new States();
		this.createPopulation();
	}

	private void createPopulation() {

		double qtdSuscetivel = (tamanho * tamanho) * 0.95;
		double qtdInfectado = (tamanho * tamanho) * 0.05;
		double qtdRemovido = 0;
		Random random = new Random();
		int randomNumber;

		for (int lin = 0; lin < tamanho; lin++) {
			matriz.add(new ArrayList<Person>());
			for (int col = 0; col < tamanho; col++) {
				Person cell = new Person();
				if (qtdSuscetivel > 0 || qtdInfectado > 0) {
					randomNumber = random.nextInt(100);
					if ((random.nextDouble() < 95 && qtdSuscetivel > 0) || qtdSuscetivel == 0) {
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
		generations = new HashMap<Integer, HashMap<String, Double>>();
		HashMap<String, Double> stats = new HashMap<String,Double>();
		stats.put(StatePossibles.SUSCETIVEL.getStateName(), (tamanho * tamanho * 0.95));
		stats.put(StatePossibles.INFECTADO.getStateName(), (tamanho * tamanho * 0.05));
		stats.put(StatePossibles.RECUPERADO.getStateName(), qtdRemovido);
		generations.put(0, stats);
//		arr[0] = ;
//		arr[1] = (tamanho * tamanho) * 0.05;
//		arr[2] = qtdRemovido;
	}

	private void applyNextGenerations(Integer currentGenerationCounter) {
		ArrayList<ArrayList<Person>> nextGeneration = new ArrayList<ArrayList<Person>>();
		//generations.put(currentGenerationCounter, new double[3]);
		HashMap<String, Double> stats = new HashMap<String,Double>();
		stats.put(StatePossibles.SUSCETIVEL.getStateName(), 0.0);
		stats.put(StatePossibles.INFECTADO.getStateName(), 0.0);
		stats.put(StatePossibles.RECUPERADO.getStateName(), 0.0);
		generations.put(currentGenerationCounter, stats);
		HashMap<String, Double> generationStatistics = generations.get(currentGenerationCounter);
		for (int lin = 0; lin < tamanho; lin++) {
			nextGeneration.add(new ArrayList<Person>());
			for (int col = 0; col < tamanho; col++) {
				int numberOfNeighboursInfected = calculateNeighboursInfected(lin, col);
				Person currentCell = matriz.get(lin).get(col);
				Person personWithStateModify = applyRule(numberOfNeighboursInfected, currentCell, generationStatistics);
				nextGeneration.get(lin).add(personWithStateModify);
			}
		}
		matriz = nextGeneration;
		nextGeneration = null;
	}

	private int calculateNeighboursInfected(int lin, int col) {
		int numberOfNeighboursInfected = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int indexLin = this.calculateIndexOfNeighbour(lin + i);
				int indexCol = this.calculateIndexOfNeighbour(col + 1);
				boolean isCurrentCell = (i == lin && j == col);
				if (!isCurrentCell) {
					Person currentNeighbour = matriz.get(indexLin).get(indexCol);
					boolean isInfected = currentNeighbour.getState().equals(StatePossibles.INFECTADO.getStateName());
					if (isInfected) {
						numberOfNeighboursInfected++;
					}
				}
			}
		}
		return numberOfNeighboursInfected;
	}

	private Person applyRule(
			int numberOfNeighboursInfected, 
			Person currentCell, 
			HashMap<String, Double> currentGenerationStatistics
	) {
		String state = currentCell.getState();
		Random rand = new Random();
		boolean willSwapState;
		List<Connection> edgesFromVertex = graph.getEdgesFromVertex(state);
		
//		for (ashMap<String, ChangeChance> edgesFromVertex : graph.getEdgesFromVertex(state)) {
		for (Connection edges: edgesFromVertex) {
			double chanceOfChange = edges.getChanceWeight().getChance(numberOfNeighboursInfected);
			String newState = edges.getState();
			willSwapState = (rand.nextDouble() < chanceOfChange);
			if (willSwapState) {
				Double quantity = currentGenerationStatistics.get(newState);
				currentGenerationStatistics.put(newState, ++quantity);
				return new Person(newState); 
			}
		}
		Double quantity = currentGenerationStatistics.get(state);
		currentGenerationStatistics.put(state, ++quantity);
		return currentCell;
		/*
		 * Pegar o estado atual EXISTEM TRES ESTADOS - SUSCETIVEL - INFECTADO - REMOVIDO
		 * POSSIVEIS FLUXOS - REMOVIDO -> SUSCETIVEL (MORREU E FOI REVIVIDO) -
		 * SUSCETIVEL -> INFECTADO - INFECTADO -> REMOVIDO (O.3 CHANCE) - REMOVIDO ->
		 * INFECTADO (0.6 CHANCE) - CALCULO - Probabilidade DE SER INFECTADO: 1 - e^-k -
		 * Onde e é a constante - K é a quantidade de infectados CASE SUSCETIVEL
		 * RANDOMNUMBER < 1 - e^k -> - Caso a probabilidade seja menor que a chance de
		 * contagio CASE INFECTADO 0.3 < CHANCE DE CONTAGIO =>
		 */
	}

	private double calculateChance(int numberOfNeighboursInfected) {
		double result = (1 - Math.pow(Math.E, -numberOfNeighboursInfected));
		return result;
	}

	private int calculateIndexOfNeighbour(int indice) {
		if (indice < 0) {
			return tamanho - 1;
		} else if (indice >= tamanho) {
			return 0;
		}
		return indice;
	}

	public void startGenerations(int numberOfGenerations) {
		for (int i = 1; i <= numberOfGenerations; i++) {
			applyNextGenerations(i);
		}
	}

	public String getStatisticsOfGenerations() {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<Integer,HashMap<String,Double>>  currentInformationsOfMap : generations.entrySet()) {
			HashMap<String,Double> statistics = currentInformationsOfMap.getValue();
			builder.append("Geracao ");
			builder.append(currentInformationsOfMap.getKey());
			builder.append(" => ");
			builder.append("Suscetiveis: ");
			builder.append(statistics.get(StatePossibles.SUSCETIVEL.getStateName()));
			builder.append(" | Infectados: ");
			builder.append(statistics.get(StatePossibles.INFECTADO.getStateName()));
			builder.append(" | Removidos: ");
			builder.append(statistics.get(StatePossibles.RECUPERADO.getStateName()));
			builder.append("\n");
		}
		return builder.toString();
	}

	public Map<Integer, HashMap<String, Double>> getStatistics() {
		return this.generations;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int lin = 0; lin < tamanho; lin++) {
			builder.append(this.matriz.get(lin));
			builder.append("\n");
		}
		return builder.toString();
	}
}
