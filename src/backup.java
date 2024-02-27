import java.util.*;



public class backup {
    protected static String[][] board = new String[][]{{"", "", ""},
            {"", "", ""},
            {"", "", ""}};
    protected static String humanPlayer = "X";
    protected static String aiPlayer = "O";
    protected static String[][] rowColumn = new String[][]{{"", "", ""},
            {"", "", ""},
            {"", "", ""}};
    protected static String[][] rowColumn2 = new String[][]{{"", "", ""},
            {"", "", ""},
            {"", "", ""}};

    public static void printEmptyBoard(){
        System.out.println("\n--------------------");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print("|     ");
            }
            System.out.print("|");
            System.out.println("\n--------------------");
        }
    }

    private static void printBoard(String[][] rowColumn){
        System.out.println("\n--------------------");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(rowColumn[i][j] == "") {
                    System.out.print("|     ");
                }else{
                    System.out.printf("|  %s  ", rowColumn[i][j]);
                }
            }
            System.out.print("|");
            System.out.println("\n--------------------");
        }
    }

    public static boolean isGameOver() {
        return isBoardFull() || checkForWinner(humanPlayer) || checkForWinner(aiPlayer);
    }

    public static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == "") {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkForWinner(String player) {
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

    public static boolean makeMove(int row, int col, String player) {
        if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != "") {
            return false;
        }
        board[row][col] = player;
        return true;
    }

    public static int minimax(String player, int depth) {
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
                    if (board[i][j] == "") {
                        board[i][j] = aiPlayer;
                        bestScore = Math.max(bestScore, minimax(humanPlayer, depth+1));
                        board[i][j] = "";
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == "") {
                        board[i][j] = humanPlayer;
                        bestScore = Math.min(bestScore, minimax(aiPlayer, depth+1));
                        board[i][j] = "";
                    }
                }
            }
        }
        return bestScore;
    }

    public static int[] aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;
        int[] bestMove = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == "") {
                    board[i][j] = aiPlayer;
                    int score = minimax(humanPlayer, 0);
                    board[i][j] = "";
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }
        //int [][] bestMove = new int[][]{{bestRow},{bestCol}};

        if (bestRow == -1 || bestCol == -1) {
            System.out.println("the case where no valid move is found");
        } else {
            bestMove[0] = bestRow;
            bestMove[1] = bestCol;
        }
        System.out.println(bestRow + "  " + bestCol);

        makeMove(bestRow, bestCol, aiPlayer);
        System.out.println(bestRow+ " "+bestCol);
        return bestMove;
    }

    //validation functon *********************************
    public static int validateDigit(){
        boolean isValid = false;
        int numInt = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            String num = scanner.next();
            if (num.isEmpty() || !num.matches("\\d")) {
                System.out.print("Please enter a number (0, 1, or 2): ");
                continue;
            }
            numInt = Integer.parseInt(num);
            if(numInt == 0 ||numInt == 1 || numInt == 2){
                isValid = true;
            }else{
                System.out.print("Please enter (0, 1, or 2): ");
            }

        }while(!isValid);
        return numInt;
    }
    public static void main(String[] args) {
        String aiPlayer;
        String user2 = "";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your name: ");//Enter first username
        String user1 = scanner.next();

        //Choose opponent 1) human 2) weakAI 3) string AI *************************
        System.out.print("Do you want to play against \n\t1) human\n\t2) weakAI\n\t3) intelligentAI \nPlease choose one of the option: ");
        String choice = scanner.next();
        while(!choice.matches("\\d") || (Integer.parseInt(choice) < 1 || Integer.parseInt(choice) > 3)){
            System.out.print("Please enter a digit between 1 and 3 to choose the one options: ");
            choice = scanner.next();
        }

        //humanplayer2 ********************
        if(choice.equals("1")){
            System.out.print("Please enter your name (second player): ");//Enter first username
            user2 = scanner.next();
        }

        //Choose symbols  *************************
        System.out.print("Please choose \"X\" or \"O\": ");
        String symbol = scanner.next().toUpperCase();
        while(!symbol.equals("X") && !symbol.equals("O")){
            System.out.print("Please choose \"X\" or \"O\": ");
            symbol = scanner.next().toUpperCase();
        }
        String humanPlayer = symbol.equals("X")? "X" : "O";
        if(humanPlayer == "X"){
            aiPlayer = "O";
        }else{
            aiPlayer = "X";
        }

        printEmptyBoard();
        while (!isGameOver()) {
            //first player*******************************
            System.out.printf("%s Turn: \n", user1);
            System.out.print("Please enter row number (0, 1 or 2): ");
            int rowNumber = validateDigit();
            System.out.print("Please enter column number (0, 1 or 2): ");
            int colNumber = validateDigit();
            rowColumn[rowNumber][colNumber] = humanPlayer;

            boolean validMove = makeMove(rowNumber, colNumber, humanPlayer);
            System.out.println(validMove);
            printBoard(rowColumn);
            if (!validMove) {
                System.out.println("Invalid move");
            } else {
                //second player human, weakAI or intelligentAI********************
                switch (choice){
                    case "1":

                        System.out.printf("%s Turn: \n", user2);
                        boolean validMove2 = false;
                        while(!validMove2){
                            System.out.print("Please enter row number (0, 1 or 2): ");
                            rowNumber = validateDigit();
                            System.out.print("Please enter column number (0, 1 or 2): ");
                            colNumber = validateDigit();

                            validMove2 = makeMove(rowNumber, colNumber, aiPlayer);
                            if(!validMove2){
                                System.out.println("Invalid move");
                            }
                        }
                        rowColumn[rowNumber][colNumber] = aiPlayer;
                        System.out.println(validMove);
                        printBoard(rowColumn);
                        break;
                    case "2":

                        break;
                    case "3":
                        System.out.println("AI Player Turn: ");
                        //aiMove();
                        int[] bestMove = aiMove();
                        rowNumber = bestMove[0];
                        colNumber = bestMove[1];
                        rowColumn[rowNumber][colNumber] = aiPlayer;
                        printBoard(rowColumn);
                        break;
                }

            }
        }
        if (checkForWinner(humanPlayer)) {
            System.out.println("You win!");
        } else if (checkForWinner(aiPlayer)) {
            if(choice.equals("1")){
                System.out.printf("%s wins!", user2);
            }else {
                System.out.println("AI wins!");
            }
        } else {
            System.out.println("Tie!");
        }
    }

}
