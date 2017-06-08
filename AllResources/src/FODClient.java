import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class FODClient {
    private static final int MS_PER_UPDATE = 10;

    private static GamePanel gamePanel;

    private static Client client;

    public static void main(String[] args) throws IOException {
        System.setProperty("sun.java2d.opengl", "true");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        gamePanel = new GamePanel(width, height);

        SwingUtilities.invokeLater(FODClient::startGUI);

        client = new Client(gamePanel, args[0]);

        gameLoop();
    }

    private static void gameLoop() {
        double previous = System.currentTimeMillis();
        double lag = 0;

        while (!client.socket.isClosed()) {
            double current = System.currentTimeMillis();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            while (lag >= MS_PER_UPDATE) {
                try {
                    client.transmit(gamePanel.getInputData());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                gamePanel.repaint();
                lag -= MS_PER_UPDATE;
            }
        }


    }

    private static void startGUI() {
        JFrame frame = new JFrame("FOD");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(false);

        gamePanel.setBackground(Color.WHITE);

        frame.add(gamePanel);
        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
