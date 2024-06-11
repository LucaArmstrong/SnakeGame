public class Position { //Position is a class for storing and x and y pair representing some position on the game grid
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Position pos) {
        return this.x == pos.x && this.y == pos.y;
    }
}