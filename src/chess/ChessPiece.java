package chess;

import boardgame.Pieces;
import boardgame.Tabuleiro;

public class ChessPiece extends Pieces{
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

	
}
