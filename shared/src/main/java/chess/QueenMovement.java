package chess;

import static chess.PieceMovement.Direction.*;

public class QueenMovement extends PieceMovement {

    private Direction[] directions = {UP, BACK, LEFT, RIGHT, UPLEFT, UPRIGHT, BACKLEFT, BACKRIGHT};

    QueenMovement() {
        super();
    }

    Direction[] getDirections() {
        return directions;
    }

}
