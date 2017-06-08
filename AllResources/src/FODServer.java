import java.io.IOException;

public class FODServer {
    public static final int PORT = 7799;

    private static final int MS_PER_UPDATE = 10;

    private static Server server;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server...");
        server = new Server(PORT);
        System.out.println("Server started.");

        gameLoop();
    }

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
