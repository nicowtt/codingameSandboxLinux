
class Queen extends Piece {

    public Queen(char type, Cell pos) {
        super();
        this.type = type;
        this.pos = pos;
    }

    // methods
    @Override
    public void updateMoveCells() {
        if (this.type != 'Q') return;

        this.moveCells = Moves.queenMoves(Utils.map, pos);
    }
}
