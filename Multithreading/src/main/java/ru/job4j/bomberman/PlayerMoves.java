package ru.job4j.bomberman;

public class PlayerMoves {

    public boolean moveUp(Board board, Player player) {
        boolean result = false;
        Cell position = player.getCurrentPosition();
        int nextX = position.getX() - 1;
        int nextY = position.getY();
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(position, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveDown(Board board, Player player) {
        boolean result = false;
        Cell position = player.getCurrentPosition();
        int nextX = position.getX() + 1;
        int nextY = position.getY();
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(position, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveLeft(Board board, Player player) {
        boolean result = false;
        Cell position = player.getCurrentPosition();
        int nextX = position.getX();
        int nextY = position.getY() - 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(position, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveRight(Board board, Player player) {
        boolean result = false;
        Cell position = player.getCurrentPosition();
        int nextX = position.getX();
        int nextY = position.getY() + 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(position, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveUpLeft(Board board, Player player) {
        boolean result = false;
        Cell position = player.getCurrentPosition();
        int nextX = position.getX() - 1;
        int nextY = position.getY() - 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(position, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveUpRight(Board board, Player player) {
        boolean result = false;
        Cell position = player.getCurrentPosition();
        int nextX = position.getX() - 1;
        int nextY = position.getY() + 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(position, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveDownLeft(Board board, Player player) {
        boolean result = false;
        Cell position = player.getCurrentPosition();
        int nextX = position.getX() + 1;
        int nextY = position.getY() - 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(position, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveDownRight(Board board, Player player) {
        boolean result = false;
        Cell position = player.getCurrentPosition();
        int nextX = position.getX() + 1;
        int nextY = position.getY() + 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(position, dist)) {
            result = true;
        }
        return result;
    }
}
