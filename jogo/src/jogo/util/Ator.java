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

	public static double vida = 1000;

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
