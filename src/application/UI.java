package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.Cor;
import chess.PartidaXadrez;
import chess.PieceXadrez;
import chess.PosicaoXadrez;

//USER INTERFACE
public class UI {

	/*
	 * C�digo abaixo pego no StackOverFlow aplica cores e fundos aos caract�res
	 * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-
	 * using-system-out-println
	 */

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// METHODS
	public static void limparConsole() {
		/*
		 * C�digo pego no StackOverFlow serve para limpar o console
		 * https://stackoverflow.com/questions/2979383/java-clear-the-console
		 */
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Somente sera aceito coordenadas de a1 a h8");
		}
	}

	public static void printPartida(PartidaXadrez partidaXadrez, List<PieceXadrez> capturadas) {
		printTabuleiro(partidaXadrez.getPieces());
		System.out.println();
		printPiecesCapturadas(capturadas);
		System.out.println();
		System.out.println("Turno: " + partidaXadrez.getTurno());

		if (!partidaXadrez.getCheckMate()) {
			System.out.println("Esperando a jogada do jogador: " + partidaXadrez.getJogadorAtual());
			if (partidaXadrez.getCheck()) {
				System.out.println("CHECK!");
			}
		} else {
			System.out.println("CHECKMATE!");
			System.out.println("Vencedor: " + partidaXadrez.getJogadorAtual());
		}
	}

	public static void printTabuleiro(PieceXadrez[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printTabuleiro(PieceXadrez[][] pieces, boolean[][] movimentosPossiveis) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPiecesCapturadas(List<PieceXadrez> capturadas) {
		List<PieceXadrez> branco = capturadas.stream().filter(x -> x.getCor() == Cor.BRANCO)
				.collect(Collectors.toList());
		List<PieceXadrez> preto = capturadas.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
		System.out.println("Capturadas: ");
		System.out.println("Brancas: ");
		System.out.println(ANSI_WHITE);
		System.out.println(Arrays.toString(branco.toArray()));
		System.out.println(ANSI_RESET);
		System.out.println("Pretas: ");
		System.out.println(ANSI_YELLOW);
		System.out.println(Arrays.toString(preto.toArray()));
		System.out.println(ANSI_RESET);
	}

	private static void printPiece(PieceXadrez piece, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (piece.getCor() == Cor.BRANCO) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
}
