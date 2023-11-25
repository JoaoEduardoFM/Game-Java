package jogo.cenario;

import jogo.personagens.jogador.Jogador;
import jogo.personagens.npc.Mob;
import jplay.Keyboard;
import jplay.Scene;
import jplay.URL;
import jplay.Window;

public class Cenario1 extends Cenario {

	private Window janela;

	private Scene cena;

	private Jogador jogador;

	private Keyboard teclado;

	private Mob esqueleto;

	public Cenario1(Window window) {
		janela = window;
		cena = new Scene();
		cena.loadFromFile(URL.scenario("Cenario1.scn"));
		jogador = new Jogador(540, 350);
		teclado = janela.getKeyboard();

		// Som.play("musica1.mid");
		run();
	}

	private void run() {

		esqueleto = new Mob(300, 300, "esqueleto.png");

		while (true) {
			// controlador jogador
			jogador.controle(janela, teclado);
			jogador.caminho(cena);
			cena.moveScene(jogador);
			jogador.x += cena.getXOffset();
			jogador.y += cena.getXOffset();
			jogador.atirarPistola(janela, cena, teclado, esqueleto);
			jogador.draw();
			esqueleto.caminho(cena);
			esqueleto.perseguir(jogador.x, jogador.y);
			esqueleto.x += cena.getXOffset();
			esqueleto.y += cena.getXOffset();
			esqueleto.morrer();
			esqueleto.atacar(jogador, esqueleto);
			esqueleto.draw();
			
			

			esqueleto.vida(janela);
			jogador.vida(janela);
			janela.update();
			//mudarCenario();
			try {
				Thread.sleep(05);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * private void mudarCenario() { if (tileCollision(4, jogador, cena) == true) {
	 * new Cenario(janela); } }
	 */

}
