import java.io.IOException;

/**
 * Really just a wrapper for the Server class to make everything cleaner.
 */
public class FODServer {
    /**
     * The port the server runs on.
     */
    public static final int PORT = 7799;

    /**
     * How fast the server updates.
     */
    private static final int MS_PER_UPDATE = 10;

    /**
     * Reference to the server which handles client connections.
     */
    private static Server server;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server...");
        server = new Server(PORT);
        System.out.println("Server started.");

        gameLoop();
    }

    /**
     * Updates the server on a regular interval.
     */
    private static void gameLoop() {
        double previous = System.currentTimeMillis();
        double lag = 0;

        while (true) {
            double current = System.currentTimeMillis();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            while (lag >= MS_PER_UPDATE) {
                server.update();
                lag -= MS_PER_UPDATE;
            }
        }
    }
}
