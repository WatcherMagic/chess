package chess;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;

import static chess.PieceMovement.Direction.*;

public class PieceMovement {

    private ChessBoard board;
    private ChessPosition pos;
    int distanceLeft;

    public PieceMovement(ChessBoard board, ChessPosition pos) {
        this.board = board;
        this.pos = pos;
    }

    private Collection<ChessMove> getMovementType(Collection<ChessMove> moves, ChessBoard board, ChessPosition position) {

        switch (this.board.getPiece(this.pos).getPieceType()) {
            case KING:
                KingMovement king = new KingMovement(board, position);
                moves = king.iterateMoves(moves, king.maxDistance, king.directions);
                break;
            case QUEEN:
                QueenMovement queen = new QueenMovement(board, position);
                moves = queen.iterateMoves(moves, queen.maxDistance, queen.directions);
                break;
            case BISHOP:
                BishopMovement bishop = new BishopMovement(board, position);
                moves = bishop.iterateMoves(moves, bishop.maxDistance, bishop.directions);
                break;
            case KNIGHT:
                //
                break;
            case ROOK:
                RookMovement rook = new RookMovement(board, position);
                moves = rook.iterateMoves(moves, rook.maxDistance, rook.directions);
                break;
            case PAWN:
                //
                break;
        }

        return moves;
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

    protected boolean colorAtPosIsSame(ChessBoard b, ChessPosition start, ChessPosition current) {
        if (b.getPiece(start).getTeamColor() != b.getPiece(current).getTeamColor()) {
            return false;
        }
        else {
            return true;
        }
    }

    protected int checkPosition(int maxDistance, int i,
                                ChessBoard board, Collection<ChessMove> moves,
                                ChessPosition startPos, ChessPosition curPos) {

        this.distanceLeft -= 1;

        //check if adding in pos direction is out of bounds for edge
        if ((curPos.getRow() > 8 || curPos.getRow() < 1)
                || (curPos.getColumn() > 8 || curPos.getColumn() < 1)) {

            i += 1;
            distanceLeft = maxDistance;
            curPos.setRow(startPos.getRow());
            curPos.setCol(startPos.getColumn());

        }
        else {

            if (this.distanceLeft > 0) {

                if (board.getPiece(curPos) != null) {

                    if (!colorAtPosIsSame(board, startPos, curPos)) {
                        //create chessmove and add to collection
                        moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
                        distanceLeft = 0;
                    } else {
                        moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
                    }
                }
                else {
                    moves.add(new ChessMove(startPos, new ChessPosition(curPos.getRow(), curPos.getColumn())));
                }

            }
            else {
                i += 1;
                distanceLeft = maxDistance;
            }

        }

        return i;
    }

    protected Collection<ChessMove> iterateMoves(Collection<ChessMove> moves, int maxDistance,
                                                 Direction[] directions) {
        //Directions[] and maxDistance provided by subclass calling this method

        int r = this.pos.getRow();
        int c = this.pos.getColumn();
        this.distanceLeft = maxDistance;

        ChessPosition curPos = new ChessPosition(r, c);

        for (int i = 0; i < directions.length; i = i) {

            switch(Array.get(directions, i)) {
                case UP:
                    curPos.setRow(curPos.getRow() + 1);
                    i = checkPosition(maxDistance, i, board, moves, this.pos, curPos);
                    break;
                case BACK:
                    curPos.setRow(curPos.getRow() - 1);
                    i = checkPosition(maxDistance, i, board, moves, this.pos, curPos);
                    break;
                case LEFT:
                    curPos.setCol(curPos.getColumn() - 1);
                    i = checkPosition(maxDistance, i, board, moves, this.pos, curPos);
                    break;
                case RIGHT:
                    distanceLeft -= 1;
                    curPos.setCol(curPos.getColumn() + 1);
                    i = checkPosition(maxDistance, i, board, moves, this.pos, curPos);
                    break;
                case UPLEFT:
                    curPos.setRow(curPos.getRow() + 1);
                    curPos.setCol(curPos.getColumn() - 1);
                    i = checkPosition(maxDistance, i, board, moves, this.pos, curPos);
                    break;
                case UPRIGHT:
                    curPos.setRow(curPos.getRow() + 1);
                    curPos.setCol(curPos.getColumn() + 1);
                    i = checkPosition(maxDistance, i, board, moves, this.pos, curPos);
                    break;
                case BACKLEFT:
                    curPos.setRow(curPos.getRow() - 1);
                    curPos.setCol(curPos.getColumn() - 1);
                    i = checkPosition(maxDistance, i, board, moves, this.pos, curPos);
                    break;
                case BACKRIGHT:
                    curPos.setRow(curPos.getRow() - 1);
                    curPos.setCol(curPos.getColumn() + 1);
                    i = checkPosition(maxDistance, i, board, moves, this.pos, curPos);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Array.get(directions, i));
            }

        }

        return moves;

    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPositon) {

        Collection<ChessMove> moves = new HashSet<>();

        moves = getMovementType(moves, board, myPositon);

        return moves;

    }

}
