package chess;

import boardgame.Tabuleiro;

public class PartidaXadrez {
	// ATTRIBUTES
	private Tabuleiro tabuleiro;

	// CONSTRUCTORS
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
	}
	//METHODS
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat  = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (ChessPiece) tabuleiro.piece(i, j);
				 
			}
		}
		return mat;
	}

}
