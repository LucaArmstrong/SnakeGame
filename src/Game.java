import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;


/*
* Starting on the game menu -> rendering all the buttons for each gamemode and game options i.e. speed and wall mechanics
* Then once a gamemode is selected -> the button listener will call the game method with a particular gamemode
*
*
*
* */

public class Game {
    public static final int FULL_WIDTH = 45;
    public static final int HALF_WIDTH = 22;
    public static final int HEIGHT = 22;
    public static int widthPixels, heightPixels;

    // game variables
    public static GameMode gameMode;
    private static boolean wallMechanics;
    private static double snakeDeltaTimeMs;

    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    public static GameFrame frame;
    private static GamePanel gamePanel;
    private static MenuPanel menuPanel;

    private Snake snake;

    public Game() {
        frame = new GameFrame();
        widthPixels = frame.getWidth();
        heightPixels = frame.getHeight();

        gameMode = new GameMode(widthPixels, heightPixels);
        frame.addKeyListener(gameMode);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel();
        gamePanel = new GamePanel(gameMode);

        gameMode.setGamePanel(gamePanel);

        mainPanel.add(menuPanel, "menu");
        mainPanel.add(gamePanel, "game");

        displayGameMenu();
    }

    public static void displayGameMenu() {
        JLabel title = new JLabel("Snake Game");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Calibri", Font.BOLD, 35));
        title.setVisible(true);
        menuPanel.add(title);

        JButton classical = new JButton("Classical Mode");
        classical.setForeground(Color.WHITE);
        classical.setFont(new Font("Calibri", Font.BOLD, 30));
        menuPanel.add(classical);

        JButton dual = new JButton("Dual Mode");
        dual.setForeground(Color.WHITE);
        dual.setFont(new Font("Calibri", Font.BOLD, 30));
        menuPanel.add(dual);

        JCheckBox wallsBox = new JCheckBox("Wall Mechanics");
        wallsBox.setEnabled(wallMechanics);
        wallsBox.setForeground(Color.WHITE);
        wallsBox.setFont(new Font("Calibri", Font.BOLD, 30));
        menuPanel.add(wallsBox);

        JSlider speedSlider = new JSlider(0, 100, 50);
        menuPanel.add(speedSlider);

        classical.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "game");
                gameMode = new Classical(widthPixels, heightPixels);
                gameMode.setGamePanel(gamePanel);
                gameMode.runGame();
            }
        });

        dual.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "game");
                gameMode = new Dual(widthPixels, heightPixels);
                gameMode.setGamePanel(gamePanel);
                gameMode.runGame();
            }
        });

        wallsBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                wallMechanics = wallsBox.isSelected();
            }
        });

        speedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                snakeDeltaTimeMs = speedToMs(speedSlider.getValue());
            }
        });
    }



    public static int speedToMs(double v) {
        return (int)(6250 / (25 + v));
    }

    public void startGame() {
        frame.render();
        cardLayout.show(mainPanel, "menu");
    }

}
