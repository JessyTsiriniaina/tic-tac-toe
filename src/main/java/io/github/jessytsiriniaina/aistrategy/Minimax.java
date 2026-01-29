package io.github.jessytsiriniaina.aistrategy;

import io.github.jessytsiriniaina.games.TicTacToeGame;

import java.util.ArrayList;
import java.util.List;

public class Minimax {
    private final TicTacToeGame game;

    public Minimax(TicTacToeGame game) {
        this.game = game;
    }

    public int[] findBestMove() {
        int[] bestMove = new int[2];
        int best_value = Integer.MIN_VALUE;

        char[][] grid = game.getGrid();

        for(int[] move: getAvailableMove(grid)) {
            grid[move[0]][move[1]] = 'O';

            //the next player is not the maximizer
            int value = minimax(0,false);
            grid[move[0]][move[1]] = ' ';
            if (value > best_value) {
                bestMove = move;
                best_value = value;
            }
            System.out.println("value for node [" + move[0] + "][" + move[1] + "] is " + value );
        }

        return bestMove;
    }


    private int minimax(int depth, boolean isMaximizing) {
        if(game.hasWinner() && !isMaximizing) return 10 - depth;
        else if(game.hasWinner() && isMaximizing) return -10 + depth;
        else if(game.isFull()) return 0;
        else {
            if(isMaximizing) {
                int max_score = Integer.MIN_VALUE;
                List<int[]> availableMove = getAvailableMove(game.getGrid());
                for (int[] move: availableMove) {
                    game.getGrid()[move[0]][move[1]] = 'O';
                    int value = minimax(depth + 1,false);
                    max_score = Math.max(max_score, value);
                    game.getGrid()[move[0]][move[1]] = ' ';
                }
                return max_score;
            }
            else {
                int min_score = Integer.MAX_VALUE;
                List<int[]> availableMove= getAvailableMove(game.getGrid());
                for (int[] move: availableMove) {
                    game.getGrid()[move[0]][move[1]] = 'X';
                    int value = minimax(depth + 1, true);
                    min_score = Math.min(min_score, value);
                    game.getGrid()[move[0]][move[1]] = ' ';
                }
                return min_score;
            }
        }
    }


    private List<int[]> getAvailableMove(char[][] grid) {
        List<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(grid[i][j] == ' ')
                    availableMoves.add(new int[]{i, j});
            }
        }

        return availableMoves;
    }
}
