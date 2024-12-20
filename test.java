import java.util.ArrayList;
import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        ChessBoard b = new ChessBoard();

        b.setPiece(new Bishop(false, b, 4, 4), 4, 4);
        b.setPiece(new Pawn(false, b, 1, 0), 1, 0);
        b.setPiece(new Pawn(true, b, 0, 1), 0, 1); 

        System.out.println(b);

        b.getPiece(4,4).updateMoveList();
        ArrayList<Integer[]> KnightMove = b.getPiece(4,4).getMoves();

        for(Integer[] move: KnightMove) {
            System.out.print(Arrays.toString(move) + " ");
        }
        System.out.println();

        Integer[] move = {4,6};


        System.out.println(b.makeMove(0,0, move));
        System.out.println(b);




    }


}