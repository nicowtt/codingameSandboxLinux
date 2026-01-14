
class Bishop extends Piece{

    public Bishop(char type, Cell pos) {
        super();
        this.type = type;
        this.pos = pos;
    }

    // methods
    @Override
    public void updateMoveCells() {
        if (this.type != 'B') return;

        this.moveCells = Moves.bishopMoves(Utils.map, pos);
    }
}
