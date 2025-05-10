package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameIntroScene extends JFrame {

    public GameIntroScene() {
        setTitle("MoonScorch - Comienzo de la Aventura");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 600);
        setMinimumSize(new Dimension(800, 500));

        JPanel contentPanel = new BackgroundPanel("./Resources/images/reyAmarillo.png"); // o fondo negro si no hay imagen
        contentPanel.setLayout(null);
        setContentPane(contentPanel);

        JTextArea loreText = new JTextArea();
        loreText.setEditable(false);
        loreText.setWrapStyleWord(true);
        loreText.setLineWrap(true);
        loreText.setOpaque(false);
        loreText.setForeground(Color.WHITE);
        loreText.setFont(new Font("Serif", Font.PLAIN, 18));
        loreText.setText("Tu aventura comienza en las puertas ruinosas de Thale...\n\nHace siglos, bajo la luz eterna de una luna rota, el Rey de Amarillo alzó la ciudad de Thale, un milagro de piedra y oro perdido en el confín del mundo. Dicen que gobernaba con sabiduría, pero su obsesión con los astros lo llevó a construir torres que tocaban el firmamento, hasta que un día la luna le respondió.\n\nLo que descendíó no fue luz, sino corrupción.\n\nThale fue consumida por un fuego que no arde: la Quemadura Lunar, una plaga invisible que tuerce la carne, quiebra la mente y deja atrás monstruos que solían ser humanos. Ahora, las ruinas de Thale se retuercen bajo un cielo sin sol, custodiadas por horrores que susurran en lenguas olvidadas.\n\nTú eres uno de los pocos lo bastante desesperados —o valientes— para cruzar sus murallas. Aventurero, carroñero, héroe o loco... Nadie sale igual de Thale. Si sales.\n\nEl Rey de Amarillo aún susurra desde su trono quebrado. Y la luna te está mirando.");

        JScrollPane scrollPane = new JScrollPane(loreText);
        scrollPane.setBounds(100, 30, 800, 450);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        contentPanel.add(scrollPane);

        JButton btnEmpezar = new JButton("ENTRAR A THALE");
        btnEmpezar.setBounds(412, 500, 200, 40);
        btnEmpezar.setBackground(new Color(0, 100, 0));
        btnEmpezar.setForeground(Color.WHITE);
        btnEmpezar.setFocusPainted(false);
        contentPanel.add(btnEmpezar);

        JButton btnFull = new JButton("⛶");
        btnFull.setFont(new Font("Dialog", Font.BOLD, 20));
        btnFull.setForeground(Color.WHITE);
        btnFull.setBackground(new Color(0, 0, 0, 180));
        btnFull.setFocusPainted(false);
        btnFull.setBorderPainted(false);
        btnFull.setOpaque(true);
        btnFull.setBounds(940, 500, 50, 50);
        btnFull.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnFull.addActionListener(e -> {
            dispose();
            setUndecorated(!isUndecorated());
            setVisible(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
        contentPanel.add(btnFull);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int w = getWidth();
                int h = getHeight();
                btnFull.setBounds(w - 70, h - 90, 50, 50);
            }
        });
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String path) {
            try {
                backgroundImage = ImageIO.read(new File(path));
            } catch (Exception e) {
                setBackground(Color.BLACK);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                setBackground(Color.BLACK);
            }
        }
    }
}
