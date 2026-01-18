package io.github.jessytsiriniaina;

import io.github.jessytsiriniaina.controllers.TicTacToeManager;
import io.github.jessytsiriniaina.games.TicTacToeGame;
import io.github.jessytsiriniaina.ui.MainUI;

public class Main {
    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();
        TicTacToeManager manager = new TicTacToeManager(game, null);
        MainUI ui = new MainUI(manager);
        manager.setView(ui);
    }
}