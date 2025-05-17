package view;

import model.GameState;
import model.PlayerCombatData;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private CardLayout layout;
    private JPanel mainPanel;
    private GameState gameState;
    private String personajeSeleccionado;

    public GameFrame() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("MoonScorch");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(true);

        layout = new CardLayout();
        mainPanel = new JPanel(layout);
        setContentPane(mainPanel);
        
        // Inicializar el GameState
        gameState = new GameState();

        mostrarLaunch();
        setVisible(true);
    }

    private void mostrarLaunch() {
        LaunchView launch = new LaunchView();

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
        String panelName = panel.getClass().getName();
        // Verificar si el panel ya está en el mainPanel
        boolean exists = false;
        for (Component comp : mainPanel.getComponents()) {
            if (comp.equals(panel)) {
                exists = true;
                break;
            }
        }
        
        // Si no existe, añadirlo
        if (!exists) {
            mainPanel.add(panel, panelName);
        }
        
        // Mostrar el panel
        layout.show(mainPanel, panelName);
        
        // Asegurar que el panel tiene el foco
        panel.requestFocusInWindow();
    }

    // NUEVO MÉTODO PÚBLICO para que otras vistas puedan solicitar un cambio
    public void cambiarVista(JPanel panel) {
        showPanel(panel);
    }

    public void mostrarCharacterMenu() {
        CharacterMenu menu = new CharacterMenu();

        menu.setLadronAction(e -> {
            personajeSeleccionado = "ladron";
            showPanel(new ConfirmPlayer(personajeSeleccionado, this));
        });

        menu.setCaballeroAction(e -> {
            personajeSeleccionado = "caballero";
            showPanel(new ConfirmPlayer(personajeSeleccionado, this));
        });

        menu.setClerigoAction(e -> {
            personajeSeleccionado = "mago";
            showPanel(new ConfirmPlayer(personajeSeleccionado, this));
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

            // Crear PlayerCombatData y asignarlo al GameState
            PlayerCombatData playerData = new PlayerCombatData(nombre, personaje, 1, statsFinales);
            gameState.setPlayerCombatData(playerData);
            gameState.asignarObjetosIniciales();
            mostrarReadyForBattle();
        });

        dataPlayer.setVolverAction(e -> mostrarCharacterMenu());
        dataPlayer.setResetAction(e -> dataPlayer.resetOpciones());
        dataPlayer.setFullscreenAction(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));

        showPanel(dataPlayer);
    }

    public void mostrarReadyForBattle() {
        ReadyForBattle readyView = new ReadyForBattle();

        readyView.setComenzarAction(e -> {
            try {
                // Crear el panel de mapa
                MapOverview mapOverview = new MapOverview(gameState);
                
                // Asignar el panel al GameState
                gameState.setMapOverview(mapOverview);
                
                // Mostrar el panel
                cambiarVista(mapOverview);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Error al cargar el mapa: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        readyView.setFullscreenAction(e -> setExtendedState(JFrame.MAXIMIZED_BOTH));

        showPanel(readyView);
    }

    public static void main(String[] args) {
        try {
            // Establecer el Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                new GameFrame();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error al iniciar la aplicación: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    // Getter para acceder al GameState desde otras clases
    public GameState getGameState() {
        return gameState;
    }
}