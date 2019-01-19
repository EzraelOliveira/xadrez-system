package boardgame;

public class Tabuleiro {
	// ATTRIBUTES
	private int linhas;
	private int colunas;
	private Pieces[][] pieces;

	// COSNTRUCTORS
	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pieces = new Pieces[linhas][colunas];
	}

	// GETTERS AND SETTERS
	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}

	// METHODS
	public Pieces piece(int linha, int coluna) {
		return pieces[linha][coluna];
	}

	public Pieces piece(Posicao posicao) {
		return pieces[posicao.getLinha()][posicao.getColuna()];
	}

	public void lugarPiece(Pieces piece, Posicao posicao) {
		pieces[posicao.getLinha()][posicao.getColuna()] = piece;
		piece.posicao = posicao;
	}

}
