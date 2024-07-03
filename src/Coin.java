import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Coin {
    private BufferedImage img;
    private Point pos;

    public Coin(int x, int y) {
        loadImage();
        pos = new Point(x, y);
    }

    private void loadImage() {
        try {
            img = ImageIO.read(new File("images/coin.png"));
        }
        catch(IOException e) {
            System.out.println("Error loading coin: "+e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(img, pos.x*Board.TILE_SIZE, pos.y*Board.TILE_SIZE, observer);
    }

    public Point getPos() {
        return pos;
    }
}
