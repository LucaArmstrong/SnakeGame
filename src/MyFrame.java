import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Dimension2D;

public class MyFrame extends JFrame {
    public MyFrame() {
        this.setTitle("Snake Game");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.WHITE);
    }

    public int getFrameWidth() {
        return this.getWidth();
    }

    public int getFrameHeight() {
        return this.getHeight();
    }

    public void render() {
        this.setVisible(true);
    }

    public void addKeyListener(GameMode gameMode) {
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                gameMode.setDirection(keyCode);
            }
        });
    }
}
