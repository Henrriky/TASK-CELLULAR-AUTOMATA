package WORK;

public class teSTE {
	public static void main(String[] args) {
		States grafo = new States();
		//System.out.println(grafo.getEdgesFromVertex(StatePossibles.INFECTADO.getStateName()));
		System.out.println(grafo.getEdgesFromVertex(StatePossibles.RECUPERADO.getStateName()));
	}
}
