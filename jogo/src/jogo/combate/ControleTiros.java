package jogo.combate;

import java.util.LinkedList;

import jogo.armas.Tiro;
import jogo.util.Ator;
import jplay.Scene;
import jplay.Sound;
import jplay.URL;

public class ControleTiros {

	LinkedList<Tiro> tiros = new LinkedList<>();

	public void adicionaTiro(double x, double y, int caminho, Scene cena) {
		Tiro tiro = new Tiro(x, y, caminho);
		tiros.add(tiro);
		// adiciona tiro da tela
		cena.addOverlay(tiro);
		somDisparo();
	}

	public void run(Ator inimigo) throws InterruptedException {
		for (int i = 0; i < tiros.size(); i++) {
			Tiro tiro = tiros.removeFirst();
			tiro.mover();
			tiros.addLast(tiro);

			// Colisao do tiro
			if (tiro.collided(inimigo)) {
				tiro.x = 10_000;
				inimigo.energia -= 250;
				inimigo.sofrerRecuo();
				inimigo.sofrerRecuo();
				tiros.removeFirst();
			}
		}

	}

	private void somDisparo() {
		new Sound(URL.audio("tiro.WAV")).play();
	}
}
