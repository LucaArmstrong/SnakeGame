import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class Classical extends GameMode {
    private Snake snake;
    private JLabel scoreLabel;
    public Classical(Game game) {
        super(game);
        this.snake = new Snake(Game.WIDTH, Game.HEIGHT, wallMechanics);
    }

    @Override
    public void gameLoop() {
        this.snake = new Snake(Game.WIDTH, Game.HEIGHT, wallMechanics);
        game.gamePanel.repaint();

        // game loop
        while (!gameHasEnded) {
            // wait snakeDeltaTimeMs milliseconds
            try {
                Thread.sleep(snakeDeltaTimeMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (gameIsPaused) continue;

            game.gamePanel.repaint();
            snake.doIteration();

            if (snake.gameState == GameState.ENDED) gameHasEnded = true;
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

        // set the snake's game state to running if a direction is set initially
        if (snake.gameState == GameState.NOT_STARTED && !snake.currentDirection.equals("start")) {
            snake.gameState = GameState.RUNNING;
            gameIsPaused = false;
        }
    }

    @Override
    public void drawGameMode(Graphics2D g2d) {
        drawGrid(g2d);
        updateScore(scoreLabel, snake.snakeLength);
        drawPellet(g2d, snake.pelletPosition, "left");
        drawSnake(g2d, snake, "left");
    }

    @Override
    public void addGameComponents() {
        JLabel scoreLabel = new JLabel();
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setFont(new Font("Calibri", Font.BOLD, 35));
        game.gamePanel.add(scoreLabel);
        this.scoreLabel = scoreLabel;

        menuButton.setBackground(Color.WHITE);
        menuButton.setForeground(Color.BLACK);
        menuButton.setFont(new Font("Calibri", Font.BOLD, 35));
        game.gamePanel.add(menuButton);

        restartButton.setBackground(Color.WHITE);
        restartButton.setForeground(Color.BLACK);
        restartButton.setFont(new Font("Calibri", Font.BOLD, 35));
        game.gamePanel.add(restartButton);

        pauseButton.setBackground(Color.WHITE);
        pauseButton.setForeground(Color.BLACK);
        pauseButton.setFont(new Font("Calibri", Font.BOLD, 35));
        game.gamePanel.add(pauseButton);
    }

    private void drawGrid(Graphics2D g2d) {
        Rectangle2D.Double gridRect = new Rectangle2D.Double(
                0, topMargin, gridWidthPixels, gridHeightPixels
        );

        g2d.setColor(Color.BLACK);
        g2d.fill(gridRect);
    }
}
