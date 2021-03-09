class Move extends Cell {

    private String direction;

    public Move(int x, int y ,String direction) {
        super (x,y);
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
