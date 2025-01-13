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
 * public ArrayList<Integer[]> whiteMoves
 * private King whiteKing                  holds all of white players current active peices, possible moves white 
 *                                         white can make and a pointer to whites king. Same for black
 * ArraList<Peice> blackPlayer piece
 * public ArrayList<Integer[]> blackMoves;
 * private King blackKing;
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
    private ArrayList<Piece> whiteCapturedPieces;
    public ArrayList<Integer[]> whiteMoves;
    protected King whiteKing;

    private ArrayList<Piece> blackPieces;
    private ArrayList<Piece> blackCapturedPieces;
    public ArrayList<Integer[]> blackMoves;
    protected King blackKing;

    /*
     * CONSTRUCTOR
     */
    public ChessBoard(){
        //make the board
        gameboard = new Piece[8][8];
        playerMove = true;

        whitePieces = new ArrayList<Piece>();
        whiteMoves = new ArrayList<Integer[]>();

        blackPieces = new ArrayList<Piece>();
        blackMoves = new ArrayList<Integer[]>();

    }


    //Getters
    public Piece getPiece(int rank, int col){
        return gameboard[rank][col];
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Integer[]> getMoves(Piece p){
        return p.getMoves();
    }

    //Setters
    public void setPiece(Piece p, int rank, int col){
        gameboard[rank][col] = p;
        if(p instanceof King){
            return;
        }
        if(p.getTeam()== true) {
            whitePieces.add(p);
        } else {
            blackPieces.add(p);
        }
    }

    public void setKing(King k){
        if(k.team && whiteKing == null) {
            whiteKing = k;
        } else if (!k.team && blackKing == null) {
            blackKing = k;
        } else {
            System.out.println("King was already set");
        }
    }

    public void switchTurn(){
        playerMove = !playerMove;
    }

    public boolean isWCheckMate() {
        return whiteKing.isCheckMate();
    }

    public boolean isBCheckMate(){
        return blackKing.isCheckMate();
    }


    /*
     * Purpose: moves a peice from one location to another, replacing
     *          other peice if its currently occupying the tile. Does so
     *          by checking if peice is at initial location then by checking
     *          if destination tile is a possible move. returns false, if
     *          unable to carry out move.
     */
    public boolean makeMove(int rankInit, int colInit, Integer[] move){
        Piece p = gameboard[rankInit][colInit];
        if(p == null){
            System.out.println("No peice at rank: " + rankInit +", file: " + colInit);
            return false;
        }

        if(p.getTeam()!= playerMove) {
            System.out.println("Wrong team's peice");
            return false;
        }

        @SuppressWarnings("unchecked")
        ArrayList<Integer[]> possMoves = p.getMoves();
        if(!checkLegalMove(p, possMoves, move)) {
            System.out.println("Move destination invalid");
            return false;
        }

        Piece origEnemyPiece = getPiece(move[0], move[1]);


        gameboard[move[0]][move[1]] = p;
        gameboard[rankInit][colInit] = null;

        updateMoveList();

        setCheckStatus();

        //check if peice is pinned or if still in check
        if((playerMove && whiteKing.isInCheck) || 
                (!playerMove && blackKing.isInCheck)){
            System.out.println("Check");
            p.setPiecePos(rankInit, colInit);
            origEnemyPiece.setPiecePos(move[0], move[1]);
            updateMoveList();
            return false;
        }

        /*
        //check for check
        if((playerMove && blackKing.isCheck(blackKing.rank, blackKing.col)) || 
                !playerMove && whiteKing.isCheck(whiteKing.rank, whiteKing.col)) {
            if(playerMove) {
                blackKing.setCheck(true);
            } else {
                whiteKing.setCheck(true);
            }

        }
        */

        if(p instanceof Pawn temp) {
            temp.pawnHasMoved();
        }

        if(p instanceof King temp){
            temp.updateKingBubble();
        }

        p.setPiecePos(move[0], move[1]);
        if(origEnemyPiece != null) {
            capturePiece(origEnemyPiece);
        }

        return true;
    }


    /*
     * Purpose: helps makeMove(by making sure Integer[]
     *          move is actually a legal move by checking if
     *          its in ArrayList<Integer[]> possMoves
     */
    private boolean checkLegalMove(Piece p,  ArrayList<Integer[]> possMoves, Integer[] move)  {
        for(Integer[] possibleMove: possMoves){
            if(Arrays.deepEquals(possibleMove, move)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void updateMoveList() {
        whiteMoves = new ArrayList<>();
        blackMoves = new ArrayList<>();

        for(Piece p: whitePieces){
            p.updateMoveList();
            whiteMoves.addAll(p.getMoves());
        }

        for(Piece p: blackPieces) {
            p.updateMoveList();
            blackMoves.addAll(p.getMoves());
        }

        whiteKing.updateMoveList();
        whiteMoves.addAll(whiteKing.getMoves());
        blackKing.updateMoveList();
        blackMoves.addAll(blackKing.getMoves());
    }

    private void capturePiece(Piece p){
        if(p.getTeam()) {
            whitePieces.remove(p);
            blackCapturedPieces.add(p);
        } else {
            blackPieces.remove(p);
            whiteCapturedPieces.add(p);
        }
    }

    private void setCheckStatus(){
        if(playerMove){
            if(whiteKing.isInCheck && !whiteKing.isCheck(whiteKing.rank, whiteKing.col)){
                whiteKing.setCheck(false);
            } else if (!whiteKing.isInCheck && whiteKing.isCheck(whiteKing.rank, whiteKing.col)){
                whiteKing.setCheck(true);
            }
        } else {
            if(blackKing.isInCheck && !blackKing.isCheck(blackKing.rank, blackKing.col)){
                blackKing.setCheck(false);
            } else if (!blackKing.isInCheck && blackKing.isCheck(blackKing.rank, blackKing.col)){
                blackKing.setCheck(true);
            }
        }
    }

    /*
     * returns a string that represents the current game state
     */
    public String toString() {
        String rstr = "";
        if(playerMove){
            rstr += "WHITE MOVE\n";
        } else {
            rstr += "BLACK MOVE\n";
        }

        int i = 0;
        for(Piece[] row: gameboard){
            int j = 0;
            rstr += (i + 1);
            for(Piece p: row) {
                rstr += " ";
                if(p == null){
                    if((i + j)% 2 == 0){
                        rstr += "\u25A9";
                    } else {
                        rstr += "\u25A2";
                    }
                } else {
                    rstr += p.toString();
                }
                rstr += " ";
                j++;
            }
            rstr += "\n";
            i++;
        }
        return rstr + "  a  b  c  d  e  f  g  h  ";
    }

    public void printMoveList(boolean team) {
        if(team) {
            System.out.println("WhitePieces:");
            System.out.print(whiteKing + ": ");
            whiteKing.printMoves();
            for(Piece p: whitePieces) {
                System.out.print(p + ": ");
                p.printMoves();
            }
        } else {
            System.out.print(blackKing + ": ");
            blackKing.printMoves();
            System.out.println("BlackPieces:");
            for(Piece p: blackPieces){
                System.out.print(p + ": ");
                p.printMoves();
            }
        }
    }

}