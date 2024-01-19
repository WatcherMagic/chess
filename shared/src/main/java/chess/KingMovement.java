package chess;

import static chess.PieceMovement.Direction.*;

public class KingMovement extends PieceMovement{

    Direction[] directions = {UP, BACK, LEFT, RIGHT, UPLEFT, UPRIGHT, BACKLEFT, BACKRIGHT};
    int maxDistance = 1;

    public KingMovement(ChessBoard board, ChessPosition pos) {
        super(board, pos);
    }
}
