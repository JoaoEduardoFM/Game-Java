package jogo.cenario;

import jogo.personagens.jogador.Jogador;
import jogo.personagens.npc.Orc;
import jogo.personagens.npc.Zumbi;
import jogo.util.Controle;
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
	
	//private Orc orc[];

	public Cenario1(Window window) throws InterruptedException {
		janela = window;
		cena = new Scene();
		cena.loadFromFile(URL.scenario("Cenario1.scn"));
		jogador = new Jogador(540, 350);
		teclado = janela.getKeyboard();
		zumbi = (Zumbi[]) new Zumbi[1];
		//orc = (Orc[]) new Orc[1];
		// Som.play("musica1.mid");
		run();
	}

	private void run() throws InterruptedException {

		for (int i = 0; i < zumbi.length; i++) {
			zumbi[i] = new Zumbi(100 * i, 100 * i);
			//orc[i] = new Orc(150 * i, 150 * i);
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
				if (zumbi[i].vida > 0) {
					zumbi[i].perseguir(jogador.x, jogador.y);
				}				
				zumbi[i].x += cena.getXOffset();
				zumbi[i].y += cena.getXOffset();
				zumbi[i].draw();
				jogador.atirarPistola(janela, cena, teclado, zumbi[i]);
				//jogador.ataqueEspada(janela, cena, teclado, zumbi[i]);
				zumbi[i].atacar(jogador);
				
				/*
				 * if (orc[i].vida > 0) { orc[i].perseguir(jogador.x, jogador.y); } orc[i].x +=
				 * cena.getXOffset(); orc[i].y += cena.getXOffset(); orc[i].draw();
				 * jogador.atirarPistola(janela, cena, teclado, orc[i]);
				 * jogador.ataqueEspada(janela, cena, teclado, orc[i]); orc[i].atacar(jogador);
				 */
			}

			jogador.vida(janela);
			
			janela.update();
			// mudarCenario();
			Controle thread = new Controle();
			thread.threadSleeap(10);
		}
	}

	private void mudarCenario() throws InterruptedException {
		if (tileCollision(4, jogador, cena) == true) {
			new Cenario2(janela);
		}
	}

}
