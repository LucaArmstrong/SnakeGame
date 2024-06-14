import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.util.Random;

public class Snake {
    public ArrayList<Position> snakePositions = new ArrayList<Position>();

    public String previousDirection, currentDirection;
    public int snakeLength;
    public Position snakeHead, pelletPosition;

    public boolean gameHasStarted, gameHasEnded;
    public int WIDTH, HEIGHT, AREA;

    public Snake(int width, int height) {
        // initialisation
        this.WIDTH = width;
        this.HEIGHT = height;
        this.AREA = width * height;

        currentDirection = "start";
        previousDirection = "start";
        snakeLength = 0;
        gameHasStarted = false;
        gameHasEnded = false;

        snakeHead = new Position(WIDTH / 2, HEIGHT / 2);    // set the snake head to the middle of the grid initially
        snakePositions.add(snakeHead);
        pelletPosition = getRandomEmptyPosition();  // initialise pellet to some random position
    }

    public void doIteration() {
        if (!gameHasStarted) return;

        // in here we iterate the snake once (doing one iteration of the game)
        doMovement();

        if (snakeLength == AREA - 1) {
            gameHasEnded = true;
        }
    }


    private void doMovement() {
        Position newHead = getNewSnakeHead();

        // check if snake has collided with itself
        if (isSnakePosition(newHead)) {
            gameHasEnded = true;
            return;
        }

        // otherwise, append the new head to the snake positions array
        snakePositions.addFirst(newHead);
        snakeHead = newHead;

        // check if pellet has been eaten
        if (pelletPosition.equals(snakeHead)) {
            snakeLength++;
            pelletPosition = getRandomEmptyPosition();  // get a new random pellet position
        } else {
            // otherwise remove the tail of the snake (moving the snake one forward)
            snakePositions.removeLast();
        }

        // save the current direction for reference
        previousDirection = currentDirection;
    }

    private Position getNewSnakeHead() {
        Position newHead = new Position(snakeHead.x, snakeHead.y);

        // get the position of the new head based on the direction the snake is facing
        switch (currentDirection) {
            case "up" -> newHead.y = (snakeHead.y == HEIGHT - 1) ? 0 : snakeHead.y + 1;
            case "down" -> newHead.y = (snakeHead.y == 0) ? HEIGHT - 1 : snakeHead.y - 1;
            case "left" -> newHead.x = (snakeHead.x == 0) ? WIDTH - 1 : snakeHead.x - 1;
            case "right" -> newHead.x = (snakeHead.x == WIDTH - 1) ? 0 : snakeHead.x + 1;
            case "start" -> { /* initially, there is no direction so do nothing */ }
            default -> System.out.println("Invalid direction");
        }

        return newHead;
    }

    // creates a pellet at some random position on the grid
    // doesn't place it anywhere the snake is
    private Position getRandomEmptyPosition() {
        int remainingCells = AREA - snakeLength;
        Random random = new Random();
        int randomCellNumber = random.nextInt(remainingCells);

        // loop through grid until the random number of empty squares have been reached
        int x, y;
        int emptyCells = 0;

        if (randomCellNumber == 0) return new Position(0, 0);

        for (y = 0; y < HEIGHT; y++) {
            for (x = 0; x < WIDTH; x++) {
                if (!isSnakePosition(new Position(x, y))) {
                    emptyCells++;
                }
                if (emptyCells == randomCellNumber) return new Position(x, y);
            }
        }

        return new Position(0, 0);
    }

    private boolean isSnakePosition(Position pos) {
        for (Position snakePos : snakePositions) {
            if (pos.equals(snakePos)) return true;
        }
        return false;
    }
}
