package chess;

import static chess.PieceMovement.Direction.*;

public class BishopMovement extends PieceMovement {

    private Direction[] directions = {UPLEFT, UPRIGHT, BACKLEFT, BACKRIGHT};

    BishopMovement() {
        super();
    }

    Direction[] getDirections() {
        return directions;
    }

}
