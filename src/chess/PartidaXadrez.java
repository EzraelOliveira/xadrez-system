package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Piece;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Bispo;
import chess.pieces.Cavalo;
import chess.pieces.Peao;
import chess.pieces.Rainha;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaXadrez {

	// ATTRIBUTES
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PieceXadrez vuneravelEnPassant;
	private PieceXadrez promover;
	private List<Piece> piecesNoTabuleiro = new ArrayList<>();
	private List<Piece> piecesCapturadas = new ArrayList<>();

	// CONSTRUCTORS
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		initialSetup();
	}

	// GETTERS AND SETTERS
	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PieceXadrez getVuneravelEnPassant() {
		return vuneravelEnPassant;
	}

	public PieceXadrez getPromover() {
		return promover;
	}

	// METHODS
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	public PieceXadrez[][] getPieces() {
		PieceXadrez[][] mat = new PieceXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
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
		Piece pieceCapturada = fazerMovimento(inicial, alvo);

		if (testeCheck(jogadorAtual)) {
			desfazerMovimento(inicial, alvo, pieceCapturada);
			throw new XadrezException("Voce nao pode se colocar em check");
		}

		PieceXadrez pieceMovida = ((PieceXadrez) tabuleiro.piece(alvo));

		//Jogada especial Promover
		promover = null;
		if(pieceMovida instanceof Peao) {
			if(pieceMovida.getCor() == Cor.BRANCO &&  alvo.getLinha() == 0 ||pieceMovida.getCor() == Cor.PRETO &&  alvo.getLinha() == 7) {
				promover = (PieceXadrez)tabuleiro.piece(alvo);
				promover = substituirPiecePromovida("Q");
				
			}
		}
		
		
		
		
		
		
		check = (testeCheck(oponente(jogadorAtual))) ? true : false;

		if (testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// Movimento especial en passant
		if (pieceMovida instanceof Peao
				&& (alvo.getLinha() == inicial.getLinha() - 2 || alvo.getLinha() == inicial.getLinha() + 2)) {
			vuneravelEnPassant = pieceMovida;
		} else {
			vuneravelEnPassant = null;
		}
		return (PieceXadrez) pieceCapturada;
	}
	
	
	
	
	public PieceXadrez substituirPiecePromovida(String tipo) {
		if(promover == null) {
			throw new IllegalStateException("Nao pode ser promovida");
		}
		if(!tipo.equals("B")&&!tipo.equals("C")&&!tipo.equals("T")&&!tipo.equals("Q")) {
			throw new InvalidParameterException("Tipo Invalido de promocao");
		}
		Posicao pos = promover.getPosicaoXadrez().toPosition();
		Piece p = tabuleiro.removerPiece(pos);
		piecesNoTabuleiro.remove(p);
		
		PieceXadrez novaPiece = novaPiece(tipo, promover.getCor());
		tabuleiro.lugarpiece(novaPiece, pos);
		piecesNoTabuleiro.add(novaPiece);
		return novaPiece;
	}
	private PieceXadrez novaPiece(String tipo, Cor cor) {
		if(tipo.equals("B")) return  new Bispo(tabuleiro,cor);
		if(tipo.equals("C")) return  new Cavalo(tabuleiro,cor);
		if(tipo.equals("T")) return  new Torre(tabuleiro,cor);
		return  new Rainha(tabuleiro,cor);
	}

	private Piece fazerMovimento(Posicao inicial, Posicao alvo) {
		PieceXadrez p = (PieceXadrez) tabuleiro.removerPiece(inicial);
		p.acrescentarMoveCount();
		Piece pieceCapturada = tabuleiro.removerPiece(alvo);
		tabuleiro.lugarpiece(p, alvo);
		if (pieceCapturada != null) {
			piecesNoTabuleiro.remove(pieceCapturada);
			piecesCapturadas.add(pieceCapturada);
		}
		// movimento especial Roque
		// Roque pequeno
		if (p instanceof Rei && alvo.getColuna() == inicial.getColuna() + 2) {
			Posicao inicialT = new Posicao(inicial.getLinha(), inicial.getColuna() + 3);
			Posicao alvoT = new Posicao(inicial.getLinha(), inicial.getColuna() + 1);
			PieceXadrez torre = (PieceXadrez) tabuleiro.removerPiece(inicialT);
			tabuleiro.lugarpiece(torre, alvoT);
			torre.acrescentarMoveCount();
		}
		// Roque grande
		if (p instanceof Rei && alvo.getColuna() == inicial.getColuna() - 2) {
			Posicao inicialT = new Posicao(inicial.getLinha(), inicial.getColuna() - 4);
			Posicao alvoT = new Posicao(inicial.getLinha(), inicial.getColuna() - 1);
			PieceXadrez torre = (PieceXadrez) tabuleiro.removerPiece(inicialT);
			tabuleiro.lugarpiece(torre, alvoT);
			torre.acrescentarMoveCount();
		}

		// Movimento especial EnPassant
		if (p instanceof Peao) {
			if (inicial.getColuna() != alvo.getColuna() && pieceCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(alvo.getLinha() + 1, alvo.getColuna());
				} else {
					posicaoPeao = new Posicao(alvo.getLinha() - 1, alvo.getColuna());

				}
				pieceCapturada = tabuleiro.removerPiece(posicaoPeao);
				piecesCapturadas.add(pieceCapturada);
				piecesNoTabuleiro.remove(pieceCapturada);
			}
		}
		return pieceCapturada;
	}

	private void desfazerMovimento(Posicao inicial, Posicao alvo, Piece pieceCapturada) {
		PieceXadrez p = (PieceXadrez) tabuleiro.removerPiece(alvo);
		p.decrencentarMoveCount();
		tabuleiro.lugarpiece(p, inicial);

		if (pieceCapturada != null) {
			tabuleiro.lugarpiece(pieceCapturada, alvo);
			piecesCapturadas.remove(pieceCapturada);
			piecesNoTabuleiro.add(pieceCapturada);
		}
		// movimento especial Roque
		// Roque pequeno
		if (p instanceof Rei && alvo.getColuna() == inicial.getColuna() + 2) {
			Posicao inicialT = new Posicao(inicial.getLinha(), inicial.getColuna() + 3);
			Posicao alvoT = new Posicao(inicial.getLinha(), inicial.getColuna() + 1);
			PieceXadrez torre = (PieceXadrez) tabuleiro.removerPiece(alvoT);
			tabuleiro.lugarpiece(torre, inicialT);
			torre.decrencentarMoveCount();
		}
		// Roque grande
		if (p instanceof Rei && alvo.getColuna() == inicial.getColuna() - 2) {
			Posicao inicialT = new Posicao(inicial.getLinha(), inicial.getColuna() - 4);
			Posicao alvoT = new Posicao(inicial.getLinha(), inicial.getColuna() - 1);
			PieceXadrez torre = (PieceXadrez) tabuleiro.removerPiece(alvoT);
			tabuleiro.lugarpiece(torre, inicialT);
			torre.decrencentarMoveCount();
		}
		// Movimento especial EnPassant
		if (p instanceof Peao) {
			if (inicial.getColuna() != alvo.getColuna() && pieceCapturada == vuneravelEnPassant) {
				PieceXadrez peao = ((PieceXadrez) tabuleiro.removerPiece(alvo));
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(3, alvo.getColuna());
				} else {
					posicaoPeao = new Posicao(4, alvo.getColuna());

				}
				tabuleiro.lugarpiece(peao, posicaoPeao);
			}
		}
	}

	private void validarPosicaoInicial(Posicao posicao) {
		if (!tabuleiro.existeUmaPiece(posicao)) {
			throw new XadrezException("Nao ha nada nessa coordenada");
		}
		if (jogadorAtual != ((PieceXadrez) tabuleiro.piece(posicao)).getCor()) {
			throw new XadrezException("Isso pertence ao adversario");

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

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PieceXadrez rei(Cor cor) {
		List<Piece> list = piecesNoTabuleiro.stream().filter(x -> ((PieceXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof Rei) {
				return (PieceXadrez) p;
			}
		}
		throw new IllegalStateException("Nao existe rei da cor " + cor + "no tabuleiro");
	}

	private boolean testeCheck(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoXadrez().toPosition();
		List<Piece> piecesOponentes = piecesNoTabuleiro.stream()
				.filter(x -> ((PieceXadrez) x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Piece p : piecesOponentes) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Cor cor) {
		if (!testeCheck(cor)) {
			return false;
		}
		List<Piece> list = piecesNoTabuleiro.stream().filter(x -> ((PieceXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < mat.length; i++) {
				for (int j = 0; j < mat.length; j++) {
					if (mat[i][j]) {
						Posicao inicial = ((PieceXadrez) p).getPosicaoXadrez().toPosition();
						Posicao alvo = new Posicao(i, j);
						Piece pieceCapturada = fazerMovimento(inicial, alvo);
						boolean testeCheck = testeCheck(cor);
						desfazerMovimento(inicial, alvo, pieceCapturada);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void lugarNovaPiece(char coluna, int linha, PieceXadrez piece) {
		tabuleiro.lugarpiece(piece, new PosicaoXadrez(coluna, linha).toPosition());
		piecesNoTabuleiro.add(piece);
	}

	private void initialSetup() {
		// branco
		lugarNovaPiece('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPiece('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		lugarNovaPiece('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		lugarNovaPiece('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		lugarNovaPiece('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		lugarNovaPiece('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		lugarNovaPiece('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		lugarNovaPiece('h', 1, new Torre(tabuleiro, Cor.BRANCO));

		lugarNovaPiece('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPiece('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPiece('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPiece('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPiece('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPiece('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPiece('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPiece('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		// preto
		lugarNovaPiece('a', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPiece('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		lugarNovaPiece('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		lugarNovaPiece('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		lugarNovaPiece('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		lugarNovaPiece('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		lugarNovaPiece('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		lugarNovaPiece('h', 8, new Torre(tabuleiro, Cor.PRETO));

		lugarNovaPiece('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPiece('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPiece('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPiece('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPiece('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPiece('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPiece('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPiece('h', 7, new Peao(tabuleiro, Cor.PRETO, this));

	}
}
