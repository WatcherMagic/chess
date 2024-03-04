package chess;

import java.lang.reflect.Array;
import java.util.Collection;

import static chess.ChessGame.TeamColor.*;
import static chess.ChessPiece.PieceType.*;
import static chess.PieceMovement.Direction.*;

public class PawnMovement extends PieceMovement {


    Direction[] directionsBlack = {BACK, BACKLEFT, BACKRIGHT};
    Direction[] directionsWhite = {UP, UPLEFT, UPRIGHT};
    int maxDistance = 1;

    PawnMovement() {
        super();
    }

    Direction[] getDirectionsBlack() {
        return directionsBlack;
    }

    Direction[] getDirectionsWhite() {
        return directionsWhite;
    }

    private void promote(Collection<ChessMove> moves, ChessPosition startPos,
                              ChessPosition endPos) {
        moves.add(new ChessMove(startPos, new ChessPosition(endPos.getRow(), endPos.getColumn()), QUEEN));
        moves.add(new ChessMove(startPos, new ChessPosition(endPos.getRow(), endPos.getColumn()), BISHOP));
        moves.add(new ChessMove(startPos, new ChessPosition(endPos.getRow(), endPos.getColumn()), ROOK));
        moves.add(new ChessMove(startPos, new ChessPosition(endPos.getRow(), endPos.getColumn()), KNIGHT));
    }

    private boolean checkPromotion(Collection<ChessMove> moves, ChessPosition pos,
                                ChessPosition startPos, ChessGame.TeamColor color) {
        if (color == BLACK && pos.getRow() == 1) {
            promote(moves, startPos, pos);
            return true;
        }
        else if (color == WHITE && pos.getRow() == 8) {
            promote(moves, startPos, pos);
            return true;
        }
        return false;
    }

    private boolean checkOffBoard(ChessPosition pos) {
        if (pos.getRow() > 8 || pos.getColumn() > 8
                || pos.getRow() < 1 || pos.getColumn() < 1) {
            return true;
        }
        return false;
    }

    private int checkPosDiagonal(Collection<ChessMove> moves, ChessBoard board,
                                  ChessPosition startPos, int i) {

        if (checkOffBoard(curPos)) {
            i += 1;
            return i;
        }

        ChessGame.TeamColor color = board.getPiece(startPos).getTeamColor();

        if (board.getPiece(curPos) != null) {
            if (curPos.getRow() > startPos.getRow() && color == WHITE
                    && board.getPiece(curPos).getTeamColor() != WHITE) {
                checkForPromotionThenAdd(moves, startPos, color);
            }
            else if (curPos.getRow() < startPos.getRow() && color == BLACK
                    && board.getPiece(curPos).getTeamColor() != BLACK) {
                checkForPromotionThenAdd(moves, startPos, color);
            }
        }

        i += 1;
        return i;
    }

    private void checkForPromotionThenAdd(Collection<ChessMove> moves, ChessPosition startPos, ChessGame.TeamColor color) {
        if(!checkPromotion(moves, new ChessPosition(curPos.getRow(), curPos.getColumn()), startPos, color)) {
            moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
        }
    }

    private int checkPosStraight(Collection<ChessMove> moves, ChessBoard board,
                                 ChessPosition startPos, int i, int dist) {

        if (checkOffBoard(curPos)) {
            i += 1;
            return i;
        }

        if (board.getPiece(curPos) == null && dist > 0) { //clear space
            if (!checkPromotion(moves, new ChessPosition(curPos.getRow(), curPos.getColumn()),
                    startPos, board.getPiece(startPos).getTeamColor())) {
                moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
                if (dist == 1) {
                    i += 1;
                }
            }
            else {
                i += 1;
            }
        }
        else { //blocked
            i += 1;
        }

        return i;
    }

    @Override
    public Collection<ChessMove> iterateMoves(Collection<ChessMove> moves, ChessBoard board,
                                              ChessPosition startPos, Direction[] directions) {

        curPos = new ChessPosition(startPos.getRow(), startPos.getColumn());
        int distance = maxDistance;

        if (startPos.getRow() == 2 && board.getPiece(startPos).getTeamColor() == WHITE) {
            distance += 1;
        }
        else if (startPos.getRow() == 7 && board.getPiece(startPos).getTeamColor() == BLACK) {
            distance += 1;
        }

        for (int x = 0; x < directions.length; x = x) {

            curPos.setRow(startPos.getRow());
            curPos.setCol(startPos.getColumn());

            switch(Array.get(directions, x)) {
                case UP:
                    while (distance > 0) {
                        curPos.setRow(curPos.getRow()+1);
                        x = checkPosStraight(moves, board, startPos, x, distance);
                        if (distance > 1 && moves.size() == 0) { //double move blocked
                            distance -= 1;
                        }
                        distance -= 1;
                    }
                    break;
                case BACK:
                    while (distance > 0) {
                        curPos.setRow(curPos.getRow()-1);
                        x = checkPosStraight(moves, board, startPos, x, distance);
                        if (distance > 1 && moves.size() == 0) { //double move blocked
                            distance -= 1;
                        }
                        distance -= 1;
                    }
                    break;
                case UPLEFT:
                    curPos.setRow(curPos.getRow()+1);
                    curPos.setCol(curPos.getColumn()-1);
                    x = checkPosDiagonal(moves, board, startPos, x);
                    break;
                case UPRIGHT:
                    curPos.setRow(curPos.getRow()+1);
                    curPos.setCol(curPos.getColumn()+1);
                    x = checkPosDiagonal(moves, board, startPos, x);
                    break;
                case BACKLEFT:
                    curPos.setRow(curPos.getRow()-1);
                    curPos.setCol(curPos.getColumn()-1);
                    x = checkPosDiagonal(moves, board, startPos, x);
                    break;
                case BACKRIGHT:
                    curPos.setRow(curPos.getRow()-1);
                    curPos.setCol(curPos.getColumn()+1);
                    x = checkPosDiagonal(moves, board, startPos, x);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Array.get(directions, x));
            }
        }

        return moves;
    }

}
