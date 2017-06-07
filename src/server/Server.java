package server;

import common.drawable.Ship;
import common.message.InputData;
import common.message.ShapeData;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

class Server extends Thread {
    private final ArrayList<Connection> clients = new ArrayList<>();

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

    private void updateShips(Ship[] ships, ArrayList<InputData[]> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
            Ship ship = ships[i];

            for (int j = 0; j < inputData.get(i).length; j++) {
                try {
                    boolean[] held = inputData.get(i)[j].held;

                    if (held[0])
                        ship.rotate(-0.05);
                    if (held[1])
                        ship.thrust(0.05);
                    if (held[2])
                        ship.rotate(0.05);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ship.transform();
        }
    }

    void update() {
        // Get all ships

        Ship[] ships = new Ship[clients.size()];

        for (int i = 0; i < ships.length; i++)
            ships[i] = clients.get(i).getShip();

        // Get all input data

        ArrayList<InputData[]> inputData = new ArrayList<>();

        for (Connection c : clients) {
            InputData[] data = c.getQueuedInputData();
            inputData.add(data);

            c.removeQueuedInputData(data.length - 1);
        }


        // Update the world

        updateShips(ships, inputData);

        // Transmit shape data to clients

        ShapeData[] shapeData = new ShapeData[ships.length];

        for (int i = 0; i < shapeData.length; i++)
            shapeData[i] = new ShapeData(ships[i].getCenter(), ships);

        for (int i = 0; i < shapeData.length; i++)
            clients.get(i).setOutputData(shapeData[i]);
    }
}
