package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.Cor;
import chess.PartidaXadrez;
import chess.PieceXadrez;

public class Rei extends PieceXadrez {

	// ATTRIBUTES
	private PartidaXadrez partidaXadrez;

	// CONSTRUCTORS
	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	// METHODS
	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		PieceXadrez p = (PieceXadrez) getTabuleiro().piece(posicao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// cima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// baixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// movimento especial Roque
		if (getMoveCount() == 0 && !partidaXadrez.getCheck()) {
			// Roque pequeno
			Posicao pT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testeRoque(pT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabuleiro().piece(p1) == null && getTabuleiro().piece(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			// Roque Grande
			Posicao pT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (testeRoque(pT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if (getTabuleiro().piece(p1) == null && getTabuleiro().piece(p2) == null
						&& getTabuleiro().piece(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}
		}
		return mat;
	}

	private boolean testeRoque(Posicao posicao) {
		PieceXadrez p = (PieceXadrez) getTabuleiro().piece(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getMoveCount() == 0;
	}
}
