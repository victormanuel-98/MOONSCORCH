package view;

import model.GameDocument;
import util.FileUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Vista para mostrar documentos del juego.
 */
public class DocumentView extends BaseView {
    
    private GameDocument document;
    private JTextArea contentArea;
    private JLabel titleLabel;
    private JLabel typeLabel;
    private JLabel authorLabel;
    private JButton closeButton;
    private JButton nextButton;
    private JButton prevButton;
    private List<GameDocument> documentList;
    private int currentIndex;
    
    /**
     * Constructor para mostrar un documento específico.
     * 
     * @param document Documento a mostrar
     */
    public DocumentView(GameDocument document) {
        this.document = document;
        this.documentList = null;
        this.currentIndex = -1;
        initComponents();
        displayDocument();
    }
    
    /**
     * Constructor para mostrar una lista de documentos.
     * 
     * @param documentList Lista de documentos
     * @param startIndex Índice inicial
     */
    public DocumentView(List<GameDocument> documentList, int startIndex) {
        if (documentList == null || documentList.isEmpty()) {
            throw new IllegalArgumentException("La lista de documentos no puede estar vacía");
        }
        
        this.documentList = documentList;
        this.currentIndex = Math.min(Math.max(0, startIndex), documentList.size() - 1);
        this.document = documentList.get(currentIndex);
        
        initComponents();
        displayDocument();
        updateNavigationButtons();
    }
    
    @Override
	public void initComponents() {
        super.initComponents();
        
        // Configurar el panel principal con un BorderLayout
        setLayout(new BorderLayout());
        
        // Panel superior con título y tipo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(50, 40, 30));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(220, 200, 150));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setOpaque(false);
        
        typeLabel = new JLabel();
        typeLabel.setFont(new Font("Serif", Font.ITALIC, 16));
        typeLabel.setForeground(new Color(180, 160, 120));
        infoPanel.add(typeLabel);
        
        authorLabel = new JLabel();
        authorLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        authorLabel.setForeground(new Color(180, 160, 120));
        infoPanel.add(authorLabel);
        
        headerPanel.add(infoPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Panel central con el contenido
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        contentPanel.setBackground(new Color(240, 230, 210));
        
        contentArea = new JTextArea();
        contentArea.setFont(new Font("Serif", Font.PLAIN, 16));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setMargin(new Insets(10, 10, 10, 10));
        contentArea.setBackground(new Color(240, 230, 210));
        
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Añadir una imagen de fondo de pergamino
        try {
            String imagePath = "Resources/images/parchment.png";
            if (new File(imagePath).exists()) {
                BufferedImage backgroundImage = FileUtils.loadImage(imagePath);
                if (backgroundImage != null) {
                    JPanel backgroundPanel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                        }
                    };
                    backgroundPanel.setLayout(new BorderLayout());
                    backgroundPanel.add(contentPanel, BorderLayout.CENTER);
                    add(backgroundPanel, BorderLayout.CENTER);
                } else {
                    add(contentPanel, BorderLayout.CENTER);
                }
            } else {
                add(contentPanel, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            add(contentPanel, BorderLayout.CENTER);
            System.err.println("Error al cargar imagen de fondo: " + e.getMessage());
        }
        
        // Panel inferior con botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(50, 40, 30));
        
        prevButton = new JButton("Anterior");
        prevButton.setFont(new Font("Serif", Font.BOLD, 14));
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousDocument();
            }
        });
        buttonPanel.add(prevButton);
        
        closeButton = new JButton("Cerrar");
        closeButton.setFont(new Font("Serif", Font.BOLD, 14));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeDocument();
            }
        });
        buttonPanel.add(closeButton);
        
        nextButton = new JButton("Siguiente");
        nextButton.setFont(new Font("Serif", Font.BOLD, 14));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextDocument();
            }
        });
        buttonPanel.add(nextButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Muestra el documento actual.
     */
    private void displayDocument() {
        if (document != null) {
            titleLabel.setText(document.getTitle());
            typeLabel.setText(document.getTypeDisplayName());
            
            if (document.getAuthor() != null && !document.getAuthor().isEmpty()) {
                authorLabel.setText("por " + document.getAuthor());
            } else {
                authorLabel.setText("");
            }
            
            contentArea.setText(document.getContent());
            contentArea.setCaretPosition(0);
            
            // Marcar como leído
            document.markAsRead();
        }
    }
    
    /**
     * Actualiza los botones de navegación según la posición actual.
     */
    private void updateNavigationButtons() {
        if (documentList == null || documentList.isEmpty()) {
            prevButton.setVisible(false);
            nextButton.setVisible(false);
            return;
        }
        
        prevButton.setEnabled(currentIndex > 0);
        nextButton.setEnabled(currentIndex < documentList.size() - 1);
    }
    
    /**
     * Muestra el documento anterior de la lista.
     */
    private void showPreviousDocument() {
        if (documentList != null && currentIndex > 0) {
            currentIndex--;
            document = documentList.get(currentIndex);
            displayDocument();
            updateNavigationButtons();
        }
    }
    
    /**
     * Muestra el siguiente documento de la lista.
     */
    private void showNextDocument() {
        if (documentList != null && currentIndex < documentList.size() - 1) {
            currentIndex++;
            document = documentList.get(currentIndex);
            displayDocument();
            updateNavigationButtons();
        }
    }
    
    /**
     * Cierra la vista del documento.
     */
    private void closeDocument() {
        // Aquí deberías implementar la lógica para volver a la vista anterior
        // Por ejemplo, usando el CardLayout o notificando al controlador
        Container parent = getParent();
        if (parent != null && parent.getLayout() instanceof CardLayout) {
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.previous(parent);
        }
    }
    
    /**
     * Establece la acción para el botón de cerrar.
     * 
     * @param listener ActionListener para el botón de cerrar
     */
    public void setCloseAction(ActionListener listener) {
        closeButton.addActionListener(listener);
    }
    
    /**
     * Obtiene el documento actual.
     * 
     * @return Documento actual
     */
    public GameDocument getDocument() {
        return document;
    }
}