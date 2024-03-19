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
        this.chessBoard = constructEmptyBoardString(constructEmptyBoardSpaces());
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

    public List<String> constructEmptyBoardSpaces() {
        List<String> newBoard = new ArrayList<>();

        String lightGray = SET_BG_COLOR_LIGHT_GREY + " # ";
        String darkGray = SET_BG_COLOR_DARK_GREY + " # ";

        for (int i = 1; i <= 8; i++) {
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
            newBoard.add(SET_BG_COLOR_WHITE);
            newBoard.add("\n");
        }
        newBoard.add(SET_BG_COLOR_WHITE);
        newBoard.add("\n");

        return newBoard;
    }

    public String constructEmptyBoardString(List<String> list) {
        String board = "";
        board = board.concat(SET_BG_COLOR_WHITE + "   A  B  C  D  E  F  G  H\n1 " + SET_TEXT_COLOR_WHITE);

        int colIncrement = 0;
        int rowIncrement = 2;
        for(int i = 0; i < list.size(); i++) {
            board = board.concat(list.get(i));
            if (list.get(i).equals("\n") && rowIncrement < 9) {
                board = board.concat(SET_TEXT_COLOR_BLACK
                        + rowIncrement + " " + SET_TEXT_COLOR_WHITE);
                rowIncrement++;
            }
        }

        return board;
    }

    public void updateBoardString(String s) {
        chessBoard = s;
    }

    public void printBoard() {
        System.out.print(chessBoard);
        System.out.print(reverseBoardString());
    }

    public String reverseBoardString() {
        List<String> board = constructEmptyBoardSpaces();
        Collections.reverse(board);

        List<String> flippedBoard = new ArrayList<>();
        flippedBoard.add(SET_BG_COLOR_WHITE + "   H  G  F  D  C  B  A\n8 " + SET_TEXT_COLOR_WHITE);
        int rowIncrement = 7;
        for (int i = 1; i < board.size(); i++) {
            if (isDigit(board.get(i).toCharArray()[0])) {
                continue;
            }
            if (board.get(i).equals("\n")) {
                flippedBoard.add(rowIncrement + " ");
                rowIncrement--;
            }
            flippedBoard.add(board.get(i));
        }
        String reverseBoardString = constructEmptyBoardString(flippedBoard);
        char[] stringArray = reverseBoardString.toCharArray();

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
        return reversed;
    }

    public String setStringChessPieces() {

        ChessPiece pieceAt;
        char[] boardCharArray = constructEmptyBoardString(constructEmptyBoardSpaces()).toCharArray();
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

        String s = String.valueOf(boardCharArray);
        return s;
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
