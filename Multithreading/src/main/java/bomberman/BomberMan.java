package bomberman;

public class BomberMan {

    private Board board = new Board();

    private Cell source = new Cell(0, 0);

    private Cell dist = new Cell(0, 1);

    public void play() {
        while (true) {
            Thread player = new Thread(
                    () -> board.move(source, dist)
            );
        }
    }

    public static void main(String[] args) {
        BomberMan game = new BomberMan();
        game.play();
    }
}
