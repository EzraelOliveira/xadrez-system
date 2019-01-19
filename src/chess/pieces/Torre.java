package chess.pieces;

import boardgame.Tabuleiro;
import chess.ChessPiece;
import chess.Color;

public class Torre extends ChessPiece {

	public Torre(Tabuleiro tabuleiro, Color color) {
		super(tabuleiro, color);
	}

	@Override
	public String toString() {
		return "T";
	}
}
