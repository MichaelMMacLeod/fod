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
        try {
            listenForConnections();
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

    private void listenForConnections() throws IOException {
        System.out.println("Began listening for connections.");
        while (true) {
            clients.add(new Connection(server.accept()));
        }
    }

    private Ship[] getShips() {
        Ship[] ships = new Ship[clients.size()];

        for (int i = 0; i < ships.length; i++)
            ships[i] = clients.get(i).getShip();

        return ships;
    }

    private InputData[] getInputData() {
        InputData[] inputData = new InputData[clients.size()];

        for (int i = 0; i < inputData.length; i++) {
            try {
                inputData[i] = clients.get(i).getInputData();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                inputData[i] = null;
            }
        }

        return inputData;
    }

    private void updateShips(Ship[] ships, InputData[] inputData) {
        for (int i = 0; i < inputData.length; i++) {
            boolean[] held = inputData[i].held;
            Ship ship = ships[i];

            if (held[0])
                ship.rotate(0.05);
            if (held[1])
                ship.thrust(0.05);
            if (held[2])
                ship.rotate(-0.05);

            ship.transform();
        }
    }

    private ShapeData[] createClientMessages(Ship[] ships) {
        ShapeData[] shapeData = new ShapeData[ships.length];

        for (int i = 0; i < shapeData.length; i++)
            shapeData[i] = new ShapeData(ships[i].getCenter(), ships);

        return shapeData;
    }

    private void updateConnections(ShapeData[] data) {
        for (int i = 0; i < clients.size(); i++) {
            try {
                clients.get(i).sendShapeData(data[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void update() {
        Ship[] ships = getShips();

        InputData[] inputData = getInputData();

        for (Ship i : ships)
            System.out.println(i.getPoints()[0]);

        updateShips(ships, inputData);

        updateConnections(createClientMessages(ships));
    }
}
