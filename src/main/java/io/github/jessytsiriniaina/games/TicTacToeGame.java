package io.github.jessytsiriniaina.games;

public class TicTacToeGame {
	private final char[][]  grid = new char[3][3];

    public TicTacToeGame() {
        initialize();
    }

    public void initialize() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    public void performMove(Move move) throws IllegalArgumentException {
        if(grid[move.x()][move.y()] == ' ')
            grid[move.x()][move.y()] = move.car();
        else throw new IllegalArgumentException("Case occupied");
    }


    public char[][] getGrid() {
        return grid;
    }


    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == ' ') {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean hasWinner() {
        for (int i = 0; i < 3; i++) {
            if (isRowWin(i)) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (isColWin(i)) {
                return true;
            }
        }

        return isDiag1Win() || isDiag2Win();
    }


    private boolean isRowWin(int row) {
        return (grid[row][0] != ' ' && grid[row][0] == grid[row][1] && grid[row][1] == grid[row][2]) ;
    }

    private boolean isColWin(int col) {
        return (grid[0][col] != ' ' && grid[0][col] == grid[1][col] && grid[1][col] == grid[2][col]);
    }

    private boolean isDiag1Win() {
        return (grid[0][0] != ' ' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]);
    }

    private boolean isDiag2Win() {
        return (grid[0][2] != ' ' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]);
    }

    public boolean isOver() {
        return hasWinner() || isFull();
    }

}
