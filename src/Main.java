import Controller.GameController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sky Defense - Test");
            GameController game = new GameController();

            frame.add(game);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            game.requestFocusInWindow();
        });
    }
}
//Cambiar la logica de altura para que cumpla con los metros (Ya Hecho)
//Cambiar Hud (Elegir donde va a ir realmente e implementarlo)
//Cambiar MenuPanel
//Guia del juego(en ajustes)
//Implementar Interfaces
//Musica(disparo dron, explosion, perder vida, pasar nivel, musica de fondo, morir) (Ya Hecho)
//Pausa (Ya hecho)
//Animacion pasar nivel


//Arbol Generico
//Cambios de Claude en el codigo, modificar diagramas


//3 tipos de drones
//- El normal(el que tenemos ahora)
//-El medio(tira lasers)
// - Boss(aparece cada 10k puntos tira lasers y misiles)
//Cuando aparece boss aparece arma disponible para dispararle
//Barra de vida boss
//Dificultades:
//-Normal: Como esta ahora( 15% de increemento por nivel, recien en el nivel 5 no se pueden ganar vidas)
//-Hard: 20% de incremento, vida cada 2000 puntos y a partir de nivel 5 no se puede ganar vidas, mas drones medianos
//-HardCore: Mismas especificaciones que dificil solo que se tiene una vida y no se puede ganar vidas
//Scoreboard
//Cooperativo(si llegamos)
