package io.github.jessytsiriniaina.ui;

import io.github.jessytsiriniaina.controllers.TicTacToeManager;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    private JPanel mainPanel;
    private final CardLayout cardLayout;

    private final TicTacToeManager manager;
    private final TicTacToeUI tictactoe;


    public MainUI(TicTacToeManager manager) {
        this.manager = manager;
        HomeUI home = new HomeUI(this);
        tictactoe = new TicTacToeUI(this);

        setContentPane(mainPanel);
        setTitle("TicTacToe");
        cardLayout = (CardLayout) mainPanel.getLayout();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // Adjust frame size based on content | Can be replaced with setSize()
        setVisible(true);

        cardLayout.addLayoutComponent(
                mainPanel.add(home.getPanel()),
                "HOMEUI");
        cardLayout.addLayoutComponent(
                mainPanel.add(tictactoe.getPanel()),
                "TICTACTOEUI");
    }

    public TicTacToeManager getManager() {
        return this.manager;
    }

    public void showPage(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void renderGameState() {
        tictactoe.renderGameState();
    }
}
