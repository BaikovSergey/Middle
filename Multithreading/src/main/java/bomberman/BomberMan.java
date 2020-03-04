package bomberman;

public class BomberMan {

    public BomberMan(Board board) {
        this.board = board;
    }

    private final Board board;

    private Cell currentPlayerPos = new Cell(0, 0);

    public void play() {
        Cell dist = this.board.getNextCell(this.board.getBoard(), this.currentPlayerPos);
        if (this.board.move(this.currentPlayerPos, dist)) {
            this.currentPlayerPos = dist;
        }
    }

    public static void main(String[] args) {
        BomberMan game = new BomberMan(new Board(3));
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
