import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A Connection object is what links the client to the server.
 *
 * It keeps track of the client's Ship, and handles output and input streams for the server.
 */
class Connection extends Thread {
    final Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * The client's ship.
     */
    private Ship ship;

    /**
     * Holds a list of all input data the client has made since removeQueuedInputData was called.
     *
     * We 'queue' the input data so that a slow client-server connection won't loose anything. When the server reads
     * the input data, it calls removeQueuedInputData with the size of data it read.
     */
    private ArrayList<InputData> queuedInputData;

    /**
     * The current data for the client to display on screen.
     */
    private ShapeData outputData;

    /**
     * Creates a Connection between the client and the server.
     *
     * @param socket is the socket.
     * @throws IOException if the output/input streams fail to be created.
     */
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
     *
     * note: not actually ever used; // TODO: delete this method (unused)
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

    /**
     * Send and receive data constantly in another thread.
     */
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
                out.reset(); // we need this. for some reason. a nasty hack. // TODO: nasty. hack. bad.
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
