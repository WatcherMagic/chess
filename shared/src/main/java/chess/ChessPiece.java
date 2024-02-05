package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import static chess.ChessGame.TeamColor.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor color;
    PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        color = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "color=" + color +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return color == that.color && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    private Collection<ChessMove> getMovementType(Collection<ChessMove> moves,
                                                  ChessBoard board, ChessPosition pos) {
        switch (type) {
            case KING:
                KingMovement king = new KingMovement();
                moves = king.iterateMoves(moves, board, pos, king.getDirections());
                break;
            case QUEEN:
                QueenMovement queen = new QueenMovement();
                moves = queen.iterateMoves(moves, board, pos, queen.getDirections());
                break;
            case BISHOP:
                BishopMovement bishop = new BishopMovement();
                moves = bishop.iterateMoves(moves, board, pos, bishop.getDirections());
                break;
            case KNIGHT:
                KnightMovement knight = new KnightMovement();
                moves = knight.iterateMoves(moves, board, pos);
                break;
            case ROOK:
                RookMovement rook = new RookMovement();
                moves = rook.iterateMoves(moves, board, pos, rook.getDirections());
                break;
            case PAWN:
                PawnMovement pawn = new PawnMovement();
                if (board.getPiece(pos).getTeamColor() == BLACK) {
                    moves = pawn.iterateMoves(moves, board, pos, pawn.getDirectionsBlack());
                }
                else {
                    moves = pawn.iterateMoves(moves, board, pos, pawn.getDirectionsWhite());
                }
                break;
        }

        return moves;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();
        moves = getMovementType(moves, board, myPosition);

        return moves;
    }
}
