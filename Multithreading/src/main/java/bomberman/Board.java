package bomberman;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Board {

    private final ReentrantLock[][] board;

    private int numberOfBlocks;

    private int numberOfMonsters;

    private List<Monster> monsters = new ArrayList<>();

    private List<Block> blocks = new ArrayList<>();

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

    public boolean moveMonster(Monster monster) {
        boolean result = false;
        Cell source = monster.getCurrentPosition();
        Cell dist = getRandomCell(source);
        if (move(source, dist)) {
            result = true;
        }
        return result;
    }

    public boolean movePlayer(Cell source, Cell dist) {
        boolean result = false;
        if (move(source, dist)) {
            result = true;
        }
        return result;
    }

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

    private Cell getRandomCell(Cell source) {
        List<Cell> list = possibleMoves(source);
        int index = ThreadLocalRandom.current().nextInt(this.board.length);
        return list.get(index);
    }

    private List<Cell> possibleMoves(Cell source) {
        List<Cell> result = allMoves(source);
        result.removeIf(cell -> cell.getX() < 0 || cell.getX() > this.board.length - 1
                || cell.getY() < 0 || cell.getY() > this.board.length - 1);
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

    private void setAllCells() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.allCells.add(new Cell(i, j));
            }
        }
    }

    private void generateBlocks(int numberOfBlocks) {
        this.allCells.remove(0);
        for (int i = 0; i < numberOfBlocks; i++) {
            int index = ThreadLocalRandom.current().nextInt(this.board.length);
            Block block = new Block(this.allCells.remove(index));
            this.blocks.add(block);
        }
    }

    private void placeBlocks() {
        for (Block block: this.blocks) {
            Cell cell = block.getCurrentPosition();
            this.board[cell.getX()][cell.getY()].lock();
        }
    }

    private void generateMonsters(int numberOfMonsters) {
        for (int i = 0; i < numberOfMonsters; i++) {
            int index = ThreadLocalRandom.current().nextInt(this.board.length);
            Monster monster = new Monster(this.allCells.remove(index));
            this.monsters.add(monster);
        }
    }

    private void placeMonsters() {
        for (Monster monster: this.monsters) {
            Cell cell = monster.getCurrentPosition();
            this.board[cell.getX()][cell.getY()].lock();
        }
    }
}
