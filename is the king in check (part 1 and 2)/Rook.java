
class Rook extends Piece {

    public Rook(char type, Cell pos) {
        super();
        this.type = type;
        this.pos = pos;
    }

    // methods
    @Override
    public void updateMoveCells() {
        if (this.type != 'R') return;
        this.moveCells = Moves.rookMove(Utils.map, pos);
    }
}
