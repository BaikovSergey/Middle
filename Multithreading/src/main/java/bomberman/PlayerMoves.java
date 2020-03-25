package bomberman;

public class PlayerMoves {

    public boolean moveUp(Board board, Cell source) {
        boolean result = false;
        int nextX = source.getX() - 1;
        int nextY = source.getY();
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(source, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveDown(Board board, Cell source) {
        boolean result = false;
        int nextX = source.getX() + 1;
        int nextY = source.getY();
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(source, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveLeft(Board board, Cell source) {
        boolean result = false;
        int nextX = source.getX();
        int nextY = source.getY() - 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(source, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveRight(Board board, Cell source) {
        boolean result = false;
        int nextX = source.getX();
        int nextY = source.getY() + 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(source, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveUpLeft(Board board, Cell source) {
        boolean result = false;
        int nextX = source.getX() - 1;
        int nextY = source.getY() - 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(source, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveUpRight(Board board, Cell source) {
        boolean result = false;
        int nextX = source.getX() - 1;
        int nextY = source.getY() + 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(source, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveDownLeft(Board board, Cell source) {
        boolean result = false;
        int nextX = source.getX() + 1;
        int nextY = source.getY() - 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(source, dist)) {
            result = true;
        }
        return result;
    }

    public boolean moveDownRight(Board board, Cell source) {
        boolean result = false;
        int nextX = source.getX() + 1;
        int nextY = source.getY() + 1;
        Cell dist = new Cell(nextX, nextY);
        if (board.movePlayer(source, dist)) {
            result = true;
        }
        return result;
    }
}
