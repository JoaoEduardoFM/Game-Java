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
	private Mob[] mobs;
	private String[] nomesCenarios = { "Cenario1.scn", "Cenario2.scn", "Cenario3.scn" };
	private int indiceCenarioAtual = 0;
	private long tempoInicialCenario = System.currentTimeMillis();

	public Pantano(Window window) {
		janela = window;
		cena = new Scene();
		cena.loadFromFile(URL.scenario(nomesCenarios[1]));
		jogador = new Jogador(540, 350);
		teclado = janela.getKeyboard();
		inicializarMobs();
		// Som.play("musica1.mid");
		run();
	}

	private void inicializarMobs() {
		mobs = new Mob[] { new Mob(500, 300, "esqueleto.png")};
	}

	private void run() {
		while (true) {
			jogadorLogica(jogador);
			mobLogica(jogador);
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
		personagem.y += cena.getYOffset();
		personagem.atirarPistola(janela, cena, teclado, mobs);
		personagem.draw();
		personagem.vida(janela);
	}

	private void mobLogica(Jogador player) {
		for (Mob mob : mobs) {
			mob.caminho(cena);
			mob.perseguir(player.x, player.y);
			mob.x += cena.getXOffset();
			mob.y += cena.getYOffset();
			mob.morrer();
			mob.atacar(player, mob);
			mob.draw();
		}
		janela.update();
	}

	private void mudarCenario() {
		long tempoAtual = System.currentTimeMillis();
		long tempoDecorrido = (tempoAtual - tempoInicialCenario) / 1000; // converta para segundos

		if (tempoDecorrido >= 3) {
			// Altere o cenário
			indiceCenarioAtual = (indiceCenarioAtual + 1) % nomesCenarios.length;
			cena.loadFromFile(URL.scenario(nomesCenarios[indiceCenarioAtual]));

			// Reinicia o tempo inicial para permitir futuras mudanças de cenário
			tempoInicialCenario = System.currentTimeMillis();
		}
	}
}
