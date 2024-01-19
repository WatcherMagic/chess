package chess;

import static chess.PieceMovement.Direction.*;

public class BishopMovement extends PieceMovement{

    Direction[] directions = {UPLEFT, UPRIGHT, BACKLEFT, BACKRIGHT};
    int maxDistance = 8;

    public BishopMovement(ChessBoard board, ChessPosition pos) {
        super(board, pos);
    }
}
