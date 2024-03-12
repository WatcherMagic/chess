package clientTests;

import chess.ChessBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardGraphicTests {

    ChessBoard board = new ChessBoard();

    @BeforeEach
    void resetBoard() {
        board.resetBoard();
    }
}
