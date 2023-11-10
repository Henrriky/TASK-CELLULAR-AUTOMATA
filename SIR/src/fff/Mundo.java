package fff;

import java.util.ArrayList;
import java.util.List;

public class Mundo {
    
	private int linhas;
    private int colunas;
	private List<List<Celula>> matriz;

	
	public void iniciar(int linhas, int colunas) {
		
		this.linhas = linhas;
		this.colunas = colunas;
        matriz = new ArrayList<>(linhas);
        for (int i = 0; i < linhas; i++) {
            List<Celula> linha = new ArrayList<>(colunas);
            for (int j = 0; j < colunas; j++) {
                linha.add(new Celula());
              }
            matriz.add(linha);
        }
        
        setVizinhos();
    }
 

    public List<List<Celula>> getMatriz() {
        return matriz;
    }
    
    
    
    //imprime uma única posição e todos seus vizinhos
    public void imprimirCelula(int i, int j) {
        List<List<Celula>> matrizCelulas = this.matriz;
        List<Celula> linha = matrizCelulas.get(i);
        Celula celula = linha.get(j);
        System.out.print("Posição [" + i +"][" + j + "]: " + celula + " | ");
        
        System.out.println();
        
        System.out.println(celula.getNoroeste() + " " + celula.getNorte() + " " + celula.getNordeste());
        System.out.println(celula.getLeste() + " " + celula + " " + celula.getOeste());
        System.out.println(celula.getSudeste() + " " + celula.getSul() + " " + celula.getSudoeste());
            }
     
    
    
    //imprime a matriz inteira   
	public void imprimirMatriz() {
   
		List<List<Celula>> matrizCelulas = this.matriz;
		
		for (int i = 0; i < matrizCelulas.size(); i++) {
			System.out.println();
			System.out.println("\n ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println();
			
			List<Celula> linha = matrizCelulas.get(i);
			
			for (int j = 0; j < linha.size(); j++) {
				System.out.print("Posição [" + i +"][" + j + "]: " + linha.get(j) + "  |  ");
			}
			
			System.out.println();
    		}
	}
	
	
	
	//setar as 8 posições de vizinhança de cada célula
	public void setVizinhos() {
		
		setVizinhosLados();
		setVizinhosAcima();
		setVizinhosAbaixo();
	}
	
	public void setVizinhosAcima() {
    	
		List<List<Celula>> matrizCelulas = this.matriz;
        
        	
    	List<Celula> linha = matrizCelulas.get(0);
        List<Celula> linhaAux = matrizCelulas.get(linhas-1);
        
        for (int j = 0; j < colunas; j++) {
       	 Celula celula = linha.get(j);
       	 
       	 celula.setNorte(linhaAux.get(j));
       	 celula.setNoroeste(celula.getNorte().getOeste());
       	 celula.setNordeste(celula.getNorte().getLeste());
      }
    	
            	
    	for (int i = 1; i < linhas-1; i++) {
        	
    		linha = matrizCelulas.get(i);
            linhaAux = matrizCelulas.get(i-1);
    		
            for (int j = 0; j < colunas; j++) {
            	 Celula celula = linha.get(j);
            	 
            	 celula.setNorte(linhaAux.get(j));
               	 celula.setNoroeste(celula.getNorte().getOeste());
               	 celula.setNordeste(celula.getNorte().getLeste());
            }
            
         }
       
	}
	
	public void setVizinhosAbaixo() {
    	List<List<Celula>> matrizCelulas = this.matriz;
        
        	
    	List<Celula> linha = matrizCelulas.get(linhas-1);
        List<Celula> linhaAux = matrizCelulas.get(0);
        
        for (int j = 0; j < colunas; j++) {
       	 Celula celula = linha.get(j);
       	 
       	 celula.setSul(linhaAux.get(j));
       	 celula.setSudoeste(celula.getSul().getOeste());
       	 celula.setSudeste(celula.getSul().getLeste());
      }
    	
            	
    	for (int i = 0; i < linhas-1; i++) {
        	
    		linha = matrizCelulas.get(i);
            linhaAux = matrizCelulas.get(i+1);
    		
            for (int j = 0; j < colunas; j++) {
            	 Celula celula = linha.get(j);
            	 
            	 celula.setSul(linhaAux.get(j));
            	 celula.setSudoeste(celula.getSul().getOeste());
            	 celula.setSudeste(celula.getSul().getLeste());
           }
            
         }
       
    }
    
    public void setVizinhosLados() {
    	List<List<Celula>> matrizCelulas = this.matriz;
        for (int i = 0; i < linhas; i++) {
        	List<Celula> linha = matrizCelulas.get(i);
            for (int j = 0; j < colunas; j++) {
            	 Celula celula = linha.get(j);
            	 
            	 if(j>0) {
                	celula.setLeste(linha.get(j-1));
                		}else celula.setLeste(linha.get(this.colunas-1));
            	 
            	 if(j<this.colunas-1) {
            		 celula.setOeste(linha.get(j+1));
            	 		}else celula.setOeste(linha.get(0));
              }
         }
    }
    
}