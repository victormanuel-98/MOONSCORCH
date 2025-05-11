package view;

import model.GameState;
import util.ResourceLoader;

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

        // Crear los botones de los nodos
        for (int i = 0; i < totalNodos; i++) {
            JButton btn = new JButton(nodos[i]);
            btn.setBounds(inicioX + i * espacio, centroY, radio, radio);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 10));

            Color color = switch (nodos[i]) {
                case "PLAYER" -> new Color(0, 153, 51);    // Verde
                case "ENEMY" -> new Color(153, 0, 0);       // Rojo
                case "TREASURE" -> new Color(255, 204, 0);  // Amarillo
                case "DOOR" -> new Color(0, 153, 255);      // Azul
                default -> Color.GRAY;
            };
            btn.setBackground(color);

            final int index = i;
            btn.addActionListener((ActionEvent e) -> manejarClic(index));
            botones.add(btn);
            add(btn);
        }

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
                    JOptionPane.showMessageDialog(this, "Simulando batalla...");
                    // Luego: showPanel(new CombatView(...));
                }
            }
            case "TREASURE" -> {
                lanzarTiradaTesoro();
                avanzar();
            }
            case "DOOR" -> {
                JOptionPane.showMessageDialog(this, "¡Has completado este mapa!");
                avanzar();
            }
        }
    }

    private void avanzar() {
        marcarNodoComoCompletado(nodoActual);
        nodoActual++;
        deshabilitarTodosMenos(nodoActual);
    }

    private void marcarNodoComoCompletado(int index) {
        JButton btn = botones.get(index);
        btn.setBackground(Color.GRAY);
        btn.setEnabled(false);
    }

    private void deshabilitarTodosMenos(int activo) {
        for (int i = 0; i < botones.size(); i++) {
            botones.get(i).setEnabled(i == activo);
        }
    }

    private void lanzarTiradaTesoro() {
        int luk = GameState.jugadorActual.getLUK();
        String[] opciones = {"Cara", "Cruz"};
        int eleccion = JOptionPane.showOptionDialog(this,
                "Elige tu lado para la tirada de moneda (basado en tu suerte LUK = " + luk + ")",
                "Tirada de Tesoro",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (eleccion == -1) return; // Cancelado

        boolean acierto = calcularTiradaMoneda(luk);
        if (acierto) {
            int rng = (int) (Math.random() * 100);
            String recompensa;
            if (rng < 33) {
                recompensa = "Poción de Vida (+30% HP)";
            } else if (rng < 66) {
                recompensa = "Gema de Suerte (+1 LUK)";
            } else {
                recompensa = "Poción de Maná (+50% MP)";
            }
            JOptionPane.showMessageDialog(this, "¡Has acertado la tirada! Recompensa: " + recompensa);
        } else {
            JOptionPane.showMessageDialog(this, "Fallaste la tirada. No hay recompensa.");
        }
    }

    private boolean calcularTiradaMoneda(int luk) {
        int bonus = Math.min(luk * 5, 25); // Máximo 75% de éxito
        int probabilidad = 50 + bonus;
        return Math.random() * 100 < probabilidad;
    }
}
