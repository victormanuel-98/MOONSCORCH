package view;

import util.FileUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

// Aquí se muestran los certificados de finalización del juego cuando se termina

public class CertificatesView extends BaseView {

    private JList<String> certificatesList;
    private DefaultListModel<String> listModel;
    private JButton viewButton;
    private JButton deleteButton;
    private JButton backButton;

    private final String CERTIFICATES_DIR = "Resources/certificates/";

    public CertificatesView() {
        initComponents();
        loadCertificates();
    }

    @Override
	public void initComponents() {
        super.initComponents();

        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setBackground(new Color(40, 40, 50));

        JLabel titleLabel = new JLabel("Certificados de Finalización");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(220, 200, 150));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);

        listModel = new DefaultListModel<>();
        certificatesList = new JList<>(listModel);
        certificatesList.setFont(new Font("SansSerif", Font.PLAIN, 16));
        certificatesList.setForeground(new Color(220, 220, 220));
        certificatesList.setBackground(new Color(60, 60, 70));
        certificatesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        certificatesList.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(certificatesList);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 120), 2));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel infoLabel = new JLabel("<html>Selecciona un certificado para verlo o eliminarlo.<br><br>" +
                "Los certificados se guardan automáticamente al completar el juego y contienen " +
                "información sobre tu personaje y estadísticas de juego.</html>");
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(200, 200, 200));
        infoPanel.add(infoLabel);

        centerPanel.add(infoPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        viewButton = new JButton("Ver Certificado");
        viewButton.setFont(new Font("Serif", Font.BOLD, 16));
        viewButton.addActionListener(e -> viewSelectedCertificate());
        buttonPanel.add(viewButton);

        deleteButton = new JButton("Eliminar");
        deleteButton.setFont(new Font("Serif", Font.BOLD, 16));
        deleteButton.addActionListener(e -> deleteSelectedCertificate());
        buttonPanel.add(deleteButton);

        backButton = new JButton("Volver");
        backButton.setFont(new Font("Serif", Font.BOLD, 16));
        backButton.addActionListener(e -> goBack());
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        certificatesList.addListSelectionListener(e -> {
            boolean hasSelection = !certificatesList.isSelectionEmpty() &&
                    !certificatesList.getSelectedValue().equals("No hay certificados guardados");
            viewButton.setEnabled(hasSelection);
            deleteButton.setEnabled(hasSelection);
        });

        viewButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    protected void viewSelectedCertificate() {
        String selected = certificatesList.getSelectedValue();
        if (selected != null && !selected.equals("No hay certificados guardados")) {
            String fileName = selected.replace(" - ", "_") + ".txt";
            File certFile = new File(CERTIFICATES_DIR + fileName);

            if (certFile.exists()) {
                try {
                    Desktop.getDesktop().open(certFile);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "No se pudo abrir el certificado.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    protected void deleteSelectedCertificate() {
        String selected = certificatesList.getSelectedValue();
        if (selected != null && !selected.equals("No hay certificados guardados")) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas eliminar este certificado?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String fileName = selected.replace(" - ", "_") + ".txt";
                File certFile = new File(CERTIFICATES_DIR + fileName);
                if (certFile.delete()) {
                    loadCertificates();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el certificado.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    protected void goBack() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.dispose();
        }
    }

    private void loadCertificates() {
        listModel.clear();

        try {
            Path dir = Paths.get(CERTIFICATES_DIR);

            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            List<Path> certificateFiles = Files.list(dir)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .collect(Collectors.toList());

            if (certificateFiles.isEmpty()) {
                listModel.addElement("No hay certificados guardados");
            } else {
                for (Path path : certificateFiles) {
                    String fileName = path.getFileName().toString();
                    String displayName = fileName.replace(".txt", "")
                            .replace("_", " - ");
                    listModel.addElement(displayName);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los certificados.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
