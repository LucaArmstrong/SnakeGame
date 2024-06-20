import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

public abstract class GameMode {
    protected final int cellLength, gridWidthPixels, gridHeightPixels;
    public int topMargin, topPadding, sidePadding;
    protected boolean gameIsPaused, gameHasEnded;
    protected JButton menuButton, restartButton, pauseButton;
    protected Game game;
    protected int snakeDeltaTimeMs;
    protected boolean wallMechanics;
    protected Thread gameThread;

    public GameMode(Game game) {
        // initialise the component dimensions
        // need one cell length at the top as padding
        // also on the grid, need half a cell's length of margin all the way round for
        // a more aesthetic transition between the grid and the side of the frame
        this.cellLength = game.widthPixels / (Game.WIDTH + 1);
        this.gridWidthPixels = game.widthPixels;
        this.gridHeightPixels = cellLength * (Game.HEIGHT + 1);

        this.topMargin = (int)(cellLength * 1.25);
        this.topPadding = (int)(cellLength * 0.5);
        this.sidePadding = (game.widthPixels - (cellLength * (Game.WIDTH))) / 2;

        this.snakeDeltaTimeMs = Game.speedToMs(game.menuPanel.getSpeedSliderValue());
        this.wallMechanics = game.menuPanel.wallMechanicsEnabled();
        this.game = game;
        this.gameThread = null;

        menuButton = new JButton("Menu");
        restartButton = new JButton("Restart");
        pauseButton = new JButton("Pause");
        gameIsPaused = false;

        game.gamePanel.removeAll();
        addGameComponents();
        addButtonListeners();
    }

    public void runGame() {
        // reset the game properties
        gameIsPaused = false;
        gameHasEnded = false;

        // run gameLoop() on the game thread
        gameThread = new Thread(this::gameLoop);
        gameThread.start();
    }

    private void stopGame() {
        System.out.println("game being stopped");
        gameHasEnded = true;    // use this to signal to the previous game loop to stop running
        if (gameThread != null && gameThread.isAlive()) {
            try {
                gameThread.join(); // Wait for the thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("\ngame not able to be stopped");
            if (gameThread == null) System.out.println("game thread is null");
            if (!gameThread.isAlive()) System.out.println("game thread is dead");
            System.out.println("");
        }
        System.out.println("game finished being stopped");
    }

    private void addButtonListeners() {
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame(); // Ensure the game stops when returning to the menu
                game.showMenuPanel();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame(); // Ensure the previous game is stopped before starting a new one
                runGame();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });
    }

    public void togglePause() {
        gameIsPaused = !gameIsPaused;
    }

    public void drawGameMode(Graphics2D g2d) {}

    public void setDirection(int keyCode) {
        System.out.println("In unoverrided method");
    }

    protected void drawSnake(Graphics2D g2d, Snake snake, String grid) {
        // draw snake head
        drawGridCell(g2d, snake.snakeHead, 1.0, Color.BLUE, grid);

        // draw body of snake
        for (Position pos : snake.snakePositions) {
            if (!pos.equals(snake.snakeHead)) {
                drawGridCell(g2d, pos, 1.0, Color.WHITE, grid);
            }
        }
    }

    public void gameLoop() {
        System.out.println("Unoverrided method running");
    }

    protected void drawPellet(Graphics2D g2d, Position position, String grid) {
        drawGridCell(g2d, position, 0.75, Color.GREEN, grid);
    }

    protected int xCoordToPixelsLeft(int x) {
        return sidePadding + (int)(cellLength * x);
    }

    protected int xCoordToPixelsRight(int x) {
        return 0;
    }

    private int yCoordToPixels(int y) {
        return topMargin + topPadding + (cellLength * (Game.HEIGHT - 1 - y));
    }

    protected void updateScore(JLabel label, int score) {
        label.setText("Score: " + score);
    }

    public void addGameComponents() {}

    protected void drawGridCell(Graphics2D g2d, Position pos, double scalar, Color colour, String grid) {
        int length = (int)(cellLength * scalar);
        int offset = (cellLength - length) / 2;

        int x = grid.equals("left") ? xCoordToPixelsLeft(pos.x) : xCoordToPixelsRight(pos.x);

        RoundRectangle2D.Double gridCell = new RoundRectangle2D.Double(
                x + offset, yCoordToPixels(pos.y) + offset, length, length, length / 5, length / 5
        );

        g2d.setColor(colour);
        g2d.fill(gridCell);
    }
}
