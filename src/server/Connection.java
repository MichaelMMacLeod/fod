package server;

import common.drawable.Ship;
import common.message.InputData;
import common.message.ShapeData;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Connection {
    private Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Ship ship;

    Connection(Socket socket) throws IOException {
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Client connected at " + socket);

        // random color credit: https://stackoverflow.com/questions/4246351/creating-random-colour-in-java
        ship = new Ship(new Color((int) (Math.random() * 0x1000000)));
    }

    Ship getShip() {
        return ship;
    }

    void sendShapeData(ShapeData data) throws IOException {
        out.writeObject(data);
        out.reset(); // TODO: fix this shitty code
    }

    InputData getInputData() throws ClassNotFoundException, IOException{
        return (InputData) in.readObject();
    }
}
