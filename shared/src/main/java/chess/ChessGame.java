package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard mainBoard;
    private TeamColor teamTurn = TeamColor.WHITE;

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

    public void undoMove(ChessMove move, ChessPiece capturedPiece) {
        mainBoard.addPiece(move.getStartPosition(), mainBoard.getPiece(move.getEndPosition()));
        mainBoard.removePiece(move.getEndPosition());

        if (capturedPiece != null) {
            mainBoard.addPiece(move.getEndPosition(), capturedPiece);
        }
    }

    public ChessPiece doMove(ChessMove move) {

        ChessPiece capturedPiece = null;

        if (mainBoard.getPiece(move.getEndPosition()) != null) {
            capturedPiece = mainBoard.getPiece(move.getEndPosition());
        }

        if (move.getPromotionPiece() != null) {
            mainBoard.addPiece(move.getEndPosition(),
                    new ChessPiece(mainBoard.getPiece(move.getStartPosition()).getTeamColor(), move.getPromotionPiece()));
            mainBoard.removePiece(move.getStartPosition());
        }
        else {
            mainBoard.addPiece(move.getEndPosition(), mainBoard.getPiece(move.getStartPosition()));
            mainBoard.removePiece(move.getStartPosition());
        }

        return capturedPiece;

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

        Collection<ChessMove> finalValid = new HashSet<>();
        TeamColor color = mainBoard.getPiece(startPosition).getTeamColor();
        ChessPiece capturedPiece = null;

        //here run the valid moves through for check
        for (ChessMove move : valid) {
            capturedPiece = doMove(move);
            if (!isInCheck(color)) {
                finalValid.add(move);
            }
            undoMove(move, capturedPiece);
        }

        return finalValid;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        if (mainBoard.getPiece(move.getStartPosition()).getTeamColor() == getTeamTurn()) {
            Collection<ChessMove> valid = validMoves(move.getStartPosition());

            if (valid.contains(move)) {
                doMove(move);
            }
            else {
                throw new InvalidMoveException();
            }
        }
        else {
            throw new InvalidMoveException();
        }

        if (getTeamTurn() == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        }
        else {
            setTeamTurn(TeamColor.WHITE);
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        ChessPosition kingPos = new ChessPosition(0, 0);

        for (int x = 0; x < mainBoard.getBoardLength(); x++) {
            for (int y = 0; y < mainBoard.getBoardLength(); y++) {
                if (mainBoard.getPiece(new ChessPosition(x+1, y+1)) != null) {
                    if (mainBoard.getPiece(new ChessPosition(x+1, y+1)).getPieceType() == ChessPiece.PieceType.KING
                            && mainBoard.getPiece(new ChessPosition(x+1, y+1)).getTeamColor() == teamColor) {
                        kingPos = new ChessPosition(x+1, y+1);
                        break;
                    }
                }
            }
        }

        //DO STUFF
        //go through board
        //check all piece moves of opposite team using pieceMoves
        //if they can reach king, check == true

        ChessPosition pos = new ChessPosition(0, 0);

        for (int x = 0; x < mainBoard.getBoardLength(); x++) {
            for (int y = 0; y < mainBoard.getBoardLength(); y++) {
                pos.setRow(x+1);
                pos.setCol(y+1);

                if (mainBoard.getPiece(pos) != null && mainBoard.getPiece(pos).getTeamColor() != teamColor) {
                    Collection<ChessMove> moves = mainBoard.getPiece(pos).pieceMoves(mainBoard, pos);

                    for (ChessMove move : moves) {
                        if (move.getEndPosition().equals(kingPos)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        if (isInCheck(teamColor)) {
            //find king
            ChessPosition kingPos = new ChessPosition(0,0);

            for (int x = 0; x < mainBoard.getBoardLength(); x++) {
                for (int y = 0; y < mainBoard.getBoardLength(); y++) {
                    if (mainBoard.getPiece(new ChessPosition(x+1, y+1)) != null) {
                        if (mainBoard.getPiece(new ChessPosition(x+1, y+1)).getPieceType() == ChessPiece.PieceType.KING
                                && mainBoard.getPiece(new ChessPosition(x+1, y+1)).getTeamColor() == teamColor) {
                            kingPos.setRow(x+1);
                            kingPos.setCol(y+1);
                            break;
                        }
                    }
                }
            }

            //get king valid moves
            Collection<ChessMove> kingValid = validMoves(kingPos);
            if (kingValid.size() == 0) {
                return true;
            }
        }
        return false;
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
        if (teamColor == getTeamTurn()) {
            for (int x = 0; x < mainBoard.getBoardLength(); x++) {
                for (int y = 0; y < mainBoard.getBoardLength(); y++) {
                    //get valid moves for each piece
                    if (mainBoard.getPiece(new ChessPosition(x+1,y+1)) != null
                            && mainBoard.getPiece(new ChessPosition(x+1, y+1)).getTeamColor() == teamColor) {
                        Collection<ChessMove> valid = validMoves(new ChessPosition(x+1, y+1));

                        //if a piece has at least 1 valid move
                        if (valid.size() > 0) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        mainBoard = board;
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
