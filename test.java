
import java.nio.charset.StandardCharsets;


public class test {
    public static void main(String[] args) {
        ChessBoard b = new ChessBoard();

        b.setPiece(new Pawn(false, b, 2, 0), 2, 0);

        b.setPiece(new King(true, b, 2, 2), 2, 2);
        b.setKing((King) b.getPiece(2, 2));

        System.out.println(b);

        b.updateMoveList();

        b.printMoveList(false);
        b.printMoveList(true);

        byte[] c = new byte[] { (byte) 0xd8, (byte) 0xA1};
        System.out.print(new String(c, StandardCharsets.UTF_8));
    }


}