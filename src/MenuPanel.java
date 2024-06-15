import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;

public class MenuPanel extends JComponent {

    public MenuPanel() {
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // antialiasing
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );
    }

    public void renderMenu() {
        this.repaint();
    }
}
