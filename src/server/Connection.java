package server;

import common.drawable.Poly;
import common.drawable.Ship;
import common.message.InputData;
import common.message.ShapeData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Ship ship;

    Connection(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Client connected: " + socket);

        ship = new Ship();

        start();
    }

    Poly getPoly() {
        return ship;
    }

    @Override
    public void run() {
        try {
            while (true) {
                out.writeObject(new ShapeData(ship.getCenter(), Server.getShapes()));
                try {
                    InputData inputData = (InputData) in.readObject();

                    boolean[] held = inputData.held;

                    if (held[1])
                        ship.thrust(0.1);
                    if (held[0])
                        ship.rotate(0.1);
                    if (held[2])
                        ship.rotate(-0.1);

                    ship.transform();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
