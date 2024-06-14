import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class GameMode {
    public static final int WIDTH = 45;
    public static final int HALF_WIDTH = 22;
    public static final int HEIGHT = 22;
    public final int cellLength, gridWidthPixels, gridHeightPixels;
    public final int topMargin, topPadding, sidePadding;
    protected boolean gameIsPaused;
    protected JButton restartButton, pauseButton;

    public GameMode(DrawingPanel drawingPanel) {
        // initialise the component dimensions
        // need one cell length at the top as padding
        // also on the grid, need half a cell's length of margin all the way round for
        // a more aesthetic transition between the grid and the side of the frame
        this.cellLength = drawingPanel.widthPixels / (WIDTH + 1);
        this.gridWidthPixels = drawingPanel.widthPixels;
        this.gridHeightPixels = cellLength * (HEIGHT + 1);

        this.topMargin = (int)(cellLength * 1.25);
        this.topPadding = (int)(cellLength * 0.5);
        this.sidePadding = (drawingPanel.widthPixels - (cellLength * (WIDTH + 1))) / 2;

        this.gameIsPaused = false;

        addButtonListeners();
    }

    private void addButtonListeners() {
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runGame();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameIsPaused = !gameIsPaused;
            }
        });
    }

    public void drawGameMode(Graphics2D g2d) {

    }

    public void setDirection(int keyCode) {

    }

    public void drawSnake(Graphics2D g2d, Snake snake, String grid) {
        // draw snake head
        drawGridCell(g2d, snake.snakeHead, 1.0, Color.BLUE, grid);

        // draw body of snake
        for (Position pos : snake.snakePositions) {
            if (!pos.equals(snake.snakeHead)) {
                drawGridCell(g2d, pos, 1.0, Color.WHITE, grid);
            }
        }
    }

    public void runGame() {

    }

    public void drawPellet(Graphics2D g2d, Position position, String grid) {
        drawGridCell(g2d, position, 0.75, Color.GREEN, grid);
    }

    private int xCoordToPixelsLeft(int x) {
        return sidePadding + (int)(cellLength * x);
    }

    protected int xCoordToPixelsRight(int x) {
        return 0;
    }

    private int yCoordToPixels(int y) {
        return topMargin + topPadding + (cellLength * (Game.HEIGHT - 1 - y));
    }

    protected void drawScore(JLabel label, int score) {
        label.setText("Score: " + score);
    }

    protected void addGameComponents() {

    }

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
