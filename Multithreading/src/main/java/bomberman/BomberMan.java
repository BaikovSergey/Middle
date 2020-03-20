package bomberman;

import java.util.ArrayList;
import java.util.List;

public class BomberMan {

    private Board board;

    private GameMenu menu = new GameMenu();

    private int boardSize;

    private int numOfMonsters;

    private List<Monster> monsters = new ArrayList<>();

    private Cell currentPlayerPos = new Cell(0, 0);

    private boolean terminate = false;

    public void createMonsters() {
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

    private void moveMonster(Monster monster) {
        Cell monsterPosition = monster.getCurrentPosition();
        Cell dist = this.board.moveMonsterToNextCell(this.board.getBoard(), this.currentPlayerPos);
        if (this.board.move(this.currentPlayerPos, dist)) {
            this.currentPlayerPos = dist;
        }
    }

    public void init(int boardSize, int numOfMonsters) {
        this.board = new Board(boardSize);
        this.board.initBoard(numOfMonsters);
        List<Cell> monsters = this.board.getMonsters();
        for (Cell cell: monsters) {
            this.monsters.add(new Monster(cell));
        }
    }

    public void setInitParameters(int boardSize, int numOfMonsters) {
        this.boardSize = boardSize;
        this.numOfMonsters = numOfMonsters;
    }

    public void play() {
        this.menu.showMenu();
        setInitParameters(this.menu.getBoardSize(), this.menu.getNumOfMonsters());
        init(this.boardSize, this.numOfMonsters);
        createMonsters(this.numOfMonsters);
    }

    public void shutdown() {
        this.terminate = true;
    }

    public static void main(String[] args) {
        BomberMan game = new BomberMan();
        game.play();
    }
}
