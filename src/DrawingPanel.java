import java.awt.*;
import java.awt.Graphics;
import java.awt.geom.*;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class DrawingPanel extends JComponent {
    private Snake snake;
    private int cellLength, gridWidthPixels, gridHeightPixels;

    public DrawingPanel(Snake snake, int widthPixels, int heightPixels) {
        this.snake = snake;

        // initialise the component dimensions
        // need one cell length on all sides of the grid as padding (+2)
        // also on the grid, need half a cell's length of margin all the way round for
        // a more aesthetic transition between the grid and the side of the frame
        this.cellLength = widthPixels / (snake.WIDTH + 3);
        this.gridWidthPixels = cellLength * (snake.WIDTH + 1);
        this.gridHeightPixels = cellLength * (snake.HEIGHT + 1);
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
        drawScore(g2d);
    }

    private void drawScore(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawString("Score: " + snake.snakeLength, gridWidthPixels / 2, cellLength / 2);
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
        drawGridCell(g2d, snake.snakeHead, 1.0, Color.BLUE);

        // draw body of snake
        for (Position pos : snake.snakePositions) {
            if (pos != snake.snakeHead) {
                drawGridCell(g2d, pos, 1.0, Color.WHITE);
            }
        }
    }

    private void drawPellet(Graphics2D g2d) {
        drawGridCell(g2d, snake.pelletPosition, 0.75, Color.GREEN);
    }

    private int xCoordToPixels(int x) {return (int)(cellLength * (1.5 + x));}

    private int yCoordToPixels(int y) {return (int)(cellLength * (0.5 + snake.HEIGHT - y));}

    private void drawGridCell(Graphics2D g2d, Position pos, double scalar, Color colour) {
        int length = (int)(cellLength * scalar);
        int offset = (cellLength - length) / 2;

        RoundRectangle2D.Double gridCell = new RoundRectangle2D.Double(
                xCoordToPixels(pos.x) + offset, yCoordToPixels(pos.y) + offset, length, length, length / 5, length / 5
        );

        g2d.setColor(colour);
        g2d.fill(gridCell);
    }
}
