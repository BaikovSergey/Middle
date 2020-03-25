package bomberman;

public class Block {

    private Cell currentPosition;

    public Block(Cell currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Cell getCurrentPosition() {
        return currentPosition;
    }
}
