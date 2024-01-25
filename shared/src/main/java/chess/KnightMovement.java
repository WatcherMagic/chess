package chess;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;

public class KnightMovement extends PieceMovement{

    Direction[] directions;

    public KnightMovement(ChessBoard board, ChessPosition pos) {
        super(board, pos);
    }

    @Override
    protected Collection<ChessMove> iterateMoves(Collection<ChessMove> moves, int maxDistance,
                                                 Direction[] directions) {
        //Directions[] and maxDistance provided by subclass calling this method

        int r = this.pos.getRow();
        int c = this.pos.getColumn();

        ChessPosition startPos = new ChessPosition(r, c);

        ChessPosition[] possibleMoves = {
                new ChessPosition(r+2, c+1),
                new ChessPosition(r+2, c-1),
                new ChessPosition(r-2, c-1),
                new ChessPosition(r-2, c+1),
                new ChessPosition(r+1, c+2),
                new ChessPosition(r-1, c+2),
                new ChessPosition(r-1, c-2),
                new ChessPosition(r+1, c-2)};

        for (int i = 0; i < possibleMoves.length; i++) {

            ChessPosition curPos = (ChessPosition) Array.get(possibleMoves, i);

            if ((curPos.getRow() > 8 || curPos.getRow() < 1)
                    || (curPos.getColumn() > 8 || curPos.getColumn() < 1)) {
                continue;
            }

            if (board.getPiece(curPos) != null) {
                if (board.getPiece(curPos).getTeamColor() !=
                        board.getPiece(startPos).getTeamColor()) {

                    moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
                }
            }
            else {
                moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
            }
        }

        return moves;
    }

}
