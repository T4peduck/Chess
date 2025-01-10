/*
 * Actually runs the game of chess.
 * 
 * Creates a chess board for the pieces
 * Sets all the pieces on the board
 * Keeps track of the number of turns
 * Keeps track of who's turn it is
 * 
 * Takes in input from the User
 */

import java.util.Scanner;

public class Game {
    private static ChessBoard b;

    private static  ChessBoard setBoard;
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        boolean isPlaying;
        String instr;
        System.out.println("Would you like to play chess? ('y' if yes)");
        instr = read.nextLine();
        isPlaying = instr.equals("y");

        setBoard = resetBoard();


        System.out.println("Plz enter move in form of '[piece][file][rank] [file][rank]' , for example 'pe2 e4' for pawn to e4");

        while(isPlaying) {
            b = setBoard;
            System.out.println(b);
            playGame(b, read);

            System.out.println("Play again? ('y' if yes)");
            isPlaying = read.nextLine().equals("y");
        }
    }

    public static ChessBoard resetBoard() {
        //white peices
        ChessBoard temp = new ChessBoard();
        temp.setPiece(new Rook(true, temp, 0, 0), 0, 0);
        temp.setPiece(new Knight(true, temp, 0, 1), 0, 1);
        temp.setPiece(new Bishop(true, temp, 0, 2), 0, 2);
        temp.setPiece(new Queen(true, temp, 0, 3), 0, 3);
        temp.setPiece(new King(true, temp, 0, 4), 0, 4);
        temp.setKing((King)temp.getPiece(0,4));
        temp.setPiece(new Bishop(true, temp, 0, 5), 0, 5);
        temp.setPiece(new Knight(true, temp, 0, 6), 0, 6);
        temp.setPiece(new Rook(true, temp, 0, 7), 0, 7);

        for(int i = 0; i < 8; i++) {
            temp.setPiece(new Pawn(true, temp, 1, i), 1, i);
        }

        //black pieces
        temp.setPiece(new Rook(false, temp, 7, 0), 7, 0);
        temp.setPiece(new Knight(false, temp, 7, 1), 7, 1);
        temp.setPiece(new Bishop(false, temp, 7, 2), 7, 2);
        temp.setPiece(new Queen(false, temp, 7, 3), 7, 3);
        temp.setPiece(new King(false, temp, 7, 4), 7, 4);
        temp.setKing((King)temp.getPiece(7,4));
        temp.setPiece(new Bishop(false, temp, 7, 5), 7, 5);
        temp.setPiece(new Knight(false, temp, 7, 6), 7, 6);
        temp.setPiece(new Rook(false, temp, 7, 7), 7, 7);

        for(int i = 0; i < 8; i++) {
            temp.setPiece(new Pawn(false, temp, 6, i), 6, i);
        }

        temp.updateMoveList();

        return temp;
    }
    
    public static boolean movePiece(ChessBoard b, String move) {
        int ogRank;
        int ogFile;
        int newRank;
        int newFile;

        b.printMoveList(true);
        b.printMoveList(false);

        try {
            ogFile  = move.charAt(1) - 97;
            ogRank   = Integer.parseInt(move.substring(2,3)) - 1;

            newFile = move.charAt(4) - 97;
            newRank  = Integer.parseInt(move.substring(5)) - 1;
            System.out.println(String.format("ogRank: %d, ogFile: %d, newRank: %d, newFile: %d", ogRank, ogFile, newRank, newFile));
        } catch (Exception e) {
            System.out.println("Incorrect Move Format");
            return false;
        }
        Integer[] moveArr = {newRank, newFile};

        return b.makeMove(ogRank, ogFile, moveArr);
    }

    public static void playGame(ChessBoard b, Scanner read) {
        while(!(b.isBCheckMate() || b.isWCheckMate())) {
            boolean legalMove = false;
            while(!legalMove) {
                String move = read.nextLine();

                legalMove = movePiece(b, move);

                System.out.println(b);
            }
        }
    }

}
