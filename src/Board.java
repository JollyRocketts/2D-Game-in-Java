import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener, KeyListener {
    private final int DELAY = 25;
    public static final int TILE_SIZE = 50;
    public static final int ROWS = 12;
    public static final int COLS = 18;
    public static final int NUM_COINS = 5;
    private static final long UID = 849285385084219330L;
    private javax.swing.Timer timer;
    private Player player;
    private ArrayList<Coin> coins;

    public Board() {
        setPreferredSize(new Dimension(TILE_SIZE*COLS, TILE_SIZE*ROWS));
        setBackground(new Color(100,193,255));
        player = new Player();
        coins = populateCoins();
        timer = new javax.swing.Timer(DELAY, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        player.tick();
        collectCoins();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawScore(g);

        for(Coin coin: coins) {
            coin.draw(g, this);
        }
        player.draw(g, this);

        Toolkit.getDefaultToolkit().sync();
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {

    }

    private void drawBackground(Graphics g) {
        g.setColor(new Color(4,255,29));

        for(int row=0;row<ROWS;row++) {
            for(int col=0;col<COLS;col++) {
                if((row+col)%2 == 1) {
                    g.fillRect(col*TILE_SIZE, row*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    private void drawScore(Graphics g) {
        String text = "$" + player.getScore();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setColor(new Color(30,190,135));
        g2d.setFont(new Font("Lato",Font.BOLD,25));

        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        Rectangle rect = new Rectangle(0, TILE_SIZE*(ROWS-1), TILE_SIZE*COLS, TILE_SIZE);
        int x = rect.x + (rect.width - metrics.stringWidth(text))/2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(text, x, y);
    }

    private ArrayList<Coin> populateCoins() {
        ArrayList<Coin> coinList = new ArrayList<>();
        Random rand = new Random();

        for(int i=0;i<NUM_COINS;i++) {
            int coinX = rand.nextInt(COLS);
            int coinY = rand.nextInt(ROWS);
            coinList.add(new Coin(coinX, coinY));
        }

        return coinList;
    }

    private void collectCoins() {
        ArrayList<Coin> collCoins = new ArrayList<>();

        for(Coin coin: coins) {
            if(player.getPos().equals(coin.getPos())) {
                player.addScore(100);
                collCoins.add(coin);
            }
        }

        coins.removeAll(collCoins);
    }
}
