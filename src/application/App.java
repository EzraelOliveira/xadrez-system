package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessPiece;
import chess.PartidaXadrez;
import chess.XadrezException;
import chess.XadrezPosicao;

public class App {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		while(true) {
			try {
				UI.limparConsole();
				UI.printBoard(partidaXadrez.getPieces());
				System.out.println();
				System.out.println("Origem: ");
				XadrezPosicao inicial = UI.lerXadrezPosicao(sc);
				
				boolean[][] movimentosPossiveis = partidaXadrez.MovimentosPossiveis(inicial);
				System.out.println();
				System.out.println("Alvo: ");
				XadrezPosicao target = UI.lerXadrezPosicao(sc);
				
				ChessPiece pieceCapturada =  partidaXadrez.movimentoXadrez(inicial, target);
			} 
			catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}
 