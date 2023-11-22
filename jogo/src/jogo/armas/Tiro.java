package jogo.armas;

import jplay.Sprite;
import jplay.URL;

public class Tiro extends Sprite {

    public static final int LEFT = 1, RIGHT = 2, STOP = 3, UP = 4, DOWN = 5;

    protected static final int VELOCIDADE_TIRO = 15;
    protected static final int ALCANCE_TIRO = 300;  // Ajuste conforme necessário
    protected static final int LimiteTiro = 301;  // Ajuste conforme necessário
    public int caminho = STOP;
    protected boolean movendo = false;
    protected int direcao = 3;
    private boolean atingiuLimite = false;

    private double origemX;  // Coordenada x onde o tiro foi disparado
    private double origemY;  // Coordenada y onde o tiro foi disparado

    public Tiro(double x, double y, int caminho) {
        super(URL.sprite("tiro.png"), 16);
        this.caminho = caminho;
        this.x = x;
        this.y = y;
        this.origemX = x;
        this.origemY = y;
    }

    public void mover() {
        if (caminho == LEFT && this.x > origemX - ALCANCE_TIRO) {
            this.x -= VELOCIDADE_TIRO;
            if (direcao != 1) {
                setSequence(3, 7);
            }
            movendo = true;
        }

        if (caminho == RIGHT && this.x < origemX + ALCANCE_TIRO) {
            this.x += VELOCIDADE_TIRO;
            if (direcao != 2) {
                setSequence(8, 11);
            }
            movendo = true;
        }

        if (caminho == UP && this.y > origemY - ALCANCE_TIRO) {
            this.y -= VELOCIDADE_TIRO;
            if (direcao != 4) {
                setSequence(12, 15);
            }
            movendo = true;
        }

        if (caminho == DOWN && this.y < origemY + ALCANCE_TIRO) {
            this.y += VELOCIDADE_TIRO;
            if (direcao != 5) {
                setSequence(0, 3);
            }
            movendo = true;
        }
        
        // Calcular a distância percorrida pelo tiro
        double distanciaPercorrida = Math.sqrt(Math.pow(this.x - origemX, 2) + Math.pow(this.y - origemY, 2));

        // Validar se a distância percorrida atingiu o alcance máximo
        if (distanciaPercorrida >= LimiteTiro) {
            atingiuLimite = true;
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
