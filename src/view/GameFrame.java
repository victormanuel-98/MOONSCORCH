package view;

import javax.swing.*;
import java.awt.*;
import model.GameState;
import model.PlayerCombatData;
import java.util.List;
import java.util.Map;

public class GameFrame extends JFrame {

    private JPanel contenedor;

    public GameFrame() {
        setUndecorated(true);           // Ventana sin barra nativa
        setTitle("MoonScorch");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        contenedor = new JPanel(new BorderLayout());
        setContentPane(contenedor);

        mostrarLaunchView();
        setVisible(true);
    }

    /* ---------------- Gestión de vistas ---------------- */
    public void cambiarVista(JPanel p) {
        contenedor.removeAll();
        contenedor.add(p, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
        p.requestFocusInWindow();
    }

    /* ---------------- Pantalla Launch ---------------- */
    public void mostrarLaunchView() {
        LaunchView launchView = new LaunchView();
        cambiarVista(launchView);
    }

    /* ---------------- Pantalla Intro ---------------- */
    public void mostrarGameIntroScene() {
        GameIntroScene introScene = new GameIntroScene();
        cambiarVista(introScene);
    }

    /* ---------------- Menú de selección ---------------- */
    public void mostrarCharacterMenu() {
        CharacterMenu m = new CharacterMenu();
        m.setLadronAction(e -> cambiarVista(new ConfirmPlayer("ladron",   this)));
        m.setCaballeroAction(e -> cambiarVista(new ConfirmPlayer("caballero",this)));
        m.setClerigoAction(e -> cambiarVista(new ConfirmPlayer("clerigo",  this)));
        cambiarVista(m);
    }

    /* ---------------- Creación de personaje ---------------- */
    public void mostrarDataPlayer(String clase) {
        DataPlayer dp = new DataPlayer(clase, getBaseStats(clase));
        dp.setVolverAction(e -> mostrarCharacterMenu());
        dp.setResetAction(e -> dp.resetOpciones());
        dp.setContinuarAction(e -> {
            if (dp.getNombreHeroe().isBlank()) {
                JOptionPane.showMessageDialog(this,"Introduce un nombre válido.");
                return;
            }
            GameState.jugadorActual = new PlayerCombatData(
                    dp.getNombreHeroe(), clase, 1, dp.getStatsFinales());
            GameState.asignarObjetosIniciales();
            mostrarReadyForBattle();
        });
        cambiarVista(dp);
    }

    /* ---------------- Pantalla READY ---------------- */
    public void mostrarReadyForBattle() {
        ReadyForBattle r = new ReadyForBattle();
        r.setAdelanteAction(e -> {
            GameState.mapaActual = new MapOverview(this);
            cambiarVista(GameState.mapaActual);
        });
        r.setVolverAction(e -> mostrarCharacterMenu());
        cambiarVista(r);
    }

    /* ---------------- Enlaces auxiliares ---------------- */
    public void mostrarCombatView(MapOverview origen) {
        cambiarVista(new CombatView(origen));
    }

    public void mostrarTesoro(MapOverview origen) {
        TesoroView tv = new TesoroView(origen);
        cambiarVista(tv);
    }

    public void mostrarInventarioDesdeMapa(MapOverview o) { InventarioView.mostrarDesdeMapa(this,o); }
    public void mostrarInventarioDesdeCombate(CombatView o){ InventarioView.mostrarDesdeCombate(this,o); }

    /* ---------------- Stats base ---------------- */
    private int[] getBaseStats(String cls) {
        return switch (cls) {
            case "ladron"    -> new int[]{7,3,7,60,0,5};
            case "caballero" -> new int[]{10,6,2,80,0,3};
            case "clerigo"   -> new int[]{1,1,1,1,0,1};
            default           -> new int[]{5,5,5,50,0,1};
        };
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(GameFrame::new); }
    
    public void mostrarLogin() {
        cambiarVista(new LoginView());
    }

    public void mostrarRegistro() {
        cambiarVista(new RegisterView());
    }

    public void mostrarSeleccionPersonaje(List<Map<String, Object>> personajes) {
        CharacterSelectView selectView = new CharacterSelectView(personajes);
        cambiarVista(selectView);
    }

    public void mostrarMapaInicial() {
        cambiarVista(new MapOverview(this));
    }

	public void showCertificatesView() {
		// TODO Auto-generated method stub
		
	}

	public void showMainMenu() {
		// TODO Auto-generated method stub
		
	}
}