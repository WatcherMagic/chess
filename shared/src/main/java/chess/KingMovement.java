package chess;

import java.lang.reflect.Array;
import java.util.Collection;

import static chess.PieceMovement.Direction.*;

public class KingMovement extends PieceMovement {

    private Direction[] directions = {UP, BACK, LEFT, RIGHT, UPLEFT, UPRIGHT, BACKLEFT, BACKRIGHT};

    KingMovement() {
        super();
    }

    Direction[] getDirections() {
        return directions;
    }


    public Collection<ChessMove> iterateMoves(Collection<ChessMove> moves, ChessBoard board,
                                              ChessPosition startPos, Direction[] directions) {

        curPos = new ChessPosition(startPos.getRow(), startPos.getColumn());

        for (int x = 0; x < directions.length; x++) {

            curPos.setRow(startPos.getRow());
            curPos.setCol(startPos.getColumn());

            switch(Array.get(directions, x)) {
                case UP:
                    curPos.setRow(curPos.getRow()+1);
                    checkPosition(moves, board, startPos, x);
                    break;
                case BACK:
                    curPos.setRow(curPos.getRow()-1);
                    checkPosition(moves, board, startPos, x);
                    break;
                case LEFT:
                    curPos.setCol(curPos.getColumn()-1);
                    checkPosition(moves, board, startPos, x);
                    break;
                case RIGHT:
                    curPos.setCol(curPos.getColumn()+1);
                    checkPosition(moves, board, startPos, x);
                    break;
                case UPLEFT:
                    curPos.setRow(curPos.getRow()+1);
                    curPos.setCol(curPos.getColumn()-1);
                    checkPosition(moves, board, startPos, x);
                    break;
                case UPRIGHT:
                    curPos.setRow(curPos.getRow()+1);
                    curPos.setCol(curPos.getColumn()+1);
                    checkPosition(moves, board, startPos, x);
                    break;
                case BACKLEFT:
                    curPos.setRow(curPos.getRow()-1);
                    curPos.setCol(curPos.getColumn()-1);
                    checkPosition(moves, board, startPos, x);
                    break;
                case BACKRIGHT:
                    curPos.setRow(curPos.getRow()-1);
                    curPos.setCol(curPos.getColumn()+1);
                    checkPosition(moves, board, startPos, x);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Array.get(directions, x));
            }
        }

        return moves;
    }

}
