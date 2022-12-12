class Node {
    private int id;
    private int x;
    private int y;
    private int g;
    private int f;
    private int h;
    private Node parent;
    private boolean isBlock;
    private boolean isHouse;
    private boolean isTree;
    private boolean isOnFire;
    private int timeOnFire;
    private boolean isSecurized;

    public Node(int id, int x, int y) {
        super();
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Node() {}

    public void calculateHeuristic(Node finalNode) {
        this.h = Math.abs(finalNode.getY() - getY()) + Math.abs(finalNode.getX() - getX());
    }

    public void setNodeData(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        setParent(currentNode);
        setG(gCost);
        calculateFinalCost();
    }

    public boolean checkBetterPath(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < getG()) {
            setNodeData(currentNode, cost);
            return true;
        }
        return false;
    }

    private void calculateFinalCost() {
        int finalCost = getG() + getH();
        setF(finalCost);
    }

    @Override
    public boolean equals(Object arg0) {
        Node other = (Node) arg0;
        return this.getY() == other.getY() && this.getX() == other.getX();
    }

    @Override
    public String toString() {
        return "Node id " + id
                + " [x=" + x +
                ", y=" + y +
                ", secu: " + isSecurized() +
                ", house: " + isHouse() +
                ", fire: " + isOnFire() +
                "]";
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    public boolean isHouse() {
        return isHouse;
    }

    public void setHouse(boolean house) {
        isHouse = house;
    }

    public boolean isTree() {
        return isTree;
    }

    public void setTree(boolean tree) {
        isTree = tree;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire(boolean onFire) {
        isOnFire = onFire;
    }

    public int getTimeOnFire() {
        return timeOnFire;
    }

    public void setTimeOnFire(int timeOnFire) {
        this.timeOnFire = timeOnFire;
    }

    public boolean isSecurized() {
        return isSecurized;
    }

    public void setSecurized(boolean securized) {
        isSecurized = securized;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}