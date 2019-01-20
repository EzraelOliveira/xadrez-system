package chess;

import boardgame.Tabuleiro;
import boardgame.Piece;
import boardgame.Posicao;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaXadrez {
	
	//ATTRIBUTES
	private Tabuleiro tabuleiro;
	
	//CONSTRUCTORS
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		initialSetup();
	}
	
	// METHODS
	public PieceXadrez[][] getPieces() {
		PieceXadrez[][] mat = new PieceXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PieceXadrez) tabuleiro.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoInicial) {
		Posicao posicao = posicaoInicial.toPosition();
		validarPosicaoInicial(posicao);
		return tabuleiro.piece(posicao).movimentosPossiveis();
	}
	
	public PieceXadrez performChessMove(PosicaoXadrez posicaoInicial, PosicaoXadrez posicaoAlvo) {
		Posicao inicial = posicaoInicial.toPosition();
		Posicao alvo = posicaoAlvo.toPosition();
		validarPosicaoInicial(inicial);
		validarPosicaoAlvo(inicial, alvo);
		Piece capturedPiece = fazerMovimento(inicial, alvo);
		return (PieceXadrez)capturedPiece;
	}
	
	private Piece fazerMovimento(Posicao inicial, Posicao alvo) {
		Piece p = tabuleiro.removerPiece(inicial);
		Piece capturedPiece = tabuleiro.removerPiece(alvo);
		tabuleiro.lugarpiece(p, alvo);
		return capturedPiece;
	}
	
	private void validarPosicaoInicial(Posicao posicao) {
		if (!tabuleiro.existeUmaPiece(posicao)) {
			throw new XadrezException("Nao ha nada nessa coordenada");
		}
		if (!tabuleiro.piece(posicao).podeSeMover()) {
			throw new XadrezException("Nao existe movimentos possiveis ");
		}
	}
	
	private void validarPosicaoAlvo(Posicao inicial, Posicao alvo) {
		if (!tabuleiro.piece(inicial).movimentoPossivel(alvo)) {
			throw new XadrezException("Nao pode se mover para a coordenada alvo");
		}
	}
	
	private void lugarNovaPiece(char coluna, int linha, PieceXadrez piece) {
		tabuleiro.lugarpiece(piece, new PosicaoXadrez(coluna, linha).toPosition());
	}
	
	private void initialSetup() {
		lugarNovaPiece('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPiece('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPiece('e', 1, new Rei(tabuleiro, Cor.BRANCO));

        lugarNovaPiece('a', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPiece('h', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPiece('e', 8, new Rei(tabuleiro, Cor.PRETO));
	}
}
