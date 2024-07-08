import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;

public class Player {
    private BufferedImage image;
    private Image img;
    private Point pos;
    private int score;
    static int n = 1;

    public Player() {
        loadImage();

        pos = new Point(0,0);
        score = 0;
    }

    public Player(int x, int y) {
        loadImage();

        pos = new Point(x, y);
        score = 0;
    }

    private void loadImage() {
        try {
            image = ImageIO.read(new File("images/Player.png"));
            img = image.getScaledInstance(50,50, Image.SCALE_DEFAULT);
        }
        catch(IOException e) {
            System.out.println("Error loading image: "+e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(img, pos.x*Board.TILE_SIZE, pos.y*Board.TILE_SIZE, observer);
    }

    public void drawInv(Graphics g, ImageObserver observer) {
        g.drawImage(img, pos.x*Board.TILE_SIZE+img.getWidth(observer), pos.y*Board.TILE_SIZE, -img.getWidth(observer), img.getHeight(observer), observer);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W) {
            pos.translate(0,-1);
        }
        if(key == KeyEvent.VK_S) {
            pos.translate(0,1);
        }
        if(key == KeyEvent.VK_A) {
            pos.translate(-1,0);
        }
        if(key == KeyEvent.VK_D) {
            pos.translate(1,0);
        }
    }

    public void tick() {
        if(pos.x<0) {
            pos.x = 0;
        }
        else if(pos.x>=Board.COLS) {
            pos.x = Board.COLS-1;
        }

        if(pos.y<0) {
            pos.y = 0;
        }
        else if(pos.y>=Board.ROWS) {
            pos.y = Board.ROWS-1;
        }
    }

    public String getScore() {
        return String.valueOf(score);
    }

    public void addScore(int amt) {
        score += (amt*n);
        n++;
    }

    public Point getPos() {
        return pos;
    }
}
