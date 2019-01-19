package chess.pieces;

import boardgame.Tabuleiro;
import chess.ChessPiece;
import chess.Color;

public class Rei extends ChessPiece{

	public Rei(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
		
	}
	
	@Override
	public String toString() {
		return"R";
	}

}
