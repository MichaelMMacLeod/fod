package server;

import common.drawable.Poly;
import common.drawable.Ship;
import common.message.InputData;
import common.message.ShapeData;

import java.io.IOException;
import java.io.ObjectInputStream;
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

    static Poly[] getShapes() {
        Poly[] shapes = new Poly[clients.size()];

        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = clients.get(i).getPoly();
        }

        return shapes;
    }
}
