package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import javax.lang.model.type.NullType;

import static chess.ChessPiece.PieceType.*;
import static java.lang.Character.toUpperCase;

public class ChessBoardGraphic {

    String emptyBoard = """
                A   B   C   D   E   F   G   H  
            --|---|---|---|---|---|---|---|---|
            1 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 |
            --|---|---|---|---|---|---|---|---|
            2 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 |
            --|---|---|---|---|---|---|---|---|
            3 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 |
            --|---|---|---|---|---|---|---|---|
            4 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 |
            --|---|---|---|---|---|---|---|---|
            5 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 |
            --|---|---|---|---|---|---|---|---|
            6 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 |
            --|---|---|---|---|---|---|---|---|
            7 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 |
            --|---|---|---|---|---|---|---|---|
            8 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 | \u2003 |
            --|---|---|---|---|---|---|---|---|
            """;
    String chessBoard = emptyBoard;
    ChessBoard boardData;
    int[] boardPosIndexes;

    public ChessBoardGraphic(ChessBoard boardData) {
        this.boardData = boardData;
        this.boardPosIndexes = getBoardPositions();
    }

    public int[] getBoardPositions() {
        char[] boardStringArray = emptyBoard.toCharArray();

        int[] boardPositionsFromA1toH8 = new int[64];
        int posArrayIndex = 0;

        for (int i = 0; i < boardStringArray.length; i++) {
            if (boardStringArray[i] == '\u2003') {
                boardPositionsFromA1toH8[posArrayIndex] = i;
            }
        }

        return boardPositionsFromA1toH8;
    }

    public void updateBoardString() {
        //untested -- need to finish display method first

        ChessPiece pieceAt = null;
        int indexAt = 0;
        char[] boardStringArray = chessBoard.toCharArray();
        char updateChar = ' ';

        for (int x = 0; x < boardData.getBoardLength(); x++) {
            for (int y = 0; y < boardData.getBoardLength(); y++) {
                pieceAt = boardData.getPiece(new ChessPosition(x, y));
                updateChar = getCharForPiece(pieceAt);

                //
            }
        }
    }

    public char getCharForPiece(ChessPiece piece) {

        if (piece == null) {
            return '\u2003';
        }

        char pieceChar = 'X';
        ChessPiece.PieceType type = piece.getPieceType();

        if (type == null || piece.getPieceType() == null) {
            return pieceChar;
        }

        switch (type) {
            case KING:
                pieceChar = 'k';
                break;
            case QUEEN:
                pieceChar = 'q';
                break;
            case BISHOP:
                pieceChar = 'b';
                break;
            case ROOK:
                pieceChar = 'r';
                break;
            case KNIGHT:
                pieceChar = 'k';
                break;
            case PAWN:
                pieceChar = 'p';
                break;
        }

        ChessGame.TeamColor color = piece.getTeamColor();
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            pieceChar = toUpperCase(pieceChar);
        }

        return pieceChar;
    }
}
