package application;

import chess.PartidaXadrez;

public class App {
	public static void main(String[] args) {

		PartidaXadrez partidaXadrez = new PartidaXadrez();
		UI.printBoard(partidaXadrez.getPieces());
	}

}
 