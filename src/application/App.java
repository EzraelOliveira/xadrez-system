package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.PartidaXadrez;
import chess.PieceXadrez;
import chess.PosicaoXadrez;
import chess.XadrezException;

public class App {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PieceXadrez> capturadas = new ArrayList<>();
		
		while (!partidaXadrez.getCheckMate()) {
			try {
				UI.limparConsole();
				UI.printPartida(partidaXadrez, capturadas);
				System.out.println();
				System.out.print("Inicial: ");
				PosicaoXadrez inicial = UI.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(inicial);
				UI.limparConsole();
				UI.printTabuleiro(partidaXadrez.getPieces(), movimentosPossiveis);
				System.out.println();
				System.out.print("Alvo: ");
				PosicaoXadrez alvo = UI.lerPosicaoXadrez(sc);
				
				PieceXadrez pieceCapturadas = partidaXadrez.performChessMove(inicial, alvo);
				
				if(pieceCapturadas != null) {
					capturadas.add(pieceCapturadas);
				}
				if(partidaXadrez.getPromover() != null) {
					System.out.print("Digite sua de promocao (B/C/T/Q): ");
					String tipo = sc.nextLine();
					partidaXadrez.substituirPiecePromovida(tipo);
				}
			}
			catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limparConsole();
		UI.printPartida(partidaXadrez, capturadas);
	}
}
