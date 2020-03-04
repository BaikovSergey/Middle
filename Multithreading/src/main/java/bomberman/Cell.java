package bomberman;


public class Cell {

    private final int x;

    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "X = " + this.x + ", Y = " + this.y;
    }
}
