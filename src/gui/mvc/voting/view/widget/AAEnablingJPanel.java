package gui.mvc.voting.view.widget;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * Von {@link JPanel} ableitende Klasse, die in ihrer
 * {@link AAEnablingJPanel#paintComponent(Graphics)}-Methode die Kantenglättung
 * für das Zeichnen aktiviert.
 * 
 * @author Patrick Fries
 */
public class AAEnablingJPanel extends JPanel
{

    @Override
    protected void paintComponent(final Graphics g)
    {
        super.paintComponent(g);

        if (g instanceof Graphics2D)
        {
            final Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
    }
}
