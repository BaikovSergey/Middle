package bomberman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Board {

    private final ReentrantLock[][] board;

    public Board(int boardSize) {
        this.board = new ReentrantLock[boardSize][boardSize];
    }

    public boolean move(Cell source, Cell dist) {
        boolean result = false;
        initBoard();
        synchronized (this.board[source.getX()][source.getY()]) {
            try {
                this.board[source.getX()][source.getY()].lock();
                if (this.board[dist.getX()][dist.getY()].tryLock(500, TimeUnit.MILLISECONDS)) {
                    this.board[dist.getX()][dist.getY()].lock();
                    this.board[source.getX()][source.getY()].unlock();
                    result = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Cell getNextCell(ReentrantLock[][] board, Cell source) {
        Cell result = null;
        List<Cell> moves = possibleMoves(board, source);
        result = moves.get(ThreadLocalRandom.current().nextInt(moves.size() - 1));
        return result;
    }

    private List<Cell> possibleMoves(ReentrantLock[][] board, Cell source) {
        List<Cell> result = allMoves(source);
        result.removeIf(cell -> cell.getX() < 0 || cell.getX() > board.length - 1
                || cell.getY() < 0 || cell.getY() > board.length - 1);
        return result;
    }

    private List<Cell> allMoves(Cell source) {
        List<Cell> result = new ArrayList<>();
        int x = source.getX();
        int y = source.getY();
        result.add(new Cell(x, y + 1));
        result.add(new Cell(x, y - 1));
        result.add(new Cell(x + 1, y));
        result.add(new Cell(x - 1, y));
        result.add(new Cell(x + 1, y + 1));
        result.add(new Cell(x - 1, y - 1));
        result.add(new Cell(x - 1, y + 1));
        result.add(new Cell(x + 1, y - 1));
        return result;
    }

    public ReentrantLock[][] getBoard() {
        return board;
    }

    private void initBoard() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = new ReentrantLock();
            }
        }
    }
}
