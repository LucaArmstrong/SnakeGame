import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Renderer {
    private Snake snake;
    private int widthPixels, heightPixels;
    private DrawingPanel panel;
    public Renderer(Snake snake, int widthPixels, int heightPixels) {
        this.snake = snake;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
        this.panel = new DrawingPanel(snake, widthPixels, heightPixels);
    }

    public void renderFrame() {
        JFrame frame = new JFrame("Snake Game");
        frame.setSize(this.widthPixels, this.heightPixels);
        frame.setResizable(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.add(this.panel);

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                snake.setDirection(keyCode);
            }
        });

        frame.setVisible(true);
    }

    public void renderObjects() {
        this.panel.repaint();
    }
}
