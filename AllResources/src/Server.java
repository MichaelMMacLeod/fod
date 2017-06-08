import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Server object keeps track of all the connections to the server, and runs the collision detection and other logic.
 */
class Server extends Thread {
    /**
     * List of connections to the server.
     */
    private final ArrayList<Connection> clients = new ArrayList<>();

    private ServerSocket server;

    /**
     * list of bullets in the world.
     *
     * note: this is a hack. We should be storing all objects here, not just the bullets.
     */
    private ArrayList<Bullet> bullets;

    /**
     * Creates a Server.
     * @param port is the port
     * @throws IOException if it can't create a ServerSocket with this port.
     */
    Server(int port) throws IOException {
        server = new ServerSocket(port);

        bullets = new ArrayList<>();

        start();
    }

    /**
     * Add new connections when they pop up.
     */
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

    /**
     * Add new connections when they pop up.
     */
    private void listenForConnections() throws IOException {
        System.out.println("Began listening for connections.");
        while (true) {
            clients.add(new Connection(server.accept()));
        }
    }

    /**
     * A big ugly block of code that does all the updating for the server.
     *
     * Moves ships, and does collision detection.
     * @param ships     is the list of all ships tracked by the server.
     * @param inputData is the collection of data for each client.
     */
    private void updateShips(Ship[] ships, ArrayList<InputData[]> inputData) {
        for (int i = 0; i < inputData.size(); i++) {
            Ship ship = ships[i];

            if (!ship.isAlive())
                continue;

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
                                        6 * Math.cos(ship.getRotation()),
                                        6 * Math.sin(ship.getRotation())
                                ),
                                ship);
                        newBullet.translate(ship.getCenter().x, ship.getCenter().y);
                        newBullet.setVector(
                                ship.getVector().x + newBullet.getVector().x,
                                ship.getVector().y + newBullet.getVector().y);
                        newBullet.rotate(ship.getRotation());

                        bullets.add(newBullet);
                    }

                    if (pressed[5])
                        ship.thrust(5);
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

            if (bullet.shouldBeRemoved()) {
                bullets.remove(bullet);
            } else { // check collisions
                for (Ship ship : ships) {
                    if (ship.isAlive() && bullet.source != ship && ship.overlaps(bullet)) {
                        ship.damage(1);
                        bullet.source.heal(0.5);
                        bullets.remove(bullet);
                    }
                }
            }
        }
    }

    /**
     * Collects client ships, input, and then updates the state of the world.
     */
    void update() {
        // Get all ships and bullets

        Ship[] ships = new Ship[clients.size()];

        for (int i = 0; i < ships.length; i++) {
            ships[i] = clients.get(i).getShip();
        }

        // Get all input data

        ArrayList<InputData[]> inputData = new ArrayList<>();

        for (Connection c : clients) {
            InputData[] data = c.getQueuedInputData();
            inputData.add(data);

            c.removeQueuedInputData(data.length - 1);
        }

        // Update the world

        updateShips(ships, inputData);

        // collapse all ships and bullets into one array

        ArrayList<Drawable> all = new ArrayList<>();
        all.addAll(Arrays.asList((Drawable[]) ships));
        all.addAll(bullets);
        Drawable[] allArray = all.toArray(new Drawable[0]);

        // Transmit shape data to clients

        ShapeData[] shapeData = new ShapeData[ships.length];

        for (int i = 0; i < shapeData.length; i++)
            shapeData[i] = new ShapeData(ships[i].getCenter(), allArray, ships[i].getHealth());

        for (int i = 0; i < shapeData.length; i++)
            clients.get(i).setOutputData(shapeData[i]);
    }
}
