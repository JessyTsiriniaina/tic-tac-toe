package io.github.jessytsiriniaina.ui;

import io.github.jessytsiriniaina.controllers.TicTacToeManager;

import javax.swing.JOptionPane;
import javax.swing.*;

public class HomeUI {
    private JPanel homePanel;
    private JButton twoPlayerLocalButton;
    private JButton playerVsBotButton;
    private JButton playOnLanButton;

    private final MainUI mainUi;
    private final TicTacToeManager manager;

    public HomeUI(MainUI mainUi) {
        this.mainUi = mainUi;
        this.manager = mainUi.getManager();

        twoPlayerLocalButton.addActionListener(_ -> startTwoPlayerLocalGame());
        playerVsBotButton.addActionListener(_ -> startPlayerVSBotGame());
        playOnLanButton.addActionListener(_ -> initiateLanGame());
    }

    public void startTwoPlayerLocalGame() {
        mainUi.showPage("TICTACTOEUI");
        try {
            manager.startTwoPlayerGame();
        } catch (Exception e) {
            throw new RuntimeException(e); //Change this to dialog box
        }
    }

    public void startPlayerVSBotGame() {
        mainUi.showPage("TICTACTOEUI");
        try {
            manager.startPlayerVSBotGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void initiateLanGame() {
        Object[] options = {"Host", "Join"};
        int choice = JOptionPane.showOptionDialog(homePanel,
                "Do you want to host or join a game?",
                "LAN Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == JOptionPane.YES_OPTION) { // Host
            try {
                manager.hostGame();
                mainUi.showPage("TICTACTOEUI");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(homePanel, "Could not start server: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (choice == JOptionPane.NO_OPTION) { // Join
            String ipAddress = JOptionPane.showInputDialog(homePanel, "Enter the IP address of the host:");
            if (ipAddress != null && !ipAddress.trim().isEmpty()) {
                try {
                    manager.joinGame(ipAddress);
                    mainUi.showPage("TICTACTOEUI");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(homePanel, "Could not connect to server: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public JPanel getPanel() {
        return homePanel;
    }
}
