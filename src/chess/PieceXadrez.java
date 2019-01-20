package chess;

import boardgame.Tabuleiro;
import boardgame.Piece;
import boardgame.Posicao;

public abstract class PieceXadrez extends Piece {
	//ATTRIBUTES
	private Cor cor;

	//CONSTRUCTORS
	public PieceXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	//GETTERS AND SETTERS
	public Cor getCor() {
		return cor;
	}
	
	// METHODS
	protected boolean temUmaPieceDoOponente(Posicao posicao) {
		PieceXadrez p = (PieceXadrez)getTabuleiro().piece(posicao);
		return p != null && p.getCor() != cor;
	}
}
