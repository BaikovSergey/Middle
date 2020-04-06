package bomberman;

public class Player {

    private Cell currentPosition;

    public Player(Cell currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Cell getCurrentPosition() {
        return currentPosition;
    }
}
