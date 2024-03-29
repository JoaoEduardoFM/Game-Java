package jogo.armas;

import jplay.Keyboard;
import jplay.Sprite;
import jplay.URL;
import jplay.Window;

public class Ataque extends Sprite {

	public static final int LEFT = 1, RIGHT = 2, STOP = 3, UP = 4, DOWN = 5;

	protected static final int VELOCIDADE_TIRO = 3;
	protected static final int ALCANCE_TIRO = 3000; // Ajuste conforme necessário
	public int caminho = STOP;
	protected boolean movendo = false;
	protected int direcao = 3;
	private boolean atingiuLimite = false;
	private long tempoInicial; // Tempo em que o tiro foi disparado
	private static final long LimiteTempo = 800; // Limite de tempo em milissegundos

	private double origemX; // Coordenada x onde o tiro foi disparado
	private double origemY; // Coordenada y onde o tiro foi disparado

	public Ataque(double x, double y, int caminho, String sprite) {
		super(URL.sprite(sprite), 16);
		this.caminho = caminho;
		this.x = x;
		this.y = y;
		this.origemX = x;
		this.origemY = y;
		this.tempoInicial = System.currentTimeMillis();
		setTotalDuration(800);
	}

	public void mover(Window janela, Keyboard teclado, Ataque tiro) {
		if (caminho == LEFT && this.x > origemX - ALCANCE_TIRO) {
			this.x -= VELOCIDADE_TIRO;
			moveTo(x, y, VELOCIDADE_TIRO);
			if (direcao != 1) {
				setSequence(5, 8);
			}
			movendo = true;
		}

		if (caminho == RIGHT && this.x < origemX + ALCANCE_TIRO) {
			this.x += VELOCIDADE_TIRO;
			moveTo(x, y, VELOCIDADE_TIRO);
			if (direcao != 2) {
				setSequence(9, 12);
			}
			movendo = true;
		}

		if (caminho == UP && this.y > origemY - ALCANCE_TIRO) {
			this.y -= VELOCIDADE_TIRO;
			moveTo(x, y, VELOCIDADE_TIRO);
			if (direcao != 4) {
				setSequence(13, 16);
			}
			movendo = true;
		}

		if (caminho == DOWN && this.y < origemY + ALCANCE_TIRO) {
			this.y += VELOCIDADE_TIRO;
			moveTo(x, y, VELOCIDADE_TIRO);
			if (direcao != 5) {
				setSequence(1, 4);
			}
			movendo = true;
		}

		/*
		 * // Diagonal superior if (teclado.keyDown(KeyEvent.VK_UP)) { this.y -=
		 * VELOCIDADE_TIRO; movendo = true; }
		 * 
		 * // Diagonal inferior if (teclado.keyDown(KeyEvent.VK_DOWN)) { this.y +=
		 * VELOCIDADE_TIRO; movendo = true; }
		 */

		// Validar se a distância percorrida atingiu o alcance máximo
		long tempoAtual = System.currentTimeMillis();

		// Validar se o tempo decorrido atingiu o limite
		if (tempoAtual - tempoInicial > LimiteTempo) {
			setAtingiuLimite(true);
			hide();
		} else {
			setAtingiuLimite(false);
		}

		if (movendo) {
			update();
			movendo = false;
		}
	}

	public boolean getAtingiuLimite() {
		return atingiuLimite;
	}

	public void setAtingiuLimite(boolean atingiuLimite) {
		this.atingiuLimite = atingiuLimite;
	}

}
