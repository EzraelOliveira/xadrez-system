package chess;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaXadrez {
	// ATTRIBUTES
	private Tabuleiro tabuleiro;

	// CONSTRUCTORS
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciarPartida();
	}

	// METHODS
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (ChessPiece) tabuleiro.piece(i, j);

			}
		}
		return mat;
	}

	public void iniciarPartida() {
		tabuleiro.lugarPiece(new Torre(tabuleiro, Color.PRETO), new Posicao(0, 0));
		tabuleiro.lugarPiece(new Torre(tabuleiro, Color.PRETO), new Posicao(0, 7));
		tabuleiro.lugarPiece(new Rei(tabuleiro, Color.PRETO), new Posicao(0, 4));
		tabuleiro.lugarPiece(new Torre(tabuleiro, Color.BRANCO), new Posicao(7, 0));
		tabuleiro.lugarPiece(new Torre(tabuleiro, Color.BRANCO), new Posicao(7, 7));
		tabuleiro.lugarPiece(new Rei(tabuleiro, Color.BRANCO), new Posicao(7, 4));
	}
}
