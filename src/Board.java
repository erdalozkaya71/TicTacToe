public class Board {
    String[][] board;

    public Board(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
            }
        }
    }

    public void printEmptyBoard(){
        System.out.println("\n--------------------");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print("|     ");
            }
            System.out.print("|");
            System.out.println("\n--------------------");
        }
    }

    //create boards after each user input
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

}
