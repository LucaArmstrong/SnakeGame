import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Dual extends GameMode {
    private final int rightGridStart;
    private Snake snake1, snake2;
    private JLabel scoreLabel1, scoreLabel2;


    public Dual(int widthPixels, int heightPixels) {
        super(widthPixels, heightPixels);

        int halfGridWidth = cellLength * HALF_WIDTH;
        this.rightGridStart = sidePadding + halfGridWidth + cellLength;

        addGameComponents();
    }

    public void runGame() {
        int deltaTimeMs = 100;
        snake1 = new Snake(HALF_WIDTH, HEIGHT);
        snake2 = new Snake(HALF_WIDTH, HEIGHT);

        // game loop
        while (!snake1.gameHasEnded && !snake2.gameHasEnded && !gameIsPaused) {
            gamePanel.renderGame();
            snake1.doIteration();
            snake2.doIteration();

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

        if (!snake1.currentDirection.equals("start")) snake1.gameHasStarted = true;
        if (!snake2.currentDirection.equals("start")) snake2.gameHasStarted = true;
    }

    @Override
    public void drawGameMode(Graphics2D g2d) {
        drawGrids(g2d);

        drawScore(scoreLabel1, snake1.snakeLength);
        drawScore(scoreLabel2, snake2.snakeLength);

        drawPellet(g2d, snake1.pelletPosition, "left");
        drawPellet(g2d, snake2.pelletPosition, "right");

        drawSnake(g2d, snake1, "left");
        drawSnake(g2d, snake2, "left");
    }

    @Override
    protected void addGameComponents() {
        gamePanel.removeAll();

        JLabel scoreLabel1 = new JLabel();
        scoreLabel1.setForeground(Color.BLACK);
        scoreLabel1.setFont(new Font("Calibri", Font.BOLD, 35));
        gamePanel.add(scoreLabel1);
        this.scoreLabel1 = scoreLabel1;

        JLabel scoreLabel2 = new JLabel();
        scoreLabel2.setForeground(Color.BLACK);
        scoreLabel2.setFont(new Font("Calibri", Font.BOLD, 35));
        gamePanel.add(scoreLabel2);
        this.scoreLabel2 = scoreLabel2;

        JButton restartButton = new JButton("Restart");
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(new Font("Calibri", Font.BOLD, 35));
        gamePanel.add(restartButton);

        JButton pauseButton = new JButton("Pause");
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setFont(new Font("Calibri", Font.BOLD, 35));
        gamePanel.add(pauseButton);
    }

    @Override
    public int xCoordToPixelsRight(int x) {
        return rightGridStart + (int)(cellLength * x);
    }

    public void drawGrids(Graphics2D g2d) {
        Rectangle2D.Double leftGridRect = new Rectangle2D.Double(
                0, topMargin, gridWidthPixels, gridHeightPixels
        );

        Rectangle2D.Double rightGridRect = new Rectangle2D.Double(
                0, topMargin, gridWidthPixels, gridHeightPixels
        );

        g2d.setColor(Color.BLACK);
        g2d.fill(leftGridRect);
        g2d.fill(rightGridRect);
    }

}
