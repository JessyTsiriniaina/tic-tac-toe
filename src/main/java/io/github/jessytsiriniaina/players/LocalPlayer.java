package io.github.jessytsiriniaina.players;

import io.github.jessytsiriniaina.games.Move;

public class LocalPlayer extends AbstractPlayer {

    public LocalPlayer(String pseudo, char character) {
        super(pseudo, character);
    }

    @Override
    public Move move() {
        return new Move(-1, -1, this.character);
    }
}
