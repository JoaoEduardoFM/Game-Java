package jogo.cenario;

import jogo.personagens.jogador.Jogador;
import jplay.Keyboard;
import jplay.Scene;
import jplay.URL;
import jplay.Window;

public class Cenario2 extends Cenario{

	private Window janela;

	private Scene cena;

	private Jogador jogador;

	private Keyboard teclado;

	public Cenario2(Window window) {
		janela = window;
		cena = new Scene();
		cena.loadFromFile(URL.scenario("Cenario2.scn"));
		jogador = new Jogador(140, 150);
		teclado = janela.getKeyboard();
		run();
	}

	private void run() {
		while (true) {
			
			// controlador jogador
			jogador.controle(janela, teclado);
			jogador.caminho(cena);
			cena.moveScene(jogador);
			jogador.x += cena.getXOffset();
			jogador.y += cena.getXOffset();
			jogador.atirarPistola(janela, cena, teclado, null);
			jogador.draw();
			janela.update();

		}
	}
}
