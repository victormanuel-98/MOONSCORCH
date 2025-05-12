package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class LaunchView extends BaseView {

    private JButton newGameButton;
    private JButton exitButton;

    public LaunchView() {
        super();
        setLayout(null);

        // Botón: Nueva Partida
        newGameButton = new JButton("Nueva Partida");
        newGameButton.setBounds(500, 400, 280, 50);
        add(newGameButton);
       
        // Botón: Salir
        exitButton = new JButton("Salir");
        exitButton.setBounds(500, 470, 280, 50);
        add(exitButton);
    }
    

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void setNewGameAction(ActionListener listener) {
        newGameButton.addActionListener(listener);
    }

    public void setExitAction(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}