package view;

import model.GameState;
import model.Jugador;
import util.FileUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.PlayerDao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Vista que muestra la pantalla de victoria al completar el juego.
 */
public class GameCompletionView extends BaseView {

    private JTextArea completionText;
    private JButton saveButton;
    private JButton mainMenuButton;
    private JButton allCertificatesButton;
    private String completionMessage;

    public GameCompletionView() {
        System.out.println(">>> GameCompletionView cargado");
        initComponents();
        generateCompletionMessage();
        saveCompletionCertificate();
    }

    private void saveCompletionCertificate() {
		// TODO Auto-generated method stub
		
	}

	private void generateCompletionMessage() {
        Jugador jugador = GameState.convertirAJugador(GameState.jugadorActual);
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(42); // Pon una variable real si la tienes
        LocalDateTime endTime = LocalDateTime.now();

        Duration playTime = Duration.between(startTime, endTime);
        long hours = playTime.toHours();
        long minutes = playTime.toMinutesPart();
        long seconds = playTime.toSecondsPart();

        StringBuilder message = new StringBuilder();
        message.append("                   MOONSCORCH                   \n");
        message.append("==============================================\n\n");
        message.append("Has completado con éxito tu aventura en el mundo de MOONSCORCH.\n\n");
        message.append("Héroe: ").append(jugador.getName()).append("\n");
        message.append("Clase: ").append(jugador.getCharacterClass().getName()).append("\n");
        message.append("Nivel alcanzado: ").append(jugador.getLevel()).append("\n\n");
        message.append("Tiempo de juego: ");
        if (hours > 0) message.append(hours).append(" horas, ");
        message.append(minutes).append(" minutos y ").append(seconds).append(" segundos\n\n");

        message.append("==============================================\n");
        message.append("ESTADÍSTICAS DE AVENTURA\n");
        message.append("==============================================\n\n");
        message.append("Enemigos derrotados: ").append(20).append("\n"); // Puedes poner un valor real
        message.append("Tesoros encontrados: ").append(5).append("\n");
        message.append("Oro acumulado: ").append(PlayerDao.getGold()).append("\n");
        message.append("Pociones usadas: ").append(3).append("\n\n");

        message.append("==============================================\n\n");
        message.append("\"Tu nombre será recordado en las leyendas de MOONSCORCH.\n");
        message.append("Has demostrado valor, astucia y determinación en tu viaje.\n");
        message.append("Que tus hazañas inspiren a futuros héroes.\"\n\n");
        message.append("                   - Los Creadores -                   \n\n");
        message.append("==============================================\n");
        message.append("Fecha de finalización: ").append(
                endTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        completionMessage = message.toString();
        completionText.setText(completionMessage);
        completionText.setCaretPosition(0);
    }
}
