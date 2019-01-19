package chess;

import boardgame.Pieces;
import boardgame.Posicao;
import boardgame.Tabuleiro;

public abstract class ChessPiece extends Pieces{
	//ATTRIBUTES
	private Color color;

	//CONSTRUCTORS
	public ChessPiece(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro);
		this.color = color;
	}
	//GETTERS AND SETTERS
	public Color getColor() {
		return color;
	}

	protected boolean temUmaPieceDoOponente(Posicao posicao) {
		ChessPiece p = (ChessPiece)getTabuleiro().piece(posicao);
		return p != null && p.getColor() != color;
	}
}
