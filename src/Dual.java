import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Dual extends GameMode {
    private final int halfGridWidth, rightGridStart;
    private Snake snake1, snake2;
    private JLabel scoreLabel1, scoreLabel2;


    public Dual(Game game) {
        super(game);

        this.halfGridWidth = (game.widthPixels - cellLength) / 2;
        this.rightGridStart = halfGridWidth + cellLength;

        this.snake1 = new Snake(Game.HALF_WIDTH, Game.HEIGHT, wallMechanics);
        this.snake2 = new Snake(Game.HALF_WIDTH, Game.HEIGHT, wallMechanics);
    }

    @Override
    public void gameLoop() {
        snake1 = new Snake(Game.HALF_WIDTH, Game.HEIGHT, wallMechanics);
        snake2 = new Snake(Game.HALF_WIDTH, Game.HEIGHT, wallMechanics);
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

            snake1.doIteration();
            snake2.doIteration();
            game.gamePanel.repaint();

            if (snake1.gameState == GameState.ENDED && snake2.gameState == GameState.ENDED) gameHasEnded = true;
        }
    }

    @Override
    public void setDirection(int keyCode) {
        switch (keyCode) {
            // snake 1 - wasd
            case KeyEvent.VK_W -> {
                if (snake1.previousDirection.equals("down") && snake1.snakeLength > 0) break;
                snake1.currentDirection = "up";
            }
            case KeyEvent.VK_S -> {
                if (snake1.previousDirection.equals("up") && snake1.snakeLength > 0) break;
                snake1.currentDirection = "down";
            }
            case KeyEvent.VK_A -> {
                if (snake1.previousDirection.equals("right") && snake1.snakeLength > 0) break;
                snake1.currentDirection = "left";
            }
            case KeyEvent.VK_D -> {
                if (snake1.previousDirection.equals("left") && snake1.snakeLength > 0) break;
                snake1.currentDirection = "right";
            }

            // snake 2 - arrows
            case KeyEvent.VK_UP -> {
                if (snake2.previousDirection.equals("down") && snake2.snakeLength > 0) break;
                snake2.currentDirection = "up";
            }
            case KeyEvent.VK_DOWN -> {
                if (snake2.previousDirection.equals("up") && snake2.snakeLength > 0) break;
                snake2.currentDirection = "down";
            }
            case KeyEvent.VK_LEFT -> {
                if (snake2.previousDirection.equals("right") && snake2.snakeLength > 0) break;
                snake2.currentDirection = "left";
            }
            case KeyEvent.VK_RIGHT -> {
                if (snake2.previousDirection.equals("left") && snake2.snakeLength > 0) break;
                snake2.currentDirection = "right";
            }
            default -> System.out.println("Invalid key pressed");
        }

        if (snake1.gameState == GameState.NOT_STARTED && !snake1.currentDirection.equals("start")) {
            snake1.gameState = GameState.RUNNING;
            gameIsPaused = false;
        }
        if (snake2.gameState == GameState.NOT_STARTED && !snake2.currentDirection.equals("start")) {
            snake2.gameState = GameState.RUNNING;
            gameIsPaused = false;
        }
    }

    @Override
    public void drawGameMode(Graphics2D g2d) {
        drawGrids(g2d);

        updateScore(scoreLabel1, snake1.snakeLength);
        updateScore(scoreLabel2, snake2.snakeLength);

        drawPellet(g2d, snake1.pelletPosition, "left");
        drawPellet(g2d, snake2.pelletPosition, "right");

        drawSnake(g2d, snake1, "left");
        drawSnake(g2d, snake2, "right");
    }

    @Override
    public void addGameComponents() {
        JLabel scoreLabel1 = new JLabel();
        scoreLabel1.setForeground(Color.BLACK);
        scoreLabel1.setFont(new Font("Calibri", Font.BOLD, 35));
        game.gamePanel.add(scoreLabel1);
        this.scoreLabel1 = scoreLabel1;

        menuButton.setBackground(Color.WHITE);
        menuButton.setForeground(Color.BLACK);
        menuButton.setFont(new Font("Calibri", Font.BOLD, 35));
        game.gamePanel.add(menuButton);

        restartButton = new JButton("Restart");
        restartButton.setBackground(Color.WHITE);
        restartButton.setForeground(Color.BLACK);
        restartButton.setFont(new Font("Calibri", Font.BOLD, 35));
        game.gamePanel.add(restartButton);

        pauseButton = new JButton("Pause");
        pauseButton.setBackground(Color.WHITE);
        pauseButton.setForeground(Color.BLACK);
        pauseButton.setFont(new Font("Calibri", Font.BOLD, 35));
        game.gamePanel.add(pauseButton);

        JLabel scoreLabel2 = new JLabel();
        scoreLabel2.setForeground(Color.BLACK);
        scoreLabel2.setFont(new Font("Calibri", Font.BOLD, 35));
        game.gamePanel.add(scoreLabel2);
        this.scoreLabel2 = scoreLabel2;
    }

    @Override
    public int xCoordToPixelsRight(int x) {
        return rightGridStart + (int)(cellLength * 0.5) + (int)(cellLength * x);
    }

    private void drawGrids(Graphics2D g2d) {
        Rectangle2D.Double leftGridRect = new Rectangle2D.Double(
                0, topMargin, halfGridWidth, gridHeightPixels
        );

        Rectangle2D.Double rightGridRect = new Rectangle2D.Double(
                rightGridStart, topMargin, halfGridWidth, gridHeightPixels
        );

        g2d.setColor(Color.BLACK);
        g2d.fill(leftGridRect);
        g2d.fill(rightGridRect);
    }

}
