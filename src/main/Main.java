package main;

import main.Controller.GamePanel;

import javax.swing.*;

public class Main {


    /**SW
     * Starts the game and graphics.
     * @param args
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame("Sky Defense");
        frame.add(new GamePanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    // Instrucciones
    // Agregar texto en el centro de la pantalla al pasar de nivel
    // Adaptar tests
    // Botones en menu
    // Borrar star y starfield y todas sus implementaciones

}
