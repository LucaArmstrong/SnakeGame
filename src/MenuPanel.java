import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MenuPanel extends JPanel {
    public JLabel title;
    public JButton classical, dual;
    public JCheckBox wallsBox;
    public JSlider speedSlider;
    public static final int INITIAL_SPEED = 50;

    public MenuPanel() {
        setBackground(Color.BLACK);
        addMenuComponents();
    }

    public void addMenuComponents() {
        title = new JLabel("Snake Game - Main Menu");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Calibri", Font.BOLD, 35));
        title.setVisible(true);
        this.add(title);

        classical = new JButton("Classical Mode");
        classical.setForeground(Color.WHITE);
        classical.setFont(new Font("Calibri", Font.BOLD, 30));
        this.add(classical);

        dual = new JButton("Dual Mode");
        dual.setForeground(Color.WHITE);
        dual.setFont(new Font("Calibri", Font.BOLD, 30));
        this.add(dual);

        wallsBox = new JCheckBox("Wall Mechanics");
        wallsBox.setSelected(false);
        wallsBox.setBackground(Color.BLACK);
        wallsBox.setForeground(Color.WHITE);
        wallsBox.setFont(new Font("Calibri", Font.BOLD, 30));
        this.add(wallsBox);

        speedSlider = new JSlider(0, 100, INITIAL_SPEED);
        speedSlider.setBackground(Color.BLACK);
        speedSlider.setForeground(Color.WHITE);
        this.add(speedSlider);
    }

    public boolean wallMechanicsEnabled() {
        return wallsBox.isSelected();
    }

    public int getSpeedSliderValue() {
        return speedSlider.getValue();
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
}
