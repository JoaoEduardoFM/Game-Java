package jogo.cenario;

import java.awt.event.KeyEvent;
import java.util.Arrays;

import jogo.personagens.jogador.Jogador;
import jogo.personagens.npc.Mob;
import jogo.util.Menu;
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
	private Boolean pause = true;

	public Pantano(Window window, Jogador backupJogador, Mob[] backupMobs, String[] backupNomesCenarios,
			long backupTempoInicialCenario, double vidaJogador) {
		janela = window;
		cena = new Scene();
		if(backupJogador != null) {
		jogador = new Jogador(500, 350, vidaJogador);
		}else {
		jogador = new Jogador(500, 350, 1500);	
		}
		mobs = new Mob[] { new Mob(800, 900, "esqueleto.png", 0.5, 250.0) };
		cena.loadFromFile(URL.scenario("Cenario1.scn"));
		teclado = janela.getKeyboard();
		// Som.play("musica1.mid");
		run(window, backupJogador, backupMobs, backupNomesCenarios, backupTempoInicialCenario, vidaJogador);
	}

	private void run(Window window, Jogador backupJogador, Mob[] backupMobs, String[] backupNomesCenarios,
			long backupTempoInicialCenario, double vidaJogador) {
		while (getPause()) {
			jogadorLogica(backupJogador != null ? backupJogador : jogador);
			mobLogica(backupJogador != null ? backupJogador : jogador);
			//spawnarMob(backupMobs);

			if (window != null) {
				setJanela(window);
			}

			if (backupNomesCenarios != null) {
				setNomesCenarios(backupNomesCenarios);
			}

			if (backupTempoInicialCenario != 0) {
				setTempoInicialCenario(backupTempoInicialCenario);
			}
			if (backupJogador != null) {
				setJogador(backupJogador);
			}
			if (backupMobs != null) {
	            setMobs(Arrays.copyOf(backupMobs, backupMobs.length));
	            backupMobs = null;
	        }
			pause();
			// mudarCenario();
			try {
				Thread.sleep(16); // 60 FPS
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (getPause().equals(false)) {
			try {
				Menu.manuLogica(janela, teclado, getJogador(), getMobs(), getNomesCenarios(), 0, vidaJogador);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void pause() {
		if (teclado.keyDown(KeyEvent.VK_ESCAPE) && getPause().equals(true)) {
			setPause(false);
		}
	}

	private void adicionarNovoMob(String mob, Mob[] backupMobs, Double velocidade, Double vidaMob) {
		int randomEdge = (int) (Math.random() * 4);

		int randomX = 0;
		int randomY = 0;

		switch (randomEdge) {
		case 0: // Topo
			randomX = (int) (Math.random() * janela.getWidth());
			randomY = -50; // Altura do Mob é 50
			break;
		case 1: // Base
			randomX = (int) (Math.random() * janela.getWidth());
			randomY = janela.getHeight();
			break;
		case 2: // Esquerda
			randomX = -50; // Largura do Mob é 50
			randomY = (int) (Math.random() * janela.getHeight());
			break;
		case 3: // Direita
			randomX = janela.getWidth();
			randomY = (int) (Math.random() * janela.getHeight());
			break;
		}

		if (backupMobs != null) {
			Mob novoMob = new Mob(randomX, randomY, mob, velocidade, vidaMob);
			backupMobs = Arrays.copyOf(backupMobs, backupMobs.length + 1);
			backupMobs[backupMobs.length - 1] = novoMob;
		}

		if (mobs != null) {
			Mob novoMob = new Mob(randomX, randomY, mob, velocidade, vidaMob);
			mobs = Arrays.copyOf(mobs, mobs.length + 1);
			mobs[mobs.length - 1] = novoMob;
		}
	}

	private void jogadorLogica(Jogador personagem) {
		personagem.controle(janela, teclado);
		personagem.caminho(cena);
		cena.moveScene(personagem);
		personagem.x += cena.getXOffset();
		personagem.y += cena.getYOffset();
		personagem.atirarPistola(janela, cena, teclado, mobs);
		personagem.ataqueEmArea(janela, cena, teclado, mobs);
		personagem.draw();
		personagem.vida(janela);
	}

	private void mobLogica(Jogador player) {
		for (Mob mob : mobs) {
			mob.morrer();
			mob.caminho(cena);
			mob.perseguir(player.x, player.y);
			mob.x += cena.getXOffset();
			mob.y += cena.getYOffset();
			mob.morrer();
			mob.atacar(player, mob);
			mob.draw();
		}

		// Desenha o jogador por último
		player.x += cena.getXOffset();
		player.y += cena.getYOffset();
		player.atirarPistola(janela, cena, teclado, mobs);
		player.ataqueEmArea(janela, cena, teclado, mobs);
		player.draw();
		player.vida(janela);

		janela.update();
	}

	private void mudarCenario() {
		long tempoAtual = System.currentTimeMillis();
		long tempoDecorrido = (tempoAtual - tempoInicialCenario) / 1000; // converta para segundos

		if (tempoDecorrido >= 4) {
			// Altere o cenário
			indiceCenarioAtual = (indiceCenarioAtual + 1) % nomesCenarios.length;
			cena.loadFromFile(URL.scenario(nomesCenarios[indiceCenarioAtual]));

			// Reinicia o tempo inicial para permitir futuras mudanças de cenário
			tempoInicialCenario = System.currentTimeMillis();
		}
	}

	private void spawnarMob(Mob[] backupMobs) {
		long tempoAtual = System.currentTimeMillis();
		long tempoDecorrido = (tempoAtual - tempoInicialCenario) / 1000; // converta para segundos

		if (tempoDecorrido >= 1) {
			adicionarNovoMob("esqueleto.png", backupMobs,0.5, 250.0);
			adicionarNovoMob("orcPequeno.png", backupMobs,2.0, 250.0);
			adicionarNovoMob("javali.png", backupMobs,1.0, 750.0);
			tempoInicialCenario = System.currentTimeMillis();
		}
	}

	public Boolean getPause() {
		return pause;
	}

	public void setPause(Boolean pause) {
		this.pause = pause;
	}

	public Window getJanela() {
		return janela;
	}

	public void setJanela(Window janela) {
		this.janela = janela;
	}

	public Scene getCena() {
		return cena;
	}

	public void setCena(Scene cena) {
		this.cena = cena;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public Keyboard getTeclado() {
		return teclado;
	}

	public void setTeclado(Keyboard teclado) {
		this.teclado = teclado;
	}

	public Mob[] getMobs() {
		return mobs;
	}

	public void setMobs(Mob[] mobs) {
		this.mobs = mobs;
	}

	public String[] getNomesCenarios() {
		return nomesCenarios;
	}

	public void setNomesCenarios(String[] nomesCenarios) {
		this.nomesCenarios = nomesCenarios;
	}

	public int getIndiceCenarioAtual() {
		return indiceCenarioAtual;
	}

	public void setIndiceCenarioAtual(int indiceCenarioAtual) {
		this.indiceCenarioAtual = indiceCenarioAtual;
	}

	public long getTempoInicialCenario() {
		return tempoInicialCenario;
	}

	public void setTempoInicialCenario(long tempoInicialCenario) {
		this.tempoInicialCenario = tempoInicialCenario;
	}

}
