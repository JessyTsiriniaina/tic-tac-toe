package io.github.jessytsiriniaina.controllers;

import io.github.jessytsiriniaina.network.GameClient;
import io.github.jessytsiriniaina.network.GameServer;
import io.github.jessytsiriniaina.players.AbstractPlayer;
import io.github.jessytsiriniaina.players.BotPlayer;
import io.github.jessytsiriniaina.games.Move;
import io.github.jessytsiriniaina.players.LocalPlayer;
import io.github.jessytsiriniaina.players.NetworkPlayer;
import io.github.jessytsiriniaina.players.Player;
import io.github.jessytsiriniaina.games.TicTacToeGame;
import io.github.jessytsiriniaina.ui.MainUI;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeManager {
    private final TicTacToeGame game;
    private MainUI UI;

    private final List<Player> players = new ArrayList<>();
    private GameServer server;
    private GameClient client;
    private boolean isHost;
    private int currentPlayerIndex;

    public final int HAS_WINNER = 1;
    public final int IS_FULL = 2;

    public TicTacToeManager(TicTacToeGame game, MainUI ui) {
        this.game = game;
        this.UI = ui;
    }

    public void start() throws Exception{
        if(this.players.isEmpty())
            throw new Exception("Can't start a game with 0 players");
        resetPlayerIndex();
    }

    public void resetPlayerIndex() {
        currentPlayerIndex = 0;
    }

    public int performMove(int x, int y) throws IllegalArgumentException {
        if(x > 3 || y > 3)
            throw new IllegalArgumentException("Invalid input");

        if (currentPlayer() instanceof NetworkPlayer) {

            return 0; // It's not the local player's turn, should throw an exception
        }

        Move move = new Move(x-1, y-1, ((AbstractPlayer) currentPlayer()).getCharacter());

        // Send move over network if in a network game
        if (server != null) {
            server.sendMove(x - 1, y - 1);
        } else if (client != null) {
            client.sendMove(x - 1, y - 1);
        }

        game.performMove(move);
        UI.renderGameState();

        if(game.isOver())
            return gameState();

        nextPlayer();

        Player bot;
        if((bot = isVersusBot()) != null) {
            Move botMove = bot.move();
            game.performMove(botMove);
            UI.renderGameState();

            if(game.isOver()) {
                return gameState();
            }

            nextPlayer();
        }

        return 0;
    }

    private Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int gameState() {
        if(game.hasWinner()) {
            return HAS_WINNER;
        }

        if(game.isFull()) {
            return IS_FULL;
        }

        return 0;
    }

    private void nextPlayer() {
        this.currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private Player isVersusBot() {
        for(Player player: players) {
            if(player instanceof BotPlayer)
                return player;
        }
        return null;
    }


    public char[][] getGrid() {
        return game.getGrid();
    }

    public void addPlayer(Player p) {
        if (players.size() > 1)
            return ; //should throw an expception
        players.add(p);
    }


    public String getCurrentPlayerPseudo() {
        return currentPlayer().getPseudo();
    }


    public String getWinner() {
        return getCurrentPlayerPseudo();
    }

    public void restart() throws Exception{
        game.initialize();
        start();
    }

    public void end() {
        players.clear();
        resetPlayerIndex();
    }

    public void startTwoPlayerGame() throws Exception {
        addPlayer(new LocalPlayer("Player 1", 'X'));
        addPlayer(new LocalPlayer("Player 2", 'O'));
        start();
    }

    public void startPlayerVSBotGame() throws Exception {
        addPlayer(new LocalPlayer("Player", 'X'));
        addPlayer(new BotPlayer(game));
        start();
    }

    public void setView(MainUI UI) {
        this.UI = UI;
    }

    public void hostGame() throws Exception {
        isHost = true;
        server = new GameServer(12345, this);
        addPlayer(new LocalPlayer("You (Host)", 'X'));
        addPlayer(new NetworkPlayer("Opponent (Client)", 'O'));
        start();
    }

    public void joinGame(String ipAddress) throws Exception {
        isHost = false;
        client = new GameClient(ipAddress, 12345, this);
        addPlayer(new NetworkPlayer("Opponent (Host)", 'X'));
        addPlayer(new LocalPlayer("You (Client)", 'O'));
        start();
    }

    public void onMoveReceived(int row, int col) {
        if (currentPlayer() instanceof NetworkPlayer) {
            Move move = new Move(row, col, ((AbstractPlayer) currentPlayer()).getCharacter());
            game.performMove(move);
            UI.renderGameState();

            if (!game.isOver()) {
                nextPlayer();
            }
        }
    }
}
