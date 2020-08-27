import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Board extends JPanel {

    private Tile[][] board;

    public static final int MAX_NUMBER_OF_MINES = Game.ROWS * Game.COLS;
    public static final int SQUARE_SIZE = 32;

    public Board() {
        board = new Tile[Game.ROWS][Game.COLS];
        setup();
    }

    private void setup() {
        addMines();
        addNumbers();
        addZeros();
    }

    private void addMines() {
        int numMinesToDelegate;
        if(Game.NUM_MINES > MAX_NUMBER_OF_MINES) numMinesToDelegate = MAX_NUMBER_OF_MINES;
        else if(Game.NUM_MINES < 0) numMinesToDelegate = 0;
        else numMinesToDelegate = Game.NUM_MINES;

        Random randomRow = new Random();
        Random randomCol = new Random();

        int mines = 0;
        int row, col;
        while (mines != numMinesToDelegate) {
            row = randomRow.nextInt(Game.ROWS);
            col = randomCol.nextInt(Game.COLS);

            if (board[row][col] == null) {
                board[row][col] = new Tile(Tile.MINE);
                mines++;
            }
        }
    }

    private void addNumbers() {
        for (int row = 0; row < Game.ROWS; row++) {
            for (int col = 0; col < Game.COLS; col++) {

                if (board[row][col] == null) {
                    int num = numberOfMinesForSquare(row, col);
                    board[row][col] = new Tile(num);
                }
            }
        }
    }

    private int numberOfMinesForSquare(int row, int col) {
        int number = 0;
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {

                if (r != 0 || c != 0) {
                    if (0 <= (row + r) && (row + r) < Game.ROWS && 0 <= (col + c) && (col + c) < Game.COLS) {
                        if (board[row + r][col + c] != null) {
                            if (board[row + r][col + c].isMine()) number++;
                        }
                    }
                }
            }
        }
        return number;
    }

    private void addZeros() {
        for (int row = 0; row < Game.ROWS; row++) {
            for (int col = 0; col < Game.COLS; col++) {
                if (board[row][col] == null) board[row][col] = new Tile(0);
            }
        }
    }

    public void flag(int row, int col) {
        if(board[row][col].isClicked() && board[row][col].getNumberOfMines() != 0) return;
        board[row][col].flagged();
    }

    public void click(int row, int col) {
        try {
            if(board[row][col].isClicked() || board[row][col].getNumberOfMines() != 0) return;
        }
        finally {
          board[row][col].clicked();
        }

        if(0 < col)
            click(row, col - 1);

        if(col + 1 < Game.COLS)
            click(row, col + 1);

        if(0 < row) {
            click(row - 1, col);

            if(0 < col)
                click(row - 1, col - 1);

            if(col + 1 < Game.COLS)
                click(row - 1, col + 1);
        }

        if(row + 1 < Game.ROWS) {
            click(row + 1, col);

            if(0 < col)
                click(row + 1, col - 1);

            if(col + 1 < Game.COLS)
                click(row + 1, col + 1);
        }
    }

    public boolean hasWon() {
        boolean hasWon = true;
        for (int row = 0; row < Game.ROWS; row++) {
            for (int col = 0; col < Game.COLS; col++) {
                if(board[row][col].isFlagged() != board[row][col].isMine())
                    hasWon = false;
                if(!board[row][col].isClicked() && board[row][col].getNumberOfMines() > 0)
                    hasWon = false;
            }
        }
        return hasWon;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw board
        for (int row = 0; row < Game.ROWS; row++) {
            for (int col = 0; col < Game.COLS; col++) {

                // flagging drawing takes precedence over clicking
                if(board[row][col].isFlagged()) { // flagged square
                    g2d.drawImage(Tile.flag, row * SQUARE_SIZE, col * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, this);
                }
                else if(board[row][col].isClicked()){ // clicked square
                    g2d.drawImage(board[row][col].getImage(), row * SQUARE_SIZE, col * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, this);
                }
                else { // unpressedBlank square
                    g2d.drawImage(Tile.unpressedBlank, row * SQUARE_SIZE, col * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, this);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < Game.ROWS; row++) {
            for (int col = 0; col < Game.COLS; col++) {
                sb.append(board[col][row].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
