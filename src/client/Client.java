package client;

import client.gui.GamePanel;
import common.message.InputData;
import common.message.ShapeData;
import server.FODServer;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Client extends Thread {
    private final GamePanel gamePanel;

    final Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    Client(GamePanel gamePanel, String ip) throws IOException {
        this.gamePanel = gamePanel;

        socket = new Socket(ip, FODServer.PORT);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        start();
    }

    void transmit(InputData inputData) throws IOException {
        if (!socket.isClosed())
            out.writeObject(inputData);
        else
            System.exit(0);
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                try {
                    ShapeData shapeData = (ShapeData) in.readObject();

                    int health = shapeData.clientShipHealth;
                    gamePanel.setBackground(new Color(
                            (int) (255.0 - (health / 10.0)),
                            (int) (255.0 * (health / 10.0)),
                            (int) (255.0 * (health / 10.0))));

                    gamePanel.updateVisuals(shapeData.focus, shapeData.shapes);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
