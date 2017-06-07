package server;

import common.drawable.Bullet;
import common.drawable.Drawable;
import common.drawable.Ship;
import common.message.InputData;
import common.message.ShapeData;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Server extends Thread {
    private final ArrayList<Connection> clients = new ArrayList<>();

    private ServerSocket server;

    private ArrayList<Bullet> bullets;

    Server(int port) throws IOException {
        server = new ServerSocket(port);

        bullets = new ArrayList<>();

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
                    boolean[] pressed = inputData.get(i)[j].pressed;

                    if (held[0])
                        ship.rotate(-0.05);
                    if (held[1])
                        ship.thrust(0.05);
                    if (held[2])
                        ship.rotate(0.05);

                    if (pressed[4]) {
                        Bullet newBullet = new Bullet(
                                new Point2D.Double(
                                        2 * Math.cos(ship.getRotation()),
                                        2 * Math.sin(ship.getRotation())
                                ),
                                ship.getFillColor());
                        newBullet.translate(ship.getCenter().x, ship.getCenter().y);
                        newBullet.setVector(
                                ship.getVector().x + newBullet.getVector().x,
                                ship.getVector().y + newBullet.getVector().y);
                        newBullet.rotate(ship.getRotation());

                        bullets.add(newBullet);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ship.getVector().x *= 0.99;
            ship.getVector().y *= 0.99;

            ship.transform();
        }

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);

            bullet.transform();

            if (bullet.shouldBeRemoved())
                bullets.remove(bullets.size() - 1);
        }
    }

    void update() {
        // Get all ships and bullets

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

        // get all elements

        ArrayList<Drawable> all = new ArrayList<>();
        all.addAll(Arrays.asList((Drawable[]) ships));
        all.addAll(bullets);
        Drawable[] allArray = all.toArray(new Drawable[0]);

        // Transmit shape data to clients

        ShapeData[] shapeData = new ShapeData[ships.length];

        for (int i = 0; i < shapeData.length; i++)
            shapeData[i] = new ShapeData(ships[i].getCenter(), allArray);

        for (int i = 0; i < shapeData.length; i++)
            clients.get(i).setOutputData(shapeData[i]);
    }
}
