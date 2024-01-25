package chess;

import java.lang.reflect.Array;
import java.util.Collection;

import static chess.ChessGame.TeamColor.*;
import static chess.PieceMovement.Direction.*;
import static chess.ChessPiece.PieceType.*;

public class PawnMovement extends PieceMovement {

    Direction[] directionsWhite = {UP, UPLEFT, UPRIGHT};
    Direction[] directionsBlack = {BACK, BACKLEFT, BACKRIGHT};
    Direction[] directions;
    int maxDistance = 1;

    public PawnMovement(ChessBoard board, ChessPosition pos) {
        super(board, pos);
    }

    private void isPromotion(Collection<ChessMove> moves, ChessPosition endPos,
                                  ChessPosition startPos) {
        moves.add(new ChessMove(startPos, new ChessPosition(endPos.getRow(), endPos.getColumn()), ROOK));
        moves.add(new ChessMove(startPos, new ChessPosition(endPos.getRow(), endPos.getColumn()), QUEEN));
        moves.add(new ChessMove(startPos, new ChessPosition(endPos.getRow(), endPos.getColumn()), BISHOP));
        moves.add(new ChessMove(startPos, new ChessPosition(endPos.getRow(), endPos.getColumn()), KNIGHT));
    }

    private int pawnDiagonalMove(int i, ChessBoard board, Collection<ChessMove> moves,
                                 ChessPosition startPos, ChessPosition curPos) {

        if (board.getPiece(curPos) != null) {

            if (!colorAtPosIsSame(board, startPos, curPos)) { //capture enemy
                if (curPos.getRow() == 1 || curPos.getRow() == 8) {
                    isPromotion(moves, curPos, startPos);
                } else {
                    moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
                }
            }
        }

        resetPos(curPos, startPos);

        i += 1;
        return i;
    }

    private void resetPos(ChessPosition curPos, ChessPosition startPos) {
        curPos.setRow(startPos.getRow());
        curPos.setCol(startPos.getColumn());
    }

    private int pawnVerticalMove(int distance, int i, ChessBoard board, Collection<ChessMove> moves,
                                 ChessPosition startPos, ChessPosition curPos) {

        if (board.getPiece(curPos) == null) {
            if (curPos.getRow() == 1 || curPos.getRow() == 8) {
                isPromotion(moves, curPos, startPos);
            } else {
                moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
            }
        }

        if ((startPos.getRow() == 2 && board.getPiece(this.pos).getTeamColor() == WHITE) ||
                (startPos.getRow() == 7 && board.getPiece(this.pos).getTeamColor() == BLACK)) {

            if (moves.size() > 0 && distance > 0) {
                if (board.getPiece(this.pos).getTeamColor() == WHITE) {
                    curPos.setRow(curPos.getRow() + 1);
                }
                else {
                    curPos.setRow(curPos.getRow() - 1);
                }
                pawnVerticalMove(distance - 1, i, board, moves, startPos, curPos);
            }
        }

        resetPos(curPos, startPos);

        i +=1;
        return i;

    }

    @Override
    protected Collection<ChessMove> iterateMoves(Collection<ChessMove> moves, int maxDistance,
                                                 Direction[] directions) {

        int r = this.pos.getRow();
        int c = this.pos.getColumn();
        int distance = maxDistance;

        ChessPosition curPos = new ChessPosition(r, c);

        if (board.getPiece(curPos).getTeamColor() == WHITE) {
            directions = this.directionsWhite;
        }
        else {
            directions = this.directionsBlack;
        }

        for (int i = 0; i < directions.length; i = i) {

            switch(Array.get(directions, i)) {
                case UP:
                    curPos.setRow(curPos.getRow() + 1);
                    i = pawnVerticalMove(distance, i, board, moves, this.pos, curPos);
                    break;
                case BACK:
                    curPos.setRow(curPos.getRow() - 1);
                    i = pawnVerticalMove(distance, i, board, moves, this.pos, curPos);
                    break;
                case UPLEFT:
                    curPos.setRow(curPos.getRow() + 1);
                    curPos.setCol(curPos.getColumn() - 1);
                    i = pawnDiagonalMove(i, board, moves, this.pos, curPos);
                    break;
                case UPRIGHT:
                    curPos.setRow(curPos.getRow() + 1);
                    curPos.setCol(curPos.getColumn() + 1);
                    i = pawnDiagonalMove(i, board, moves, this.pos, curPos);
                    break;
                case BACKLEFT:
                    curPos.setRow(curPos.getRow() - 1);
                    curPos.setCol(curPos.getColumn() - 1);
                    i = pawnDiagonalMove(i, board, moves, this.pos, curPos);
                    break;
                case BACKRIGHT:
                    curPos.setRow(curPos.getRow() - 1);
                    curPos.setCol(curPos.getColumn() + 1);
                    i = pawnDiagonalMove(i, board, moves, this.pos, curPos);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Array.get(directions, i));
            }

        }

        return moves;

    }

}
