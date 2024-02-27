import java.util.*;


public class Main {
    protected static String[][] board = new String[][]{{"", "", ""},
            {"", "", ""},
            {"", "", ""}};
    protected static String humanPlayer = "X";
    protected static String aiPlayer = "O";
    protected static String[][] rowColumn = new String[][]{{"", "", ""},
            {"", "", ""},
            {"", "", ""}};
    //Create an empty board at the beginnig of the game
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
    //to create board (boolean firstGame if user choose second time to play, empties rowColumn)
    private static void printBoard(String[][] rowColumn, boolean firstGame){
        if (firstGame)
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    rowColumn[i][j] = "";
                }
            }
        System.out.print("     0      1     2");
        System.out.println("\n  --------------------");
        for(int i = 0; i < 3; i++){
            System.out.print(i+" ");
            for(int j = 0; j < 3; j++){
                if(rowColumn[i][j] == "") {
                    System.out.print("|     ");
                }else{
                    System.out.printf("|  %s  ", rowColumn[i][j]);
                }
            }
            System.out.print("|");
            System.out.println("\n  --------------------");
        }
        firstGame = false;
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
    //checking winner*************************************
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
    //minimax for intelligent AI***********************
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
            System.out.println("");
        } else {
            bestMove[0] = bestRow;
            bestMove[1] = bestCol;
        }

        makeMove(bestRow, bestCol, aiPlayer);

        return bestMove;
    }

    //validation functon *********************************
    public static int validateDigit(){
        boolean isValid = false;
        int numInt = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            String num = scanner.nextLine();
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

    public static int generateRandomNum(){
        int randomNumber  = (int)(Math.random()*3);
        return randomNumber;
    }


    public static void main(String[] args) {
        String gameOver = "";
        do {
            gameOver = "";
            String aiPlayer;
            String humanPlayer;
            String user2 = "";
            int maxMove = 0;
            String user1;
            String choice;
            String symbol;
            String start;
            String startGame;

            Scanner scanner = new Scanner(System.in);

            System.out.print("Please enter your name: ");//Enter first username
            user1 = scanner.nextLine();
            while (user1.isEmpty()){
                System.out.print("Please enter your name: ");
                user1 = scanner.nextLine();
            }
            //Choose opponent 1) human 2) weakAI 3) string AI *************************
            System.out.print("Do you want to play against \n\t1) human\n\t2) weakAI\n\t3) intelligentAI \nPlease choose one of the option: ");
            choice = scanner.nextLine();
            while (!choice.matches("\\d") || (Integer.parseInt(choice) < 1 || Integer.parseInt(choice) > 3)) {
                System.out.print("Please enter a number between 1 and 3 to choose the one options: ");
                choice = scanner.nextLine();
            }

            //human player2 ********************
            if (choice.equals("1")) {
                System.out.print("Please enter your name (second player): ");//Enter first username
                user2 = scanner.nextLine();
                while (user2.isEmpty()){
                    System.out.print("Please enter your name (second player): ");
                    user2 = scanner.nextLine();
                }
            }
            user2 = choice.equals("1") ? user2 : "AIPlayer";

            //Choose symbols  *************************
            System.out.printf("%s, please choose \"X\" or \"O\": ", user1);
            symbol = scanner.nextLine().toUpperCase();
            while (!symbol.equals("X") && !symbol.equals("O") || symbol.isEmpty()) {
                System.out.print("Please choose \"X\" or \"O\": ");
                symbol = scanner.nextLine().toUpperCase();
            }
            humanPlayer = symbol.equals("X") ? "X" : "O";
            aiPlayer = humanPlayer.equals("X") ? "O" : "X";

            //Who will statrt first******************
            System.out.printf("%s, who will start first 1) you 2) %s? ", user1, user2);
            System.out.print("Please enter a number, 1 or 2 to choose one of two options: ");
            while (true) {
                start = scanner.nextLine();
                try {
                    int ch = Integer.parseInt(start);
                    if (ch == 1 || ch == 2) {
                        break;
                    } else {
                        System.out.print("Invalid input. Please enter 1 or 2: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a number (1 or 2): ");
                }
            }
            startGame = start.equals("1") ? user1 : user2;


            //print empty board**************************
            printEmptyBoard();
            while (!isGameOver()) {
                boolean validMove;
                int rowNumber = -1;
                int colNumber = -1;
                //first player*******************************

                if (startGame.equals(user1)) {
                    do {
                        System.out.printf("%s Turn: \n", user1);
                        System.out.print("Please enter row number (0, 1 or 2): ");
                        rowNumber = validateDigit();
                        System.out.print("Please enter column number (0, 1 or 2): ");
                        colNumber = validateDigit();

                        validMove = makeMove(rowNumber, colNumber, humanPlayer);
                        if(validMove) {
                            rowColumn[rowNumber][colNumber] = humanPlayer;
                            board[rowNumber][colNumber] = humanPlayer;
                            printBoard(rowColumn, false);
                        }
                        if (!validMove) {
                            System.out.println("Invalid move");
                        }
                    } while (!validMove);
                    startGame = user2;
                } else if (startGame.equals(user2)) {
                    maxMove++;
                    if (maxMove > 9) {
                        break;
                    }
                    //second player human, weakAI or intelligentAI********************
                    switch (choice) {
                        case "1": //second human player************
                            System.out.printf("%s Turn: \n", user2);
                            boolean validMove2 = false;
                            while (!validMove2) {
                                System.out.print("Please enter row number (0, 1 or 2): ");
                                rowNumber = validateDigit();
                                System.out.print("Please enter column number (0, 1 or 2): ");
                                colNumber = validateDigit();

                                validMove2 = makeMove(rowNumber, colNumber, aiPlayer);
                                if (validMove2) {
                                    rowColumn[rowNumber][colNumber] = aiPlayer;
                                    board[rowNumber][colNumber] = aiPlayer;
                                    printBoard(rowColumn, false);
                                    break;
                                }
                                System.out.println("Invalid move");
                            }
                            break;
                        case "2":  //weakAI *********************************
                            System.out.println("AI Player Turn: ");
                            boolean isAvailableForAI = false;
                            while (!isAvailableForAI) {
                                rowNumber = generateRandomNum();
                                colNumber = generateRandomNum();
                                isAvailableForAI = makeMove(rowNumber, colNumber, aiPlayer);
                            }
                            //maxTurn++;
                            board[rowNumber][colNumber] = aiPlayer;
                            rowColumn[rowNumber][colNumber] = aiPlayer;

                            printBoard(rowColumn, false);
                            break;
                        case "3":  //intelligentAI ******************************
                            System.out.println("AI Player Turn: ");
                            //aiMove();
                            int[] bestMove = aiMove();
                            rowNumber = bestMove[0];
                            colNumber = bestMove[1];
                            rowColumn[rowNumber][colNumber] = aiPlayer;
                            board[rowNumber][colNumber] = aiPlayer;
                            printBoard(rowColumn, false);
                            break;
                    }
                    maxMove++;
                    if (maxMove > 9) {
                        break;
                    }
                    startGame = user1;
                }
                // isGameOver = isGameOver();
            }

            if (checkForWinner(humanPlayer)) {
                System.out.println("You win!");
            } else if (checkForWinner(aiPlayer)) {
                if (choice.equals("1")) {
                    System.out.printf("%s wins!", user2);
                } else if(choice.equals("2")){
                    System.out.println("AI wins!");
                }  else if(choice.equals("3")){
                    System.out.println("AI wins!");
                }
            } else {
                System.out.println("Tie!");
            }
            System.out.println("Do you want to play again (y/n): ");
            gameOver = scanner.next();
            while(!gameOver.toUpperCase().equals("Y") && !gameOver.toUpperCase().equals("N")){
                System.out.println("Please enter \"Y\" or \"N\" to choose to continue or exit: ");
                gameOver = scanner.next();

            }
            if(gameOver.toUpperCase().equals("Y")){
                board = new String[][]{{"", "", ""},
                        {"", "", ""},
                        {"", "", ""}};

                printBoard(rowColumn, true);
            }

        }while (gameOver.toUpperCase().equals("Y"));
    }
}

