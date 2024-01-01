package jogo.combate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jogo.armas.AtaqueEmArea;
import jogo.armas.Tiro;
import jogo.personagens.npc.Mob;
import jplay.Keyboard;
import jplay.Scene;
import jplay.Sound;
import jplay.URL;
import jplay.Window;

public class ControleAtaqueEmArea {

	LinkedList<AtaqueEmArea> atkArea = new LinkedList<>();

	public AtaqueEmArea atacarEmArea(double x, double y, int caminho, Scene cena) {
		AtaqueEmArea tiro = new AtaqueEmArea(x, y, caminho);
		atkArea.add(tiro);
		// adiciona animação na tela
		cena.addOverlay(tiro);
		// somAtaque();
		return tiro;
	}

	boolean ataqueMob = false;

	public void run(Mob[] mobs, Window janela, Keyboard teclado) {
		List<AtaqueEmArea> tirosToRemove = new ArrayList<>();

		for (AtaqueEmArea tiro : atkArea) {
			tiro.mover(janela, teclado, tiro);

			for (Mob inimigo : mobs) {
				if (inimigo.vidaMob > 0) {
					if (tiro.collided(inimigo)) {
						tiro.x = 10_000;
						inimigo.vidaMob -= 250;

						if (inimigo.vidaMob >= 0) {
							inimigo.morrer();
							inimigo.sofrerRecuo(50);
						}

						tirosToRemove.add(tiro);
					}
				}
			}
		}
		
		atkArea.removeAll(tirosToRemove);
	}

	private void somAtaque() {
		new Sound(URL.audio("tiro.WAV")).play();
	}
}
