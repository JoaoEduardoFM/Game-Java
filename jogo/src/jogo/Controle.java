package jogo;

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

}
