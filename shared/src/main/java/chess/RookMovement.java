package chess;

import static chess.PieceMovement.Direction.*;

public class RookMovement extends PieceMovement{

    Direction[] directions = {UP, BACK, LEFT, RIGHT};
    int maxDistance = 8;

    public RookMovement(ChessBoard board, ChessPosition pos) {
        super(board, pos);
    }
}
