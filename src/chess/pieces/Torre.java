package chess.pieces;

import boardgame.Posicao;
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

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		
		//cima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if(getTabuleiro().posicaoExiste(p) && temUmaPieceDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()]= true;
		}
		//esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna()-1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()-1);
		}
		if(getTabuleiro().posicaoExiste(p) && temUmaPieceDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()]= true;
		}
		//direita
		p.setValores(posicao.getLinha(), posicao.getColuna()+1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()+1);
		}
		if(getTabuleiro().posicaoExiste(p) && temUmaPieceDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()]= true;
		}
		//baixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPiece(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if(getTabuleiro().posicaoExiste(p) && temUmaPieceDoOponente(p)) {
			mat[p.getLinha()][p.getColuna()]= true;
		}
		return null;
		
	}
}
