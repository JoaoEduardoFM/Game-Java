package jogo.personagens.npc;

import jogo.personagens.jogador.Jogador;
import jogo.util.Ator;
import jplay.URL;

public class Mob extends Ator {

	private double ataque = 1;
	private double velocidade = 1;
	public double vidaMob = 250;
	private long tempoInicial = System.currentTimeMillis();

	public Mob(double x, double y, String sprite) {
		// arquivo + frames
		super(URL.sprite(sprite), 20);
		this.x = x;
		this.y = y;
		this.setTotalDuration(2000);
		this.velocidade = 0.3;

	}

	public void perseguir(double x, double y) {

		if (esquerda(x, y) && !direita(x, y)) {
			moveTo(x, y, velocidade);
			if (direcao != 1) {
				setSequence(5, 8);
				direcao = 1;
			}
			movendo = true;
		} else if (paraBaixo(y) && !paraCima(y) && !esquerda(x, y) && !direita(x, y)) {
			moveTo(x, y, velocidade);
			if (direcao != 5) {
				setSequence(1, 4);
				direcao = 5;
			}
			movendo = true;

		} else if (direita(x, y) && !esquerda(x, y)) {
			moveTo(x, y, velocidade);
			if (direcao != 2) {
				setSequence(8, 12);
				direcao = 2;
			}
			movendo = true;
		} else if (paraCima(y) && !paraBaixo(y) && !direita(x, y)) {
			moveTo(x, y, velocidade);
			if (direcao != 4) {
				setSequence(12, 16);
				direcao = 4;
			}
			movendo = true;
		}

		if (movendo) {
			update();
			movendo = false;
		}
	}

	public void morrer() {
		long tempoAtual = System.currentTimeMillis();
		long tempoDecorrido = (tempoAtual - getTempoInicial()) / 1000;
		if (vidaMob <= 0) {
			setSequence(19, 20);
			this.ataque = 0;
			this.velocidade = 0;
			this.direcao = 0;
			movendo = false;
			// Ao morrer o mob é teleportado
			if (tempoDecorrido > 3) {
				hide();
				x = -10_000_000;
			}
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

	private boolean esquerda(double x, double y) {
		return this.x > x && this.y <= y + 50 && this.y >= y - 60;
	}

	private boolean direita(double x, double y) {
		return this.x < x && this.y < y + 50 && this.y >= y - 60;
	}

	private boolean paraCima(double y) {
		return this.y > y;
	}

	private boolean paraBaixo(double y) {
		return this.y < y;
	}

	public long getTempoInicial() {
		return tempoInicial;
	}

	public void setTempoInicial(long tempoInicial) {
		this.tempoInicial = tempoInicial;
	}

}