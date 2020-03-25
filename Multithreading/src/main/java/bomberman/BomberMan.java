package bomberman;

import java.util.List;

public class BomberMan {

    private Board board;

    private GameMenu menu = new GameMenu();

    private int boardSize;

    private int numOfMonsters;

    private Cell currentPlayerPos = new Cell(0, 0);

    private boolean terminate = false;

    public void runMonsters() {
        List<Monster> monsters = this.board.getMonsters();
        for (int i = 0; i < monsters.size(); i++) {
            int index = i;
            Thread monster = new Thread(
                    () -> {
                        while (!this.terminate) {
                            this.board.moveMonsterToNextCell(monsters.get(index));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            monster.start();
        }
    }


    public void init(int boardSize, int numOfMonsters) {
        this.board = new Board(boardSize, numOfMonsters);
        this.board.initBoard();
    }

    public void setInitParameters(int boardSize, int numOfMonsters) {
        this.boardSize = boardSize;
        this.numOfMonsters = numOfMonsters;
    }

    public void play() {
        this.menu.showMenu();
        setInitParameters(this.menu.getBoardSize(), this.menu.getNumOfMonsters());
        init(this.boardSize, this.numOfMonsters);
        runMonsters();
    }

    public void shutdown() {
        this.terminate = true;
    }

    public static void main(String[] args) {
        BomberMan game = new BomberMan();
        game.play();
    }
}
