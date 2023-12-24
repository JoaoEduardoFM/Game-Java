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
	private Jogador backupJogador;
	private Mob[] backupMobs;
	private String[] backupNomesCenarios;
	private int backupIndiceCenarioAtual = 0;
	private long backupTempoInicialCenario = System.currentTimeMillis();

	public Pantano(Window window, Jogador backupJogador2, Mob[] backupMobs2, String[] backupNomesCenarios2, long backupTempoInicialCenario2) {
		janela = window;
		cena = new Scene();
		jogador = new Jogador(500, 350);
		mobs = new Mob[] { new Mob(800, 900, "esqueleto.png") };
		cena.loadFromFile(URL.scenario("Cenario1.scn"));
		teclado = janela.getKeyboard();
		 // Inicialize as variáveis de backup
        backupJogador = backupJogador2;
        backupMobs = backupMobs2;
        backupNomesCenarios = backupNomesCenarios2;
        backupTempoInicialCenario = backupTempoInicialCenario2;
		// Som.play("musica1.mid");
		run(backupJogador2);
	}

	private void run(Jogador backupJogador2) {
		while (getPause()) {
			jogadorLogica(backupJogador != null ? backupJogador : jogador);
			mobLogica(backupJogador != null ? backupJogador : jogador);
			if(backupJogador != null) {
				setJogador(backupJogador);
			}
			spawnarMob();
			pause();
			// mudarCenario();
			try {
				Thread.sleep(16); // 60 FPS
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(getPause().equals(false));
			try {
				Menu.manuLogica(janela, teclado, getJogador(), getMobs(), getNomesCenarios(), 0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void pause() {
		if (teclado.keyDown(KeyEvent.VK_ESCAPE) && getPause().equals(true)) {
			setPause(false);
		}
	}

	public void pauseOff() {
        setJogador(backupJogador);
        setMobs(backupMobs);
        setNomesCenarios(backupNomesCenarios);
        setIndiceCenarioAtual(backupIndiceCenarioAtual);
        setTempoInicialCenario(backupTempoInicialCenario);
    }

	private void adicionarNovoMob(String mob) {
		int randomEdge = (int) (Math.random() * 4); // 0: topo, 1: base, 2: esquerda, 3: direita

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

		// Adiciona um novo mob ao array com as coordenadas aleatórias
		Mob novoMob = new Mob(randomX, randomY, mob);
		mobs = Arrays.copyOf(mobs, mobs.length + 1);
		mobs[mobs.length - 1] = novoMob;
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
			mob.morrer();
			mob.pontosMorteMob(janela);
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

	private void spawnarMob() {
		long tempoAtual = System.currentTimeMillis();
		long tempoDecorrido = (tempoAtual - tempoInicialCenario) / 1000; // converta para segundos

		if (tempoDecorrido >= 1) {
			adicionarNovoMob("esqueleto.png");
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
