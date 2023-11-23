package jogo.util;

import java.awt.Point;
import java.util.Vector;

import jplay.GameObject;
import jplay.Scene;
import jplay.Sound;
import jplay.Sprite;
import jplay.TileInfo;
import jplay.URL;

public class Ator extends Sprite {

	public double velocidade = 0.01;

	public int direcao = 0;

	public boolean movendo = false;

	Controle controle = new Controle();

	public static double vida = 50000;

	public void caminho(Scene cena) {
		Point min = new Point((int) this.x, (int) this.y);
		Point max = new Point((int) this.x + this.width, (int) this.y + this.height);

		Vector<?> tiles = cena.getTilesFromPosition(min, max);

		for (int i = 0; i < tiles.size(); i++) {
			TileInfo tile = (TileInfo) tiles.elementAt(i);

			if (controle.colisao(this, tile) == true) {
				if (colisaoVertical(this, tile)) {
					// verifica colisao a cima
					if (tile.y + tile.height - 2 < this.y) {
						this.y = tile.y + this.height;
						// verifica colisao a baixo
					} else if (tile.y > this.y + this.height - 2) {
						this.y = tile.y - this.height;
					}
				}

				if (colisaoHorizontal(this, tile)) {
					if (tile.x > this.x + this.width - 2) {
						this.x = tile.x - this.width; // Ajuste aqui
					} else {
						this.x = tile.x + tile.width - 2;
					}
				}

			}
		}
	}

	private boolean colisaoVertical(GameObject obj, GameObject obj2) {
		if (obj2.x + obj2.width <= obj.x)
			return false;
		if (obj.x + obj.width <= obj2.x) {
			return false;
		}
		return true;
	}

	private boolean colisaoHorizontal(GameObject obj, GameObject obj2) {
		if (obj2.y + obj2.height <= obj.y)
			return false;
		if (obj.y + obj.height <= obj2.y) {
			return false;
		}
		return true;
	}

	boolean dano = false;

	
	public void sofrerRecuo() throws InterruptedException {
		int recuoTotal = 50; // Quantidade total de recuo desejada
		int recuoPorFrame = 5; // Ajuste conforme necessário

		vida -= 250; // Ajuste conforme necessário
		// Adicione a lógica de recuo aqui

		for (int i = 0; i < recuoTotal; i += recuoPorFrame) {
			if (direcao == 1) {
				this.x += recuoPorFrame; // Recua para a direita
				somSofrerDanoAudio();
			} else if (direcao == 2) {
				this.x -= recuoPorFrame; // Recua para a esquerda
				somSofrerDanoAudio();
			} else if (direcao == 4) {
				this.y += recuoPorFrame; // Recua para baixo
				somSofrerDanoAudio();
			} else if (direcao == 5) {
				this.y -= recuoPorFrame; // Recua para cima
				somSofrerDanoAudio();
			}


			// Aqui você pode adicionar uma pequena pausa entre os quadros
			// para dar a sensação de animação mais lenta
			try {
				if(dano) {
				Thread.sleep(05); // Ajuste conforme necessário	
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	long ultimoSpawn = System.currentTimeMillis();
	public void sofrerRecuoMob() throws InterruptedException {
		int recuoTotal = 50; // Quantidade total de recuo desejada
		int recuoPorFrame = 100; // Ajuste conforme necessário
		

		for (int i = 0; i < recuoTotal; i += recuoPorFrame) {
			if (direcao == 1) {
				this.x += recuoPorFrame; // Recua para a direita
			} else if (direcao == 2) {
				this.x -= recuoPorFrame; // Recua para a esquerda
			} else if (direcao == 4) {
				this.y += recuoPorFrame; // Recua para baixo
			} else if (direcao == 5) {
				this.y -= recuoPorFrame; // Recua para cima
			}
			try {
				Thread.sleep(100); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}

	private void somSofrerDanoAudio() throws InterruptedException {
		new Sound(URL.audio("levarDano.wav")).play();
	}

	public Ator(String fileName, int numeFrames) {
		super(fileName, numeFrames);
	}

	public double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}

}
