import javax.swing.*;

public class App {
    private static void initWindow() {
        JFrame window = new JFrame("2D Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board board = new Board();
        window.add(board);
        window.addKeyListener(board);
    }
}
