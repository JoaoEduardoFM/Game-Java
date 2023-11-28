package jogo.personagens.jogador;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import jogo.combate.ControleEspada;
import jogo.combate.ControleTiros;
import jogo.personagens.npc.Mob;
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

	public void atirarPistola(Window janela, Scene cena, Keyboard teclado, Mob[] mobs) {
		// Verifica se a tecla "A" está pressionada e se o tempo desde o último disparo
		// é maior que o delay
		if (teclado.keyDown(KeyEvent.VK_A) && System.currentTimeMillis() - ultimoDisparo > delayEntreTiros) {
			tiros.adicionaTiro(x + 5, y + 12, direcao, cena);
			ultimoDisparo = System.currentTimeMillis(); // Atualiza o tempo do último disparo
		}

		try {
			tiros.run(mobs, janela, teclado);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void ataqueEspada(Window janela, Scene cena, Keyboard teclado, Mob inimigo) {
		// Verifica se a tecla "A" está pressionada e se o tempo desde o último disparo
		// é maior que o delay
		if (teclado.keyDown(KeyEvent.VK_S) && System.currentTimeMillis() - ultimoDisparo > delayEntreTiros) {
			espada.adicionaEspada(x + 5, y + 12, direcao, cena);
			ultimoDisparo = System.currentTimeMillis(); // Atualiza o tempo do último disparo
		}
		try {
			espada.run(inimigo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void controle(Window janela, Keyboard teclado) {

		// correr
		if (teclado.keyDown(Keyboard.LEFT_KEY) && teclado.keyDown(KeyEvent.VK_SPACE)
				|| teclado.keyDown(Keyboard.RIGHT_KEY) && teclado.keyDown(KeyEvent.VK_SPACE)
				|| teclado.keyDown(Keyboard.UP_KEY) && teclado.keyDown(KeyEvent.VK_SPACE)
				|| teclado.keyDown(Keyboard.DOWN_KEY)) {
			correrLogica(janela, teclado);
		}

		// Diagonal Superior Direita
		if (teclado.keyDown(KeyEvent.VK_UP) && teclado.keyDown(Keyboard.RIGHT_KEY)) {
			moverDiagonalSuperiorDireita(janela);
		}

		// Diagonal superior esquerda
		if (teclado.keyDown(KeyEvent.VK_UP) && teclado.keyDown(Keyboard.LEFT_KEY)) {
			moverDiagonalSuperiorEsquerda(janela);
		}

		// Diagonal Inferior Direita
		if (teclado.keyDown(KeyEvent.VK_DOWN) && teclado.keyDown(Keyboard.RIGHT_KEY)) {
			moverDiagonalInferiorDireita(janela);
		}

		// Diagonal inferior esquerda
		if (teclado.keyDown(KeyEvent.VK_DOWN) && teclado.keyDown(Keyboard.LEFT_KEY)) {
			moverDiagonalInferiorEsquerda(janela);
		}

		// movendo para esquerda
		if (teclado.keyDown(Keyboard.LEFT_KEY)) {
			if (this.x > 0) {
				this.x -= velocidade;// impede que não saia da jenela
			}
			if (direcao != 1) {
				setSequence(5, 8);// sprite 4 e 8 do personagem
				direcao = 1;

			}
			movendo = true;
			// movendo para direta
		} else if (teclado.keyDown(Keyboard.RIGHT_KEY) == true) {
			if (this.x < janela.getWidth() - 60) {
				this.x += velocidade;
			}
			if (direcao != 2) {
				setSequence(9, 12);
				direcao = 2;

			}
			movendo = true;
			// movendo para cima
		} else if (teclado.keyDown(Keyboard.UP_KEY) == true) {
			if (this.y > 0) {
				this.y -= velocidade;
			}
			if (direcao != 4) {
				setSequence(13, 16);
				direcao = 4;

			}
			movendo = true;

			// movendo para baixo
		} else if (teclado.keyDown(Keyboard.DOWN_KEY) == true) {
			if (this.y < janela.getHeight() - 60) {
				this.y += velocidade;
			}
			if (direcao != 5) {
				setSequence(1, 4);
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
					velocidade = 6;
				} else {
					velocidade = 3;
				}

			}
		});
	}

	private void moverDiagonalSuperiorDireita(Window janela) {
		if (this.x < janela.getWidth() - 60 && this.y > 0) {
			this.x += velocidade / Math.sqrt(3);
			this.y -= velocidade / Math.sqrt(3);
		}
		if (direcao != 7) {
			setSequence(8, 11);
			direcao = 7;
		}
		movendo = true;
	}

	private void moverDiagonalSuperiorEsquerda(Window janela) {
		if (this.x > 0 && this.y > 0) {
			this.x -= velocidade / Math.sqrt(7);
			this.y -= velocidade / Math.sqrt(7);
		}
		if (direcao != 8) {
			setSequence(4, 8);
			direcao = 8;
		}
		movendo = true;
	}

	private void moverDiagonalInferiorDireita(Window janela) {
		if (this.x < janela.getWidth() - 60 && this.y > 0) {
			this.x -= velocidade / Math.sqrt(7);
			this.y += velocidade / Math.sqrt(7);
		}
		if (direcao != 9) {
			setSequence(8, 11);
			direcao = 9;
		}
		movendo = true;
	}

	private void moverDiagonalInferiorEsquerda(Window janela) {
		if (this.x > 0 && this.y > 0) {
			this.x += velocidade / Math.sqrt(7);
			this.y += velocidade / Math.sqrt(7);
		}
		if (direcao != 6) {
			setSequence(4, 8);
			direcao = 6;
		}
		movendo = true;
	}

	public void vida(Window janela) {
		janela.drawText("Vida Jogador: " + Ator.vida, 30, 30, Color.BLUE);
	}
}
