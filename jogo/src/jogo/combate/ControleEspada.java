package jogo.combate;

import java.util.LinkedList;

import jogo.armas.Espada;
import jogo.util.Ator;
import jplay.Scene;
import jplay.Sound;
import jplay.URL;

public class ControleEspada {

	LinkedList<Espada> listEspada = new LinkedList<>();

	public void adicionaEspada(double x, double y, int caminho, Scene cena) {
		Espada espada = new Espada(x, y, caminho);
		listEspada.add(espada);
		// adiciona tiro da tela
		cena.addOverlay(espada);
		somDisparo();
	}

	public void run(Ator inimigo) throws InterruptedException {
		for (int i = 0; i < listEspada.size(); i++) {
			Espada espada = listEspada.removeFirst();
			espada.mover();
			listEspada.addLast(espada);

			// Colisao do tiro
			if (espada.collided(inimigo)) {
				espada.x = 10_000;
				inimigo.vida -= 250;
				inimigo.sofrerRecuo();
				listEspada.removeFirst();
			}
			
			if(espada.getAtingiuLimite()){
				espada.x = 10_000;
				listEspada.removeFirst();
			}
		}

	}

	private void somDisparo() {
		new Sound(URL.audio("espada.wav")).play();
	}
}
