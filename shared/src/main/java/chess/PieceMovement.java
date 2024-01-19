package chess;

import java.util.Collection;

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
                //,
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

        //initialize collection

        int r = startPos.getRow();
        int c = startPos.getColumn();
        int distanceLeft = maxDistance;

        ChessPosition newPos = new ChessPosition(r, c);

        for (Direction direction : directions) {

            switch(direction) {
                case UP:
                    newPos.setRow(newPos.getRow() + 1);
                case BACK:
                    newPos.setRow(newPos.getRow() - 1);
                case LEFT:
                    newPos.setCol(newPos.getColumn() - 1);
                case RIGHT:
                    newPos.setCol(newPos.getColumn() + 1);
                case UPLEFT:
                    newPos.setRow(newPos.getRow() + 1);
                    newPos.setCol(newPos.getColumn() - 1);
                case UPRIGHT:
                    newPos.setRow(newPos.getRow() + 1);
                    newPos.setCol(newPos.getColumn() + 1);
                case BACKLEFT:
                    newPos.setRow(newPos.getRow() - 1);
                    newPos.setCol(newPos.getColumn() - 1);
                case BACKRIGHT:
                    newPos.setRow(newPos.getRow() - 1);
                    newPos.setCol(newPos.getColumn() + 1);
            }

            distanceLeft = distanceLeft - 1;

            if (distanceLeft > 0 && board.getPiece(newPos).getPieceType()
                    != board.getPiece(startPos).getPieceType()) {

                //create chessmove and add to collection

            }
            else {
                //
            }

        }

        //return collection

    }

}
