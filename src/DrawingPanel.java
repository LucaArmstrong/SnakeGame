import java.awt.*;
import java.awt.Graphics;
import java.awt.geom.*;
import java.util.Objects;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class DrawingPanel extends JComponent {
    public int widthPixels, heightPixels;
    private final Game game;

    public DrawingPanel(Game game, int widthPixels, int heightPixels) {
        this.game = game;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
        this.setBounds(0, 0, widthPixels, heightPixels);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // antialiasing
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );


        if (game.gameState == GameState.GAME_MENU) {
            System.out.println("painting the game menu");
            game.doGameMenu();
            //drawGameMenu(g2d);
        }
        game.gameMode.drawGameMode(g2d);
    }

    public void drawGameMenu(Graphics2D g2d) {

    }

    public void renderObjects() {
        this.repaint();
    }
}
