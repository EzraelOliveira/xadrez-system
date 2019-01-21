package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Piece;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class PartidaXadrez {

	// ATTRIBUTES
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
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
		check = (testeCheck(oponente(jogadorAtual))) ? true : false;

		if (testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}else {
			proximoTurno();
		}
		return (PieceXadrez) pieceCapturada;
	}

	private Piece fazerMovimento(Posicao inicial, Posicao alvo) {
		PieceXadrez p = (PieceXadrez)tabuleiro.removerPiece(inicial);
		p.acrescentarMoveCount();
		Piece pieceCapturada = tabuleiro.removerPiece(alvo);
		tabuleiro.lugarpiece(p, alvo);
		if (pieceCapturada != null) {
			piecesNoTabuleiro.remove(pieceCapturada);
			piecesCapturadas.add(pieceCapturada);
		}
		return pieceCapturada;
	}

	private void desfazerMovimento(Posicao inicial, Posicao alvo, Piece pieceCapturada) {
		PieceXadrez p = (PieceXadrez)tabuleiro.removerPiece(alvo);
		p.decrencentarMoveCount();
		tabuleiro.lugarpiece(p, inicial);

		if (pieceCapturada != null) {
			tabuleiro.lugarpiece(pieceCapturada, alvo);
			piecesCapturadas.remove(pieceCapturada);
			piecesNoTabuleiro.add(pieceCapturada);
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
		lugarNovaPiece('h', 7, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPiece('d', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPiece('e', 1, new Rei(tabuleiro, Cor.BRANCO));

		lugarNovaPiece('b', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPiece('a', 8, new Rei(tabuleiro, Cor.PRETO));
	}
}
