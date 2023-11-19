package jogo.personagens.jogador;

import java.awt.Color;
import java.awt.event.KeyEvent;

import jogo.armas.Espada;
import jogo.combate.ControleEspadas;
import jogo.combate.ControleTiros;
import jogo.util.Ator;
import jplay.Keyboard;
import jplay.Scene;
import jplay.URL;
import jplay.Window;

public class Jogador extends Ator {
	
	public static double energia = 1000;

	public Jogador(int x, int y) {
		super(URL.sprite("jogador.png"), 20);
		this.x = x;
		this.y = y;
		this.setTotalDuration(2000);
	}

	ControleTiros tiros = new ControleTiros();
	Espada espada = new Espada(direcao);
	private ControleEspadas controleEspadas = new ControleEspadas();

	public void atirar(Window janela, Scene cena, Keyboard teclado, Ator inimigo) {

		if (teclado.keyDown(KeyEvent.VK_A)) {
			tiros.adicionaTiro(x + 5, y + 12, direcao, cena);
		}
		tiros.run(inimigo);
	}
	
	public void ataqueEspada(Window janela, Scene cena, Keyboard teclado, Ator inimigo) {
	    if (teclado.keyDown(KeyEvent.VK_SPACE)) {
	        controleEspadas.adicionaEspada(x, y, direcao);
	    }

	    controleEspadas.run(inimigo);
	}

	public void controle(Window janela, Keyboard teclado) {

		// movendo para esquerda
		if (teclado.keyDown(Keyboard.LEFT_KEY)) {
			if (this.x > 0) {
				this.x -= valocidade;// impede que não saia da jenela
			}
			if (direcao != 1) {
				setSequence(4, 8);// sprite 4 e 8 do personagem
				direcao = 1;
			}
			movendo = true;
			// movendo para direta
		} else if (teclado.keyDown(Keyboard.RIGHT_KEY) == true) {
			if (this.x < janela.getWidth() - 60) {
				this.x += valocidade;
			}
			if (direcao != 2) {
				setSequence(8, 11);
				direcao = 2;
			}
			movendo = true;
			// movendo para cima
		} else if (teclado.keyDown(Keyboard.UP_KEY) == true) {
			if (this.y > 0) {
				this.y -= valocidade;
			}
			if (direcao != 4) {
				setSequence(12, 16);
				direcao = 4;
			}
			movendo = true;

			// movendo para baixo
		} else if (teclado.keyDown(Keyboard.DOWN_KEY) == true) {
			if (this.y < janela.getHeight() - 60) {
				this.y += valocidade;
			}
			if (direcao != 5) {
				setSequence(0, 4);
				direcao = 5;
			}
			movendo = true;

		}

		if (movendo) {
			// atualiza o frame caso o movendo seja true
			update();
			movendo = false;
		}
	}
	
	public void vida(Window janela) {
		janela.drawText("Vida" + Jogador.energia , 30, 30, Color.BLUE);
	}

	// Controle de caminho que não irá permitir que utrapasse blqueios

}
