package server;

import common.drawable.Drawable;
import common.drawable.Ship;
import common.message.InputData;
import common.message.ShapeData;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

class Server extends Thread {
    private ArrayList<Connection> clients = new ArrayList<>();

    private ServerSocket server;

    Server(int port) throws IOException {
        server = new ServerSocket(port);

        start();
    }

    @Override
    public void run() {
        System.out.println("Listening for connections (ongoing)");
        try {
            while (true) {
                clients.add(new Connection(server.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void update() {
        Ship[] ships = new Ship[clients.size()];
        for (int i = 0; i < ships.length; i++)
            ships[i] = clients.get(i).getShip();

        InputData[] data = new InputData[clients.size()];
        for (int i = 0; i < data.length; i++) {
            try {
                data[i] = clients.get(i).getInputData();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < clients.size(); i++) {
            boolean[] held = data[i].held;
            Ship ship = ships[i];

            if (held[0])
                ship.rotate(0.05);
            if (held[1])
                ship.thrust(0.05);
            if (held[2])
                ship.rotate(-0.05);
        }

        Drawable[] shapes = new Drawable[clients.size()];
        for (int i = 0; i < shapes.length; i++)
            shapes[i] = ships[i];

        for (int i = 0; i < clients.size(); i++) {
            try {
                clients.get(i).sendShapeData(new ShapeData(ships[i].getCenter(), shapes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
