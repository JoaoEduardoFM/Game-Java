package jogo.personagens.npc;

import jogo.personagens.jogador.Jogador;
import jogo.util.Ator;
import jplay.URL;

public class Mob extends Ator {

	private double ataque = 1;
	private double velocidade = 1;
	public double vidaMob = 250;
	public Integer pontos = 0;
	private long tempoInicial = System.currentTimeMillis();
	private boolean isDead = false;
	private boolean morto;

	public Mob(int x, int y, String sprite) {
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

		public Boolean morrer() {
		    if (vidaMob <= 0) {
		        long tempoAtual = System.currentTimeMillis();
		        long tempoDecorrido = (tempoAtual - tempoInicial) / 5000; // convert to seconds
	
		        if (!isDead) {
		            isDead = true;  // Set a flag to ensure points are added only once
		            pontos += 1;  
		            System.out.println(pontos);
		        }else {
		        	isDead = false;
		        }
	
		        moveTo(x, y, velocidade);
		        setSequence(19, 20);
		        update();
		        this.ataque = 0;
		        this.velocidade = 0;
		        this.direcao = 0;
		        movendo = false;
	
		        // When the mob dies, teleport it after 6 seconds
		        if (tempoDecorrido > 6) {
		            hide();
		            x = -10_000_000;
		        }
		    }else {
		    	isDead = false;
		    }
			return isDead;
		}


	boolean ataqueMob = false;

	public void atacar(Jogador jogador, Mob mob) {
	    if (this.collided(jogador)) {
	        if (vidaMob > 0) {
	            ataqueMob = true;
	            vida -= this.ataque;
	        }

	        if (ataqueMob) {
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
	
	// Construtor e outros métodos...

		public boolean estaMorto() {
			return morto;
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

	public Integer getPontos() {
		return pontos;
	}

	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}
	
	

}