package jogo.armas;

import jplay.Keyboard;
import jplay.Sprite;
import jplay.URL;
import jplay.Window;

public class AtaqueEmArea extends Sprite {

	public static final int LEFT = 1, RIGHT = 2, STOP = 3, UP = 4, DOWN = 5;

	protected static final int VELOCIDADE_TIRO = 0;
	protected static final int ALCANCE_TIRO = 500;
	public int caminho = STOP;
	protected boolean movendo = false;
	protected int direcao = 3;
	private boolean atingiuLimite = false;
	private long tempoInicial;
	private static final long LimiteTempo = 500;
	private double origemX;
	private double origemY;

	public AtaqueEmArea(double x, double y, int caminho) {
		super(URL.sprite("atakAreaGrande.png"), 5);
		this.caminho = caminho;
		this.x = x;
		this.y = y;
		this.origemX = x + 50;
		this.origemY = y;
		this.tempoInicial = System.currentTimeMillis();
		setTotalDuration(800);
	}

	public void mover(Window janela, Keyboard teclado, AtaqueEmArea tiro) {
		
		for (int i = 1; i <= 6; i++) {
		    if (caminho == LEFT && this.x > origemX - ALCANCE_TIRO) {
		    	if (direcao != 1) {
					setSequence(1, 5);// sprite 4 e 8 do personagem
					direcao = 1;

				}
				movendo = true;
		    } else if (caminho == RIGHT && this.x < origemX + ALCANCE_TIRO) {
		    	if (direcao != 2) {
					setSequence(1, 5);
					direcao = 2;

				}
				movendo = true;
		    } else if (caminho == UP && this.y > origemY - ALCANCE_TIRO) {
		    	if (direcao != 4) {
					setSequence(1, 5);
					direcao = 4;

				}
				movendo = true;
		    } else if (caminho == DOWN && this.y < origemY + ALCANCE_TIRO) {
		    	if (direcao != 5) {
					setSequence(1, 5);
					direcao = 5;

				}
				movendo = true;
		    } else {
		    	movendo = false;
		    }
		}

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

	public double getOrigemX() {
		return origemX;
	}

	public void setOrigemX(double origemX) {
		this.origemX = origemX;
	}

	public double getOrigemY() {
		return origemY;
	}

	public void setOrigemY(double origemY) {
		this.origemY = origemY;
	}

	public long getTempoInicial() {
		return tempoInicial;
	}

	public void setTempoInicial(long tempoInicial) {
		this.tempoInicial = tempoInicial;
	}

	public boolean getAtingiuLimite() {
		return atingiuLimite;
	}

	public void setAtingiuLimite(boolean atingiuLimite) {
		this.atingiuLimite = atingiuLimite;
	}

}
