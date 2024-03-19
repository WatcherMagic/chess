package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;

import static ui.EscapeSequences.*;

import static java.lang.Character.toUpperCase;

public class ChessBoardGraphic {

    String chessBoard;
    ChessBoard boardData;
    int[] boardPosIndexes;

    public ChessBoardGraphic(ChessBoard boardData) {
        this.boardData = boardData;
        this.chessBoard = constructEmptyBoardString();
        this.boardPosIndexes = getBoardSpaceStringIndexes();
    }

    public int[] getBoardSpaceStringIndexes() {
        char[] boardStringArray = chessBoard.toCharArray();

        int[] boardPositionsFromA1toH8 = new int[64];
        int arrayIndex = 0;

        for (int i = 0; i < boardStringArray.length; i++) {
            if (boardStringArray[i] == '#') {
                boardPositionsFromA1toH8[arrayIndex] = i;
                ++arrayIndex;
            }
        }

        return boardPositionsFromA1toH8;
    }

    private List<String> constructEmptyBoardList() {
        List<String> newBoard = new ArrayList<>();

        newBoard.add(SET_BG_COLOR_WHITE);
        newBoard.add("   A ");
        newBoard.add(" B ");
        newBoard.add(" C ");
        newBoard.add(" D ");
        newBoard.add(" E ");
        newBoard.add(" F ");
        newBoard.add(" G ");
        newBoard.add(" H ");


        String lightDark = SET_BG_COLOR_LIGHT_GREY + " # ";
        String darkLight = SET_BG_COLOR_DARK_GREY + " # ";

        for (int i = 1; i <= 8; i++) {
            newBoard.add(SET_BG_COLOR_WHITE);
            newBoard.add(SET_TEXT_COLOR_BLACK);
            newBoard.add("\n");
            newBoard.add(i + " ");
            newBoard.add(SET_TEXT_COLOR_WHITE);

            for (int x = 0; x < 4; x++) {
                if (i % 2 == 0) {
                    newBoard.add(darkLight);
                    newBoard.add(lightDark);
                }
                else {
                    newBoard.add(lightDark);
                    newBoard.add(darkLight);
                }
            }
        }
        newBoard.add(SET_BG_COLOR_WHITE);
        newBoard.add(SET_TEXT_COLOR_BLACK);
        newBoard.add("\n");

        return newBoard;
    }

    private String constructEmptyBoardString() {
        List<String> list = constructEmptyBoardList();
        String board = "";

        for(int i = 0; i < list.size(); i++) {
            board = board.concat(list.get(i));
        }

        return board;
    }

    public void printBoard() {
        System.out.print(chessBoard);
    }

    public String updateBoardString() {
        //untested -- need to finish display method first

        ChessPiece pieceAt;
        int indexAt = 0;
        char[] boardCharArray = chessBoard.toCharArray();
        char updateChar;

        for (int x = 1; x <= boardData.getBoardLength(); x++) {
            for (int y = 1; y <= boardData.getBoardLength(); y++) {
                pieceAt = boardData.getPiece(new ChessPosition(x, y));
                updateChar = getTextCharForPiece(pieceAt);

                boardCharArray[boardPosIndexes[indexAt]] = updateChar;
                indexAt++;
            }
        }

        chessBoard = String.valueOf(boardCharArray);
        return chessBoard;
    }

    public char getTextCharForPiece(ChessPiece piece) {

        if (piece == null) {
            return ' ';
        }

        char pieceChar = 'X';
        ChessPiece.PieceType type = piece.getPieceType();
        ChessGame.TeamColor color = piece.getTeamColor();

        if (type == null || color == null) {
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

        if (color == ChessGame.TeamColor.WHITE) {
            pieceChar = toUpperCase(pieceChar);
        }

        return pieceChar;
    }
}
