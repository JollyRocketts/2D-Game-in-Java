import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Coin {
    private BufferedImage image;
    private Image img;
    private Point pos;

    public Coin(int x, int y) {
        loadImage();
        pos = new Point(x, y);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(new File("images/coin.png"));
            img = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
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
