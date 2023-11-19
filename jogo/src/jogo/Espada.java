package jogo;

import jplay.Animation;
import jplay.URL;

public class Espada extends Animation {

    protected int direcao;

    public Espada(int direcao) {
        super(URL.sprite("espada.png"), 16);
        this.direcao = direcao;
        configuraAnimacao();
    }

    private void configuraAnimacao() {
        switch (direcao) {
            case Tiro.LEFT:
                setSequence(3, 7);
                break;
            case Tiro.RIGHT:
                setSequence(8, 11);
                break;
            case Tiro.UP:
                setSequence(12, 15);
                break;
            case Tiro.DOWN:
                setSequence(0, 3);
                break;
            default:
                // Lógica padrão para outras direções, se necessário
                break;
        }
    }

    public void atacar(double x, double y) {
        this.x = x;
        this.y = y;
        play();
    }
}
