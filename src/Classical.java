import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Classical extends GameMode {

    private DrawingPanel drawingPanel;
    private Snake snake;
    private JLabel scoreLabel;

    public Classical(DrawingPanel drawingPanel) {
        super(drawingPanel);

        this.drawingPanel = drawingPanel;
    }

    public void runGame() {
        int deltaTimeMs = 100;
        this.snake = new Snake(WIDTH, HEIGHT);

        // game loop
        while (!snake.gameHasEnded && !gameIsPaused) {
            drawingPanel.renderObjects();
            snake.doIteration();

            // wait deltaTimeMs milliseconds
            try {
                Thread.sleep(deltaTimeMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void setDirection(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                if (snake.previousDirection.equals("down") && snake.snakeLength > 0) break;
                snake.currentDirection = "up";
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                if (snake.previousDirection.equals("up") && snake.snakeLength > 0) break;
                snake.currentDirection = "down";
            }
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                if (snake.previousDirection.equals("right") && snake.snakeLength > 0) break;
                snake.currentDirection = "left";
            }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                if (snake.previousDirection.equals("left") && snake.snakeLength > 0) break;
                snake.currentDirection = "right";
            }
            default -> System.out.println("Invalid key pressed");
        }

        if (!snake.currentDirection.equals("start")) snake.gameHasStarted = true;
    }

    @Override
    public void drawGameMode(Graphics2D g2d) {
        drawGrid(g2d);
        drawScore(scoreLabel, snake.snakeLength);
        drawPellet(g2d, snake.pelletPosition, "left");
    }

    @Override
    protected void addGameComponents() {
        JLabel scoreLabel = new JLabel("Snake Game");
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setFont(new Font("Calibri", Font.BOLD, 35));
        drawingPanel.add(scoreLabel);
        this.scoreLabel = scoreLabel;

        restartButton = new JButton("Restart");
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(new Font("Calibri", Font.BOLD, 35));
        drawingPanel.add(restartButton);

        pauseButton = new JButton("Pause");
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setFont(new Font("Calibri", Font.BOLD, 35));
        drawingPanel.add(pauseButton);
    }

    public void drawGrid(Graphics2D g2d) {
        Rectangle2D.Double gridRect = new Rectangle2D.Double(
                0, topMargin, gridWidthPixels, gridHeightPixels
        );

        g2d.setColor(Color.BLACK);
        g2d.fill(gridRect);
    }



}
