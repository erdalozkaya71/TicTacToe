public class IntelligentAI {
    private String[][] board = new String[3][3];
    private String humanPlayer;
    private String aiPlayer;

    public IntelligentAI(String humanPlayer, String aiPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
            }
        }
        this.aiPlayer = aiPlayer;
        this.humanPlayer = humanPlayer;
    }

    public String getHumanPlayer() {
        return humanPlayer;
    }

    public String getAiPlayer() {
        return aiPlayer;
    }

    public void setHumanPlayer(String humanPlayer) {
        this.humanPlayer = humanPlayer;
    }

    public void setAiPlayer(String aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    public boolean isGameOver(String humanPlayer, String aiPlayer, String[][] board) {
        return isBoardFull(board) || checkForWinner(humanPlayer, board) || checkForWinner(aiPlayer, board);
    }

    public boolean isBoardFull(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == " ") {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkForWinner(String player, String[][] board) {
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

    public boolean makeMove(int row, int col, String player, String[][] board) {
        if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != " ") {
            return false;
        }
        board[row][col] = player;
        return true;
    }

    public int minimax(String player, int depth, String[][] board) {
        if (checkForWinner(humanPlayer, board)) {
            return -10 + depth;
        }
        if (checkForWinner(aiPlayer, board)) {
            return 10 - depth;
        }
        if (isBoardFull(board)) {
            return 0;
        }

        int bestScore;
        if (player == aiPlayer) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == " ") {
                        board[i][j] = aiPlayer;
                        bestScore = Math.max(bestScore, minimax(humanPlayer, depth+1, board));
                        board[i][j] = " ";
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == " ") {
                        board[i][j] = humanPlayer;
                        bestScore = Math.min(bestScore, minimax(aiPlayer, depth+1, board));
                        board[i][j] = " ";
                    }
                }
            }
        }
        return bestScore;
    }

    public void aiMove(String[][] board) {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == " ") {
                    board[i][j] = aiPlayer;
                    int score = minimax(humanPlayer, 0, board);
                    board[i][j] = " ";
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        makeMove(bestRow, bestCol, aiPlayer, board);
    }
}
