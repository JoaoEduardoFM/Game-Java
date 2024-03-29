package jogo.combate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jogo.armas.Ataque;
import jogo.personagens.npc.Mob;
import jplay.Keyboard;
import jplay.Scene;
import jplay.Sound;
import jplay.URL;
import jplay.Window;

public class ControleTiros {

	LinkedList<Ataque> tiros = new LinkedList<>();
	private  long  tempoInicialCenario = System . currentTimeMillis ();

	public Ataque adicionaTiro(double x, double y, int caminho, Scene cena, String sprite) {
		Ataque tiro = new Ataque(x, y, caminho, sprite);
		tiros.add(tiro);
		// adiciona tiro da tela
		cena.addOverlay(tiro);
		// somDisparo();
		return tiro;
	}

	boolean ataqueMob = false;

	public void run(Mob[] mobs, Window janela, Keyboard teclado, String sprite) {
		List<Ataque> tirosToRemove = new ArrayList<>();
		long tempoAtual = System . currentTimeMillis ();
		long  tempoDecorrido =( tempoAtual - tempoInicialCenario )/ 1000 ; //converte para segundos

		for (Ataque tiro : tiros) {
			tiro.mover(janela, teclado, tiro);

			for (Mob inimigo : mobs) {
				if (inimigo.vidaMob > 0) {
					if (tiro.collided(inimigo)) {
						if(sprite == "iceAtak.png") {
							if ( tempoDecorrido > 2 ) {
								inimigo.velocidade = 1;
							}else {
								inimigo.velocidade = 0;
							}
						}
						tiro.x = 10_000;
						inimigo.vidaMob -= 250;
						if (inimigo.vidaMob >= 0) {
							inimigo.morrer();
							inimigo.sofrerRecuo(7);
						}

						tirosToRemove.add(tiro);
					}
				}
			}
		}

		// Remove bullets after processing collisions
		tiros.removeAll(tirosToRemove);
	}

	private void somDisparo() {
		new Sound(URL.audio("tiro.WAV")).play();
	}
}
