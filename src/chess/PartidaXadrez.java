package chess;

import boardgame.Pieces;
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
	

	public ChessPiece movimentoXadrez(XadrezPosicao posicaoInicial, XadrezPosicao posicaoAlvo) {
		Posicao inicial = posicaoInicial.toPosicao();
		Posicao alvo = posicaoAlvo.toPosicao();
		validarPosicaoInicial(inicial);
		Pieces pieceCapturada = fazerMovimento(inicial, alvo);
		return (ChessPiece)pieceCapturada;
	}
	
	private Pieces fazerMovimento(Posicao inicial, Posicao alvo) {
		Pieces p = tabuleiro.removerPiece(inicial);
		Pieces pieceCapturada = tabuleiro.removerPiece(alvo);
		tabuleiro.lugarPiece(p, alvo);
		return pieceCapturada;
	}
	private void validarPosicaoInicial(Posicao posicaoInicial) {
		if(!tabuleiro.temUmaPiece(posicaoInicial)) {
			throw new XadrezException("Não existe peça na posição de origem");
		}
	}
	
	public void lugarNovaPiece(char coluna, int linha, ChessPiece piece) {
		tabuleiro.lugarPiece(piece, new XadrezPosicao(coluna, linha).toPosicao());
	}

	public void iniciarPartida() {
		lugarNovaPiece('a', 1, new Torre(tabuleiro, Color.BRANCO));
		lugarNovaPiece('h', 1, new Torre(tabuleiro, Color.BRANCO));
		lugarNovaPiece('e', 1, new Rei(tabuleiro, Color.BRANCO));

		lugarNovaPiece('a', 8, new Torre(tabuleiro, Color.PRETO));
		lugarNovaPiece('h', 8, new Torre(tabuleiro, Color.PRETO));
		lugarNovaPiece('e', 8, new Rei(tabuleiro, Color.PRETO));

	}
}
