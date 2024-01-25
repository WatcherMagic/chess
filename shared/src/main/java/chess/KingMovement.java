package chess;

import java.lang.reflect.Array;
import java.util.Collection;

import static chess.PieceMovement.Direction.*;

public class KingMovement extends PieceMovement {

    Direction[] directions = {UP, BACK, LEFT, RIGHT, UPLEFT, UPRIGHT, BACKLEFT, BACKRIGHT};
    int maxDistance = 1;

    public KingMovement(ChessBoard board, ChessPosition pos) {
        super(board, pos);
    }

    @Override
    protected int checkPosition(int maxDistance, int i,
                                ChessBoard board, Collection<ChessMove> moves,
                                ChessPosition startPos, ChessPosition curPos) {


        return i;
    }

    @Override
    protected Collection<ChessMove> iterateMoves(Collection<ChessMove> moves, int maxDistance,
                                                 Direction[] directions) {
        //Directions[] and maxDistance provided by subclass calling this method

        return moves;

    }

}

