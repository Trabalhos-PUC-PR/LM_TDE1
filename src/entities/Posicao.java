package entities;

public class Posicao {

	private Integer inicio = 0;
	private Integer fim = 0;
	
	public Posicao() {
	}
	
	public Posicao(Integer inicio, Integer fim) {
		this.inicio = inicio;
		this.fim = fim;
	}

	public Integer getInicio() {
		return inicio;
	}

	public void setInicio(Integer inicio) {
		this.inicio = inicio;
	}

	public Integer getFim() {
		return fim;
	}

	public void setFim(Integer fim) {
		this.fim = fim;
	}

	@Override
	public String toString() {
		return "["+inicio+", "+fim+"]";
	}

}
