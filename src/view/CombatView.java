package view;

import model.Enemy;
import model.Player;
import controller.CombatController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CombatView extends JFrame {

    private Player player;
    private Enemy enemy;
    private CombatController combatController;

    private JTextArea combatLog;
    private JButton attackButton;
    private JButton skillButton;
    private JButton itemButton;
    private JButton endTurnButton;

    private JLabel playerStatsLabel;
    private JLabel enemyStatsLabel;

    public CombatView(Player player, Enemy enemy, CombatController combatController) {
        this.player = player;
        this.enemy = enemy;
        this.combatController = combatController;

        setTitle("Combate");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        updateStats();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel de estadísticas
        JPanel statsPanel = new JPanel(new GridLayout(1, 2));
        playerStatsLabel = new JLabel();
        enemyStatsLabel = new JLabel();
        statsPanel.add(playerStatsLabel);
        statsPanel.add(enemyStatsLabel);
        add(statsPanel, BorderLayout.NORTH);

        // Área de texto para el log de combate
        combatLog = new JTextArea();
        combatLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(combatLog);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones de acción
        JPanel actionPanel = new JPanel(new FlowLayout());

        attackButton = new JButton("Atacar");
        skillButton = new JButton("Habilidad");
        itemButton = new JButton("Objeto");
        endTurnButton = new JButton("Terminar Turno");

        actionPanel.add(attackButton);
        actionPanel.add(skillButton);
        actionPanel.add(itemButton);
        actionPanel.add(endTurnButton);

        add(actionPanel, BorderLayout.SOUTH);

        // Listeners
        attackButton.addActionListener(e -> performAttack());
        skillButton.addActionListener(e -> performSkill());
        itemButton.addActionListener(e -> useItem());
        endTurnButton.addActionListener(e -> endTurn());
    }

    private void updateStats() {
        playerStatsLabel.setText("<html><b>Jugador</b><br>HP: " + player.getCurrentHp() + "/" + player.getCurrentMaxHp() +
                "<br>MP: " + player.getCurrentMp() + "/" + player.getCurrentMaxMp() + "</html>");

        enemyStatsLabel.setText("<html><b>Enemigo</b><br>HP: " + enemy.getCurrentHp() + "/" + enemy.getMaxHp() + "</html>");
    }

    private void performAttack() {
        String result = combatController.playerAttack();
        combatLog.append(result + "\n");
        updateStats();
        checkCombatEnd();
    }

    private void performSkill() {
        // Aquí puedes abrir un diálogo para seleccionar habilidades
        String result = combatController.playerUseSkill();
        combatLog.append(result + "\n");
        updateStats();
        checkCombatEnd();
    }

    private void useItem() {
        // Aquí puedes abrir un diálogo para seleccionar objetos
        String result = combatController.playerUseItem();
        combatLog.append(result + "\n");
        updateStats();
        checkCombatEnd();
    }

    private void endTurn() {
        String result = combatController.enemyTurn();
        combatLog.append(result + "\n");
        updateStats();
        checkCombatEnd();
    }

    private void checkCombatEnd() {
        if (combatController.isCombatOver()) {
            String winner = combatController.getWinner();
            JOptionPane.showMessageDialog(this, "Combate terminado. Ganador: " + winner);
            this.dispose();
        }
    }
}