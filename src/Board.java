import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.util.*;
import javax.swing.*;
import java.util.Timer;

public class Board extends JPanel implements ActionListener, KeyListener {
    private final int DELAY = 25;
    public static final int TILE_SIZE = 50;
    public static final int ROWS = 15;
    public static final int COLS = 21;
    public static final int NUM_COINS = 5;
    private static final long UID = 849285385084219330L;
    private javax.swing.Timer timer;
    private Player player;
    private Player2 p2;
    private ArrayList<Coin> coins;
    private final Set<Integer> activeKeys = new HashSet<>();
    Graphics g;
    private Timer cdTimer;
    private int countdown = 60;
    private int starting = 1000;
    private int ticking = 1000;

    public Board() {
        setPreferredSize(new Dimension(TILE_SIZE*COLS, TILE_SIZE*ROWS));
        setBackground(new Color(0, 154, 255));
        player = new Player();
        p2 = new Player2();
        coins = populateCoins();
        timer = new javax.swing.Timer(DELAY, this);
        timer.start();
        SetupTimer();
    }

    public void actionPerformed(ActionEvent e) {
        player.tick();
        p2.tick();
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
        p2.draw(g, this);

        Toolkit.getDefaultToolkit().sync();
    }

    public void keyTyped(KeyEvent e) {

    }

    public synchronized void keyPressed(KeyEvent e) {
//        player.keyPressed(e);
//        p2.keyPressed(e);

        activeKeys.add(e.getKeyCode());

        //System.out.println(activeKeys);

        if(!activeKeys.isEmpty()) {
            if(activeKeys.contains(KeyEvent.VK_W)) {
                //pos.translate(0,-1);
                player.getPos().translate(0,-1);
                //player.keyPressed(e);
            }
            if(activeKeys.contains(KeyEvent.VK_S)) {
//                pos.translate(0,1);
                player.getPos().translate(0,1);
                //player.keyPressed(e);
            }
            if(activeKeys.contains(KeyEvent.VK_A)) {
//                pos.translate(-1,0);
                player.getPos().translate(-1,0);
//                player.drawInv(g, this);
                //player.keyPressed(e);
            }
            if(activeKeys.contains(KeyEvent.VK_D)) {
//                pos.translate(1,0);
                player.getPos().translate(1,0);
//                player.draw(g, this);
                //player.keyPressed(e);
            }


            if(activeKeys.contains(KeyEvent.VK_UP)) {
                //pos.translate(0,-1);
                p2.getPos().translate(0,-1);
                //p2.keyPressed(e);
            }
            if(activeKeys.contains(KeyEvent.VK_DOWN)) {
//                pos.translate(0,1);
                p2.getPos().translate(0,1);
                //p2.keyPressed(e);
            }
            if(activeKeys.contains(KeyEvent.VK_LEFT)) {
//                pos.translate(-1,0);
                p2.getPos().translate(-1,0);
//                p2.draw(g, this);

                //p2.keyPressed(e);
            }
            if(activeKeys.contains(KeyEvent.VK_RIGHT)) {
//                pos.translate(1,0);
                p2.getPos().translate(1,0);
//                p2.drawInv(g, this);

                //p2.keyPressed(e);
            }
        }
    }

    public synchronized void keyReleased(KeyEvent e) {
        activeKeys.remove(e.getKeyCode());
    }

    private void SetupTimer() {
        cdTimer = new Timer();
        cdTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(countdown == 1) {
                    countdown--;
                    cdTimer.cancel();
                }
                else {
                    countdown--;
                }
            }
        }, starting, ticking);
    }

    private void drawBackground(Graphics g) {
        g.setColor(new Color(9, 23, 30));

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
        String text2 = "$" + p2.getScore();
        String p1_text = "P1";
        String p2_text = "P2";
        String time_cd = String.valueOf(countdown);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setColor(new Color(255,255,255));
        g2d.setFont(new Font("Lato",Font.BOLD,25));

        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        Rectangle rect = new Rectangle(0, TILE_SIZE*(ROWS-1), TILE_SIZE*COLS, TILE_SIZE);
        int x = rect.x + (rect.width - metrics.stringWidth(text))/3;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 3) + metrics.getAscent();

        Rectangle rect2 = new Rectangle(0, TILE_SIZE*(ROWS-1), TILE_SIZE*COLS, TILE_SIZE);
        int x2 = rect.x + 2*(rect.width - metrics.stringWidth(text))/3;
        int y2 = rect.y + (2*(rect.height - metrics.getHeight()) / 3) + metrics.getAscent();

        Rectangle rect3 = new Rectangle(0, 0, TILE_SIZE*COLS, TILE_SIZE);
        int x3 = rect.x + (rect.width - metrics.stringWidth(text))/2;
        int y3 = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.drawString(text, x, y);
        g2d.drawString(text2, x2, y2);
        g2d.drawString(p1_text, x, y-2*TILE_SIZE/3);
        g2d.drawString(p2_text, x2, y2-2*TILE_SIZE/3);
        g2d.drawString(time_cd, x3, y3);
    }

    private ArrayList<Coin> populateCoins() {
        ArrayList<Coin> coinList = new ArrayList<>();
        Random rand = new Random();

        int i=0;
        if(coins != null) {
            i = coins.size();
        }

        for(int j = i;j<NUM_COINS;j++) {
            int coinX = rand.nextInt(COLS);
            int coinY = rand.nextInt(ROWS);

            //while(coinX == findComponentAt())
            coinList.add(new Coin(coinX, coinY));
        }

        return coinList;
    }

    private void collectCoins() {
        ArrayList<Coin> collCoins = new ArrayList<>();
        ArrayList<Coin> addedCoins = new ArrayList<>();

        for(Coin coin: coins) {
            if(player.getPos().equals(coin.getPos())) {
                player.addScore(100);
                collCoins.add(coin);
            }
            else if(p2.getPos().equals(coin.getPos())) {
                p2.addScore(100);
                collCoins.add(coin);
            }
        }
        coins.removeAll(collCoins);

        if(!collCoins.isEmpty()) {
            addedCoins = populateCoins();
            coins.addAll(addedCoins);
        }
    }
}
