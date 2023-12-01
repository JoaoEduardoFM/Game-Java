package jogo;

import jogo.cenario.Pantano;
import jplay.GameImage;
import jplay.Keyboard;
import jplay.URL;
import jplay.Window;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // resolucao do jogo
        Window janela = new Window(960, 720);

        // imagem do menu
        Keyboard teclado = janela.getKeyboard();

        GameImage menuStart = menu("start2.gif");
        GameImage menuOptions = menu("options2.gif");
        GameImage menuExit = menu("exit2.gif");

        GameImage currentMenu = menuStart;

        while (true) {
            currentMenu.draw();// printa a tela
            janela.update();// atualiza em quanto percorre o loop

            if (teclado.keyDown(Keyboard.DOWN_KEY)) {
                // Determina o próximo menu com base no menu atual
                if (currentMenu == menuStart) {
                    currentMenu = menuOptions;                  
                } else if (currentMenu == menuOptions) {
                    currentMenu = menuExit;
                } else if (currentMenu == menuExit) {
                    // Volta para o menu inicial se já estiver no último menu
                    currentMenu = menuStart;
                }

                // Aguarda a tecla ser solta para evitar trocas rápidas de menu
                while (teclado.keyDown(Keyboard.DOWN_KEY)) {
                    Thread.sleep(10);
                }
            }else if (teclado.keyDown(Keyboard.UP_KEY)) {
                // Determina o próximo menu com base no menu atual
                if (currentMenu == menuStart) {
                    currentMenu = menuExit;
                } else if (currentMenu == menuOptions) {
                    currentMenu = menuStart;
                } else if (currentMenu == menuExit) {
                    // Volta para o menu inicial se já estiver no último menu
                    currentMenu = menuOptions;
                }

                // Aguarda a tecla ser solta para evitar trocas rápidas de menu
                while (teclado.keyDown(Keyboard.UP_KEY)) {
                    Thread.sleep(10);
                }
            }
            
            if(teclado.keyDown(Keyboard.ENTER_KEY) && currentMenu == menuStart) {
            	new Pantano(janela);
            }
            
            if(teclado.keyDown(Keyboard.ENTER_KEY) && currentMenu == menuExit) {
            	System.exit(0);
            }
            
            
        }
    }

    public static GameImage menu(String menuImage) {
        return new GameImage(URL.sprite(menuImage));
    }
    
    public static void shutDown(Window janela) {
        janela.exit();  // Fecha a janela
    }

}
