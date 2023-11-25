package jogo.personagens.npc;

import java.awt.Color;

import jogo.personagens.jogador.Jogador;
import jogo.util.Ator;
import jplay.URL;
import jplay.Window;

public class Mob extends Ator {

	private double ataque = 1;
	private double velocidade = 1;
	public double vidaMob = 1000;

	public Mob(int fileName, int numeFrames, String sprite) {
		// arquivo + frames
		super(URL.sprite(sprite), 20);
		this.setTotalDuration(2000);
		this.velocidade = 0.3;
	}

	public void perseguir(double x, double y) {

		if (this.x > x && this.y <= y + 50 && this.y >= y - 50) {
			moveTo(x, y, velocidade);
			if (direcao != 1) {
				setSequence(5, 8);
			}
			movendo = true;
		} else if (this.x < x && this.y <= y + 50 && this.y >= -50) {
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

	public void morrer() {

		if (vidaMob <= 0) {
			setSequence(19, 20);
			this.ataque = 0;
			this.velocidade = 0;
			this.direcao = 0;
			movendo = false;
		}
	}

	public void atacar(Jogador jogador, Mob mob) {
		if (this.collided(jogador)) {
			if (vida > 0) {
				vida -= this.ataque;
			}
		}
	}

	public void vida(Window janela) {
		janela.drawText("Vida Mob: " + vidaMob, 30, 60, Color.RED);
	}
}