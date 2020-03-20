package bomberman;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Board {
//Переделать распределение ячеек: создаем список из всех ячеек - убираем 0.0 - выбираем случайные ячйки для блков и удоляем их из общего сприска
    // - тоже для монстров
    private final ReentrantLock[][] board;

    private int numberOfBlocks;

    private int numberOfMonsters;

    private List<Monster> monsters = new ArrayList<>();

    private List<Block> blocks = new ArrayList<>();

    public Board(ReentrantLock[][] board, int numberOfBlocks, int numberOfMonsters) {
        this.board = board;
        this.numberOfBlocks = numberOfBlocks;
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

    public boolean moveMonsterToNextCell(Monster monster) {

    }

    public boolean movePlayerToNextCell(Cell source, Cell dist) {
        boolean result = false;
        List<Cell> moves = possibleMoves(this.board, source);
        if (canMove(moves, dist)) {
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

    private boolean canMove(List<Cell> listOfPossibleMoves, Cell dist) {
        boolean result = false;
        if (listOfPossibleMoves.contains(dist)) {
            result = true;
        }
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


    public void initBoard(int numberOfMonsters) {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = new ReentrantLock();
            }
        }
        generateBlocks(this.numberOfBlocks);
        placeBlocks();
        generateMonsters(this.numberOfMonsters);
        placeMonsters();
    }

    private void generateMonsters(int numberOfMonsters) {
        for (int i = 0; i < numberOfMonsters; i++) {
            Monster monster = new Monster(generateCell());
            while (!listContainsMonster(monster, this.monsters)) {
                monster = new Monster(generateCell());
            }
            this.monsters.add(monster);

        }
    }

    private void generateBlocks(int numberOfBlocks) {
        for (int i = 0; i < numberOfBlocks; i++) {
            Block block = new Block(generateCell());
            while (!listContainsBlock(block, this.blocks)) {
                block = new Block(generateCell());
            }
            this.blocks.add(block);
            this.blocks.remove()
        }
    }

    private void placeMonsters() {
        for (Monster monster: this.monsters) {
            Cell cell = monster.getCurrentPosition();
            this.board[cell.getX()][cell.getY()].lock();
        }
    }

    private void placeBlocks() {
        for (Block block: this.blocks) {
            Cell cell = block.getCurrentPosition();
            this.board[cell.getX()][cell.getY()].lock();
        }
    }

    /**
     * Generates random Cell in this.bord dimension. If generated Cell has coordinates 0:0
     * (initial player position) then generates Cell again.
     * @return Random Cell
     */
    private Cell generateCell() {
        Cell result = null;
        boolean generate = false;
        while (!generate) {
            int x = ThreadLocalRandom.current().nextInt(this.board.length - 1);
            int y = ThreadLocalRandom.current().nextInt(this.board.length - 1);
            if (x != 0 && y != 0) {
                result = new Cell(x, y);
                generate = true;
            }
        }
        return result;
    }

    private boolean listContainsMonster(Monster monster, List<Monster> list) {
        boolean result = false;
        for (Monster listMonster: list) {
            if (monster.equals(listMonster)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean listContainsBlock(Block block, List<Block> list) {
        boolean result = false;
        for (Block listBlock: list) {
            if (block.equals(listBlock)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
