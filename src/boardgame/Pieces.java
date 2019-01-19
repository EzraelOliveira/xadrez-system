package boardgame;

public class Pieces {
	// ATTRIBUTES
	protected Posicao posicao;
	private Tabuleiro tabuleiro;

	//CONSTRUCTORS
	public Pieces(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
}
