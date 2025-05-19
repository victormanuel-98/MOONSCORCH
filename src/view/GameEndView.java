package view;

import javax.swing.*;
import java.awt.*;

public class GameEndView extends JFrame {

    public interface GameEndListener {
        void onExit();
    }

    private final GameEndListener listener;

    public GameEndView(GameEndListener listener) {
        this.listener = listener;

        setTitle("¡Juego Terminado!");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel congratsLabel = new JLabel("¡Felicidades! Has completado el juego.", SwingConstants.CENTER);
        congratsLabel.setFont(new Font("Arial", Font.BOLD, 32));
        congratsLabel.setForeground(new Color(0, 128, 0));
        add(congratsLabel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Salir");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        exitButton.addActionListener(e -> {
            dispose();
            if (listener != null) listener.onExit();
        });
    }
}