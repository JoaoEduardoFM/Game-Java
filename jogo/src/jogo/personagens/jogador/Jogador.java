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
	private boolean movendo = true;
	private long ultimoDisparo = 0;
	private long delayEntreTiros = 700;
	private long lastUpdateTime = System.currentTimeMillis();
	private long animationDelay = 1000; // Ajuste conforme necessário (em milissegundos)
	private String sprite;

	public Jogador(int x, int y) {
		super(URL.sprite("magoMarrom.png"), 20);
		this.x = x;
		this.y = y;
		this.sprite = getSprite();
		this.setTotalDuration(2000);
	}

	ControleTiros tiros = new ControleTiros();
	ControleEspada espada = new ControleEspada();

	private int proximaSequencia = -1;
	private float interpolacao = 0.0f;
	private static final float VELOCIDADE_INTERPOLACAO = 0.1f;

	public void atualizarAnimacao() {
		if (proximaSequencia != -1) {
			interpolacao += VELOCIDADE_INTERPOLACAO;
			setSequence(proximaSequencia, proximaSequencia + 1);
			update();
			if (interpolacao >= 2.0f) {
				proximaSequencia = -1;
				interpolacao = 0.0f;
				if (direcao == 5) {
					setSequence(1, 4);
				} else if (direcao == 4) {
					setSequence(13, 16);
				} else if (direcao == 2) {
					setSequence(9, 12);
				} else if (direcao == 1) {
					setSequence(5, 8);
				}
			}

		}
	}

	public void atirarPistola(Window janela, Scene cena, Keyboard teclado, Mob[] mobs) {
		if (teclado.keyDown(KeyEvent.VK_A) && System.currentTimeMillis() - ultimoDisparo > delayEntreTiros) {
			proximaSequencia = (direcao == 1) ? 17
					: (direcao == 2) ? 18 : (direcao == 5) ? 16 : (direcao == 4) ? 19 : 1;
			tiros.adicionaTiro(x + 5, y + 12, direcao, cena);
			ultimoDisparo = System.currentTimeMillis();
		}

		tiros.run(mobs, janela, teclado);
		atualizarAnimacao();
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
			e.printStackTrace();
		}
	}

	public Boolean controle(Window janela, Keyboard teclado) {

		if (teclado.keyDown(Keyboard.UP_KEY) || teclado.keyDown(Keyboard.DOWN_KEY)
				|| teclado.keyDown(Keyboard.RIGHT_KEY) || teclado.keyDown(Keyboard.LEFT_KEY)) {
			correrLogica(janela, teclado);
		}

		// Diagonal Superior Direita
		if (teclado.keyDown(Keyboard.UP_KEY) && teclado.keyDown(Keyboard.RIGHT_KEY)) {
			moverDiagonalSuperiorDireita(janela);
		}

		// Diagonal superior esquerda
		if (teclado.keyDown(Keyboard.UP_KEY) && teclado.keyDown(Keyboard.LEFT_KEY)) {
			moverDiagonalSuperiorEsquerda(janela);
		}

		// Diagonal Inferior Direita
		if (teclado.keyDown(Keyboard.DOWN_KEY) && teclado.keyDown(Keyboard.RIGHT_KEY)) {
			moverDiagonalInferiorDireita(janela);
		}

		// Diagonal inferior esquerda
		if (teclado.keyDown(Keyboard.DOWN_KEY) && teclado.keyDown(Keyboard.LEFT_KEY)) {
			moverDiagonalInferiorEsquerda(janela);
		}

		// movendo para esquerda
		if (teclado.keyDown(Keyboard.LEFT_KEY) && !teclado.keyDown(Keyboard.RIGHT_KEY)) {
			if (this.x > 0) {
				this.x -= velocidade;// impede que não saia da jenela
			}
			if (direcao != 1) {
				setSequence(5, 8);// sprite 4 e 8 do personagem
				direcao = 1;

			}
			movendo = true;
			// movendo para direta
		} else if (teclado.keyDown(Keyboard.RIGHT_KEY) && !teclado.keyDown(Keyboard.LEFT_KEY)) {
			if (this.x < janela.getWidth() - 60) {
				this.x += velocidade;
			}
			if (direcao != 2) {
				setSequence(9, 12);
				direcao = 2;

			}
			movendo = true;
			// movendo para cima
		} else if (teclado.keyDown(Keyboard.UP_KEY) && !teclado.keyDown(Keyboard.DOWN_KEY)) {
			if (this.y > 0) {
				this.y -= velocidade;
			}
			if (direcao != 4) {
				setSequence(13, 16);
				direcao = 4;

			}
			movendo = true;

			// movendo para baixo
		} else if (teclado.keyDown(Keyboard.DOWN_KEY) && !teclado.keyDown(Keyboard.UP_KEY)) {
			if (this.y < janela.getHeight() - 60) {
				this.y += velocidade;
			}
			if (direcao != 5) {
				setSequence(1, 4);
				direcao = 5;

			}
			movendo = true;
		} else {
			// parado
			movendo = false;
			if (direcao != 125) {
				setSequence(1, 4);
				direcao = 125;

			}
		}

		if (!movendo) {
			long currentTime = System.currentTimeMillis();			
			if (currentTime - lastUpdateTime > animationDelay) {		
				update();
				lastUpdateTime = currentTime;
			}
			movendo = false;
		}

		if (movendo) {
			// atualiza o frame caso o movendo seja true
			update();
			movendo = false;
		}
		return movendo;
	}

	private void correrLogica(Window janela, Keyboard teclado) {
		janela.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// Lógica para tratar a tecla pressionada
				char keyChat = e.getKeyChar();
				if (keyChat == KeyEvent.VK_SPACE) {
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
		janela.drawText("Vida: " + Ator.vida, 30, 30, Color.green);
	}

	public String getSprite() {
		return sprite;
	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}

}
