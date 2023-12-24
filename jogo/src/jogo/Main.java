package jogo;

import jogo.util.Menu;
import jplay.GameImage;
import jplay.Keyboard;
import jplay.URL;
import jplay.Window;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// resolucao do jogo
		Window janela = new Window(1280, 720);

		// imagem do menu
		Keyboard teclado = janela.getKeyboard();

		try {
			Menu.manuLogicainicial(janela, teclado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static GameImage menu(String menuImage) {
		return new GameImage(URL.sprite(menuImage));
	}

	public static void shutDown(Window janela) {
		janela.exit(); // Fecha a janela
	}

}
