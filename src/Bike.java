
class Bike extends Cell{
    private int id;
    private int speed;
    private boolean accelerate;
    private boolean active;
    private boolean invalidOrder;
    private Cell nextCell;

    public Bike() {

        this.invalidOrder = false;
        this.accelerate = false;
    }

    public void setId(int id) { this.id = id; }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setAccelerate(boolean accelerate) { this.accelerate = accelerate; }
    public void setActive(boolean active) { this.active = active; }
    public void setInvalidOrder(boolean invalidOrder) { this.invalidOrder = invalidOrder; }
    public void setNextCell(Cell nextCell) { this.nextCell = nextCell; }

    public int getId() { return id; }
    public int getSpeed() { return speed; }
    public boolean isAccelerate() { return accelerate; }
    public boolean isActive() { return active; }
    public boolean isInvalidOrder() { return invalidOrder; }
    public Cell getNextCell() { return nextCell; }

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
