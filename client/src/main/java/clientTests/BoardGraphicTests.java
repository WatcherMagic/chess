package clientTests;

import chess.ChessBoard;
import chess.ChessPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.ChessBoardGraphic;

import java.util.List;

import static chess.ChessGame.TeamColor.*;
import static chess.ChessPiece.PieceType.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardGraphicTests {

    ChessBoard board = new ChessBoard();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }

    @Test
    void getPieceCharMethodWorks() {
        ChessBoardGraphic graphic = new ChessBoardGraphic(board);

        char c = graphic.getTextCharForPiece(new ChessPiece(BLACK, KING));
        assertNotEquals('X', c, "the piece type could not be determined");
        assertEquals('k', c, "method returned the wrong character");

        ChessPiece piece = new ChessPiece(WHITE, PAWN);
        c = graphic.getTextCharForPiece(piece);
        assertNotEquals('p', c, "the character failed to be capitalized");
        assertEquals('P', c, "wrong capitzalization or character");
    }

    @Test
    void correctCharactersForAllChessPieces() {
        ChessBoardGraphic graphic = new ChessBoardGraphic(board);

        //arrays size 12
        ChessPiece[] pieces =  {
                new ChessPiece(BLACK, KING),
                new ChessPiece(BLACK, QUEEN),
                new ChessPiece(BLACK, BISHOP),
                new ChessPiece(BLACK, ROOK),
                new ChessPiece(BLACK, KNIGHT),
                new ChessPiece(BLACK, PAWN),

                new ChessPiece(WHITE, KING),
                new ChessPiece(WHITE, QUEEN),
                new ChessPiece(WHITE, BISHOP),
                new ChessPiece(WHITE, ROOK),
                new ChessPiece(WHITE, KNIGHT),
                new ChessPiece(WHITE, PAWN),
        };
        char[] pieceChars = {'k','q','b','r','k','p','K','Q','B','R','K','P'};
        char testChar = ' ';

        for (int i = 0; i < 12; i++) {
            testChar = graphic.getTextCharForPiece(pieces[i]);
            assertNotEquals('X', testChar, "the piece type is missing");
            assertEquals(pieceChars[i], testChar, "the wrong color was returned");
        }

    }

    @Test
    void getCharsForPiecesFails() {
        ChessBoardGraphic graphic = new ChessBoardGraphic(board);

        char c = graphic.getTextCharForPiece(new ChessPiece(null, null));
        assertEquals('X', c, "something bad happened!");

        c = graphic.getTextCharForPiece(null);
        assertEquals('\u2003', c);
    }

    @Test
    void printEmptyBoardSpacesAndString() {
        ChessBoardGraphic graphic = new ChessBoardGraphic(board);

        List<String> list = graphic.constructEmptyBoardSpaces(false);
        String spaces = "";
        for (String s : list) {
            spaces = spaces.concat(s);
        }
        System.out.print(spaces);
        String fullString = graphic.constructEmptyBoardString(list, false);
        System.out.print(fullString);
    }

    @Test
    void printBoardBlackDown() {
        ChessBoardGraphic graphic = new ChessBoardGraphic(board);

        graphic.updateBoardString();
        graphic.printBoard();
    }

    @Test
    void printBoardWhiteDown() {
        ChessBoardGraphic graphic = new ChessBoardGraphic(board);

        List<String> list = graphic.constructEmptyBoardSpaces(true);
        String spaces = "";
        for (String s : list) {
            spaces = spaces.concat(s);
        }
        System.out.print(spaces);

        graphic.updateBoardString();
        graphic.printBoard();
    }
}
