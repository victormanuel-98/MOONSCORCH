package view;

import model.PlayerCombatData;
import util.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReadyForBattle extends BaseView {

    private JLabel personajeImg;
    private JLabel resumenLabel;
    private JButton comenzarBtn;

    public ReadyForBattle(PlayerCombatData jugador) {
        super();
        setLayout(null);

        // Imagen del personaje
        personajeImg = new JLabel(resizeImage(
                ResourceLoader.loadImageIcon(jugador.clase.toLowerCase() + ".png"), 200, 200));
        personajeImg.setBounds(100, 200, 200, 200);
        add(personajeImg);

        // Resumen de los stats
        String resumen = "<html><center>" +
                "<b>" + jugador.nombre + "</b><br>" +
                "Clase: " + jugador.clase + "<br>" +
                "Nivel: " + jugador.nivel + "<br><br>" +
                "ATK: " + jugador.stats[0] + " &nbsp;&nbsp; " +
                "DEF: " + jugador.stats[1] + " &nbsp;&nbsp; " +
                "EVA: " + jugador.stats[2] + "<br>" +
                "HP: " + jugador.stats[3] + " &nbsp;&nbsp; " +
                "MP: " + jugador.stats[4] + " &nbsp;&nbsp; " +
                "LUK: " + jugador.stats[5] + "<br><br>" +
                "¿Estás listo para comenzar tu primera batalla?" +
                "</center></html>";

        resumenLabel = new JLabel(resumen, SwingConstants.CENTER);
        resumenLabel.setForeground(Color.WHITE);
        resumenLabel.setFont(new Font("Serif", Font.BOLD, 20));
        resumenLabel.setBounds(350, 150, 600, 250);
        add(resumenLabel);

        // Botón: Comenzar batalla
        comenzarBtn = new JButton("Comenzar Batalla");
        comenzarBtn.setBounds(540, 450, 200, 50);
        add(comenzarBtn);
    }

    public void setComenzarAction(ActionListener listener) {
        comenzarBtn.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}