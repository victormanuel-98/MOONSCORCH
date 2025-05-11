package view;

import model.GameState;
import model.PlayerCombatData;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private CardLayout layout;
    private JPanel mainPanel;

    private String personajeSeleccionado;

    public GameFrame() {
        setTitle("MoonScorch");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(true);

        layout = new CardLayout();
        mainPanel = new JPanel(layout);
        setContentPane(mainPanel);

        mostrarLaunch();
        setVisible(true);
    }

    private void mostrarLaunch() {
        LaunchView launch = new LaunchView();

        // Conecta con GameIntroScene
        launch.setNewGameAction(e -> {
            GameIntroScene intro = new GameIntroScene();
            intro.setContinuarAction(ev -> mostrarCharacterMenu());
            intro.setFullscreenAction(ev -> setExtendedState(JFrame.MAXIMIZED_BOTH));
            showPanel(intro);
        });

        launch.setExitAction(e -> System.exit(0));
        launch.setFullscreenAction(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));

        showPanel(launch);
    }

    private void showPanel(JPanel panel) {
        mainPanel.add(panel, panel.getClass().getName());
        layout.show(mainPanel, panel.getClass().getName());
    }

    public void mostrarCharacterMenu() {
        CharacterMenu menu = new CharacterMenu();

        menu.setLadronAction(e -> {
            personajeSeleccionado = "ladron";
            showPanel(new ConfirmPlayer(personajeSeleccionado,this));
        });

        menu.setCaballeroAction(e -> {
            personajeSeleccionado = "caballero";
            showPanel(new ConfirmPlayer(personajeSeleccionado,this));
        });

        menu.setClerigoAction(e -> {
            personajeSeleccionado = "mago";
            showPanel(new ConfirmPlayer(personajeSeleccionado,this));
        });

        menu.setFullscreenAction(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));
        showPanel(menu);
    }

    public void mostrarDataPlayer(String personaje) {
        int[] baseStats;
        switch (personaje) {
            case "ladron":
                baseStats = new int[]{5, 3, 7, 60, 30, 1};
                break;
            case "caballero":
                baseStats = new int[]{7, 6, 2, 80, 20, 1};
                break;
            case "mago":
                baseStats = new int[]{4, 2, 4, 50, 60, 1};
                break;
            default:
                baseStats = new int[]{5, 5, 5, 50, 50, 1};
        }

        DataPlayer dataPlayer = new DataPlayer(personaje, baseStats);

        dataPlayer.setContinuarAction(e -> {
            String nombre = dataPlayer.getNombreHeroe();
            int[] statsFinales = dataPlayer.getStatsFinales();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un nombre para tu héroe.");
                return;
            }

            GameState.jugadorActual = new PlayerCombatData(nombre, personaje, 1, statsFinales);
            mostrarReadyForBattle();
        });

        dataPlayer.setVolverAction(e -> mostrarCharacterMenu());
        dataPlayer.setResetAction(e -> dataPlayer.resetOpciones());
        dataPlayer.setFullscreenAction(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));

        showPanel(dataPlayer);
    }

    public void mostrarReadyForBattle() {
        ReadyForBattle readyView = new ReadyForBattle();

        readyView.setComenzarAction(e -> showPanel(new MapOverview()));
        readyView.setFullscreenAction(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));

        showPanel(readyView);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}