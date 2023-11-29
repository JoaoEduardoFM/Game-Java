package jogo.combate;

import java.util.LinkedList;

import jogo.armas.Tiro;
import jogo.personagens.npc.Mob;
import jplay.Keyboard;
import jplay.Scene;
import jplay.Sound;
import jplay.URL;
import jplay.Window;

public class ControleTiros {

	LinkedList<Tiro> tiros = new LinkedList<>();
	private boolean npcMorto = false;

	public Tiro adicionaTiro(double x, double y, int caminho, Scene cena) {
		Tiro tiro = new Tiro(x, y, caminho);
		tiros.add(tiro);
		// adiciona tiro da tela
		cena.addOverlay(tiro);
		somDisparo();
		return tiro;
	}

	boolean ataqueMob = false;

	public void run(Mob[] mobs, Window janela, Keyboard teclado) throws InterruptedException {
		for (int i = 0; i < tiros.size(); i++) {
			Tiro tiro = tiros.removeFirst();
			tiro.mover(janela, teclado, tiro);
			tiros.addLast(tiro);

			// Colisao do tiro
			for (Mob inimigo : mobs) {
				// Colisao do tiro com o inimigo
				if (tiro.collided(inimigo)) {
					tiro.x = 10_000;
					if (inimigo.vidaMob > 0) {
						inimigo.vidaMob -= 250;
						ataqueMob = true;

						// Verificar morte apenas do mob atingido
						if (inimigo.vidaMob >= 0) {
							npcMorto = true;
						}
					}

					if (npcMorto) {
						inimigo.morrer();
						npcMorto = false;
					}
				}

				// Aplicar recuo apenas ao mob atingido
				if (ataqueMob) {
					inimigo.sofrerRecuo(7);
					ataqueMob = false;
				}
			}

			if (tiro.getAtingiuLimite()) {
				tiros.removeFirst();
			}
		}
	}

	private void somDisparo() {
		new Sound(URL.audio("tiro.WAV")).play();
	}
}
