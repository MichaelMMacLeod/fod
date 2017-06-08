import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Really just a wrapper for the Client class to make everything a bit cleaner looking.
 */
class FODClient {
    /**
     * How fast the client sends data to the server.
     */
    private static final int MS_PER_UPDATE = 10;

    /**
     * The GUI portion of the client.
     */
    private static GamePanel gamePanel;

    /**
     * The server communication portion of the client.
     */
    private static Client client;

    /**
     * Creates a client.
     *
     * @param args the first argument is the ip address of the server. Other arguments are ignored.
     * @throws IOException when there is a problem establishing a connection to the server.
     */
    public static void main(String[] args) throws IOException {
        System.setProperty("sun.java2d.opengl", "true");

        // I forget where I originally found the idea to use toolkit, but it wasn't mine.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        gamePanel = new GamePanel(width, height);

        SwingUtilities.invokeLater(FODClient::startGUI);

        client = new Client(gamePanel, args[0]);

        gameLoop();
    }

    /**
     * Transmit data to the server on a regular interval.
     */
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

    /**
     * Starts the GUI portion of the client.
     *
     * note: I think I got this a long time ago from an Oracle tutorial on how to do GUI.
     */
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
