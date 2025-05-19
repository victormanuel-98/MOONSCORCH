package view;

import javax.swing.*;
import java.awt.*;

public class GameOverView extends JFrame {

    public interface GameOverListener {
        void onContinue();
        void onExit();
    }

    private final GameOverListener listener;

    public GameOverView(GameOverListener listener) {
        this.listener = listener;

        setTitle("Game Over");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gameOverLabel.setForeground(Color.RED);
        add(gameOverLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton continueButton = new JButton("Continuar");
        JButton exitButton = new JButton("Salir");

        buttonPanel.add(continueButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        continueButton.addActionListener(e -> {
            dispose();
            if (listener != null) listener.onContinue();
        });

        exitButton.addActionListener(e -> {
            dispose();
            if (listener != null) listener.onExit();
        });
    }
}