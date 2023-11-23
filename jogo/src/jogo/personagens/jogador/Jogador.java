package jogo.personagens.jogador;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import jogo.combate.ControleEspada;
import jogo.combate.ControleTiros;
import jogo.util.Ator;
import jplay.Keyboard;
import jplay.Scene;
import jplay.URL;
import jplay.Window;

public class Jogador extends Ator {

	public double vidaJogador = 1000;

	private long ultimoDisparo = 0;
	private long delayEntreTiros = 700;

	public Jogador(int x, int y) {
		super(URL.sprite("jogador.png"), 20);
		this.x = x;
		this.y = y;
		this.setTotalDuration(2000);
	}

	ControleTiros tiros = new ControleTiros();
	ControleEspada espada = new ControleEspada();

	public void atirarPistola(Window janela, Scene cena, Keyboard teclado, Ator inimigo) throws InterruptedException {
		// Verifica se a tecla "A" está pressionada e se o tempo desde o último disparo
		// é maior que o delay
		if (teclado.keyDown(KeyEvent.VK_A) && System.currentTimeMillis() - ultimoDisparo > delayEntreTiros) {
			tiros.adicionaTiro(x + 5, y + 12, direcao, cena);
			ultimoDisparo = System.currentTimeMillis(); // Atualiza o tempo do último disparo
		}

		tiros.run(inimigo);
	}
	
	public void ataqueEspada(Window janela, Scene cena, Keyboard teclado, Ator inimigo) throws InterruptedException {
		// Verifica se a tecla "A" está pressionada e se o tempo desde o último disparo
		// é maior que o delay
		if (teclado.keyDown(KeyEvent.VK_S) && System.currentTimeMillis() - ultimoDisparo > delayEntreTiros) {
			espada.adicionaEspada(x + 5, y + 12, direcao, cena);
			ultimoDisparo = System.currentTimeMillis(); // Atualiza o tempo do último disparo
		}
		espada.run(inimigo);
	}

	public void controle(Window janela, Keyboard teclado) {

		
		if (teclado.keyDown(Keyboard.LEFT_KEY) || teclado.keyDown(Keyboard.RIGHT_KEY)
				|| teclado.keyDown(Keyboard.UP_KEY) || teclado.keyDown(Keyboard.DOWN_KEY)) {
			correrLogica(janela, teclado);
		}

		// movendo para esquerda
		if (teclado.keyDown(Keyboard.LEFT_KEY)) {
			if (this.x > 0) {
				this.x -= velocidade;// impede que não saia da jenela
			}
			if (direcao != 1) {
				setSequence(4, 8);// sprite 4 e 8 do personagem
				direcao = 1;

			}
			movendo = true;
			// movendo para direta
		} else if (teclado.keyDown(Keyboard.RIGHT_KEY) == true) {
			if (this.x < janela.getWidth() - 60) {
				this.x += velocidade;
			}
			if (direcao != 2) {
				setSequence(8, 11);
				direcao = 2;

			}
			movendo = true;
			// movendo para cima
		} else if (teclado.keyDown(Keyboard.UP_KEY) == true) {
			if (this.y > 0) {
				this.y -= velocidade;
			}
			if (direcao != 4) {
				setSequence(12, 16);
				direcao = 4;

			}
			movendo = true;

			// movendo para baixo
		} else if (teclado.keyDown(Keyboard.DOWN_KEY) == true) {
			if (this.y < janela.getHeight() - 60) {
				this.y += velocidade;
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

	private void correrLogica(Window janela, Keyboard teclado) {
		janela.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// Lógica para tratar a tecla pressionada
				char keyChat = e.getKeyChar();
				// int keyCode = e.getKeyCode();
				System.out.println(keyChat);

				if (keyChat == KeyEvent.VK_SPACE && teclado.keyDown(Keyboard.LEFT_KEY)
						|| keyChat == KeyEvent.VK_SPACE && teclado.keyDown(Keyboard.RIGHT_KEY)
						|| keyChat == KeyEvent.VK_SPACE && teclado.keyDown(Keyboard.UP_KEY)
						|| keyChat == KeyEvent.VK_SPACE && teclado.keyDown(Keyboard.DOWN_KEY)) {
					velocidade = 3;
				} else {
					velocidade = 1;
				}

			}
		});
	}

	public void vida(Window janela) {
		janela.drawText("Vida" + Ator.vida, 30, 30, Color.BLUE);
	}
}
