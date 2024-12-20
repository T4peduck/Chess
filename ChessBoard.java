/*
 * Simulates the chess board using a 2D arrayList
 * Keeps track of the player turn using a boolean "playerMove"
 * 
 * Piece[][] gameboard:     a 2D array that holds Piece objects. Dimensions are
 *                          8 x 8 but can be increased if want to.
 * 
 * boolean playerMove:      holds current players move, T for one player and F for other
 * 
 * ArrayList<Peice> whitePlayer piece
 * ArraList<Peice> blackPlayer piece
 * 
 * Getters:
 *      getPiece(int rank, int col)
 *      getMoves(Piece p)
 * 
 * Setters:
 *      setPiece(Piece p, int rank, int col)
 * 
 * METHODS:
 *      makeMove(int rankInit, int colInit, Integer[] move)
 *      checkLegalMove(Piece p,  ArrayList<Integer[]> possMoves, Integer[] move)
 *      toString()
 * 
 */



import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoard {
    private Piece[][] gameboard;
    private boolean playerMove;

    private ArrayList<Piece> whitePeices;
    public ArrayList<Integer[]> whiteMoves;

    private ArrayList<Piece> blackPeices;
    public ArrayList<Integer[]> blackMoves;

    /*
     * CONSTRUCTOR
     */
    public ChessBoard() {
        //make the board
        gameboard = new Piece[8][8];
        playerMove = true;
    }






    //Getters
    public Piece getPiece(int rank, int col) {
        return gameboard[rank][col];
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Integer[]> getMoves(Piece p) {
        return p.getMoves();
    }

    //Setters
    public void setPiece(Piece p, int rank, int col) {
        gameboard[rank][col] = p;

        if(p.getTeam() == true) {
            whitePeices.add(p);
        } else {
            blackPeices.add(p);
        }
    }





    /*
     * Purpose: moves a peice from one location to another, replacing
     *          other peice if its currently occupying the tile. Does so
     *          by checking if peice is at initial location then by checking
     *          if destination tile is a possible move.
     */
    public boolean makeMove(int rankInit, int colInit, Integer[] move) {
        Piece p = gameboard[rankInit][colInit];
        if(p == null) {
            System.out.println("No peice at rank: " + rankInit +", col: " + colInit);
            return false;
        }

        if(p.getTeam() != playerMove) {
            System.out.println("Wrong team's peice");
            return false;
        }

        @SuppressWarnings("unchecked")
        ArrayList<Integer[]> possMoves = p.getMoves();
        if(!checkLegalMove(p, possMoves, move)) {
            System.out.println("Move destination invalid");
            return false;
        }
        p.setPiecePos(move[0], move[1]);
        gameboard[move[0]][move[1]] = p;
        gameboard[rankInit][colInit] = null;

        updateMoveList();
        
        return true;
    }


    /*
     * Purpose: helps makeMove() by making sure Integer[]
     *          move is actually a legal move by checking if
     *          its in ArrayList<Integer[]> possMoves
     */
    private boolean checkLegalMove(Piece p,  ArrayList<Integer[]> possMoves, Integer[] move)  {
        for(Integer[] possibleMove: possMoves) {
            if(Arrays.deepEquals(possibleMove, move)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void updateMoveList() {
        for(Piece p: whitePeices) {
            p.updateMoveList();
            whiteMoves.addAll(p.getMoves());
        }

        for(Piece p: blackPeices) {
            p.updateMoveList();
            blackMoves.addAll(p.getMoves());
        }
    }

    /*
     * returns a string that represents the current game state
     */
    public String toString() {
        String rstr = "";
        int i = 0;
        for(Piece[] row: gameboard) {
            rstr += i;
            for(Piece p: row) {
                rstr += " ";
                if(p == null) {
                    rstr += " ";
                } else {
                    rstr += p.toString();
                }
                rstr += " ";
            }
            rstr += "\n";
            i++;
        }
        return rstr + " 0  1  2  3  4  5  6  7  ";
    }

}
