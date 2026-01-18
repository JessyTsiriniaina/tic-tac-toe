package io.github.jessytsiriniaina.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import io.github.jessytsiriniaina.controllers.TicTacToeManager;

public class GameServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private final int port;
    private final TicTacToeManager manager;


    public GameServer(int port, TicTacToeManager manager) {
        this.port = port;
        this.manager = manager;
        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port + ". Waiting for a player to connect...");
            clientSocket = serverSocket.accept();
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            System.out.println("Player connected from " + clientSocket.getInetAddress());
            listenForMoves();
        } catch (IOException e) {
            // In a real application, you'd want to handle this more gracefully
            e.printStackTrace();
        }
    }

    private void listenForMoves() {
        while (true) {
            try {
                int row = in.readInt();
                int col = in.readInt();
                manager.onMoveReceived(row, col);
            } catch (IOException e) {
                // Handle client disconnection
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
