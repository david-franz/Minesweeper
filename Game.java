import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JFrame {

    private Board board;

    // bug- can't set rows and cols to different numbers
    public static final int ROWS = 8;
    public static final int COLS = 8;
    public static final int NUM_MINES = 10;

    public static final int WIDTH = COLS * Board.SQUARE_SIZE;
    public static final int HEIGHT = ROWS * Board.SQUARE_SIZE;
    public static final int TOOLBAR_HEIGHT = 22;

    public Game() {
        board = new Board();
        setup();
    }

    private void setup() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(WIDTH, HEIGHT + TOOLBAR_HEIGHT));
        setResizable(false);

        board.addMouseListener((new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int row = (int) ((e.getX()) / board.SQUARE_SIZE);
                int col = (int) ((e.getY()) / board.SQUARE_SIZE);

                if(SwingUtilities.isLeftMouseButton(e)) { // normal click
                    board.click(row, col);
                }
                else if(SwingUtilities.isRightMouseButton(e)) { // flag
                    board.flag(row, col);
                }
                // mine detection method- check if lost
                if(board.hasWon()) System.out.println("you win");
                board.repaint();
            }
        }));

        add(board);

        pack();
        setLocationRelativeTo(null); // makes game come in center
        setVisible(true);
    }
}
