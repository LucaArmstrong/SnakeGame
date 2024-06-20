import java.awt.*;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class GamePanel extends JPanel {
    private GameMode gameMode;

    public GamePanel() {
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener();
    }

    // used to avoid circular dependency between GamePanel and GameMode classes
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void addKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_SPACE) {
                    gameMode.togglePause();
                } else if (gameMode != null) {
                    gameMode.setDirection(keyCode);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // antialiasing
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (gameMode != null) {
            gameMode.drawGameMode(g2d);
        } else {
            System.out.println("Gamemode is null");
        }
    }
}
