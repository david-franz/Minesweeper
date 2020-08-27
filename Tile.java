import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {

    private final int numberOfMines;
    private final boolean isMine;

    private boolean isFlagged;
    private boolean isClicked;

    private final static String DIRECTORY_PATH = Tile.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    private BufferedImage image;

    public static BufferedImage flag;
    public static BufferedImage mine;
    public static BufferedImage unpressedBlank;
    public static BufferedImage pressedBlank;

    private static BufferedImage one;
    private static BufferedImage two;
    private static BufferedImage three;
    private static BufferedImage four;
    private static BufferedImage five;
    private static BufferedImage six;
    private static BufferedImage seven;
    private static BufferedImage eight;

    static {
        try {
            flag = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/flag.png"));
            mine = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/mine.png"));
            unpressedBlank = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/unpressedBlank.png"));
            pressedBlank = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/pressedBlank.png"));
            one = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/1.png"));
            two = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/2.png"));
            three = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/3.png"));
            four = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/4.png"));
            five = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/5.png"));
            six = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/6.png"));
            seven = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/7.png"));
            eight = ImageIO.read(new File(DIRECTORY_PATH + "/Graphics/8.png"));
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static final int MINE = -1;

    public Tile(int numberOfMines) {
        this.numberOfMines = numberOfMines;
        this.isMine = this.numberOfMines == MINE? true : false;
        try {
            image = getImageForNumberOfMines(numberOfMines);
        }
        catch(IOException e) { e.printStackTrace(); }
    }

    private BufferedImage getImageForNumberOfMines(int numberOfMines) throws IOException {
        switch(numberOfMines) {
            case -1: return Tile.mine;
            case 0: return Tile.pressedBlank;
            case 1: return Tile.one;
            case 2: return Tile.two;
            case 3: return Tile.three;
            case 4: return Tile.four;
            case 5: return Tile.five;
            case 6: return Tile.six;
            case 7: return Tile.seven;
            case 8: return Tile.eight;
        }

        throw new IOException("Couldn't find image.");
    }


    public void flagged() { isFlagged = !isFlagged; }

    public void clicked() {
        isClicked = true;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public String toString() {
        return (isMine ? " " : "  ") + numberOfMines + " ";
    }
}
