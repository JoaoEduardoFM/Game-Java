package jogo;

import jogo.cenario.Pantano;
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

		GameImage menuStart = menu("menu1Start.png");
		GameImage menuOptions = menu("menu1Options.png");
		GameImage menuExit = menu("menu1Exit.png");

		GameImage currentMenu = menuStart;

		while (true) {
			currentMenu.draw();
			janela.update();

			if (teclado.keyDown(Keyboard.DOWN_KEY)) {
				if (currentMenu == menuStart) {
					currentMenu = menuOptions;
				} else if (currentMenu == menuOptions) {
					currentMenu = menuExit;
				} else if (currentMenu == menuExit) {
					currentMenu = menuStart;
				}

				while (teclado.keyDown(Keyboard.DOWN_KEY)) {
					Thread.sleep(10);
				}
			} else if (teclado.keyDown(Keyboard.UP_KEY)) {

				if (currentMenu == menuStart) {
					currentMenu = menuExit;
				}
				if (currentMenu == menuOptions) {
					currentMenu = menuStart;
				}
				if (currentMenu == menuExit) {
					currentMenu = menuOptions;
				}

				while (teclado.keyDown(Keyboard.UP_KEY)) {
					Thread.sleep(10);
				}
			}

			if (teclado.keyDown(Keyboard.ENTER_KEY)) {
				if (currentMenu == menuStart) {
					new Pantano(janela);
				} else if (currentMenu == menuOptions) {
					// logica options
				} else if (currentMenu == menuExit) {
					System.exit(0);
				}

			}
		}
	}

	public static GameImage menu(String menuImage) {
		return new GameImage(URL.sprite(menuImage));
	}

	public static void shutDown(Window janela) {
		janela.exit(); // Fecha a janela
	}

}
