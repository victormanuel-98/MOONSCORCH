package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import util.ResourceLoader;

public class GameIntroScene extends BaseView {

    private JLabel introTexto;
    private JButton continuarBtn;

    public GameIntroScene() {
        super();
        setLayout(null);

        // Texto de introducción
        String texto = "<html><center>Hace siglos, bañada bajo la luz eterna de una luna moribunda, el Rey Amarillo alzó la ciudad de Eleum Loyce,<br>" +
                "una imponente ciudad levantada en el confín del mundo. Dicen que gobernó con sabiduría, pero un día su soberbia enfureció a la Luna...<br><br>" +
                "Lo que alguna vez fue luz se convirtió en la más terrible corrupción.<br>" +
                "Eleum Loyce fue consumida por un fuego que no arde: la Quemadura Lunar, una plaga que tuerce la carne y quiebra la mente. " +
                "Ahora, la que antaño fue la cúspide de la civilización, se retuerce bajo un cielo negro, custodiada por horrores que susurran en lenguas olvidadas. " +
                "Tú eres uno de los pocos lo bastante desesperados __o valientes__ para cruzar sus decrépitas murallas. " +
                "El Rey Amarillo aún susurra desde su trono quebrado. Y la luna te está observando.</center></html>";

        introTexto = new JLabel(texto, SwingConstants.CENTER);
        introTexto.setFont(new Font("Serif", Font.ITALIC, 25));
        introTexto.setForeground(Color.YELLOW);
        introTexto.setBounds(200, 140, 880, 400);
        add(introTexto);

        // Botón continuar
        continuarBtn = new JButton("Continuar");
        continuarBtn.setBounds(550, 560, 180, 40);
        add(continuarBtn);
        continuarBtn.setBackground(new Color(30, 30, 30));
        continuarBtn.setForeground(Color.yellow);
        continuarBtn.setFont(new Font("Georgia", Font.BOLD, 18));
        continuarBtn.setBorderPainted(false);
        continuarBtn.setFocusPainted(false);
        
        // Efecto hover
        continuarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseEntered(java.awt.event.MouseEvent evt) {
        		continuarBtn.setBackground(new Color(50,50,50));
        	}
        	
        	public void mouseExited(java.awt.event.MouseEvent evt) {
        		continuarBtn.setBackground(new Color(30,30,30));
        	}
        });
        
       
    }

    public void setContinuarAction(ActionListener listener) {
        continuarBtn.addActionListener(listener);
    }

    public void setFullscreenAction(ActionListener listener) {
        fullscreenButton.addActionListener(listener);
    }
}
