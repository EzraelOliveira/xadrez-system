package boardgame;

public abstract class Piece {
	
	// ATTRIBUTES
	protected Posicao posicao;
	private Tabuleiro tabuleiro;

	// CONSTRUCTORS
	public Piece(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	// GETTERS AND SETTERS
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	// METHODS
	public abstract boolean[][] movimentosPossiveis();

	public boolean movimentoPossivel(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean podeSeMover() {
		boolean[][] mat = movimentosPossiveis();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
