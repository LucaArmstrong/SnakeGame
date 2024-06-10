import javax.swing.JFrame;
import java.awt.*;

public class Renderer {
    private int widthPixels, heightPixels;
    private DrawingPanel panel;
    public Renderer(Snake snake, int widthPixels, int heightPixels) {
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
        this.panel = new DrawingPanel(snake, widthPixels, heightPixels);
    }

    public void renderFrame() {
        JFrame frame = new JFrame("Snake Game");
        frame.setSize(this.widthPixels, this.heightPixels);
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.add(this.panel);
        frame.setVisible(true);
    }

    public void renderObjects() {
        this.panel.repaint();
    }
}
