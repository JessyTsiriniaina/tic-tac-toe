package io.github.jessytsiriniaina.ui;

import io.github.jessytsiriniaina.controllers.TicTacToeManager;

import javax.swing.*;
import java.awt.*;


public class TicTacToeUI {
    private JPanel game;
    private JButton grid11;
    private JButton grid13;
    private JButton grid12;
    private JButton grid31;
    private JButton grid32;
    private JButton grid33;
    private JButton grid23;
    private JButton grid22;
    private JButton grid21;
    private JPanel tictactoePanel;
    private JLabel information;
    private JPanel gamePanel;
    private JPanel infoPanel;
    private JButton restartButton;
    private JButton quitButton;

    private final MainUI mainUi;
    private final TicTacToeManager manager;

    private final Color[] colors = {new Color(243, 40, 40), new Color(0x1F80E5)};
    private int currentColor;

    public TicTacToeUI(MainUI mainUi) {
        this.mainUi = mainUi;
        this.manager = mainUi.getManager();

        grid11.addActionListener(_ -> handleGridClick(grid11, 1, 1));
        grid21.addActionListener(_ -> handleGridClick(grid21, 2, 1));
        grid31.addActionListener(_ -> handleGridClick(grid31, 3, 1));
        grid12.addActionListener(_ -> handleGridClick(grid12, 1, 2));
        grid22.addActionListener(_ -> handleGridClick(grid22, 2, 2));
        grid32.addActionListener(_ -> handleGridClick(grid32, 3, 2));
        grid13.addActionListener(_ -> handleGridClick(grid13, 1, 3));
        grid23.addActionListener(_ -> handleGridClick(grid23, 2, 3));
        grid33.addActionListener(_ -> handleGridClick(grid33, 3, 3));

        restartButton.addActionListener(_ -> handleRestartGame());
        quitButton.addActionListener(_ -> handleQuit());
    }

    private void handleGridClick(JButton button, int x, int y) {
        if(manager.gameState() == 0 && button.getText().trim().isEmpty()) {
            try {
                //button.setForeground(colors[(currentColor++) % colors.length]);
                int code = manager.performMove(x,y);
                if(code == manager.HAS_WINNER) {
                    information.setText(manager.getWinner() + " wins");
                    return;
                }

                if(code == manager.IS_FULL) {
                    information.setText("It's tie, game over");
                    return;
                }

                information.setText(manager.getCurrentPlayerPseudo() + "'s turn");
            } catch (Exception e) {
                information.setText(e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }
    
    private void handleRestartGame() {
        try {
            manager.restart();
            grid11.setText("");
            grid21.setText("");
            grid31.setText("");
            grid12.setText("");
            grid22.setText("");
            grid32.setText("");
            grid13.setText("");
            grid23.setText("");
            grid33.setText("");
            information.setText(manager.getCurrentPlayerPseudo() + "'s turn");
            currentColor = 0;
        }
        catch (Exception e) {
            information.setText(e.getMessage());
        }
    }


    public void renderGameState() {
        char[][] gameGridState = manager.getGrid();

        grid11.setText(String.valueOf(gameGridState[0][0]));
        grid21.setText(String.valueOf(gameGridState[1][0]));
        grid31.setText(String.valueOf(gameGridState[2][0]));
        grid12.setText(String.valueOf(gameGridState[0][1]));
        grid22.setText(String.valueOf(gameGridState[1][1]));
        grid32.setText(String.valueOf(gameGridState[2][1]));
        grid13.setText(String.valueOf(gameGridState[0][2]));
        grid23.setText(String.valueOf(gameGridState[1][2]));
        grid33.setText(String.valueOf(gameGridState[2][2]));

        System.out.println("Finished rendering");
    }

    private void handleQuit() {
        handleRestartGame();
        mainUi.showPage("HOMEUI");
        information.setText("Game starts");
        manager.end();
    }

    public JPanel getPanel() {
        return tictactoePanel;
    }
}
