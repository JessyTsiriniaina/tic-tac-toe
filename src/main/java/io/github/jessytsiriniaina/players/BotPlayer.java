package io.github.jessytsiriniaina.players;

import io.github.jessytsiriniaina.aistrategy.Minimax;
import io.github.jessytsiriniaina.games.Move;
import io.github.jessytsiriniaina.games.TicTacToeGame;

public class BotPlayer extends AbstractPlayer {

    private final Minimax minimax;

    public BotPlayer(TicTacToeGame game) {
        super("AI", 'O');
        minimax = new Minimax(game);
    }

    @Override
    public Move move() {
        int[] movePoint = minimax.findBestMove();
        return new Move(movePoint[0], movePoint[1], this.character);
    }
}
