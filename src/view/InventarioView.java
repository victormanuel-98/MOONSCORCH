package view;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class InventarioView extends BaseView {

    public InventarioView() {
        super();
        setLayout(null);

        JLabel titulo = new JLabel("Inventario", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(400, 40, 480, 40);
        add(titulo);

        Map<String, Integer> inventario = GameState.jugadorActual.getInventario();

        if (inventario.isEmpty()) {
            JLabel vacio = new JLabel("No tienes objetos.", SwingConstants.CENTER);
            vacio.setForeground(Color.LIGHT_GRAY);
            vacio.setFont(new Font("SansSerif", Font.PLAIN, 18));
            vacio.setBounds(400, 120, 480, 30);
            add(vacio);
        } else {
            int y = 120;
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                String item = entry.getKey();
                int cantidad = entry.getValue();
                JButton btnItem = new JButton(String.format("Usar: %s (x%d)", item, cantidad));
                btnItem.setBounds(480, y, 320, 40);
                btnItem.addActionListener(e -> usarObjeto(item));
                add(btnItem);
                y += 60;
            }
        }

        JButton btnVolver = new JButton("Volver al mapa");
        btnVolver.setBounds(540, 500, 200, 40);
        btnVolver.addActionListener(e -> {
            GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(this);
            frame.cambiarVista(GameState.mapaActual);
        });
        add(btnVolver);
    }

    private void usarObjeto(String item) {
        // Obtener la cantidad actual del objeto
        int cantidadActual = GameState.jugadorActual.getInventario().get(item);
        
        // Simular efecto del objeto
        JOptionPane.showMessageDialog(this,
            "Has usado el objeto: " + item + "\n(Efecto simulado)",
            "Objeto usado", JOptionPane.INFORMATION_MESSAGE);
        
        // Si solo queda 1, eliminar el objeto
        if (cantidadActual <= 1) {
            GameState.jugadorActual.eliminarObjeto(item);
        } else {
            // Si hay más de 1, reducir la cantidad
            GameState.jugadorActual.reducirCantidadObjeto(item);
        }

        // Recargar inventario
        GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(this);
        frame.cambiarVista(new InventarioView());
    }

    public static void mostrarDesdeMapa(Component parent) {
        GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(parent);
        frame.cambiarVista(new InventarioView());
    }
}