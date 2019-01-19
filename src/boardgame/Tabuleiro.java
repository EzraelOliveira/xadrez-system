package boardgame;

public class Tabuleiro {
	// ATTRIBUTES
	private int linhas;
	private int colunas;
	private Pieces[][] pieces;

	// COSNTRUCTORS
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro ao criar o tabuleiro, tem que haver pelo menos uma linha  e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pieces = new Pieces[linhas][colunas];
	}

	// GETTERS AND SETTERS
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	// METHODS
	public Pieces piece(int linha, int coluna) {
		if(!posicaoExiste(linha, coluna)){
			throw new TabuleiroException("Coordenada informada está fora do tabuleiro");
		}
		return pieces[linha][coluna];
	}

	public Pieces piece(Posicao posicao) {
		if(!posicaoExiste(posicao)){
			throw new TabuleiroException("Coordeanda informada está fora do tabuleiro");
		}
		return pieces[posicao.getLinha()][posicao.getColuna()];
	}

	public void lugarPiece(Pieces piece, Posicao posicao) {
		if(temUmaPiece(posicao)) {
			throw new TabuleiroException("Já existe algo nessa coordenada: "+ posicao);
		}
		pieces[posicao.getLinha()][posicao.getColuna()] = piece;
		piece.posicao = posicao;
	}
	
	public Pieces removerPiece(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new TabuleiroException("Coordenada fora do tabuleiro");
		}
		if (piece(posicao)==null) {
			return null;
		}
		Pieces aux = piece(posicao);
		aux.posicao = null;
		pieces[posicao.getLinha()][posicao.getColuna()]= null;
		return aux;
		
	}

	public boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}

	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}

	public boolean temUmaPiece(Posicao posicao) {
		if(!posicaoExiste(posicao)){
			throw new TabuleiroException("Coordenada está fora do tabuleiro");
		}
		return piece(posicao) != null;
	}
}
