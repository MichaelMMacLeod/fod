package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Server {
    private ArrayList<Connection> clients = new ArrayList<>();

    Server(int port) throws IOException {
        ServerSocket listener = new ServerSocket(port);

        try {
            while (true) {
                clients.add(new Connection(listener.accept()));
            }
        } finally {
            listener.close();
        }
    }

    private static class Connection extends Thread {
        private Socket socket;

        Connection(Socket socket) {
            this.socket = socket;

            System.out.println("Client connected: " + socket);
        }

        @Override
        public void run() {

        }
    }
}
