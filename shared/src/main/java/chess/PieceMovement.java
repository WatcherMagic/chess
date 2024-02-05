package chess;

import java.lang.reflect.Array;
import java.util.Collection;

import static chess.PieceMovement.Direction.*;

public class PieceMovement {

    protected ChessPosition curPos;

    PieceMovement() {}

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

    protected int resetIteration(ChessPosition pos, ChessPosition resetTo, int i) {
        pos.setRow(resetTo.getRow());
        pos.setCol(resetTo.getColumn());
        i += 1;

        return i;
    }

    protected int checkPosition(Collection<ChessMove> moves, ChessBoard board,
                                ChessPosition startPos, int i) {

        if (curPos.getRow() > 8 || curPos.getRow() < 1
                || curPos.getColumn() > 8 || curPos.getColumn() < 1) {
            i = resetIteration(curPos, startPos, i);
        }
        else if (board.getPiece(curPos) != null) {
            //blocked
            if (board.getPiece(curPos).getTeamColor() == board.getPiece(startPos).getTeamColor()) {
                i = resetIteration(curPos, startPos, i);
            }
            //capture piece
            else {
                moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
                i = resetIteration(curPos, startPos, i);
            }
        }
        //empty space
        else {
            moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
        }

        return i;
    }

    public Collection<ChessMove> iterateMoves(Collection<ChessMove> moves, ChessBoard board,
                                              ChessPosition startPos, Direction[] directions) {

        curPos = new ChessPosition(startPos.getRow(), startPos.getColumn());

        for (int x = 0; x < directions.length; x = x) {
            switch(Array.get(directions, x)) {
                case UP:
                    curPos.setRow(curPos.getRow()+1);
                    x = checkPosition(moves, board, startPos, x);
                    break;
                case BACK:
                    curPos.setRow(curPos.getRow()-1);
                    x = checkPosition(moves, board, startPos, x);
                    break;
                case LEFT:
                    curPos.setCol(curPos.getColumn()-1);
                    x = checkPosition(moves, board, startPos, x);
                    break;
                case RIGHT:
                    curPos.setCol(curPos.getColumn()+1);
                    x = checkPosition(moves, board, startPos, x);
                    break;
                case UPLEFT:
                    curPos.setRow(curPos.getRow()+1);
                    curPos.setCol(curPos.getColumn()-1);
                    x = checkPosition(moves, board, startPos, x);
                    break;
                case UPRIGHT:
                    curPos.setRow(curPos.getRow()+1);
                    curPos.setCol(curPos.getColumn()+1);
                    x = checkPosition(moves, board, startPos, x);
                    break;
                case BACKLEFT:
                    curPos.setRow(curPos.getRow()-1);
                    curPos.setCol(curPos.getColumn()-1);
                    x = checkPosition(moves, board, startPos, x);
                    break;
                case BACKRIGHT:
                    curPos.setRow(curPos.getRow()-1);
                    curPos.setCol(curPos.getColumn()+1);
                    x = checkPosition(moves, board, startPos, x);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Array.get(directions, x));
            }
        }

        return moves;
    }
}
