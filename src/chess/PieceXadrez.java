package chess;

import boardgame.Tabuleiro;
import boardgame.Piece;
import boardgame.Posicao;

public abstract class PieceXadrez extends Piece {
	//ATTRIBUTES
	private Cor cor;
	private int moveCount;

	//CONSTRUCTORS
	public PieceXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	//GETTERS AND SETTERS
	public Cor getCor() {
		return cor;
	}
	public int getMoveCount() {
		return moveCount;
	}
	
	// METHODS
	public void acrescentarMoveCount() {
		moveCount++;
	}
	public void decrencentarMoveCount() {
		moveCount--;
	}
	public PosicaoXadrez getPosicaoXadrez() {
		 return PosicaoXadrez.fromPosicao(posicao);
	}
	
	protected boolean temUmaPieceDoOponente(Posicao posicao) {
		PieceXadrez p = (PieceXadrez)getTabuleiro().piece(posicao);
		return p != null && p.getCor() != cor;
	}
}
