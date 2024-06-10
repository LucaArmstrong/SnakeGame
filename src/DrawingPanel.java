import java.awt.*;
import java.awt.Graphics;
import java.awt.geom.*;
import javax.swing.JComponent;
import java.math.*;

public class DrawingPanel extends JComponent {
    //public Graphics2D g2d;
    private Snake snake;
    private int cellLength, gridWidthPixels, gridHeightPixels;

    public DrawingPanel(Snake snake, int widthPixels, int heightPixels) {
        this.snake = snake;

        // initialise the component dimensions
        // need one cell length on all sides of the grid as padding (+2)
        this.cellLength = widthPixels / (snake.WIDTH + 2);
        this.gridWidthPixels = cellLength * snake.WIDTH;
        this.gridHeightPixels = cellLength * snake.HEIGHT;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // antialiasing
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );

        drawGrid(g2d);
        drawSnake(g2d);
        drawPellet(g2d);
    }

    private void drawGrid(Graphics2D g2d) {
        Rectangle2D.Double gridRect = new Rectangle2D.Double(
                cellLength, cellLength, gridWidthPixels, gridHeightPixels
        );

        g2d.setColor(Color.BLACK);
        g2d.fill(gridRect);
    }

    private void drawSnake(Graphics2D g2d) {
        // draw snake head
        drawGridCell(g2d, snake.snakeHead, Color.GRAY);

        // draw body of snake
        for (Position pos : snake.snakePositions) {
            if (pos != snake.snakeHead) {
                drawGridCell(g2d, pos, Color.WHITE);
            }
        }
    }

    private void drawPellet(Graphics2D g2d) {
        drawGridCell(g2d, snake.pelletPosition, Color.GREEN);
    }

    private int xCoordToPixels(int x) {
        return cellLength * (1 + x);
    }

    private int yCoordToPixels(int y) {
        return cellLength * (snake.HEIGHT - y);
    }

    private void drawGridCell(Graphics2D g2d, Position pos, Color colour) {
        Rectangle2D.Double gridCell = new Rectangle2D.Double(
                xCoordToPixels(pos.x), yCoordToPixels(pos.y), cellLength, cellLength
        );

        g2d.setColor(colour);
        g2d.fill(gridCell);
    }
}
