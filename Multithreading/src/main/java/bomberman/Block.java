package bomberman;

import java.util.Objects;

public class Block {

    private Cell currentPosition;

    public Block(Cell currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Cell getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(currentPosition, block.currentPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPosition);
    }
}
