package jogo;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import jogo.cenario.Cenario1;
import jplay.GameImage;
import jplay.Keyboard;
import jplay.URL;
import jplay.Window;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// resolucao do jogo
		// Window janela = new Window(800, 600);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		DisplayMode mode = gd.getDisplayMode();

		Window janela = new Window(mode.getWidth(), mode.getHeight());

		// imagem do menu
		GameImage plano = new GameImage(URL.sprite("menu.png"));
		Keyboard teclado = janela.getKeyboard();

		while (true) {
			plano.draw();// printa a tela
			janela.update();// atualiza em quanto percorre o loop

			if (teclado.keyDown(Keyboard.ENTER_KEY)) {
				new Cenario1(janela);
			}
		}
	}

}
