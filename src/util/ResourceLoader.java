package util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ResourceLoader {

    // Se asume que las imágenes están en: Resources/images/
    private static final String IMAGE_PATH = "Resources/images/";

    /* Carga un ImageIcon desde la carpeta Resources/images/ */
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

    /* Carga una imagen escalable directamente (Image) */
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

    /* Carga una imagen como BufferedImage (ideal para pintar en paneles)*/
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

    /* Escala un ImageIcon al tamaño deseado */
    public static ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        if (icon == null || icon.getImage() == null) return new ImageIcon();
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    /* Verifica si existe un recurso */
    public static boolean exists(String filename) {
        return new File(IMAGE_PATH + filename).exists();
    }
}