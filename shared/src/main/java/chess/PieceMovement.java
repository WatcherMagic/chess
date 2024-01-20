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

    private Collection<ChessMove> getMovementType(Collection<ChessMove> moves, ChessBoard board, ChessPosition position) {

        switch (this.board.getPiece(this.pos).getPieceType()) {
            case KING:
                KingMovement king = new KingMovement(board, position);
                moves = king.iterateMoves(moves, king.maxDistance, king.directions);
            case QUEEN:
                QueenMovement queen = new QueenMovement(board, position);
                moves = queen.iterateMoves(moves, queen.maxDistance, queen.directions);
            case BISHOP:
                BishopMovement bishop = new BishopMovement(board, position);
                moves = bishop.iterateMoves(moves, bishop.maxDistance, bishop.directions);
            case KNIGHT:
                //
            case ROOK:
                RookMovement rook = new RookMovement(board, position);
                moves = rook.iterateMoves(moves, rook.maxDistance, rook.directions);
            case PAWN:
                //
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

    protected boolean colorAtPosIsSame(ChessBoard b, ChessPosition p1, ChessPosition p2) {
        if (b.getPiece(p1).getTeamColor() != b.getPiece(p2).getTeamColor()) {
            return false;
        }
        else {
            return true;
        }
    }

    protected int checkPosition(int maxDistance, int distanceLeft, int i,
                                ChessBoard board, Collection<ChessMove> moves,
                                ChessPosition startPos, ChessPosition curPos) {
        distanceLeft -= 1;

        //check if adding in pos direction is out of bounds for edge
        if ((curPos.getRow() > 8 || curPos.getRow() < 1)
                || (curPos.getColumn() > 8 || curPos.getColumn() < 1)) {

            i += 1;
            distanceLeft = maxDistance;

        }
        else {

            if (distanceLeft > 0 && !colorAtPosIsSame(board, startPos, curPos)) {

                //create chessmove and add to collection
                moves.add(new ChessMove(startPos, curPos));

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
        int distanceLeft = maxDistance;

        ChessPosition curPos = new ChessPosition(r, c);

        for (int i = 0; i < directions.length; i = i) {

            switch(Array.get(directions, i)) {
                case UP:
                    curPos.setRow(curPos.getRow() + 1);
                    i = checkPosition(maxDistance, distanceLeft, i, board, moves, this.pos, curPos);
                case BACK:
                    curPos.setRow(curPos.getRow() - 1);
                    i = checkPosition(maxDistance, distanceLeft, i, board, moves, this.pos, curPos);
                case LEFT:
                    curPos.setCol(curPos.getColumn() - 1);
                    i = checkPosition(maxDistance, distanceLeft, i, board, moves, this.pos, curPos);
                case RIGHT:
                    curPos.setCol(curPos.getColumn() + 1);
                    i = checkPosition(maxDistance, distanceLeft, i, board, moves, this.pos, curPos);
                case UPLEFT:
                    curPos.setRow(curPos.getRow() + 1);
                    curPos.setCol(curPos.getColumn() - 1);
                    i = checkPosition(maxDistance, distanceLeft, i, board, moves, this.pos, curPos);
                case UPRIGHT:
                    curPos.setRow(curPos.getRow() + 1);
                    curPos.setCol(curPos.getColumn() + 1);
                    i = checkPosition(maxDistance, distanceLeft, i, board, moves, this.pos, curPos);
                case BACKLEFT:
                    curPos.setRow(curPos.getRow() - 1);
                    curPos.setCol(curPos.getColumn() - 1);
                    i = checkPosition(maxDistance, distanceLeft, i, board, moves, this.pos, curPos);
                case BACKRIGHT:
                    curPos.setRow(curPos.getRow() - 1);
                    curPos.setCol(curPos.getColumn() + 1);
                    i = checkPosition(maxDistance, distanceLeft, i, board, moves, this.pos, curPos);
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
