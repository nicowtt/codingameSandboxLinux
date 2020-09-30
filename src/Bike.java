
class Bike extends Cell{
    private int id;
    private int speed;
    private boolean active;
    private boolean invalidOrder;
    private Cell nextCell;

    public Bike() {
        this.invalidOrder = false;
    }

    public void setId(int id) { this.id = id; }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setActive(boolean active) { this.active = active; }
    public void setInvalidOrder(boolean invalidOrder) { this.invalidOrder = invalidOrder; }
    public void setNextCell(Cell nextCell) { this.nextCell = nextCell; }

    public int getId() { return id; }
    public int getSpeed() { return speed; }
    public boolean isActive() { return active; }
    public boolean isInvalidOrder() { return invalidOrder; }
    public Cell getNextCell() { return nextCell; }

    // methods
    public Cell nextCellMove(String indication) {
        if (indication.equals("SPEED")) {
            if (speed == 0) {
                return new Cell(this.getX() + 1, this.getY());
            } else {
                return new Cell(this.getX() + speed + 1, this.getY());
            }
        }
        if (indication.equals("WAIT")) {
            if (speed == 0) {
                return new Cell(this.getX(), this.getY());
            } else {
                return new Cell(this.getX() + speed, this.getY());
            }
        }
        if (indication.equals("SLOW")) {
            if (speed == 0) {
                return new Cell(this.getX() - 1, this.getY());
            } else {
                return new Cell(this.getX() + speed - 1, this.getY());
            }
        }
        return new Cell();
    }

    // to string
    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", speed=" + speed +
                ", active=" + active +
                ", invalidOrder=" + invalidOrder +
                ", nextCell=" + nextCell +
                '}';
    }
}
