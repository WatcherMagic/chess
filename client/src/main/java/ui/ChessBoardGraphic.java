package ui;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

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

    ChessBoardGraphic(ChessBoard boardData) {
        this.boardData = boardData;
    }

    public void updateBoardString(ChessPosition pos) {
        char[] boardStringArray = chessBoard.toCharArray();

        //
    }

}
