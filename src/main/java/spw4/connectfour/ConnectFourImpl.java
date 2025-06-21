package spw4.connectfour;

import java.security.InvalidParameterException;

public class ConnectFourImpl implements ConnectFour {    private final int ROWS = 6;
    private final int COLS = 7;
    private final char PLACEHOLDER = '.';
    private char[][] board;
    private Player currentPlayer;
    private boolean gameOver;
    private Player winner;

    public ConnectFourImpl(Player playerOnTurn) {
        if (playerOnTurn == null || playerOnTurn == Player.none) throw new InvalidParameterException();
        currentPlayer = playerOnTurn;
        initBoard();
    }    private void initBoard() {
        gameOver = false;
        winner = Player.none;
        board = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                board[i][j] = PLACEHOLDER;
            }
        }
    }    public Player getPlayerAt(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            throw new InvalidParameterException();
        }

        char cellValue = board[row][col];

        if (cellValue == 'R') {
            return Player.red;
        } else if (cellValue == 'Y') {
            return Player.yellow;
        } else {
            return Player.none;
        }
    }

    public Player getPlayerOnTurn() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();        for (int row = 0; row < ROWS; row++) {
            sb.append("|");
            for (int col = 0; col < COLS; col++) {
                char cellValue = board[row][col];
                sb.append(" ").append(cellValue).append(" ");
            }
            sb.append("|\n");
        }

        return sb.toString();
    }    public void reset(Player playerOnTurn) {
        if (playerOnTurn == null || playerOnTurn == Player.none) throw new InvalidParameterException();
        currentPlayer = playerOnTurn;
        initBoard();
    }    public void drop(int col) {
        if (col < 0 || col >= COLS) throw new InvalidParameterException();
        int row = lookupRow(col);
        if (row == -1) throw new RuntimeException();
        board[row][col] = getPlayerOnTurn() == Player.red ? 'R' : 'Y';

        if (hasFourConnected()) {
            this.gameOver = true;
            this.winner = this.currentPlayer;
        } else {
            this.currentPlayer = this.currentPlayer == Player.red ? Player.yellow : Player.red;
        }
    }

    private int lookupRow(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == PLACEHOLDER) {
                return row;
            }
        }
        return -1;
    }

    private boolean hasFourConnected() {
        int[][] directions = {
                {0, 1},
                {1, 0},
                {1, 1},
                {1, -1}
        };        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                char currentCell = board[row][col];

                if (currentCell == PLACEHOLDER) {
                    continue;
                }

                for (int[] direction : directions) {
                    if (checkDirection(row, col, direction[0], direction[1], currentCell)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int startRow, int startCol, int deltaRow, int deltaCol, char player) {
        int endRow = startRow + 3 * deltaRow;
        int endCol = startCol + 3 * deltaCol;

        if (endRow < 0 || endRow >= ROWS || endCol < 0 || endCol >= COLS) {
            return false;
        }        for (int i = 1; i < 4; i++) {
            int checkRow = startRow + i * deltaRow;
            int checkCol = startCol + i * deltaCol;

            if (board[checkRow][checkCol] != player) {
                return false;
            }
        }

        return true;
    }
}
