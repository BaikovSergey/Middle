package bomberman;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class Blocks {

    public List<Cell> getBlccks(ReentrantLock[][] board) {
        List<Cell> result = new ArrayList<>();
        int percent = 10;
        int numberOfBlocks = board.length * ((board.length * percent) / 100);
        while (result.size() != numberOfBlocks) {
            result.add(generateCell(board));
        }
        return result;
    }

    private Cell generateCell(ReentrantLock[][] board) {
        Cell result = null;
        boolean generate = false;
            while (!generate) {
                int x = ThreadLocalRandom.current().nextInt(board.length - 1);
                int y = ThreadLocalRandom.current().nextInt(board.length - 1);
                if (x != 0 && y != 0) {
                    result = new Cell(x, y);
                    generate = true;
                }
            }
        return result;
    }

}
