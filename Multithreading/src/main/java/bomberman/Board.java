package bomberman;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Board {

    private final ReentrantLock[][] board = new ReentrantLock[3][3];

    private final Cell currentPlayerPos = new Cell(0, 0);

    public void move(Cell source) {
        boolean movedSuccess = false;
        ReentrantLock currentPos = this.board[source.getY()][source.getY()];
        Cell dist = getNextCell(this.board, this.currentPlayerPos);
        ReentrantLock distPos = this.board[dist.getY()][dist.getY()];
        synchronized (this.board[source.getX()][source.getY()]) {
            currentPos.lock();
            while (!movedSuccess) {
                if(!distPos.isLocked()) {
                    this.currentPlayerPos = dist;
                    currentPos.unlock();
                    movedSuccess = true;
                } else {
                    dist = getNextCell(this.board, this.currentPlayerPos);
                }
            }
        }
    }

    public Cell getNextCell(ReentrantLock[][] board, Cell source) {
        Cell result = null;
        List<Cell> moves = possibleMoves(source);
        result = moves.get(ThreadLocalRandom.current().nextInt(moves.size() - 1));
        return result;
    }

    private List<Cell> possibleMoves(Cell source) {
        List<Cell> result = allMoves(source);
        for (Cell cell: result) {
            if (cell.getX() < 0 || cell.getY() < 0) {
                result.remove(cell);
            }
        }
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

}
