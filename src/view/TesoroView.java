package view;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TesoroView extends BaseView {

    private JLabel monedaLabel;
    private JLabel resultadoLabel;
    private JButton btnCara, btnCruz, btnAceptar;

    private String eleccionUsuario;
    private final Random random = new Random();

    public TesoroView() {
        super();
        setLayout(null);

        JLabel titulo = new JLabel("Tirada del Tesoro", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(400, 50, 480, 40);
        add(titulo);

        JLabel instruccion = new JLabel("Elige tu lado de la moneda:", SwingConstants.CENTER);
        instruccion.setForeground(Color.WHITE);
        instruccion.setFont(new Font("SansSerif", Font.PLAIN, 18));
        instruccion.setBounds(420, 120, 440, 30);
        add(instruccion);

        btnCara = new JButton("Cara ($)");
        btnCara.setBounds(460, 170, 120, 40);
        add(btnCara);

        btnCruz = new JButton("Cruz (X)");
        btnCruz.setBounds(700, 170, 120, 40);
        add(btnCruz);

        monedaLabel = new JLabel("¿?", SwingConstants.CENTER);
        monedaLabel.setFont(new Font("Serif", Font.BOLD, 100));
        monedaLabel.setForeground(Color.YELLOW);
        monedaLabel.setBounds(540, 240, 200, 100);
        add(monedaLabel);

        resultadoLabel = new JLabel("", SwingConstants.CENTER);
        resultadoLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        resultadoLabel.setForeground(Color.WHITE);
        resultadoLabel.setBounds(400, 350, 480, 60);
        add(resultadoLabel);

        btnAceptar = new JButton("Aceptar y volver al mapa");
        btnAceptar.setBounds(540, 430, 200, 40);
        btnAceptar.setEnabled(false);
        add(btnAceptar);

        // Eventos
        btnCara.addActionListener(e -> hacerTirada("Cara"));
        btnCruz.addActionListener(e -> hacerTirada("Cruz"));
        btnAceptar.addActionListener(e -> volverAlMapa());
    }

    private void hacerTirada(String eleccion) {
        this.eleccionUsuario = eleccion;
        btnCara.setEnabled(false);
        btnCruz.setEnabled(false);

        monedaLabel.setText("...");
        resultadoLabel.setText("Lanzando moneda...");
        Timer timer = new Timer(1000, e -> mostrarResultado());
        timer.setRepeats(false);
        timer.start();
    }

    private void mostrarResultado() {
        String resultado = random.nextBoolean() ? "Cara" : "Cruz";
        monedaLabel.setText(resultado.equals("Cara") ? "$" : "X");

        boolean acierta = resultado.equals(eleccionUsuario);
        boolean gana = acierta && pasaTiradaLUK();

        if (gana) {
            String recompensa = calcularRecompensa();
            resultadoLabel.setText("<html>¡Acertaste la tirada!<br>Recompensa: " + recompensa + "</html>");
        } else if (acierta) {
            resultadoLabel.setText("Acertaste... pero la suerte no te acompañó. No hay recompensa.");
        } else {
            resultadoLabel.setText("Fallaste la tirada. No hay recompensa.");
        }

        btnAceptar.setEnabled(true);
    }

    private boolean pasaTiradaLUK() {
        int luk = GameState.jugadorActual.getLUK();
        int chance = 40 + (luk * 4); // Máx 60%
        return random.nextInt(100) < chance;
    }

    private String calcularRecompensa() {
        int chance = random.nextInt(100);
        if (chance < 1) return "¡Gema de Suerte! (+1 LUK)";
        else if (chance < 51) return "Poción de Vida (+30% HP)";
        else return "Poción de Maná (+50% MP)";
    }

    private void volverAlMapa() {
        GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(this);
        frame.cambiarVista(model.GameState.mapaActual);
    }
}