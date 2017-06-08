import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Handles server communication for the client.
 */
class Client extends Thread {
    /**
     * Reference to the GUI portion of the client.
     */
    private final GamePanel gamePanel;

    final Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Creates a Client.
     * @param gamePanel is the reference to the GUI portion of the client
     * @param ip        is the ip address of the server.
     * @throws IOException is thrown when we have trouble creating a socket or input/output streams.
     */
    Client(GamePanel gamePanel, String ip) throws IOException {
        this.gamePanel = gamePanel;

        socket = new Socket(ip, FODServer.PORT);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        start();
    }

    /**
     * Sends data to the server.
     * @param inputData is the data to send.
     * @throws IOException when you hurt its feelings.
     */
    void transmit(InputData inputData) throws IOException {
        if (!socket.isClosed())
            out.writeObject(inputData);
        else
            System.exit(0);
    }

    /**
     * Read input from the server and update the background color based on the client's shi health.
     */
    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                try {
                    ShapeData shapeData = (ShapeData) in.readObject();

                    double health = shapeData.clientShipHealth;
                    // Make the screen get darker as you loose health.
                    gamePanel.setBackground(new Color(
                            (int) (255.0 * (health / 10.0)),
                            (int) (255.0 * (health / 10.0)),
                            (int) (255.0 * (health / 10.0))));

                    // Tell the GUI portion of the client what to draw on screen.
                    gamePanel.updateVisuals(shapeData.focus, shapeData.shapes);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
