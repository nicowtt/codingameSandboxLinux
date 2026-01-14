import java.util.ArrayList;
import java.util.List;

abstract class Piece {

    char type;
    Cell pos;
    List<Cell> moveCells;

    public Piece() {
        this.moveCells = new ArrayList<>();
    }

    // methods
    public void updateMoveCells() {
    }
}
