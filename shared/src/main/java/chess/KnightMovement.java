package chess;

import java.util.Collection;
import java.util.HashSet;

import static chess.PieceMovement.Direction.*;

public class KnightMovement extends PieceMovement{

    Direction[] directions = {UP, /*more context later*/};
    int maxDistance = 2;

    public KnightMovement(ChessBoard board, ChessPosition pos) {
        super(board, pos);
    }

    @Override
    protected Collection<ChessMove> iterateMoves(ChessBoard board, ChessPosition startPos,
                                               int maxDistance, Direction[] directions) {
        Collection<ChessMove> moves = new HashSet<>();

        return moves;
    }

}
