package server;

import common.drawable.Poly;
import common.message.Message;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Server {
    private static ArrayList<Connection> clients = new ArrayList<>();

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

    private static Poly[] getShapes() {
        Poly[] shapes = new Poly[clients.size()];

        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = clients.get(i).getPoly();
        }

        return shapes;
    }

    private static class Connection extends Thread {
        private Socket socket;

        private Poly poly;

        Connection(Socket socket) {
            this.socket = socket;

            System.out.println("Client connected: " + socket);

            poly = new Poly();

            start();
        }

        Poly getPoly() {
            return poly;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    out.writeObject(new Message(new Point(0, 0), getShapes()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
