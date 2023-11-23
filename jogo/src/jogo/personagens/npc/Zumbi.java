package jogo.personagens.npc;

import jogo.personagens.jogador.Jogador;
import jogo.util.Ator;
import jplay.URL;

public class Zumbi extends Ator {

	private double ataque = 1;

	public Zumbi(int fileName, int numeFrames) {
		// arquivo + frames
		super(URL.sprite("Zumbi00.png"), 16);
		this.setTotalDuration(2000);
		this.velocidade = 0.50;

	}

	public void perseguir(double x, double y) {

		if (this.x > x && this.y <= y + 50 && this.y >= y - 50) {
			moveTo(x, y, velocidade);
			if (direcao != 1) {
				setSequence(5, 8);
				direcao = 1;
			}
			movendo = true;
		} else if (this.x < x && this.y <= y + 50 && this.y >= -50) {
			moveTo(x, y, velocidade);
			if (direcao != 2) {
				setSequence(9, 12);
				direcao = 2;
			}
			movendo = true;
		} else if (this.y > y) {
			moveTo(x, y, velocidade);
			if (direcao != 4) {
				setSequence(13, 16);
				direcao = 4;
			}
			movendo = true;
		} else if (this.y < y) {
			moveTo(x, y, velocidade);
			if (direcao != 5) {
				setSequence(1, 4);
				direcao = 5;
			}
			movendo = true;
		}

		if (movendo) {
			update();
			movendo = false;
		}
	}

	public void morrer() {
		if (vida <= 0) {
			this.velocidade = 0;
			this.direcao = 0;
			this.movendo = false;
			this.ataque = 0;
		}
	}

	@SuppressWarnings("static-access")
	public void atacar(Jogador jogador) throws InterruptedException {
		if (this.collided(jogador)) {
			jogador.vida -= this.ataque;
			jogador.sofrerRecuo();
		} else {
			morrer();
		}

		if (jogador.vida <= 0) {
			System.exit(0);
		}
	}
	
	public void colisaoMob(Zumbi zumbi) throws InterruptedException {
		if (this.collided(zumbi)) {
			zumbi.sofrerRecuoMob();
		} else {
			morrer();
		}

	}
}
