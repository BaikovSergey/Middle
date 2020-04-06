package ru.job4j.bomberman;

import java.util.List;

public class Game {

    private Board board;

    private GameMenu menu = new GameMenu();

    private PlayerMoves playerMoves = new PlayerMoves();

    private int boardSize;

    private int numOfMonsters;

    private Player playerPossition = new Player(new Cell(0, 0));

    private boolean terminate = false;

    private Game() {
    }

    public static Game getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final Game INSTANCE = new Game();
    }

    public void play() {
        this.menu.showMenu();
        setInitParameters(this.menu.getBoardSize(), this.menu.getNumOfMonsters());
        init(this.boardSize, this.numOfMonsters);
        runMonsters();
        giveControlToPlayer();
    }

    public void shutdown() {
        this.terminate = true;
    }

    private void init(int boardSize, int numOfMonsters) {
        this.board = new Board(boardSize, numOfMonsters);
        this.board.initBoard();
    }

    private void setInitParameters(int boardSize, int numOfMonsters) {
        this.boardSize = boardSize;
        this.numOfMonsters = numOfMonsters;
    }

    private void runMonsters() {
        List<Monster> monsters = this.board.getMonsters();
        for (int i = 0; i < monsters.size(); i++) {
            int index = i;
            Thread monster = new Thread(
                    () -> {
                        while (!this.terminate) {
                            this.board.moveMonster(monsters.get(index));
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

    private void giveControlToPlayer() {
        while (!this.terminate) {
            menu.showPossibleMoves();
        }
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
