package boardgame;

public abstract class Pieces {
	// ATTRIBUTES
	protected Posicao posicao;
	private Tabuleiro tabuleiro;

	//CONSTRUCTORS
	public Pieces(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	public abstract boolean[][] movimentosPossiveis();
		 
	public boolean movimentoPossivel() {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	public  boolean existeAlgumMovimentoPossivel() {
		boolean [][]mat=  movimentosPossiveis() ;
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if(mat [i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
}
