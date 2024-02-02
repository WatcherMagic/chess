package chess;

import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard mainBoard;
    private TeamColor teamTurn;

    public ChessGame() {}

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        Collection<ChessMove> valid;
        ChessPiece piece = mainBoard.getPiece(startPosition);

        valid = piece.pieceMoves(mainBoard, startPosition);

        //here run the valid moves through to check for check/checkmate

        return valid;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        mainBoard.addPiece(move.getEndPosition(), mainBoard.getPiece(move.getStartPosition()));
        mainBoard.removePiece(move.getStartPosition());

//        if (mainBoard.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING) {
//            if (mainBoard.getPiece(move.getEndPosition()).getTeamColor() == TeamColor.BLACK) {
//                blackKingPosition.setRow(move.getEndPosition().getRow());
//                blackKingPosition.setCol(move.getEndPosition().getColumn());
//            }
//            else {
//                whiteKingPosition.setRow(move.getEndPosition().getRow());
//                whiteKingPosition.setCol(move.getEndPosition().getColumn());
//            }
//        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //get pieces of opposite color
        //for each piece, get valid moves
        //for each valid move, check if end position == king position
        //if yes, break, return true
        //else, return false
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //if isInCheck returns true
        //get king valid moves
        //if king valid moves == 0
        //true
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //for pieces of teamColor
        //get valid moves for each piece
        //if a piece has at least 1 valid move
        //return false
        //else return true
    }

//    private void updateKingPositions() {
//        for (int x = 0; x < mainBoard.getBoardLength(); x++) {
//            for (int y = 0; y < mainBoard.getBoardLength(); y++) {
//                if (mainBoard.getPiece(new ChessPosition(x, y)).getPieceType() == ChessPiece.PieceType.KING) {
//                    if (mainBoard.getPiece(new ChessPosition(x, y)).getTeamColor() == TeamColor.BLACK) {
//                        this.blackKingPosition = new ChessPosition(x, y);
//                    }
//                    else {
//                        this.whiteKingPosition = new ChessPosition(x, y);
//                    }
//                }
//            }
//        }
//    }

//    private void updateKingPositions(TeamColor color, ChessPosition position) {
//        if (color == TeamColor.BLACK) {
//            blackKingPosition.setRow(position.getRow());
//            blackKingPosition.setCol(position.getColumn());
//        }
//        else {
//            whiteKingPosition.setRow(position.getRow());
//            whiteKingPosition.setCol(position.getColumn());
//        }
//    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        mainBoard = board;

//        //iterate through board
//        for (int x = 0; x < mainBoard.getBoardLength(); x++) {
//            for (int y = 0; y < mainBoard.getBoardLength(); y++) {
//                if (mainBoard.getPiece(new ChessPosition(x, y)) != null) {
//
//                }
//            }
//        }
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return mainBoard;
    }
}
