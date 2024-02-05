package chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

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

        //here run the valid moves through for check

        return valid;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        try {
            Collection<ChessMove> valid = validMoves(move.getStartPosition());

            if (valid.contains(move)) {
                mainBoard.addPiece(move.getEndPosition(), mainBoard.getPiece(move.getStartPosition()));
                mainBoard.removePiece(move.getStartPosition());
            }
            else {
                throw(new InvalidMoveException());
            }

        } catch (InvalidMoveException ex) {
            throw ex;
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

        ChessPosition checkPos = new ChessPosition(kingPos.getRow(), kingPos.getColumn());

        //check upright
        while (checkPos.getRow() < 8 && checkPos.getColumn() < 8) {

            if (mainBoard.getPiece(checkPos) != null) {

                //if it's an ally, move on
                if (mainBoard.getPiece(checkPos).getTeamColor() == teamColor
                        && mainBoard.getPiece(checkPos).getPieceType() != ChessPiece.PieceType.KING) {
                    break;
                }

                if (checkPos.getRow() == checkPos.getRow() + 1) {
                    if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING) {
                        return true;
                    }
                    //check for pawns for white king
                    if (teamColor == TeamColor.WHITE && mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.PAWN) {
                        return true;
                    }
                }
                else if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.BISHOP
                        || mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.QUEEN) {
                    return true;
                }
            }
            checkPos.setRow(checkPos.getRow() + 1);
            checkPos.setCol(checkPos.getColumn() + 1);
        }
        checkPos.setRow(kingPos.getRow());
        checkPos.setCol(kingPos.getColumn());
        //check upleft
        while (checkPos.getRow() < 8 && checkPos.getColumn() > 0) {

            if (mainBoard.getPiece(checkPos) != null) {

                if (mainBoard.getPiece(checkPos).getTeamColor() == teamColor
                    && mainBoard.getPiece(checkPos).getPieceType() != ChessPiece.PieceType.KING) {
                    break;
                }
                if (checkPos.getRow() == checkPos.getRow() + 1) {
                    if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING) {
                        return true;
                    }
                    //check for pawns for white king
                    if (teamColor == TeamColor.WHITE && mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.PAWN) {
                        return true;
                    }
                }
                else if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.BISHOP
                        || mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.QUEEN) {
                    return true;
                }
            }
            checkPos.setRow(checkPos.getRow() + 1);
            checkPos.setCol(checkPos.getColumn() - 1);
        }
        checkPos.setRow(kingPos.getRow());
        checkPos.setCol(kingPos.getColumn());
        //check backleft
        while (checkPos.getRow() > 0 && checkPos.getColumn() > 0) {

            if (mainBoard.getPiece(checkPos) != null) {

                if (mainBoard.getPiece(checkPos).getTeamColor() == teamColor && mainBoard.getPiece(checkPos).getPieceType() != ChessPiece.PieceType.KING) {
                    break;
                }
                if (checkPos.getRow() == checkPos.getRow() - 1) {
                    if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING) {
                        return true;
                    }
                    //check for pawns for black king
                    if (teamColor == TeamColor.BLACK && mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.PAWN) {
                        return true;
                    }
                }
                else if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.BISHOP
                        || mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.QUEEN) {
                    return true;
                }
            }
            checkPos.setRow(checkPos.getRow() - 1);
            checkPos.setCol(checkPos.getColumn() - 1);
        }
        checkPos.setRow(kingPos.getRow());
        checkPos.setCol(kingPos.getColumn());
        //check backright
        while (checkPos.getRow() > 0 && checkPos.getColumn() < 8) {//

            if (mainBoard.getPiece(checkPos) != null) {

                if (mainBoard.getPiece(checkPos).getTeamColor() == teamColor && mainBoard.getPiece(checkPos).getPieceType() != ChessPiece.PieceType.KING) {
                    break;
                }

                if (checkPos.getRow() == checkPos.getRow() - 1) {
                    if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING) {
                        return true;
                    }
                    //check for pawns for black king
                    if (teamColor == TeamColor.BLACK && mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.PAWN) {
                        return true;
                    }
                }
                else if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.BISHOP
                        || mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.QUEEN) {
                    return true;
                }
            }
            checkPos.setRow(checkPos.getRow() - 1);
            checkPos.setCol(checkPos.getColumn() + 1);
        }
        checkPos.setRow(kingPos.getRow());
        checkPos.setCol(kingPos.getColumn());
        //check up
        while (checkPos.getRow() < 8) {


            if (mainBoard.getPiece(checkPos) != null) {

                if (mainBoard.getPiece(checkPos).getTeamColor() == teamColor && mainBoard.getPiece(checkPos).getPieceType() != ChessPiece.PieceType.KING) {
                    break;
                }

                if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING
                        && checkPos.getRow() == checkPos.getRow() + 1) {
                    return true;
                }
                else if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.ROOK
                        || mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.QUEEN) {
                    return true;
                }
            }
            checkPos.setRow(checkPos.getRow() + 1);
        }
        checkPos.setRow(kingPos.getRow());
        checkPos.setCol(kingPos.getColumn());
        //check back
        while (checkPos.getRow() > 0) {


            if (mainBoard.getPiece(checkPos) != null) {

                if (mainBoard.getPiece(checkPos).getTeamColor() == teamColor && mainBoard.getPiece(checkPos).getPieceType() != ChessPiece.PieceType.KING) {
                    break;
                }

                if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING
                        && checkPos.getRow() == checkPos.getRow() - 1) {
                    return true;
                }
                else if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.ROOK
                        || mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.QUEEN) {
                    return true;
                }
            }
            checkPos.setRow(checkPos.getRow() - 1);
        }
        checkPos.setRow(kingPos.getRow());
        checkPos.setCol(kingPos.getColumn());
        //check left
        while (checkPos.getColumn() > 0) {

            if (mainBoard.getPiece(checkPos) != null) {

                if (mainBoard.getPiece(checkPos).getTeamColor() == teamColor && mainBoard.getPiece(checkPos).getPieceType() != ChessPiece.PieceType.KING) {
                    break;
                }

                if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING
                        && checkPos.getRow() == checkPos.getColumn() - 1) {
                    return true;
                }
                else if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.ROOK
                        || mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.QUEEN) {
                    return true;
                }
            }
            checkPos.setCol(checkPos.getColumn() - 1);
        }
        checkPos.setRow(kingPos.getRow());
        checkPos.setCol(kingPos.getColumn());
        //check right
        while (checkPos.getColumn() < 8) {

            if (mainBoard.getPiece(checkPos) != null) {

                if (mainBoard.getPiece(checkPos).getTeamColor() == teamColor && mainBoard.getPiece(checkPos).getPieceType() != ChessPiece.PieceType.KING) {
                    break;
                }

                if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.KING
                        && checkPos.getRow() == checkPos.getColumn() + 1) {
                    return true;
                }
                else if (mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.ROOK
                        || mainBoard.getPiece(checkPos).getPieceType() == ChessPiece.PieceType.QUEEN) {
                    return true;
                }
            }
            checkPos.setCol(checkPos.getColumn() + 1);
        }
        //check knights
        ChessPosition[] checkKnight = {
            new ChessPosition(checkPos.getRow()+2, checkPos.getColumn()+1),
            new ChessPosition(checkPos.getRow()+2, checkPos.getColumn()-1),
            new ChessPosition(checkPos.getRow()-2, checkPos.getColumn()-1),
            new ChessPosition(checkPos.getRow()-2, checkPos.getColumn()+1),
            new ChessPosition(checkPos.getRow()+1, checkPos.getColumn()+2),
            new ChessPosition(checkPos.getRow()-1, checkPos.getColumn()+2),
            new ChessPosition(checkPos.getRow()-1, checkPos.getColumn()-2),
            new ChessPosition(checkPos.getRow()+1, checkPos.getColumn()-2)
        };
        for (int i = 0; i < checkKnight.length; i++) {
            if (checkKnight[i].getRow() < 8 && checkKnight[i].getRow() > 0
                    && checkKnight[i].getColumn() < 8 && checkKnight[i].getColumn() > 0) {
                if (mainBoard.getPiece(checkKnight[i]) != null) {
                    if (mainBoard.getPiece(checkKnight[i]).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    //make UNDO MOVE method!

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
        //get valid moves for each piece
        //if a piece has at least 1 valid move
        //return false
        //else return true
        return false;
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
