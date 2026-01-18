package io.github.jessytsiriniaina.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import io.github.jessytsiriniaina.controllers.TicTacToeManager;

public class GameClient {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private final String serverAddress;
    private final int port;
    private final TicTacToeManager manager;

    public GameClient(String serverAddress, int port, TicTacToeManager manager) throws IOException {
        this.serverAddress = serverAddress;
        this.port = port;
        this.manager = manager;
        connectToServer();
    }

    private void connectToServer() throws IOException {
        System.out.println("Connecting to server at " + serverAddress + ":" + port + "...");
        this.socket = new Socket(serverAddress, port);
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        System.out.println("Connected to server.");
        new Thread(this::listenForMoves).start();
    }

    private void listenForMoves() {
        while (true) {
            try {
                int row = in.readInt();
                int col = in.readInt();
                manager.onMoveReceived(row, col);
            } catch (IOException e) {
                // Handle server disconnection
                break;
            }
        }
    }

    public void sendMove(int row, int col) {
        try {
            out.writeInt(row);
            out.writeInt(col);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
