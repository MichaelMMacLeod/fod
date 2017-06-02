package client;

import server.FODServer;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect(args[0]);
    }

    private void connect(String ip) throws IOException {
        Socket socket = new Socket(ip, FODServer.PORT);
    }
}
