package jogo.personagens.npc;

import jogo.personagens.jogador.Jogador;
import jogo.util.Ator;
import jplay.URL;

public class Mob extends Ator {

	private double ataque = 1;
	private double velocidade = 1;
	public double vidaMob = 1000;

	public Mob(double x, double y, String sprite) {
		// arquivo + frames
		super(URL.sprite(sprite), 20);
		this.setTotalDuration(2000);
		this.velocidade = 0.3;
		this.x = x;
		this.y = y;
	}

	public void perseguir(double x, double y) {

		if (this.x > x && this.y <= y + 50 && this.y >= y - 60) {
			moveTo(x, y, velocidade);
			if (direcao != 1) {
				setSequence(5, 8);
			}
			movendo = true;
		} else if (this.x < x && this.y <= y + 50 && this.y >= -60) {
			moveTo(x, y, velocidade);
			if (direcao != 2) {
				setSequence(8, 12);
				direcao = 2;
			}
			movendo = true;
		} else if (this.y > y) {
			moveTo(x, y, velocidade);
			if (direcao != 4) {
				setSequence(12, 16);
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

	boolean visible = false;

	public void morrer() {
		if (vidaMob <= 0) {
			setSequence(19, 20);
			this.ataque = 0;
			this.velocidade = 0;
			this.direcao = 0;
			movendo = false;
			// x = -10_000_000;
		}
	}

	boolean ataqueMob = false;

	public void atacar(Jogador jogador, Mob mob) {
		if (this.collided(jogador)) {

			if (vidaMob > 0) {
				ataqueMob = true;
				vida -= this.ataque;
			}

			if (ataqueMob = true) {
				ataqueMob = false;
			}
		}
	}

	public void sofrerRecuo(double recuo) {
		double recoilX = 0;
		double recoilY = 0;

		switch (direcao) {
		case 1:
			recoilX = recuo;
			break;
		case 2:
			recoilX = -recuo;
			break;
		case 4:
			recoilY = recuo;
			break;
		case 5:
			recoilY = -recuo;
			break;
		}

		x += recoilX;
		y += recoilY;
	}
}