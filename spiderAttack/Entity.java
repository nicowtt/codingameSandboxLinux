class Entity {
    private int id;
    private int type;
    private int x, y;
    private int shieldLife;
    private Controlled isControlled;
    private int health;
    private int vx, vy;
    private int nearBase;
    private MonsterDirection direction;
    private Cell cell;

    // valid
    private Integer distFromBase;
    private Entity target;
    private String finalMoveText;

    // on test
    private boolean targetedByMyHero;
    private HeroMode mode;

    public Entity() {
    }

    public Entity(Cell cell) {
        this.cell = cell;
    }

    Entity(int id, int type, int x, int y, int shieldLife, int isControlled, int health, int vx, int vy, int nearBase, int threatFor, Cell cell) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.shieldLife = shieldLife;
        if (isControlled == 1) { this.isControlled = Controlled.YES; }
        if (isControlled == 0) {this.isControlled = Controlled.NO;}
        this.health = health;
        this.vx = vx;
        this.vy = vy;
        this.nearBase = nearBase;
        if (threatFor == -1) { this.direction = MonsterDirection.NOMONSTER; }
        if (threatFor == 0) { this.direction = MonsterDirection.NOBASE; }
        if (threatFor == 1) { this.direction = MonsterDirection.MYBASE; }
        if (threatFor == 2) { this.direction = MonsterDirection.OPPBASE; }
        this.cell = cell;

        // init
        this.targetedByMyHero = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getShieldLife() {
        return shieldLife;
    }

    public void setShieldLife(int shieldLife) {
        this.shieldLife = shieldLife;
    }

    public Controlled getIsControlled() {
        return isControlled;
    }

    public void setIsControlled(Controlled isControlled) {
        this.isControlled = isControlled;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getNearBase() {
        return nearBase;
    }

    public void setNearBase(int nearBase) {
        this.nearBase = nearBase;
    }

    public MonsterDirection getDirection() {
        return direction;
    }

    public void setDirection(MonsterDirection direction) {
        this.direction = direction;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public Integer getDistFromBase() {
        return distFromBase;
    }

    public void setDistFromBase(Integer distFromBase) {
        this.distFromBase = distFromBase;
    }

    public String getFinalMoveText() {
        return finalMoveText;
    }

    public void setFinalMoveText(String finalMoveText) {
        this.finalMoveText = finalMoveText;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public boolean isTargetedByMyHero() {
        return targetedByMyHero;
    }

    public void setTargetedByMyHero(boolean targetedByMyHero) {
        this.targetedByMyHero = targetedByMyHero;
    }

    public HeroMode getMode() {
        return mode;
    }

    public void setMode(HeroMode heroMode) {
        this.mode = heroMode;
    }
}

enum Controlled {
    YES(1), NO(0);

    private int numVal;

    Controlled(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}

enum MonsterDirection {
    NOMONSTER(-1), NOBASE(0), MYBASE(1), OPPBASE(2);

    private int numVal;

    MonsterDirection(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}