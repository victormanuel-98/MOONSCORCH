package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class CharacterMenu extends BaseView {

    private JButton ladronBtn;
    private JButton caballeroBtn;
    private JButton clerigoBtn;

    public CharacterMenu() {
        super();
        setLayout(null);

        // Botón: Ladrón
        ladronBtn = new JButton("Ladrón", resizeImage(
                ResourceLoader.loadImageIcon("ladron.png"), 150, 150));
        ladronBtn.setBounds(150, 250, 200, 200);
        ladronBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        ladronBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(ladronBtn);

        // Botón: Caballero
        caballeroBtn = new JButton("Caballero", resizeImage(
                ResourceLoader.loadImageIcon("caballero.png"), 150, 150));
        caballeroBtn.setBounds(450, 250, 200, 200);
        caballeroBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        caballeroBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(caballeroBtn);

        // Botón: Clérigo
        clerigoBtn = new JButton("Clérigo", resizeImage(
                ResourceLoader.loadImageIcon("mago.png"), 150, 150));
        clerigoBtn.setBounds(750, 250, 200, 200);
        clerigoBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        clerigoBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(clerigoBtn);
    }

    public void setLadronAction(ActionListener listener) {
        ladronBtn.addActionListener(listener);
    }

    public void setCaballeroAction(ActionListener listener) {
        caballeroBtn.addActionListener(listener);
    }

    public void setClerigoAction(ActionListener listener) {
        clerigoBtn.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}