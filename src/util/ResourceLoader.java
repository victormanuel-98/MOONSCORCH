package util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ResourceLoader {


    private static final String IMAGE_PATH = "Resources/images/";
    private static final String FONT_PATH = "Resources/fonts/";

    public static ImageIcon loadImageIcon(String filename) {
        File file = new File(IMAGE_PATH + filename);
        if (file.exists()) {
            return new ImageIcon(file.getPath());
        } else {
            System.err.println("[ResourceLoader] Imagen no encontrada: " + filename);
            System.err.println("[ResourceLoader] Ruta absoluta esperada: " + file.getAbsolutePath());
            return null;
        }
    }

    public static Image loadImage(String filename) {
        try {
            File file = new File(IMAGE_PATH + filename);
            if (!file.exists()) {
                System.err.println("[ResourceLoader] Imagen no encontrada: " + filename);
                System.err.println("[ResourceLoader] Ruta absoluta esperada: " + file.getAbsolutePath());
                return null;
            }
            return ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("[ResourceLoader] Error cargando imagen: " + filename);
            return null;
        }
    }

    public static BufferedImage loadBufferedImage(String filename) {
        try {
            File file = new File(IMAGE_PATH + filename);
            if (!file.exists()) {
                System.err.println("[ResourceLoader] BufferedImage no encontrada: " + filename);
                System.err.println("[ResourceLoader] Ruta absoluta esperada: " + file.getAbsolutePath());
                return null;
            }
            return ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("[ResourceLoader] Error leyendo BufferedImage: " + filename);
            return null;
        }
    }

    public static ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        if (icon == null || icon.getImage() == null) return new ImageIcon();
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public static boolean exists(String filename) {
        return new File(IMAGE_PATH + filename).exists();
    }

    public static Font cargarFuente(String rutaRelativa, float tamaño) {
        try {
            File archivoFuente = new File(FONT_PATH + rutaRelativa);
            if (!archivoFuente.exists()) {
                System.err.println("[ResourceLoader] Fuente no encontrada: " + archivoFuente.getAbsolutePath());
                return null;
            }
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, archivoFuente);
            return fuente.deriveFont(tamaño);
        } catch (FontFormatException | IOException e) {
            System.err.println("[ResourceLoader] Error al cargar fuente: " + rutaRelativa);
            e.printStackTrace();
            return null;
        }
    }
}
