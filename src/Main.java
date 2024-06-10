public class Main {
    private Snake snake = new Snake(30, 20);
    private Renderer renderer = new Renderer(snake, 1500, 750);

    public void main(String[] args) {
        runGame();
    }

    public void runGame() {
        // in here we have a loop that iterates over the snake iterate method
        int deltaTimeMs = 50;
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