package fff;

public class Celula {
	
	public char estado;

	private Celula noroeste;
	private Celula norte;
	private Celula nordeste;
	private Celula leste;
	private Celula sudeste;
	private Celula sul;
	private Celula sudoeste;
	private Celula oeste;
	
	@Override
	public String toString() {
		return "[" + Celula.this.hashCode() + estado + "]";
	}

	
	
	public char getEstado() {
		return estado;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}

	public Celula getNoroeste() {
		return noroeste;
	}

	public void setNoroeste(Celula noroeste) {
		this.noroeste = noroeste;
	}

	public Celula getNorte() {
		return norte;
	}

	public void setNorte(Celula norte) {
		this.norte = norte;
	}

	public Celula getNordeste() {
		return nordeste;
	}

	public void setNordeste(Celula nordeste) {
		this.nordeste = nordeste;
	}

	public Celula getLeste() {
		return leste;
	}

	public void setLeste(Celula leste) {
		this.leste = leste;
	}

	public Celula getSudeste() {
		return sudeste;
	}

	public void setSudeste(Celula sudeste) {
		this.sudeste = sudeste;
	}

	public Celula getSul() {
		return sul;
	}

	public void setSul(Celula sul) {
		this.sul = sul;
	}

	public Celula getSudoeste() {
		return sudoeste;
	}

	public void setSudoeste(Celula sudoeste) {
		this.sudoeste = sudoeste;
	}

	public Celula getOeste() {
		return oeste;
	}

	public void setOeste(Celula oeste) {
		this.oeste = oeste;
	}

	
}
