package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Character.isDigit;
import static ui.EscapeSequences.*;

import static java.lang.Character.toUpperCase;

public class ChessBoardGraphic {

    String chessBoard;
    ChessBoard boardData;

    public ChessBoardGraphic(ChessBoard boardData) {
        this.boardData = boardData;
        this.chessBoard = constructEmptyBoardString(constructEmptyBoardSpaces(false), false);
    }

    public List<String> constructEmptyBoardSpaces(boolean flipped) {
        List<String> newBoard = new ArrayList<>();

        String lightGray = SET_BG_COLOR_LIGHT_GREY + " # ";
        String darkGray = SET_BG_COLOR_DARK_GREY + " # ";

        if (flipped) {
            newBoard.add("\n");
            newBoard.add(SET_BG_COLOR_WHITE);
        }
        for (int i = 1; i <= 8; i++) {
            if(flipped) {
                newBoard.add("\n");
                newBoard.add(SET_BG_COLOR_WHITE);
            }
            for (int x = 0; x < 4; x++) {
                if (i % 2 == 0) {
                    newBoard.add(darkGray);
                    newBoard.add(lightGray);
                }
                else {
                    newBoard.add(lightGray);
                    newBoard.add(darkGray);
                }
            }
            if (!flipped) {
                newBoard.add(SET_BG_COLOR_WHITE);
                newBoard.add("\n");
            }
        }
        if (!flipped) {
            newBoard.add(SET_BG_COLOR_WHITE);
            newBoard.add("\n");
        }

        return newBoard;
    }

    public String constructEmptyBoardString(List<String> list, boolean flipped) {
        String board = "";
        int rowIncrement = 2;

        if (flipped) {
            rowIncrement = 7;
            board = board.concat(SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLACK +
                    "   H  G  F  E  D  C  B  A\n8 " + SET_TEXT_COLOR_WHITE);
        }
        else {
            board = board.concat(SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLACK +
                    "   A  B  C  D  E  F  G  H\n1 " + SET_TEXT_COLOR_WHITE);
        }

        int incrementLimit = 9;
        if (flipped) {
            incrementLimit = 0;
        }

        for(int i = 0; i < list.size(); i++) {
            board = board.concat(list.get(i));
            if (list.get(i).equals("\n") && rowIncrement != incrementLimit) {
                board = board.concat(SET_TEXT_COLOR_BLACK);
                board = board.concat(rowIncrement + " " + SET_TEXT_COLOR_WHITE);
                if (flipped) {
                    rowIncrement--;
                }
                else {
                    rowIncrement++;
                }
            }
        }

        return board;
    }

    public void printBoard() {
        updateBoardString();
        System.out.print(chessBoard);
        System.out.print(reverseBoardString());
    }

    public int[] getBoardSpaceStringIndexes(char[] boardStringArray) {

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

    public void updateBoardString() {

        ChessPiece pieceAt;
        char[] boardCharArray = chessBoard.toCharArray();
        int[] boardPosIndexes = getBoardSpaceStringIndexes(boardCharArray);

        char updateChar;
        int indexAt = 0;

        for (int x = 1; x <= boardData.getBoardLength(); x++) {
            for (int y = 1; y <= boardData.getBoardLength(); y++) {
                pieceAt = boardData.getPiece(new ChessPosition(x, y));
                updateChar = getTextCharForPiece(pieceAt);

                boardCharArray[boardPosIndexes[indexAt]] = updateChar;
                indexAt++;
            }
        }

        chessBoard = String.valueOf(boardCharArray);
        chessBoard = chessBoard + SET_TEXT_COLOR_BLACK;
    }

    public String reverseBoardString() {
        List<String> flipped = constructEmptyBoardSpaces(true);
        Collections.reverse(flipped);

        String reverseString = constructEmptyBoardString(flipped, true);
        char[] stringArray = reverseString.toCharArray();

        ChessPiece pieceAt;
        char updateChar;
        int[] indexes = getBoardSpaceStringIndexes(stringArray);
        int index = 0;
        for (int x = boardData.getBoardLength(); x >= 1; x--) {
            for (int y = boardData.getBoardLength(); y >= 1; y--) {
                pieceAt = boardData.getPiece(new ChessPosition(x, y));
                updateChar = getTextCharForPiece(pieceAt);

                stringArray[indexes[index]] = updateChar;
                index++;
            }
        }

        String reversed = String.valueOf(stringArray);
        reversed = reversed + SET_TEXT_COLOR_BLACK;
        return reversed;
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

    public char getChessCharForPiece(ChessPiece piece) {
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
                if (color == ChessGame.TeamColor.WHITE) {
                    pieceChar = WHITE_KING.charAt(0);
                }
                else {
                    pieceChar = BLACK_KING.charAt(0);
                }
                break;
            case QUEEN:
                if (color == ChessGame.TeamColor.WHITE) {
                    pieceChar = WHITE_QUEEN.charAt(0);
                }
                else {
                    pieceChar = BLACK_QUEEN.charAt(0);
                }
                break;
            case BISHOP:
                if (color == ChessGame.TeamColor.WHITE) {
                    pieceChar = WHITE_BISHOP.charAt(0);
                }
                else {
                    pieceChar = BLACK_BISHOP.charAt(0);
                }
                break;
            case ROOK:
                if (color == ChessGame.TeamColor.WHITE) {
                    pieceChar = WHITE_ROOK.charAt(0);
                }
                else {
                    pieceChar = BLACK_ROOK.charAt(0);
                }
                break;
            case KNIGHT:
                if (color == ChessGame.TeamColor.WHITE) {
                    pieceChar = WHITE_KNIGHT.charAt(0);
                }
                else {
                    pieceChar = BLACK_KNIGHT.charAt(0);
                }
                break;
            case PAWN:
                if (color == ChessGame.TeamColor.WHITE) {
                    pieceChar = WHITE_PAWN.charAt(0);
                }
                else {
                    pieceChar = BLACK_PAWN.charAt(0);
                }
                break;
        }

        return pieceChar;
    }
}
