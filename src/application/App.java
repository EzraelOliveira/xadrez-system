package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.XadrezException;
import chess.PartidaXadrez;
import chess.PieceXadrez;
import chess.PosicaoXadrez;

public class App {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while (true) {
			try {
				UI.limparConsole();
				UI.printPartida(partidaXadrez);
				System.out.println();
				System.out.print("Inicial: ");
				PosicaoXadrez inicial = UI.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(inicial);
				UI.limparConsole();
				UI.printTabuleiro(partidaXadrez.getPieces(), movimentosPossiveis);
				System.out.println();
				System.out.print("Alvo: ");
				PosicaoXadrez alvo = UI.lerPosicaoXadrez(sc);
				
				PieceXadrez capturedPiece = partidaXadrez.performChessMove(inicial, alvo);
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
	}
}
