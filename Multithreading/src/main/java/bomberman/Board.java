package bomberman;

import java.util.concurrent.locks.ReentrantLock;

public class Board {

    private final ReentrantLock[][] board;

    private final Cell cell = new Cell(1,1);

    public Board(int rows, int columns) {
        this.board = new ReentrantLock[rows][columns];
    }

    public boolean move(Cell source, Cell dist) {
        boolean result = false;
        synchronized (this.board[cell.getX()][cell.getY()]) {
            cell = new Cell(2,2);
        }
        return result;
    }
}
