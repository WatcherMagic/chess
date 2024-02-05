package chess;

import java.lang.reflect.Array;
import java.util.Collection;

import static chess.PieceMovement.Direction.*;

public class KnightMovement extends PieceMovement {


    KnightMovement() {
        super();
    }

    public Collection<ChessMove> iterateMoves(Collection<ChessMove> moves, ChessBoard board,
                                              ChessPosition startPos) {

        curPos = new ChessPosition(startPos.getRow(), startPos.getColumn());

        ChessPosition[] positions = {
            new ChessPosition(curPos.getRow()+2, curPos.getColumn()+1),
            new ChessPosition(curPos.getRow()+1, curPos.getColumn()+2),
            new ChessPosition(curPos.getRow()-1, curPos.getColumn()+2),
            new ChessPosition(curPos.getRow()-2, curPos.getColumn()+1),
            new ChessPosition(curPos.getRow()-2, curPos.getColumn()-1),
            new ChessPosition(curPos.getRow()-1, curPos.getColumn()-2),
            new ChessPosition(curPos.getRow()+1, curPos.getColumn()-2),
            new ChessPosition(curPos.getRow()+2, curPos.getColumn()-1)
        };

        for (int i = 0; i < positions.length; i++) {
            curPos = positions[i];
            checkPosition(moves, board, startPos, i);
        }

        return moves;
    }

}
