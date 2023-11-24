package jogo.combate;

import java.util.LinkedList;

import jogo.armas.Tiro;
import jogo.util.Ator;
import jplay.Keyboard;
import jplay.Scene;
import jplay.Sound;
import jplay.URL;
import jplay.Window;

public class ControleTiros {

	LinkedList<Tiro> tiros = new LinkedList<>();

	public void adicionaTiro(double x, double y, int caminho, Scene cena) {
		Tiro tiro = new Tiro(x, y, caminho);
		tiros.add(tiro);
		// adiciona tiro da tela
		cena.addOverlay(tiro);
		somDisparo();
	}

	public void run(Ator inimigo, Window janela, Keyboard teclado) throws InterruptedException {
		for (int i = 0; i < tiros.size(); i++) {
			Tiro tiro = tiros.removeFirst();
			tiro.mover(janela, teclado, tiro);
			tiros.addLast(tiro);

			// Colisao do tiro
			if (tiro.collided(inimigo)) {
				tiro.x = 10_000;
				inimigo.vida -= 250;
				inimigo.sofrerRecuo();
				tiros.removeFirst();
			}
			
			if(tiro.getAtingiuLimite()){
				tiro.x = 10_000;
				tiros.removeFirst();
			}
		}

	}

	private void somDisparo() {
		new Sound(URL.audio("tiro.WAV")).play();
	}
}
