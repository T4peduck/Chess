/*
 * holds classes for each of the peices and 
 * how they move
 * 
 * Each piece is a subclass of the Piece class:
 *      boolean team:   either T or F, differentiates what team they on
 *      Board board:    references the board on which the Piece is placed
 *      int rank:       row on which the piece is placed
 *      int col:        col on which the piece is placed
 * 
 *      ArrayList<Integer[]> moves:
 *                      List of possible  moves a peice can make
 * 
 *      TODO: finish each type of piece movement
 */


import java.util.ArrayList;
import java.util.Arrays;

public class Piece {
    protected boolean     team;
    protected ChessBoard  board;
    protected int         rank;
    protected int         col;
    
    protected ArrayList<Integer[]>   moves;
    

    public Piece(boolean team, ChessBoard board, int rank, int col) {
        this.team = team;
        this.board = board;
        this.rank = rank;
        this.col = col;
    }

    public void setPiecePos(int newrank, int newcol) {
        this.rank = newrank;
        this.col =  newcol;
    }

    public ArrayList getMoves() {
        return moves;
    }

    public boolean getTeam() {
        return team;
    }

    public void updateMoveList() {}


}

/*
 * -------------------------------------------------------------------
 */

class Pawn extends Piece {
    private boolean hasMoved;
    private boolean promote;

    public Pawn(boolean team, ChessBoard board, int rank, int col) {
        super(team, board, rank, col);
        hasMoved = false;
        promote = false;
    }

    public String toString() {
        return "^";
    }

    public void updateMoveListB() {
        int r;
        int c;
        ArrayList<Integer[]> temp = new ArrayList<>();
        if(col == 7) {
            promote = true;
        }

        //move 1
        c = col + 1;
        if(c < 8 && board.getPiece(rank, c) == null) {
            hasMoved = true;
            Integer[] move = {rank, c};
            temp.add(move);
        }

        //move 2
        c = col + 2;
        if(c < 8 && board.getPiece(rank, c) == null && hasMoved == false){
            hasMoved = true;
            Integer[] move = {rank, c};
            temp.add(move);
        }

        //takeR
        c = col + 1;
        r = rank + 1;
        if(c < 8 && r < 8 &&
            board.getPiece(r, c) != null && board.getPiece(r,c).team != team) {
            hasMoved = true;
            Integer[] move = {r,c};
            temp.add(move);
        }

        //takeL
        c = col + 1;
        r = rank - 1;
        if(c < 8 && r > -1 &&
            board.getPiece(r, c) != null && board.getPiece(r,c).team != team) {
            hasMoved = true;
            Integer[] move = {r,c};
            temp.add(move);
        }

        moves = temp;
    }

    public void updateMoveListW() {
        int r;
        int c;
        ArrayList<Integer[]> temp = new ArrayList<>();
        if(col == 0) {
            promote = true;
        }

        //move 1
        c = col - 1;
        if(c > -1 && board.getPiece(rank, c) == null) {
            hasMoved = true;
            Integer[] move = {rank, c};
            temp.add(move);
        }

        //move 2
        c = col - 2;
        if(c > -1 && board.getPiece(rank, c) == null && hasMoved == false){
            hasMoved = true;
            Integer[] move = {rank, c};
            temp.add(move);
        }

        //takeR
        c = col - 1;
        r = rank + 1;
        if(c > -1 && r < 8 &&
            board.getPiece(r, c) != null && board.getPiece(r,c).team != team) {
            hasMoved = true;
            Integer[] move = {r,c};
            temp.add(move);
        }

        //takeL
        c = col - 1;
        r = rank - 1;
        if(c > -1 && r > -1 &&
            board.getPiece(r, c) != null && board.getPiece(r,c).team != team) {
            hasMoved = true;
            Integer[] move = {r,c};
            temp.add(move);
        }

        moves = temp;
    }
}

/*
 * -------------------------------------------------------------------
 */

class Knight extends Piece {
    public Knight(boolean team, ChessBoard board, int rank, int col) {
        super(team, board, rank, col);
    }

    public String toString() {
        return "N";
    }

    public void updateMoveList() {
        int r;
        int c;
        ArrayList<Integer[]> temp = new ArrayList<>();
        
        //nne
        r = rank - 2;
        c = col + 1;
        if(r > -1 && c < 8 && 
            (board.getPiece(r,c) == null || board.getPiece(r,c).team != team)) {
                Integer[] move = {r, c};
                temp.add(move);
            }

        //nee
        r = rank - 1;
        c = col + 2;
        if(r > -1 && c < 8 && 
            (board.getPiece(r,c) == null || board.getPiece(r,c).team != team)) {
                Integer[] move = {r, c};
                temp.add(move);
            }
        
        //see
        r = rank + 1;
        c = col + 2;

        if(r < 8 && c < 8 && 
            (board.getPiece(r,c) == null || board.getPiece(r,c).team != team)) {
                Integer[] move = {r, c};
                temp.add(move);
            }
        
        //sse
        r = rank + 2;
        c = col + 1;

        if(r < 8 && c < 8 && 
            (board.getPiece(r,c) == null || board.getPiece(r,c).team != team)) {
                Integer[] move = {r, c};
                temp.add(move);
            }

        //ssw
        r = rank + 2;
        c = col - 1;

        if(r < 8 && c > -1 && 
            (board.getPiece(r,c) == null || board.getPiece(r,c).team != team)) {
                Integer[] move = {r, c};
                temp.add(move);
            }

        //sww
        r = rank + 1;
        c = col - 2;

        if(r < 8 && c > -1 && 
            (board.getPiece(r,c) == null || board.getPiece(r,c).team != team)) {
                Integer[] move = {r, c};
                temp.add(move);
            }

        //nww
        r = rank - 1;
        c = col - 2;

        if(r > -1 && c > -1 && 
            (board.getPiece(r,c) == null || board.getPiece(r,c).team != team)) {
                Integer[] move = {r, c};
                temp.add(move);
            }
        
        //nnw
        r = rank - 2;
        c = col - 1;

        if(r > -1 && c > -1 && 
            (board.getPiece(r,c) == null || board.getPiece(r,c).team != team)) {
                Integer[] move = {r, c};
                temp.add(move);
            }

        moves = temp;
    }
}

/*
 * -------------------------------------------------------------------
 */

class Bishop extends Piece {
    public Bishop(boolean team, ChessBoard board, int rank, int col) {
        super(team, board, rank, col);
    }

    public String toString() {
        return "B";
    }

    public void updateMoveList() {
        ArrayList<Integer[]> temp = new ArrayList<>();

        //upLeft
        int r = rank - 1;
        int c = col - 1;
        while(r > -1 && c > -1) {
            if(board.getPiece(r, c) == null) {
                Integer[] move = {r, c};
                temp.add(move);
            } else if(board.getPiece(r, c).team != team) {
                Integer[] move = {r, c};
                temp.add(move);
                break;
            } else {
                break;
            }
            r--;
            c--;
        }

        //upRight
        r = rank - 1;
        c = col + 1;
        while(r > -1 && c < 8) {
            if(board.getPiece(r, c) == null) {
                Integer[] move = {r, c};
                temp.add(move);
            } else if(board.getPiece(r, c).team != team) {
                Integer[] move = {r, c};
                temp.add(move);
                break;
            } else {
                break;
            }
            r--;
            c++;
        }

        //downLeft
        r = rank + 1;
        c = col - 1;
        while(r < 8 && c > -1) {
            if(board.getPiece(r, c) == null) {
                Integer[] move = {r, c};
                temp.add(move);
            } else if(board.getPiece(r, c).team != team) {
                Integer[] move = {r, c};
                temp.add(move);
                break;
            } else {
                break;
            }
            r++;
            c--;
        }

        //downRight
        r = rank + 1;
        c = col + 1;
        while(r < 8 && c < 8) {
            if(board.getPiece(r, c) == null) {
                Integer[] move = {r, c};
                temp.add(move);
            } else if(board.getPiece(r, c).team != team) {
                Integer[] move = {r, c};
                temp.add(move);
                break;
            } else {
                break;
            }
            r++;
            c++;
        }

        moves = temp;
    }
}

/*
 * -------------------------------------------------------------------
 */
class Rook extends Piece {
    public Rook(boolean team, ChessBoard board, int rank, int col) {
        super(team, board, rank, col);
    }

    public String toString() {
        return "R";
    }

    public void updateMoveList() {
        ArrayList<Integer[]> temp = new ArrayList<>();
        //down
        for(int r = rank + 1; r < 8; r++) {
            if(board.getPiece(r, col) == null) {
                Integer[] move = {r, col};
                temp.add(move);
            } else if(board.getPiece(r, col).team != team) {
                Integer[] move = {r, col};
                temp.add(move);
                break;
            } else {
                break;
            }
        }

        //left
        for(int c = col - 1; c > -1; c--) {
            if(board.getPiece(rank, c) == null) {
                Integer[] move = {rank, c};
                temp.add(move);
            } else if(board.getPiece(rank, c).team != team) {
                Integer[] move = {rank, c};
                temp.add(move);
                break;
            } else {
                break;
            }
        }

        //right
        for(int c = col + 1; c < 8; c++) {
            if(board.getPiece(rank, c) == null) {
                Integer[] move = {rank, c};
                temp.add(move);
            } else if(board.getPiece(rank, c).team != team) {
                Integer[] move = {rank, c};
                temp.add(move);
                break;
            } else {
                break;
            }
        }

        //up
        for(int r = rank - 1; r > -1; r--) {
            if(board.getPiece(r, col) == null) {
                Integer[] move = {r, col};
                temp.add(move);
            } else if(board.getPiece(r, col).team != team) {
                Integer[] move = {r, col};
                temp.add(move);
                break;
            } else {
                break;
            }
        }

        moves = temp;
    }
}

/*
 * -----------------------------------------------------------
 */
class Queen extends Piece {
    public Queen(boolean team, ChessBoard board, int rank, int col) {
        super(team, board, rank, col);
    }

    public String toString() {
        return "Q";
    }

    public void updateMoveList() {
        ArrayList<Integer[]> temp = new ArrayList<>();

        //down
        for(int r = rank + 1; r < 8; r++) {
            if(board.getPiece(r, col) == null) {
                Integer[] move = {r, col};
                temp.add(move);
            } else if(board.getPiece(r, col).team != team) {
                Integer[] move = {r, col};
                temp.add(move);
                break;
            } else {
                break;
            }
        }

        //left
        for(int c = col - 1; c > -1; c--) {
            if(board.getPiece(rank, c) == null) {
                Integer[] move = {rank, c};
                temp.add(move);
            } else if(board.getPiece(rank, c).team != team) {
                Integer[] move = {rank, c};
                temp.add(move);
                break;
            } else {
                break;
            }
        }

        //right
        for(int c = col + 1; c < 8; c++) {
            if(board.getPiece(rank, c) == null) {
                Integer[] move = {rank, c};
                temp.add(move);
            } else if(board.getPiece(rank, c).team != team) {
                Integer[] move = {rank, c};
                temp.add(move);
                break;
            } else {
                break;
            }
        }

        //up
        for(int r = rank - 1; r > -1; r--) {
            if(board.getPiece(r, col) == null) {
                Integer[] move = {r, col};
                temp.add(move);
            } else if(board.getPiece(r, col).team != team) {
                Integer[] move = {r, col};
                temp.add(move);
                break;
            } else {
                break;
            }
        }

        //upLeft
        int r = rank - 1;
        int c = col - 1;
        while(r > -1 && c > -1) {
            if(board.getPiece(r, c) == null) {
                Integer[] move = {r, c};
                temp.add(move);
            } else if(board.getPiece(r, c).team != team) {
                Integer[] move = {r, c};
                temp.add(move);
                break;
            } else {
                break;
            }
            r--;
            c--;
        }

        //upRight
        r = rank - 1;
        c = col + 1;
        while(r > -1 && c < 8) {
            if(board.getPiece(r, c) == null) {
                Integer[] move = {r, c};
                temp.add(move);
            } else if(board.getPiece(r, c).team != team) {
                Integer[] move = {r, c};
                temp.add(move);
                break;
            } else {
                break;
            }
            r--;
            c++;
        }

        //downLeft
        r = rank + 1;
        c = col - 1;
        while(r < 8 && c > -1) {
            if(board.getPiece(r, c) == null) {
                Integer[] move = {r, c};
                temp.add(move);
            } else if(board.getPiece(r, c).team != team) {
                Integer[] move = {r, c};
                temp.add(move);
                break;
            } else {
                break;
            }
            r++;
            c--;
        }

        //downRight
        r = rank + 1;
        c = col + 1;
        while(r < 8 && c < 8) {
            if(board.getPiece(r, c) == null) {
                Integer[] move = {r, c};
                temp.add(move);
            } else if(board.getPiece(r, c).team != team) {
                Integer[] move = {r, c};
                temp.add(move);
                break;
            } else {
                break;
            }
            r++;
            c++;
        }

        moves = temp;
    }

}

/*
 * -------------------------------------------------------------------
 */

class King extends Piece {

public King(boolean team, ChessBoard board, int rank, int col) {
    super(team, board, rank, col);
}

public boolean isCheck() {
    Integer[] currentPosition = {rank, col};
    if(team) {
        for(Integer[] m: board.blackMoves) {
            if(Arrays.equals(currentPosition, m)) {
                return true;
            }
        }
    } else {
        for(Integer[] m: board.whiteMoves) {
            if(Arrays.equals(currentPosition, m)) {
                return true;
            }
        }
    }
    return false;
}

public boolean isCheckMate() {
    if(moves.size() == 0 && isCheck()) {
        return true;
    }
    return false;
}

public boolean isStaleMate() {
    if(moves.size() == 0 && !isCheck()) {
        return true;
    }
    return false;
}

public String toString() {
    return "K";
}
}