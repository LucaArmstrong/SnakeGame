import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game {
    public static final int WIDTH = 46;
    public static final int HALF_WIDTH = 22;
    public static final int HEIGHT = 22;
    public int widthPixels, heightPixels;

    public static GameFrame gameFrame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    public GamePanel gamePanel;
    public MenuPanel menuPanel;

    public Game() {
        // make JFrame
        gameFrame = new GameFrame();
        widthPixels = gameFrame.getWidth();
        heightPixels = gameFrame.getHeight();

        // make the card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // make the panels and add them to the layout
        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();
        setupMenuActions();

        mainPanel.add(menuPanel);
        mainPanel.add(gamePanel);
        gameFrame.add(mainPanel);

        cardLayout.addLayoutComponent(menuPanel, "menu");
        cardLayout.addLayoutComponent(gamePanel, "game");
    }

    public void setupMenuActions() {
        menuPanel.classical.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runGameMode(new Classical(Game.this));
            }
        });

        menuPanel.dual.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runGameMode(new Dual(Game.this));
            }
        });
    }

    public void showMenuPanel() {
        cardLayout.show(mainPanel,"menu");
        menuPanel.requestFocusInWindow();
    }

    public void showGamePanel() {
        cardLayout.show(mainPanel, "game");
        gamePanel.requestFocusInWindow();
    }

    public void runGameMode(GameMode gameMode) {
        gamePanel.setGameMode(gameMode);
        showGamePanel();

        // Delay starting the game loop to ensure the panel is shown
        SwingUtilities.invokeLater(gameMode::runGame);
    }

    public static int speedToMs(int sliderValue) {
        return 6250 / (25 + sliderValue);
    }

    public void startGame() {
        showMenuPanel();
        gameFrame.setVisible(true);
    }

}
