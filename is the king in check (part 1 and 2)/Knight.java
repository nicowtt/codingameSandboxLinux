
class Knight extends Piece{

    public Knight(char type, Cell pos) {
        super();
        this.type = type;
        this.pos = pos;
    }

    // methods
    @Override
    public void updateMoveCells() {
        if (this.type != 'N') return;

        this.moveCells = Moves.knightMoves(Utils.map, pos);
    }
}
