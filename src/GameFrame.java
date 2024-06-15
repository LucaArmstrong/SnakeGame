import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame {
    public GameFrame() {
        this.setTitle("Snake Game");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.WHITE);
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
