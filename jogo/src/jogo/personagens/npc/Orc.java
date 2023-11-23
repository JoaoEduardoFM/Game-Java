package jogo.personagens.npc;

import jogo.personagens.jogador.Jogador;
import jogo.util.Ator;
import jplay.URL;

public class Orc extends Ator {

	private double ataque = 1;

	public Orc(int fileName, int numeFrames) {
		// arquivo + frames
		super(URL.sprite("spriteOrc.png"), 36);
		this.setTotalDuration(2000);
		this.velocidade = 0.50;

	}

	public void perseguir(double x, double y) {

	    // Verifica se o objeto está à esquerda
	    if (this.x > x && this.y <= y + 50 && this.y >= y - 50) {
	        moveTo(x, y, velocidade);
	        if (direcao != 1) {
	            setSequence(10, 18);
	            direcao = 1;
	        }
	        movendo = true; // Indica que o objeto está se movendo.
	    } 
	    // Verifica se o objeto está à direita 
	    else if (this.x < x && this.y <= y + 50 && this.y >= -50) {
	        moveTo(x, y, velocidade);
	        if (direcao != 2) {
	            setSequence(28, 36);
	            direcao = 2;
	        }
	        movendo = true; // Indica que o objeto está se movendo.
	    } 
	    // Verifica se o objeto está acima
	    else if (this.y > y) {
	        moveTo(x, y, velocidade);
	        if (direcao != 4) {
	            setSequence(1, 9);
	            direcao = 4;
	        }
	        movendo = true; // Indica que o objeto está se movendo.
	    } 
	    // Verifica se o objeto está abaixo 
	    else if (this.y < y) {
	        moveTo(x, y, velocidade);
	        if (direcao != 5) {
	            setSequence(19, 27);
	            direcao = 5;
	        }
	        movendo = true; // Indica que o objeto está se movendo.
	    }

	    // Se o objeto está se movendo, atualiza o estado.
	    if (movendo) {
	        update();
	        movendo = false; // Redefine o indicador de movimento.
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
		}else {
			morrer();
		}
		

		if (jogador.vida <= 0) {
			System.exit(0);
		}
	}
}
