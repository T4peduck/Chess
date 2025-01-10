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

    private ArrayList<Piece> whitePieces;
    public ArrayList<Integer[]> whiteMoves;
    private King whiteKing;

    private ArrayList<Piece> blackPieces;
    public ArrayList<Integer[]> blackMoves;
    private King blackKing;

    /*
     * CONSTRUCTOR
     */
    public ChessBoard() {
        //make the board
        gameboard = new Piece[8][8];
        playerMove = true;

        whitePieces = new ArrayList<Piece>();
        whiteMoves = new ArrayList<Integer[]>();

        blackPieces = new ArrayList<Piece>();
        blackMoves = new ArrayList<Integer[]>();

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
            whitePieces.add(p);
        } else {
            blackPieces.add(p);
        }
    }

    public void setKing(King k) {
        if(k.team && whiteKing == null) {
            whiteKing = k;
        } else if (!k.team && blackKing == null) {
            blackKing = k;
        } else {
            System.out.println("King was already set");
        }
    }

    public void switchTurn() {
        playerMove = !playerMove;
    }

    public boolean isWCheckMate() {
        return whiteKing.isCheckMate();
    }

    public boolean isBCheckMate() {
        return blackKing.isCheckMate();
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
    public void updateMoveList() {
        for(Piece p: whitePieces) {
            p.updateMoveList();
            whiteMoves.addAll(p.getMoves());
        }

        for(Piece p: blackPieces) {
            p.updateMoveList();
            blackMoves.addAll(p.getMoves());
        }
    }

    /*
     * returns a string that represents the current game state
     */
    public String toString() {
        String rstr = "";
        int i = 1;
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
        return rstr + "  a  b  c  d  e  f  g  h  ";
    }

    public void printMoveList(boolean team) {
        if(team) {
            System.out.println("WhitePieces:");
            for(Piece p: whitePieces) {
                System.out.print(p + ": ");
                p.printMoves();
            }
        } else {
            System.out.println("BlackPieces:");
            for(Piece p: blackPieces) {
                System.out.print(p + ": ");
                p.printMoves();
            }
        }
    }

}
