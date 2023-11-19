package jogo.cenario;

import jogo.personagens.jogador.Jogador;
import jogo.personagens.zumbi.Zumbi;
import jplay.Keyboard;
import jplay.Scene;
import jplay.URL;
import jplay.Window;

public class Cenario1 extends Cenario {

	private Window janela;

	private Scene cena;

	private Jogador jogador;

	private Keyboard teclado;

	private Zumbi zumbi[];

	public Cenario1(Window window) {
		janela = window;
		cena = new Scene();
		cena.loadFromFile(URL.scenario("Cenario1.scn"));
		jogador = new Jogador(540, 350);
		teclado = janela.getKeyboard();
		zumbi = (Zumbi[]) new Zumbi[1];
		// Som.play("musica1.mid");
		run();
	}

	private void run() {

		for (int i = 0; i < zumbi.length; i++) {
			zumbi[i] = new Zumbi(100 * i, 100 * i);

		}

		while (true) {
			// controlador jogador
			jogador.controle(janela, teclado);
			jogador.caminho(cena);
			cena.moveScene(jogador);
			jogador.x += cena.getXOffset();
			jogador.y += cena.getXOffset();

			jogador.draw();

			for (int i = 0; i < zumbi.length; i++) {
				zumbi[i].caminho(cena);
				zumbi[i].perseguir(jogador.x, jogador.y);
				zumbi[i].x += cena.getXOffset();
				zumbi[i].y += cena.getXOffset();
				zumbi[i].draw();
				jogador.atirar(janela, cena, teclado, zumbi[i]);
				jogador.ataqueEspada(janela, cena, teclado, zumbi[i]);
				zumbi[i].morrer();
				zumbi[i].atacar(jogador);
			}

			jogador.vida(janela);
			janela.update();
			mudarCenario();
			try {
				Thread.sleep(05);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void mudarCenario() {
		if (tileCollision(4, jogador, cena) == true) {
			new Cenario2(janela);
		}
	}

}
