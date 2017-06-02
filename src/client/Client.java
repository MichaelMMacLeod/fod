package client;

import client.gui.GamePanel;
import server.FODServer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static GamePanel gamePanel;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect(args[0]);

        System.setProperty("sun.java2d.opengl", "true");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        gamePanel = new GamePanel(width, height);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                startGUI();
            }
        });
    }

    private void connect(String ip) throws IOException {
        Socket socket = new Socket(ip, FODServer.PORT);
    }

    private static void startGUI() {
        JFrame frame = new JFrame("Sonder");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(false);

        gamePanel.setBackground(Color.WHITE);

        frame.add(gamePanel);
        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
