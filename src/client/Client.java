package client;

import client.gui.GamePanel;
import common.message.InputData;
import common.message.ShapeData;
import server.FODServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {
    private GamePanel gamePanel;

    private Socket socket;
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
        out.writeObject(inputData);
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    ShapeData shapeData = (ShapeData) in.readObject();
                    System.out.println(shapeData.shapes[0].getPoints()[0]);
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
