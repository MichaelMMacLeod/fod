package server;

import java.io.IOException;

public class FODServer {
    public static final int PORT = 7799;

    public static void main(String[] args) throws IOException {
        Server server = new Server(PORT);
    }
}
