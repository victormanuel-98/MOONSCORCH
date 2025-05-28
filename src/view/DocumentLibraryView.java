package view;

import model.GameDocument;
import dao.GameDocumentDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

/**
 * Vista para mostrar la biblioteca de documentos recolectados.
 */
public class DocumentLibraryView extends BaseView {
    
    private JList<GameDocument> documentList;
    private DefaultListModel<GameDocument> listModel;
    private JButton backButton;
    private JButton readButton;
    private JComboBox<String> filterComboBox;
    private GameDocumentDAO documentDAO;
    
    /**
     * Constructor de la vista de biblioteca de documentos.
     */
    public DocumentLibraryView() {
        documentDAO = new GameDocumentDAO();
        initComponents();
        loadDocuments();
    }
    
    @Override
	public void initComponents() {
        super.initComponents();
        
        setLayout(new BorderLayout());
        
        // Panel de título
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(50, 40, 30));
        titlePanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Biblioteca de Documentos");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(220, 200, 150));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        // Panel de filtro
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setOpaque(false);
        
        JLabel filterLabel = new JLabel("Filtrar por: ");
        filterLabel.setForeground(new Color(220, 200, 150));
        filterPanel.add(filterLabel);
        
        String[] filterOptions = {"Todos", "No leídos", "Pergaminos", "Libros", "Notas", "Cartas", "Diarios", "Carteles", "Runas", "Recetas", "Mapas", "Certificados"};
        filterComboBox = new JComboBox<>(filterOptions);
        filterComboBox.setBackground(new Color(70, 60, 50));
        filterComboBox.setForeground(new Color(220, 200, 150));
        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDocuments();
            }
        });
        filterPanel.add(filterComboBox);
        
        titlePanel.add(filterPanel, BorderLayout.EAST);
        add(titlePanel, BorderLayout.NORTH);
        
        // Lista de documentos
        listModel = new DefaultListModel<>();
        documentList = new JList<>(listModel);
        documentList.setCellRenderer(new DocumentListCellRenderer());
        documentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        documentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelectedDocument();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(documentList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getViewport().setBackground(new Color(240, 230, 210));
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(50, 40, 30));
        
        backButton = new JButton("Volver");
        backButton.setFont(new Font("Serif", Font.BOLD, 14));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        buttonPanel.add(backButton);
        
        readButton = new JButton("Leer");
        readButton.setFont(new Font("Serif", Font.BOLD, 14));
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSelectedDocument();
            }
        });
        buttonPanel.add(readButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Carga los documentos según el filtro seleccionado.
     */
    private void loadDocuments() {
        listModel.clear();
        
        String filter = (String) filterComboBox.getSelectedItem();
        List<GameDocument> documents;
        
        if ("No leídos".equals(filter)) {
            documents = documentDAO.getUnreadDocuments();
        } else if ("Todos".equals(filter)) {
            documents = documentDAO.getAllCollected();
        } else {
            // Filtrar por tipo
            GameDocument.DocumentType type = null;
            
            switch (filter) {
                
                case "Certificados":
                    type = GameDocument.DocumentType.CERTIFICATE;
                    break;
                default:
                    type = null;
                    break;
            }
            
            if (type != null) {
                documents = documentDAO.getByType(type);
            } else {
                documents = documentDAO.getAllCollected();
            }
        }
        
        for (GameDocument document : documents) {
            listModel.addElement(document);
        }
        
        if (listModel.isEmpty()) {
            readButton.setEnabled(false);
        } else {
            documentList.setSelectedIndex(0);
            readButton.setEnabled(true);
        }
    }
    
    /**
     * Abre el documento seleccionado.
     */
    private void openSelectedDocument() {
        GameDocument selectedDocument = documentList.getSelectedValue();
        
        if (selectedDocument != null) {
            // Marcar como leído
            documentDAO.markAsRead(selectedDocument.getId());
            
            // Mostrar la vista del documento
            DocumentView documentView = new DocumentView(selectedDocument);
            documentView.setCloseAction(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Volver a esta vista
                    Container parent = getParent();
                    if (parent != null) {
                        CardLayout layout = (CardLayout) parent.getLayout();
                        layout.show(parent, "documentLibrary");
                        loadDocuments(); // Recargar para actualizar estado de lectura
                    }
                }
            });
            
            // Mostrar la vista del documento
            Container parent = getParent();
            if (parent != null && parent.getLayout() instanceof CardLayout) {
                parent.add(documentView, "documentView");
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "documentView");
            }
        }
    }
    
    /**
     * Vuelve a la vista anterior.
     */
    private void goBack() {
        Container parent = getParent();
        if (parent != null && parent.getLayout() instanceof CardLayout) {
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.previous(parent);
        }
    }
    
    /**
     * Establece la acción para el botón de volver.
     * 
     * @param listener ActionListener para el botón de volver
     */
    public void setBackAction(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    
    /**
     * Renderer personalizado para la lista de documentos.
     */
    private class DocumentListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof GameDocument) {
                GameDocument document = (GameDocument) value;
                
                // Configurar el texto
                label.setText(document.getTitle());
                
                // Configurar el icono según el tipo
                String iconPath = "Resources/images/icons/";
                switch (document.getType()) {
                
                    case CERTIFICATE:
                        iconPath += "certificate.png";
                        break;
                    default:
                        iconPath += "document.png";
                        break;
                }
                
                try {
                    if (new File(iconPath).exists()) {
                        label.setIcon(new ImageIcon(iconPath));
                    }
                } catch (Exception e) {
                    // Si no se puede cargar el icono, no hacer nada
                }
                
                // Estilo para documentos no leídos
                if (!document.isRead()) {
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                    label.setText(label.getText() + " (No leído)");
                }
            }
            
            return label;
        }
    }
}