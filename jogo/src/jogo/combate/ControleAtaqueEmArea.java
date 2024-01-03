package jogo.combate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jogo.armas.AtaqueEmArea;
import jogo.personagens.npc.Mob;
import jplay.Keyboard;
import jplay.Scene;
import jplay.Window;

public class ControleAtaqueEmArea {

    LinkedList<AtaqueEmArea> atkArea = new LinkedList<>();

    public AtaqueEmArea atacarEmArea(double x, double y, int caminho, Scene cena, String tipoAtaque, int nrSprite) {
        AtaqueEmArea tiro = new AtaqueEmArea(x, y, caminho,tipoAtaque,nrSprite);
        atkArea.add(tiro);
        // adiciona animação na tela
        cena.addOverlay(tiro);
        // somAtaque();
        return tiro;
    }

    public void run(Mob[] mobs, Window janela, Keyboard teclado, int nrSprite) {
        List<AtaqueEmArea> tirosToRemove = new ArrayList<>();

        for (Iterator<AtaqueEmArea> iterator = atkArea.iterator(); iterator.hasNext();) {
            AtaqueEmArea tiro = iterator.next();

            // Check if 1 second has passed since the attack area was created
            long currentTime = System.currentTimeMillis();
            if (currentTime - tiro.getTempoInicial() >= 1000) {
                // Teleport after 1 second
                tiro.setOrigemX(10_000);
                iterator.remove(); // Remove the attack area from the list
                continue; // Skip further processing for this attack area
            }

            tiro.mover(teclado, nrSprite);

            for (Mob inimigo : mobs) {
                if (inimigo.vidaMob > 0) {
                    if (tiro.collided(inimigo)) {
                        inimigo.vidaMob -= 250;

                        if (inimigo.vidaMob >= 0) {
                            inimigo.morrer();
                            inimigo.sofrerRecuo(50);
                        }
                    }
                }
            }
        }

        // Remove the attack areas that hit mobs
        atkArea.removeAll(tirosToRemove);
    }

    private void somAtaque() {
        // Add your sound logic here
        // new Sound(URL.audio("tiro.WAV")).play();
    }
}
