package jogo;

import java.util.LinkedList;

import jplay.Sound;
import jplay.URL;

public class ControleEspadas {

	LinkedList<Espada> espadas = new LinkedList<>();

	public void adicionaEspada(double x, double y, int direcao) {
		Espada espada = new Espada(direcao);
		espada.x = x;
		espada.y = y;
		espadas.add(espada);
		somAtaqueEspada();
	}

	public void run(Ator inimigo) {
		for (int i = 0; i < espadas.size(); i++) {
			Espada espada = espadas.removeFirst();
			espada.update();
			espadas.addLast(espada);

			if (espada.collided(inimigo)) {
				espada.x = 10_000;
				inimigo.energia -= 250;
			}
		}
	}

	private void somAtaqueEspada() {
		new Sound(URL.audio("ataqueEspada.wav")).play();
	}
}
