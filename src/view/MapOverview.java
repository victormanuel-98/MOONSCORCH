package view;

import model.GameState;
import model.Enemy;
import model.Player;
import controller.CombatController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MapOverview extends BaseView {

    private final String[] nodos = {"PLAYER", "ENEMY", "TREASURE", "ENEMY", "DOOR"};
    private final ArrayList<JButton> botones = new ArrayList<>();
    private int nodoActual = 0;

    public MapOverview() {
        super();
        setLayout(null);

        int totalNodos = nodos.length;
        int espacio = 200;
        int inicioX = 100;
        int centroY = 300;
        int radio = 60;

        // Crear botones
        for (int i = 0; i < totalNodos; i++) {
            JButton btn = new JButton(nodos[i]);
            btn.setBounds(inicioX + i * espacio, centroY, radio, radio);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 10));

            Color color = switch (nodos[i]) {
                case "PLAYER" -> new Color(0, 153, 51);
                case "ENEMY" -> new Color(153, 0, 0);
                case "TREASURE" -> new Color(255, 204, 0);
                case "DOOR" -> new Color(0, 153, 255);
                default -> Color.GRAY;
            };
            btn.setBackground(color);

            final int index = i;
            btn.addActionListener((ActionEvent e) -> manejarClic(index));
            botones.add(btn);
            add(btn);
        }

        // Botón para abrir el inventario
        JButton btnInventario = new JButton("Inventario");
        btnInventario.setBounds(1060, 20, 150, 40);
        btnInventario.addActionListener(e -> InventarioView.mostrarDesdeMapa(this));
        add(btnInventario);

        deshabilitarTodosMenos(nodoActual);
    }

    private void manejarClic(int index) {
        if (index != nodoActual) {
            JOptionPane.showMessageDialog(this,
                    "No puedes acceder a este nodo todavía.\nCompleta los anteriores primero.",
                    "Acceso denegado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipo = nodos[index];
        switch (tipo) {
            case "PLAYER" -> {
                JOptionPane.showMessageDialog(this,
                        "Eres " + GameState.jugadorActual.nombre + ". ¡Comienza tu recorrido!");
                avanzar();
            }
            case "ENEMY" -> {
                int r = JOptionPane.showConfirmDialog(this,
                        "¿Quieres enfrentarte al enemigo?", "Batalla", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    avanzar();
                    // Obtener jugador y enemigo
                    Player jugador = GameState.jugadorActual;
                    Enemy enemigo = obtenerEnemigoDelNodo(index);

                    // Crear controlador y vista de combate
                    CombatController combatController = new CombatController(jugador, enemigo);
                    CombatView combatView = new CombatView(jugador, enemigo, combatController);
                    combatView.setVisible(true);
                }
            }
            case "TREASURE" -> {
                GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(this);
                GameState.mapaActual.marcarNodoComoCompletado(nodoActual);
                GameState.mapaActual.incrementarNodoActual();
                frame.cambiarVista(new TesoroView());
            }
            case "DOOR" -> {
                JOptionPane.showMessageDialog(this, "¡Has completado este mapa!");
                avanzar();
            }
        }
    }

    private Enemy obtenerEnemigoDelNodo(int index) {
        // Aquí debes obtener el enemigo real según el nodo.
        // Por ahora, devolvemos un enemigo de ejemplo.
        Enemy enemigoEjemplo = new Enemy();
        enemigoEjemplo.setName("Enemigo Nivel " + (index + 1));
        enemigoEjemplo.setCurrentHp(100);
        enemigoEjemplo.setMaxHp(100);
        enemigoEjemplo.setAtk(15);
        enemigoEjemplo.setDef(5);
        return enemigoEjemplo;
    }

    private void avanzar() {
        marcarNodoComoCompletado(nodoActual);
        nodoActual++;
        deshabilitarTodosMenos(nodoActual);
    }

    public void marcarNodoComoCompletado(int index) {
        JButton btn = botones.get(index);
        btn.setBackground(Color.GRAY);
        btn.setEnabled(false);
    }

    public void incrementarNodoActual() {
        nodoActual++;
        deshabilitarTodosMenos(nodoActual);
    }

    private void deshabilitarTodosMenos(int activo) {
        for (int i = 0; i < botones.size(); i++) {
            botones.get(i).setEnabled(i == activo);
        }
    }
}