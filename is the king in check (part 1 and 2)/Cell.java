
class Cell {
    int x;
    int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) { this.x = x; }
    public int getX() { return x; }
    public void setY(int y) { this.y = y; }
    public int getY() { return y; }

    public String toString() {
        return "{" +
                "x:" + x +
                " y:" + y +
                "}";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Cell)) return false;

        Cell cell = (Cell) obj;
        return this.x == cell.x && this.y == cell.y;
    }
}
