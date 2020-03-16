package bomberman;

import java.util.List;

public class BomberMan {

    private final Board board;

    private Cell currentPlayerPos = new Cell(0, 0);

    public BomberMan(Board board) {
        this.board = board;
    }

    public void play() {
        init();
        Cell dist = this.board.getNextCell(this.board.getBoard(), this.currentPlayerPos);
        if (this.board.move(this.currentPlayerPos, dist)) {
            this.currentPlayerPos = dist;
        }
    }

    private void init() {
        Blocks blocks = new Blocks();
        List<Cell> cellsToBlock = blocks.getBlccks(this.board.getBoard());
        for (Cell cell: cellsToBlock) {
            this.board.blockCell(cell.getX(), cell.getY());
        }
    }

    public static void main(String[] args) {
        BomberMan game = new BomberMan(new Board(5));
        Thread player = new Thread(
                () -> {
                    while (true) {
                        game.play();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        player.start();
    }
}
