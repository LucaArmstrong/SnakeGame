import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;

enum GameState {
    GAME_MENU,
    CLASSICAL,
    DUAL
}

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

    // game variables
    public static GameState gameState;
    public static GameMode gameMode;
    private static boolean wallMechanics;
    private static double snakeDeltaTimeMs;

    private static MyFrame frame;
    private static DrawingPanel drawingPanel;
    private Snake snake;

    public Game() {
        gameState = GameState.GAME_MENU;

        frame = new MyFrame();
        frame.addKeyListener(gameMode);
        drawingPanel = new DrawingPanel(this, frame.getFrameWidth(), frame.getFrameHeight());
    }

    public static void doGameMenu() {
        drawingPanel.removeAll();
        frame.setBackground(Color.BLACK);

        JLabel title = new JLabel("Snake Game");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Calibri", Font.BOLD, 35));
        drawingPanel.add(title);

        JButton classical = new JButton("Classical Mode");
        classical.setForeground(Color.WHITE);
        classical.setFont(new Font("Calibri", Font.BOLD, 30));
        drawingPanel.add(classical);

        JButton dual = new JButton("Dual Mode");
        dual.setForeground(Color.WHITE);
        dual.setFont(new Font("Calibri", Font.BOLD, 30));
        drawingPanel.add(dual);

        JCheckBox wallsBox = new JCheckBox("Wall Mechanics");
        wallsBox.setEnabled(wallMechanics);
        wallsBox.setForeground(Color.WHITE);
        wallsBox.setFont(new Font("Calibri", Font.BOLD, 30));
        drawingPanel.add(wallsBox);

        JSlider speedSlider = new JSlider(0, 100, 50);
        drawingPanel.add(speedSlider);

        drawingPanel.validate();

        classical.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameState = GameState.CLASSICAL;
                gameMode = new Classical(drawingPanel);
                gameMode.runGame();
            }
        });

        dual.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameState = GameState.DUAL;
                gameMode = new Dual(drawingPanel);
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

    public static void startGame() {
        System.out.println("Starting the game");
        frame.render();
        System.out.println("frame rendered");
        doGameMenu();
        System.out.println("game menu created");
        drawingPanel.renderObjects();
    }

}
