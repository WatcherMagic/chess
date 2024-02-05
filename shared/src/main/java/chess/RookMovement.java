package chess;

import static chess.PieceMovement.Direction.*;

public class RookMovement extends PieceMovement {

    private Direction[] directions = {UP, BACK, LEFT, RIGHT};

    RookMovement() {
        super();
    }

    Direction[] getDirections() {
        return directions;
    }

}
