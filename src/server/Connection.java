package server;

import common.drawable.Ship;
import common.message.InputData;
import common.message.ShapeData;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

class Connection extends Thread {
    final Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Ship ship;

    private ArrayList<InputData> queuedInputData;
    private ShapeData outputData;

    Connection(Socket socket) throws IOException {
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Client connected at " + socket);

        // random color credit: https://stackoverflow.com/questions/4246351/creating-random-colour-in-java
        ship = new Ship(new Color((int) (Math.random() * 0x1000000)));

        queuedInputData = new ArrayList<>();

        // Store a blank message in outputData until we receive input from the server.
        outputData = new ShapeData();

        start();
    }

    /**
     * Ends the connection to the server.
     */
    void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return all the input data received from the client.
     */
    InputData[] getQueuedInputData() {
        InputData[] data = queuedInputData.toArray(new InputData[0]);

        for (int i = 0; i < data.length; i++)
            data[i] = new InputData(data[i]);

        return data;
    }

    /**
     * Removes queued input data.
     * @param amount is the number of items to remove from the start of the list.
     */
    void removeQueuedInputData(int amount) {
        for (int i = amount; i >= 0; i--)
            queuedInputData.remove(i);
    }

    /**
     * Sets the current data to be sent to the client.
     */
    void setOutputData(ShapeData outputData) {
        this.outputData = outputData;
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                InputData data = (InputData) in.readObject();

                if (data.held[3]) {
                    in.close();
                    out.close();
                    socket.close();

                    break;
                }

                queuedInputData.add(data);

                out.writeObject(outputData);
                out.reset();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();

                try {
                    out.close();
                    in.close();
                    socket.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        }
    }

    Ship getShip() {
        return ship;
    }
}
