package boardgame;

public class Posicao {
	
	// ATTRIBUTES
	private int linha;
	private int coluna;

	// CONSTRUCTORS
	public Posicao(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	// GETTERS AND SETTERS
	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public void setValores(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	// METHODS
	@Override
	public String toString() {
		return linha + ", " + coluna;
	}
}
