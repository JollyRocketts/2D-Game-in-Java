import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    private BufferedImage img;
    private Point pos;
    private int score;

    public Player() {
        loadImage();

        pos = new Point(0,0);
        score = 0;
    }

    private void loadImage() {
        try {
            img = ImageIO.read(new File("images/Player.png"));
        }
        catch(IOException e) {
            System.out.println("Error loading image: "+e.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(img, pos.x*Board.TILE_SIZE, pos.y*Board.TILE_SIZE, observer);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_UP) {
            pos.translate(0,-1);
        }
        if(key == KeyEvent.VK_DOWN) {
            pos.translate(0,1);
        }
        if(key == KeyEvent.VK_LEFT) {
            pos.translate(-1,0);
        }
        if(key == KeyEvent.VK_RIGHT) {
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
        score += amt;
    }

    public Point getPos() {
        return pos;
    }
}
