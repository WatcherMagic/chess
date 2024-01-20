package chess;

import java.util.Collection;
import java.util.HashSet;

import static chess.PieceMovement.Direction.*;

public class PawnMovement extends PieceMovement{

    Direction[] directions = {UP, UPLEFT, UPRIGHT};
    int maxDistance = 2;

    public PawnMovement(ChessBoard board, ChessPosition pos) {
        super(board, pos);
    }

    //@Override

}
