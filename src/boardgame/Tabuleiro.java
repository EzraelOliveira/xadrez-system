package boardgame;

public class Tabuleiro {

	// ATTRIBUTES
	private int linhas;
	private int colunas;
	private Piece[][] pieces;

	// CONSTRUCTORS
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro ao criar o tabuleiro, deve haver ao menos uma linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pieces = new Piece[linhas][colunas];
	}

	// GETTERS AND SETTERS
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	// METHODS
	public Piece piece(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new TabuleiroException("Coordenada fora do tabuleiro");
		}
		return pieces[linha][coluna];
	}

	public Piece piece(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("Coordenada fora do tabuleiro");
		}
		return pieces[posicao.getLinha()][posicao.getColuna()];
	}

	public void lugarpiece(Piece piece, Posicao posicao) {
		if (existeUmaPiece(posicao)) {
			throw new TabuleiroException("Nao ha nada na coordenada: " + posicao);
		}
		pieces[posicao.getLinha()][posicao.getColuna()] = piece;
		piece.posicao = posicao;
	}

	public Piece removerPiece(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("Coordenada fora do tabuleiro");
		}
		if (piece(posicao) == null) {
			return null;
		}
		Piece aux = piece(posicao);
		aux.posicao = null;
		pieces[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}

	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}

	public boolean existeUmaPiece(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new TabuleiroException("Coordenada fora do tabuleiro");
		}
		return piece(posicao) != null;
	}
}
