package server;

import common.drawable.Ship;
import common.message.InputData;
import common.message.ShapeData;

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

        ship = new Ship();
    }

    Ship getShip() {
        return ship;
    }

    void sendShapeData(ShapeData data) throws IOException {
        out.writeObject(data);
    }

    InputData getInputData() throws ClassNotFoundException, IOException{
        return (InputData) in.readObject();
    }
}
