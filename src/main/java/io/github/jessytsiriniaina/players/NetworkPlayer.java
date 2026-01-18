package io.github.jessytsiriniaina.players;

import io.github.jessytsiriniaina.games.Move;

public class NetworkPlayer extends AbstractPlayer {

    public NetworkPlayer(String pseudo, char character) {
        super(pseudo, character);
    }

    @Override
    public Move move() {
        // Network player moves are received from the network, not generated locally.
        return null;
    }
}
