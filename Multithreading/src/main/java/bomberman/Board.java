package bomberman;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Board {

    /**
     * Game board.
     */
    private final ReentrantLock[][] board;

    /**
     * Number of blocks on game board.
     */
    private int numberOfBlocks;

    /**
     * Number of monsters on game board.
     */
    private int numberOfMonsters;

    /**
     * List of all monsters on game board.
     */
    private List<Monster> monsters = new ArrayList<>();

    /**
     * List of all blocks on game board.
     */
    private List<Block> blocks = new ArrayList<>();

    /**
     * List of all cells of board.
     */
    private List<Cell> allCells = new ArrayList<>();

    public Board(int boardSize, int numberOfMonsters) {
        this.board = new ReentrantLock[boardSize][boardSize];
        double percentOfBlocks = 0.1;
        this.numberOfBlocks = (int) (boardSize * boardSize * percentOfBlocks);
        this.numberOfMonsters = numberOfMonsters;
    }

    public ReentrantLock[][] getBoard() {
        return board;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     * Method moves monster to one random Cell from current position.
     * @param monster Monster to move.
     * @return true if monster was successfully moved, false otherwise.
     */
    public boolean moveMonster(Monster monster) {
        boolean result = false;
        Cell source = monster.getCurrentPosition();
        Cell dist = getRandomCell(source);
        if (move(source, dist)) {
            result = true;
        }
        return result;
    }

    /**
     * Method moves player to one Cell from current position.
     * @param source current player position.
     * @param dist destination Cell.
     * @return true if player was successfully moved, false otherwise.
     */
    public boolean movePlayer(Cell source, Cell dist) {
        boolean result = false;
        if (move(source, dist)) {
            result = true;
        }
        return result;
    }

    /**
     * Method trays to lock one Cell from current position.
     * @param source Current position.
     * @param dist destination Cell.
     * @return true if Cell was successfully locked.
     */
    private boolean move(Cell source, Cell dist) {
        boolean result = false;
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

    /**
     * Method selects one random Cell from list of Cells.
     * @param source Current position.
     * @return Cell.
     */
    private Cell getRandomCell(Cell source) {
        List<Cell> list = possibleMoves(source);
        int index = ThreadLocalRandom.current().nextInt(this.board.length);
        return list.get(index);
    }

    /**
     * Method forms list of all possible moves from current position.
     * @param source Current position.
     * @return list of Cells.
     */
    private List<Cell> possibleMoves(Cell source) {
        List<Cell> result = allMoves(source);
        result.removeIf(cell -> cell.getX() < 0 || cell.getX() > this.board.length - 1
                || cell.getY() < 0 || cell.getY() > this.board.length - 1);
        return result;
    }

    /**
     * Method generates list of all Cells around current position.
     * @param source Current position.
     * @return list of all Cell around current position.
     */
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

    /**
     * Initiate game board.
     */
    public void initBoard() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = new ReentrantLock();
            }
        }
        setAllCells();
        generateBlocks(this.numberOfBlocks);
        placeBlocks();
        generateMonsters(this.numberOfMonsters);
        placeMonsters();
    }

    /**
     * Method adds all possible Cells, depending on board size, to list "allCells".
     */
    private void setAllCells() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.allCells.add(new Cell(i, j));
            }
        }
    }

    /**
     * Remove random Cells from list "allCells" and puts it to list "blocks"
     * @param numberOfBlocks Number of blocks.
     */
    private void generateBlocks(int numberOfBlocks) {
        this.allCells.remove(0);
        for (int i = 0; i < numberOfBlocks; i++) {
            int index = ThreadLocalRandom.current().nextInt(this.board.length);
            Block block = new Block(this.allCells.remove(index));
            this.blocks.add(block);
        }
    }

    /**
     * Lock all ReentrantLocks objects (blocks) in board, according to there Cells position.
     */
    private void placeBlocks() {
        for (Block block: this.blocks) {
            Cell cell = block.getCurrentPosition();
            this.board[cell.getX()][cell.getY()].lock();
        }
    }

    /**
     * Remove random Cells from list "allCells" and puts it to list "monsters"
     * @param numberOfMonsters Number of monsters.
     */
    private void generateMonsters(int numberOfMonsters) {
        for (int i = 0; i < numberOfMonsters; i++) {
            int index = ThreadLocalRandom.current().nextInt(this.board.length);
            Monster monster = new Monster(this.allCells.remove(index));
            this.monsters.add(monster);
        }
    }

    /**
     * Lock all ReentrantLocks objects (monsters) in board, according to there Cells position.
     */
    private void placeMonsters() {
        for (Monster monster: this.monsters) {
            Cell cell = monster.getCurrentPosition();
            this.board[cell.getX()][cell.getY()].lock();
        }
    }
}
