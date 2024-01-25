package chess;

import java.lang.reflect.Array;
import java.util.Collection;

import static chess.ChessGame.TeamColor.*;
import static chess.PieceMovement.Direction.*;
import static chess.ChessPiece.PieceType.*;

public class KingMovement extends PieceMovement {

    Direction[] directions = {UP, BACK, LEFT, RIGHT, UPLEFT, UPRIGHT, BACKLEFT, BACKRIGHT};
    ChessPiece.PieceType[] tabooStraight = {QUEEN, ROOK};
    ChessPiece.PieceType[] tabooDiagonal = {QUEEN, BISHOP};
    ChessPiece.PieceType[] tabooImmediateDiagonal = {PAWN};
    ChessPiece.PieceType[] tabooImmediate = {KING};
    int maxDistance = 1;
    int distanceTraveled = 0;

    public KingMovement(ChessBoard board, ChessPosition pos) {
        super(board, pos);
    }

    private boolean coastClear(ChessPosition p, ChessPiece.PieceType[] types) {
        boolean clear = true;

        for (int i = 0; i < types.length; i++) {
            if (board.getPiece(p).getPieceType() == Array.get(types, i)) {
                clear = false;
                break;
            }
        }

//        if (direction == UP || direction == BACK || direction == LEFT || direction == RIGHT) {
//            if (distanceTraveled <= 1) {
//                //check immediate
//            }
//        }
//        else if (direction == UPLEFT || direction == UPRIGHT
//                || direction == BACKLEFT || direction == BACKRIGHT) {
//            if (distanceTraveled <= 1) {
//                if (direction == UPLEFT || direction == UPRIGHT
//                        && board.getPiece(p).getTeamColor() != BLACK) {
//                    //check for black pawns
//                }
//                else {
//                    //check for white
//                }
//            }
//        }

        return clear;
    }

    @Override
    protected Collection<ChessMove> iterateMoves(Collection<ChessMove> moves, int maxDistance,
                                                 Direction[] directions) {

        int r = this.pos.getRow();
        int c = this.pos.getColumn();
        this.distanceLeft = maxDistance;
        boolean clear;

        ChessPosition curPos = new ChessPosition(r, c);

        for (int i = 0; i < directions.length; i = i) {

            switch(Array.get(directions, i)) {
                case UP:
                    curPos.setRow(curPos.getRow() + 1);
                    clear = coastClear(curPos, UP);

                    break;
                case BACK:
                    clear = coastClear(curPos, BACK);

                    break;
                case LEFT:
                    clear = coastClear(curPos, LEFT);

                    break;
                case RIGHT:
                    clear = coastClear(curPos, RIGHT);

                    break;
                case UPLEFT:
                    clear = coastClear(curPos, UPLEFT);

                    break;
                case UPRIGHT:
                    clear = coastClear(curPos, UPRIGHT);

                    break;
                case BACKLEFT:
                    clear = coastClear(curPos, BACKLEFT);

                    break;
                case BACKRIGHT:
                    clear = coastClear(curPos, BACKRIGHT);

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Array.get(directions, i));
            }
        }

        return moves;

    }

}

