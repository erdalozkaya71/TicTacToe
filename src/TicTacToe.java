import java.util.*;

public class TicTacToe {
    private char[][] board = new char[3][3];
    private char humanPlayer = 'X';
    private char aiPlayer = 'O';

    public TicTacToe() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isGameOver() {
        return isBoardFull() || checkForWinner(humanPlayer) || checkForWinner(aiPlayer);
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkForWinner(char player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    public boolean makeMove(int row, int col, char player) {
        if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != ' ') {
            return false;
        }
        board[row][col] = player;
        return true;
    }

    public int minimax(char player, int depth) {
        if (checkForWinner(humanPlayer)) {
            return -10 + depth;
        }
        if (checkForWinner(aiPlayer)) {
            return 10 - depth;
        }
        if (isBoardFull()) {
            return 0;
        }

        int bestScore;
        if (player == aiPlayer) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = aiPlayer;
                        bestScore = Math.max(bestScore, minimax(humanPlayer, depth+1));
                        board[i][j] = ' ';
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = humanPlayer;
                        bestScore = Math.min(bestScore, minimax(aiPlayer, depth+1));
                        board[i][j] = ' ';
                    }
                }
            }
        }
        return bestScore;
    }

    public void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = aiPlayer;
                    int score = minimax(humanPlayer, 0);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        makeMove(bestRow, bestCol, aiPlayer);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe game = new TicTacToe();

        while (!game.isGameOver()) {
            game.printBoard();
            System.out.println("Enter row and column (1-3):");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            boolean validMove = game.makeMove(row, col, game.humanPlayer);
            if (!validMove) {
                System.out.println("Invalid move, try again");
            } else {
                game.aiMove();
            }
        }

        game.printBoard();

        if (game.checkForWinner(game.humanPlayer)) {
            System.out.println("You win!");
        } else if (game.checkForWinner(game.aiPlayer)) {
            System.out.println("AI wins!");
        } else {
            System.out.println("Tie!");
        }

        scanner.close();
    }
}
