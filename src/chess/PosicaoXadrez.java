package chess;

import boardgame.Posicao;

public class PosicaoXadrez {
	
	//ATTRIBUTES
	private char coluna;
	private int linha;
	
	//CONSTRUCTORS
	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezException("Somente sera aceito coordenadas de a1 a h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	//GETTERS AND SETTERS
	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	// METHODS
	protected Posicao toPosition() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	protected static PosicaoXadrez fromPosicao(Posicao posicao) {
		return new PosicaoXadrez((char)('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
