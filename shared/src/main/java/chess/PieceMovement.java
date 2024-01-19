package chess;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;

import static chess.PieceMovement.Direction.*;

public class PieceMovement {

    private ChessBoard board;
    private ChessPosition pos;

    public PieceMovement(ChessBoard board, ChessPosition pos) {
        this.board = board;
        this.pos = pos;
    }

    private void getMovementType() {
        switch (this.board.getPiece(this.pos).getPieceType()) {
            case KING:
                //
            case QUEEN:
                //
            case BISHOP:
                //
            case KNIGHT:
                //
            case ROOK:
                //
            case PAWN:
                //
        }
    }

    protected enum Direction {
        UP,
        BACK,
        LEFT,
        RIGHT,
        UPLEFT,
        UPRIGHT,
        BACKLEFT,
        BACKRIGHT
    }

    protected Collection<ChessMove> iterateMoves(ChessBoard board, ChessPosition startPos,
                                               int maxDistance, Direction[] directions) {
        //Directions[] and maxDistance provided by subclass calling this method

        Collection<ChessMove> moves = new HashSet<>();

        int r = startPos.getRow();
        int c = startPos.getColumn();
        int distanceLeft = maxDistance;

        ChessPosition curPos = new ChessPosition(r, c);

        for (int i = 0; i < directions.length; i = i) {

            switch(Array.get(directions, i)) {
                case UP:
                    curPos.setRow(curPos.getRow() + 1);
                    break;
                case BACK:
                    curPos.setRow(curPos.getRow() - 1);
                    break;
                case LEFT:
                    curPos.setCol(curPos.getColumn() - 1);
                    break;
                case RIGHT:
                    curPos.setCol(curPos.getColumn() + 1);
                    break;
                case UPLEFT:
                    curPos.setRow(curPos.getRow() + 1);
                    curPos.setCol(curPos.getColumn() - 1);
                    break;
                case UPRIGHT:
                    curPos.setRow(curPos.getRow() + 1);
                    curPos.setCol(curPos.getColumn() + 1);
                    break;
                case BACKLEFT:
                    curPos.setRow(curPos.getRow() - 1);
                    curPos.setCol(curPos.getColumn() - 1);
                    break;
                case BACKRIGHT:
                    curPos.setRow(curPos.getRow() - 1);
                    curPos.setCol(curPos.getColumn() + 1);
                    break;
            }

            distanceLeft -= 1;

            if (distanceLeft > 0 && board.getPiece(curPos).getPieceType()
                    != board.getPiece(startPos).getPieceType()) {

                //create chessmove and add to collection
                moves.add(new ChessMove(startPos, curPos));

            }
            else {
                i = i + 1;
                distanceLeft = maxDistance;
            }

        }

        return moves;

    }

}
