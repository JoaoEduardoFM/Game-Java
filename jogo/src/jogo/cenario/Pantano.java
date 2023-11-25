package jogo.cenario;

import jogo.personagens.jogador.Jogador;
import jogo.personagens.npc.Mob;
import jplay.Keyboard;
import jplay.Scene;
import jplay.URL;
import jplay.Window;

public class Pantano extends Cenario {

	private Window janela;

	private Scene cena;

	private Jogador jogador;

	private Keyboard teclado;

	private Mob esqueleto;
	
	private String[] nomesCenarios = {"Cenario1.scn", "Cenario2.scn", "Cenario3.scn" };
	
	private int indiceCenarioAtual = 0;

    private long tempoInicialCenario = System.currentTimeMillis();

	public Pantano(Window window) {
		janela = window;
		cena = new Scene();
		cena.loadFromFile(URL.scenario(nomesCenarios[1]));
		jogador = new Jogador(540, 350);
		teclado = janela.getKeyboard();

		// Som.play("musica1.mid");
		run();
	}

	private void run() {

		esqueleto = new Mob(300, 300, "esqueleto.png");

		while (true) {
			jogadorLogica(jogador);
			mobLogica(esqueleto);
			mudarCenario();
			try {
				Thread.sleep(16); // 60 FPS
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void jogadorLogica(Jogador personagem) {
		personagem.controle(janela, teclado);
		personagem.caminho(cena);
		cena.moveScene(personagem);
		personagem.x += cena.getXOffset();
		personagem.y += cena.getXOffset();
		personagem.atirarPistola(janela, cena, teclado, esqueleto);
		personagem.draw();
		personagem.vida(janela);
	}

	private void mobLogica(Mob mob) {
		mob.caminho(cena);
		mob.perseguir(jogador.x, jogador.y);
		mob.x += cena.getXOffset();
		mob.y += cena.getXOffset();
		mob.morrer();
		mob.atacar(jogador, mob);
		mob.draw();
		mob.vida(janela);
		janela.update();
	}

	private void mudarCenario() {
        // Calcule o tempo decorrido em segundos
        long tempoAtual = System.currentTimeMillis();
        long tempoDecorrido = (tempoAtual - tempoInicialCenario) / 500; // converta para segundos

        // Se passou pelo menos 1 segundo desde a última mudança de cenário
        if (tempoDecorrido >= 1) {
            // Altere o cenário
            indiceCenarioAtual = (indiceCenarioAtual + 1) % nomesCenarios.length;
            cena.loadFromFile(URL.scenario(nomesCenarios[indiceCenarioAtual]));

            // Reinicie o tempo inicial para permitir futuras mudanças de cenário
            tempoInicialCenario = System.currentTimeMillis();
        }
    }

}
