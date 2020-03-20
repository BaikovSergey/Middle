package bomberman;

import java.util.Objects;

public class Monster {

    private Cell currentPosition;

    public Monster(Cell currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Cell getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster monster = (Monster) o;
        return Objects.equals(currentPosition, monster.currentPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPosition);
    }
}
