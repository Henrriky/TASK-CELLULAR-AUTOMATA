package WORK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class City {
	
	private ArrayList<ArrayList<Person>> matriz;
	private int tamanho;
	private States graph;
	private Map<Integer, int[]> generations;
	
	public City (int tamanho) {
        this.tamanho = tamanho;
        this.matriz = new ArrayList<ArrayList<Person>>();
        this.graph = new States();
        this.createPopulation();
    } 
	
	private void createPopulation () {
		
		double qtdSuscetivel = (tamanho*tamanho) * 0.95; 
		double qtdInfectado = (tamanho*tamanho) * 0.05;
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
	}
	
	public void applyNextGenerations () {
		ArrayList<ArrayList<Person>> nextGeneration = new ArrayList<ArrayList<Person>>();
		for (int lin = 0; lin < tamanho; lin++) {
			for (int col = 0; col < tamanho; col++) {
				int numberOfNeighboursInfected = calculateNeighboursInfected(lin, col);
				Person currentNeighbour = matriz.get(lin).get(col);
				applyRule(numberOfNeighboursInfected, currentNeighbour);
			}
		}
		matriz = nextGeneration;
		nextGeneration = null;
	}
	
	private int calculateNeighboursInfected (int lin, int col) {
		int numberOfNeighboursInfected = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int indexLin = this.calculateIndexOfNeighbour(lin+i);
				int indexCol = this.calculateIndexOfNeighbour(col+1);
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
	
	private Person applyRule (int numberOfNeighboursInfected, Person currentNeighbour) {
		
		String state = currentNeighbour.getState();
		if (state.equals(StatePossibles.SUSCETIVEL.getStateName())) {
			Random rand = new Random();
			boolean willBeInfected = rand.nextDouble() < calculateChance(numberOfNeighboursInfected);
			if (willBeInfected) {
				return new Person(StatePossibles.INFECTADO); 
			} else {
				return currentNeighbour;
			}
		} else if (state.equals(StatePossibles.INFECTADO.getStateName())) {
			boolean willBeRemoved;
			for (
				Map.Entry<String, Double> entry 
				: 
				graph.getEdgesFromVertex(
					StatePossibles.INFECTADO.getStateName()
				).entrySet()
			) {
			    String adjancentVertexValue = entry.getKey();
			    Double edgeWeightValue = entry.getValue();
			    willBeRemoved = edgeWeightValue < calculateChance(numberOfNeighboursInfected);
			    if (willBeRemoved) {
			    	return new Person(adjancentVertexValue);
			    } else {
			    	return currentNeighbour;
			    }
			}
		} else if (state.equals(StatePossibles.REMOVIDO.getStateName())) {
			boolean willBeRemoved;
			for (
				Map.Entry<String, Double> entry 
				: 
				graph.getEdgesFromVertex(
					StatePossibles.REMOVIDO.getStateName()
				).entrySet()
			) {
			    String adjancentVertexValue = entry.getKey();
			    Double edgeWeightValue = entry.getValue();
			    willBeRemoved = edgeWeightValue < calculateChance(numberOfNeighboursInfected);
			    if (willBeRemoved) {
			    	return new Person(adjancentVertexValue);
			    } else {
			    	return currentNeighbour;
			    }
			}
		}
		return null;
		/* Pegar o estado atual
		EXISTEM TRES ESTADOS
			- SUSCETIVEL
			- INFECTADO
			- REMOVIDO
		POSSIVEIS FLUXOS
			- REMOVIDO -> SUSCETIVEL (MORREU E FOI REVIVIDO)
			- SUSCETIVEL -> INFECTADO
			- INFECTADO -> REMOVIDO (O.3 CHANCE)
			- REMOVIDO -> INFECTADO (0.6 CHANCE)
		- CALCULO
			- Probabilidade DE SER INFECTADO: 1 - e^-k
				- Onde e é a constante
				- K é a quantidade de infectados
			CASE SUSCETIVEL
				RANDOMNUMBER < 1 - e^k -> 
				- Caso a probabilidade seja menor que a chance de contagio 
			CASE INFECTADO 
				0.3 < CHANCE DE CONTAGIO =>  
		 * */
	}
	
	private double calculateChance (int numberOfNeighboursInfected) {
		double result = (1 - Math.pow(Math.E, -numberOfNeighboursInfected));
		return result;
	}
	
	private int calculateIndexOfNeighbour (int indice) {
		if (indice < 0) {
			return tamanho - 1;
		} else if (indice >= tamanho) {
			return 0;
		}
		return indice;
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
