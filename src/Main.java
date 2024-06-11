public class Main {
    private static final Snake snake = new Snake(45, 22);
    private static final Renderer renderer = new Renderer(snake, 1500, 750);

    public static void main(String[] args) {
        runGame();
    }

    public static void runGame() {
        // in here we have a loop that iterates over the snake iterate method
        int deltaTimeMs = 125;
        renderer.renderFrame();

        while (!snake.gameHasEnded) {
            renderer.renderObjects();
            snake.doIteration();

            // wait deltaTimeMs milliseconds
            try {
                Thread.sleep(deltaTimeMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}