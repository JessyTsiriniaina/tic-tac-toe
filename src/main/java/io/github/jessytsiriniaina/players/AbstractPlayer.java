package io.github.jessytsiriniaina.players;

public abstract class AbstractPlayer implements Player {
    protected String pseudo;
    protected char character;

    public AbstractPlayer(String pseudo, char character) {
        this.pseudo = pseudo;
        this.character = character;
    }

    @Override
    public String getPseudo() {
        return pseudo;
    }

    public char getCharacter() {
        return character;
    }
}
