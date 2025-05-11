package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GameFrame extends JFrame {

    private CardLayout layout;
    private JPanel container;
    private HashMap<String, JPanel> vistas;

    private String personajeSeleccionado;
    private int[] statsSeleccionados;
    private String nombreHeroe;

    public GameFrame() {
        super("MoonScorch");

        layout = new CardLayout();
        container = new JPanel(layout);
        vistas = new HashMap<>();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);

        // Vistas iniciales
        addVista("launch", new LaunchView());
        addVista("intro", new GameIntroScene());
        addVista("select", new CharacterMenu());

        setContentPane(container);
        setVisible(true);

        configurarNavegacion();
    }

    private void addVista(String nombre, JPanel vista) {
        container.add(vista, nombre);
        vistas.put(nombre, vista);
    }

    private void configurarNavegacion() {
        // Launch → Intro
        LaunchView lv = (LaunchView) vistas.get("launch");
        lv.setNewGameAction(e -> mostrarVista("intro"));
        lv.setExitAction(e -> System.exit(0));
        lv.setFullscreenAction(e -> toggleFullscreen());

        // Intro → Selección
        GameIntroScene intro = (GameIntroScene) vistas.get("intro");
        intro.setContinuarAction(e -> mostrarVista("select"));
        intro.setFullscreenAction(e -> toggleFullscreen());

        // Selección → Confirmación
        CharacterMenu menu = (CharacterMenu) vistas.get("select");

        menu.setLadronAction(e -> {
            personajeSeleccionado = "Ladron";
            statsSeleccionados = new int[]{8, 6, 9, 90, 25, 6};
            cargarVistaConfirmacion();
        });

        menu.setCaballeroAction(e -> {
            personajeSeleccionado = "Caballero";
            statsSeleccionados = new int[]{12, 10, 5, 120, 10, 3};
            cargarVistaConfirmacion();
        });

        menu.setClerigoAction(e -> {
            personajeSeleccionado = "Clerigo";
            statsSeleccionados = new int[]{6, 6, 6, 100, 40, 5};
            cargarVistaConfirmacion();
        });

        menu.setFullscreenAction(e -> toggleFullscreen());
    }

    private void cargarVistaConfirmacion() {
        ConfirmPlayer confirm = new ConfirmPlayer(personajeSeleccionado);

        confirm.setConfirmarAction(e -> {
            CharacterStats statsView = new CharacterStats(personajeSeleccionado, statsSeleccionados);
            statsView.setContinuarAction(ev -> cargarVistaDataPlayer());
            statsView.setFullscreenAction(ev -> toggleFullscreen());
            addVista("stats", statsView);
            mostrarVista("stats");
        });

        confirm.setFullscreenAction(e -> toggleFullscreen());

        addVista("confirm", confirm);
        mostrarVista("confirm");
    }

    private void cargarVistaDataPlayer() {
        DataPlayer dp = new DataPlayer(personajeSeleccionado, statsSeleccionados);

        dp.setResetAction(e -> dp.resetOpciones());

        dp.setContinuarAction(e -> {
            nombreHeroe = dp.getNombreHeroe();
            String mejora = dp.getOpcionSeleccionada();

            if (nombreHeroe.isEmpty() || mejora == null) {
                JOptionPane.showMessageDialog(this, "Debes escribir un nombre y elegir una mejora.");
                return;
            }

            cargarVistaReadyForBattle();
        });

        dp.setVolverAction(e -> mostrarVista("select"));
        dp.setFullscreenAction(e -> toggleFullscreen());

        addVista("data", dp);
        mostrarVista("data");
    }

    private void cargarVistaReadyForBattle() {
        ReadyForBattle rfb = new ReadyForBattle(nombreHeroe, personajeSeleccionado);

        rfb.setComenzarAction(e -> {
            JOptionPane.showMessageDialog(this, "Aquí iniciará la batalla...");
            // Aquí cargarías CombatView o la primera batalla
        });

        rfb.setFullscreenAction(e -> toggleFullscreen());

        addVista("ready", rfb);
        mostrarVista("ready");
    }

    public void mostrarVista(String nombre) {
        layout.show(container, nombre);
    }

    private void toggleFullscreen() {
        setExtendedState(getExtendedState() ^ JFrame.MAXIMIZED_BOTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
