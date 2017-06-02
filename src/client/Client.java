package client;

import client.gui.GamePanel;
import common.drawable.Poly;
import common.message.Message;
import server.FODServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {
    private GamePanel gamePanel;
    private Socket socket;

    Client(GamePanel gamePanel, String ip) throws IOException {
        this.gamePanel = gamePanel;

        socket = new Socket(ip, FODServer.PORT);

        start();
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                try {
                    Message message = (Message) in.readObject();
                    gamePanel.updateVisuals(message.focus, message.shapes);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
