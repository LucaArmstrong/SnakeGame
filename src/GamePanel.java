import java.awt.*;
import java.awt.Graphics;
import javax.swing.JComponent;

public class GamePanel extends JComponent {
    private final GameMode gameMode;

    public GamePanel(GameMode gameMode) {
        this.gameMode = gameMode;

        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // antialiasing
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );

        gameMode.drawGameMode(g2d);
    }

    public void renderGame() {
        this.repaint();
    }
}
