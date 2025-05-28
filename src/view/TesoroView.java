
package view;

import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;

public class TesoroView extends BaseView {

    private JLabel resultadoLabel;
    private JButton btnCara, btnCruz, btnAceptar;
    private String eleccionUsuario;
    private final Random random = new Random();
    private final MapOverview origen;
    private Image backgroundImage;

    private final Color ROJO_OSCURO = new Color(139, 0, 0);

    public TesoroView(MapOverview origen) {
        super();
        this.origen = origen;
        setLayout(null);
        setBackground(Color.BLACK);

        backgroundImage = cargarImagenFondo("/Resources/backgrounds/tesoro_background.png");

        add(new WindowControls((JFrame) SwingUtilities.getWindowAncestor(this)));

       
        JLabel line1 = crearLabel("HAS ENCONTRADO UN", 25);
        line1.setBounds(0, 80, 1280, 30);
        add(line1);

        JLabel line2 = crearLabel("IDOLO DE REZO", 34);
        line2.setBounds(0, 110, 1280, 40);
        add(line2);

        JLabel cofreLabel = new JLabel();
        cofreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cofreLabel.setBounds(540, 180, 200, 200);
        ImageIcon cofreIcon = cargarIcono("/Resources/enemies/cofre.png");
        if (cofreIcon != null) cofreLabel.setIcon(cofreIcon);
        add(cofreLabel);

        resultadoLabel = crearLabel("", 20);
        resultadoLabel.setBounds(0, 400, 1280, 30);
        add(resultadoLabel);

        JLabel instruccion = crearLabel("SELECCIONA CARA O CRUZ", 20);
        instruccion.setBounds(0, 440, 1280, 30);
        add(instruccion);

        btnCara = crearBoton("CARA");
        btnCruz = crearBoton("CRUZ");
        btnCara.setBounds(440, 480, 180, 40);
        btnCruz.setBounds(660, 480, 180, 40);
        btnCara.addActionListener(e -> hacerTirada("CARA"));
        btnCruz.addActionListener(e -> hacerTirada("CRUZ"));
        add(btnCara);
        add(btnCruz);

        btnAceptar = crearBoton("ACEPTAR");
        btnAceptar.setEnabled(false);
        btnAceptar.setBounds(540, 540, 200, 40);
        btnAceptar.addActionListener(e -> {
            origen.incrementarNodoActual();
            GameFrame frame = (GameFrame) SwingUtilities.getWindowAncestor(this);
            frame.cambiarVista(origen);
        });
        add(btnAceptar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void hacerTirada(String eleccion) {
        this.eleccionUsuario = eleccion;
        btnCara.setEnabled(false);
        btnCruz.setEnabled(false);

        resultadoLabel.setText("Lanzando moneda...");

        Timer timer = new Timer(1000, ev -> mostrarResultado());
        timer.setRepeats(false);
        timer.start();
    }

    private void mostrarResultado() {
        boolean saleCara = random.nextBoolean();
        String resultado = saleCara ? "CARA" : "CRUZ";

        if (resultado.equals(eleccionUsuario)) {
            int chance = random.nextInt(100);
            String recompensa;
            if (chance < 2) {
                recompensa = "¡Gema de Suerte!";
                GameState.jugadorActual.agregarObjeto("Gema de la Suerte", 1);
            } else if (chance < 22) {
                recompensa = "Poción de Vida (+30% HP)";
                GameState.jugadorActual.agregarObjeto("Poción de Vida", 1);
            } else {
                recompensa = "El ídolo no tiene nada para tí";
            }
            resultadoLabel.setText("Tuviste suerte, recibes " + recompensa);
        } else {
            resultadoLabel.setText("Mala suerte, prosigue tu viaje");
        }

        btnAceptar.setEnabled(true);
    }

    public void setVolverAction(ActionListener listener) {
        for (ActionListener al : btnAceptar.getActionListeners()) {
            btnAceptar.removeActionListener(al);
        }
        btnAceptar.addActionListener(listener);
    }

    private JLabel crearLabel(String texto, int tamaño) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("Serif", Font.BOLD, tamaño));
        lbl.setForeground(ROJO_OSCURO);
        return lbl;
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setForeground(ROJO_OSCURO);
        btn.setBackground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(ROJO_OSCURO, 2));
        return btn;
    }

    private ImageIcon cargarIcono(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            if (url != null) return new ImageIcon(url);
            return new ImageIcon("." + ruta);
        } catch (Exception e) {
            System.err.println("No se pudo cargar icono: " + ruta);
            return null;
        }
    }

    private Image cargarImagenFondo(String ruta) {
        ImageIcon icon = cargarIcono(ruta);
        return icon != null ? icon.getImage() : null;
    }
}
