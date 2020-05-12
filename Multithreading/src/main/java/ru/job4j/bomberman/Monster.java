package ru.job4j.bomberman;

public class Monster {

    private Cell currentPosition;

    public Monster(Cell currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Cell getCurrentPosition() {
        return currentPosition;
    }

}
