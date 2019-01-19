package chess;

import boardgame.Posicao;

public class XadrezPosicao {
	// ATTRIBUTES
	private int linha;
	private char coluna;

	// CONSTRUCTORS
	public XadrezPosicao(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezException("Valor inválido, somente serão aceito valores de a1 a h8");
		}
		this.linha = linha;
		this.coluna = coluna;
	}

	// GETTERS AND SETTERS
	public int getLinha() {
		return linha;
	}

	public char getColuna() {
		return coluna;
	}

	// METHODS
	protected Posicao toPosicao() {
		return new Posicao(8 - linha, coluna - 'a');
	}

	protected static XadrezPosicao fromPosicao(Posicao posicao) {
		return new XadrezPosicao((char) ('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}

	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
