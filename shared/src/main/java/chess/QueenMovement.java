package chess;

import static chess.PieceMovement.Direction.*;

public class QueenMovement extends PieceMovement {

    Direction[] directions = {UP, BACK, LEFT, RIGHT, UPLEFT, UPRIGHT, BACKLEFT, BACKRIGHT};
    int maxDistance = 8;

    public QueenMovement(ChessBoard board, ChessPosition pos) {
        super(board, pos);
    }
}
