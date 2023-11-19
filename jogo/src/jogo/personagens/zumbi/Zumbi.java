package jogo.personagens.zumbi;

import jogo.personagens.jogador.Jogador;
import jogo.util.Ator;
import jplay.URL;

public class Zumbi extends Ator {

	private double ataque = 1;

	public Zumbi(int fileName, int numeFrames) {
		// arquivo + frames
		super(URL.sprite("Zumbi00.png"), 16);
		this.x = x;
		this.y = y;
		this.setTotalDuration(2000);
		this.valocidade = 0.3;

	}

	public void perseguir(double x, double y) {

		if (this.x > x && this.y <= y + 50 && this.y >= y - 50) {
			moveTo(x, y, valocidade);
			if (direcao != 1) {
				setSequence(5, 8);
			}
			movendo = true;
		} else if (this.x < x && this.y <= y + 50 && this.y >= -50) {
			moveTo(x, y, valocidade);
			if (direcao != 2) {
				setSequence(9, 12);
				direcao = 2;
			}
			movendo = true;
		} else if (this.y > y) {
			moveTo(x, y, valocidade);
			if (direcao != 4) {
				setSequence(13, 16);
				direcao = 4;
			}
			movendo = true;
		} else if (this.y < y) {
			moveTo(x, y, valocidade);
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

		if (energia <= 0) {
			this.valocidade = 0;
			this.direcao = 0;
			this.movendo = false;
			this.x = 1_0000_000;
		}
	}

	public void atacar(Jogador jogador) {
		if(this.collided(jogador)) {
			jogador.energia -= this.ataque;
		}
		
		if(jogador.energia <=0) {
			System.exit(0);
		}
	}
}
