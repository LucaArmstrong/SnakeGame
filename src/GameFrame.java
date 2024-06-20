import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame {
    public GameFrame() {
        this.setTitle("Snake Game");
        this.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, (int)(screenSize.width), (int)(screenSize.height));
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.WHITE);
    }
}