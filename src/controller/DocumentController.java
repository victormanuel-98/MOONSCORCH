package controller;

import dao.GameDocumentDAO;
import model.GameDocument;
import model.GameState;
import view.DocumentLibraryView;
import view.DocumentView;
import view.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// Controlador para gestionar los documentos del juego

public class DocumentController {
    
    private GameDocumentDAO documentDAO;
    private GameState gameState;
    private GameFrame gameFrame;
    
    public DocumentController(GameState gameState, GameFrame gameFrame) {
        this.documentDAO = new GameDocumentDAO();
        this.gameState = gameState;
        this.gameFrame = gameFrame;
    }
    
    public void showDocumentLibrary() {
        DocumentLibraryView libraryView = new DocumentLibraryView();
        
        libraryView.setBackAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Volver al mapa o menú principal
                gameFrame.cambiarVista(gameState.getMapOverview());
            }
        });
        
        gameFrame.cambiarVista(libraryView);
    }
    
    public void showDocument(int documentId) {
        GameDocument document = documentDAO.getById(documentId);
        
        if (document != null) {
            // Marcar como leído
            documentDAO.markAsRead(documentId);
            
            DocumentView documentView = new DocumentView(document);
            
            documentView.setCloseAction(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Volver al mapa o menú principal
                    gameFrame.cambiarVista(gameState.getMapOverview());
                }
            });
            
            gameFrame.cambiarVista(documentView);
        }
    }
    
    public void showDocumentFromFile(String filePath, GameDocument.DocumentType type, String title, String author) {
        try {
            if (!new File(filePath).exists()) {
                JOptionPane.showMessageDialog(
                    gameFrame,
                    "El archivo no existe: " + filePath,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            
            if (title == null || title.isEmpty()) {
                title = new File(filePath).getName().replace(".txt", "");
            }
            
            GameDocument document = new GameDocument(0, title, content, type);
            document.setAuthor(author != null ? author : "");
            document.setLocation("Archivo: " + filePath);
            document.setCollected(true);
            
            // Guardar en la base de datos
            int documentId = documentDAO.save(document);
            document.setId(documentId);
            
            // Mostrar el documento
            DocumentView documentView = new DocumentView(document);
            
            documentView.setCloseAction(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Volver al mapa o menú principal
                    gameFrame.cambiarVista(gameState.getMapOverview());
                }
            });
            
            gameFrame.cambiarVista(documentView);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                gameFrame,
                "Error al leer el archivo: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public boolean collectDocument(int documentId, String location) {
        return documentDAO.collectDocument(documentId, location);
    }
    
    public int loadDocumentsFromDirectory(String directoryPath, GameDocument.DocumentType type) {
        return documentDAO.loadDocumentsFromFiles(directoryPath, type);
    }
    
    public List<GameDocument> getUnreadDocuments() {
        return documentDAO.getUnreadDocuments();
    }
    
    public boolean hasUnreadDocuments() {
        return !documentDAO.getUnreadDocuments().isEmpty();
    }
}