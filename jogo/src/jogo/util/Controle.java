package jogo.util;

import jplay.GameObject;
import jplay.TileInfo;

public class Controle {

	public boolean colisao(GameObject obj, TileInfo tile) {
		// items percorriveis até o indice 7 baseado no cenario1.scn
		if (tile.id >= 7 && obj.collided(tile)) {
			return true;
		}
		return false;
	}
	
	public void threadSleeap(Integer valor) {
		try {
			// deixa o jogo mais fluido ao carregar sprites
			Thread.sleep(valor);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
