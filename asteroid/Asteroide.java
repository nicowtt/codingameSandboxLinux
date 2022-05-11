class Asteroide {

    private char name;

    private int posT1X;
    private int posT1Y;

    private int posT2X;
    private int posT2Y;

    private int moveT2T1X;
    private int moveT2T1Y;

    private int posT3X;
    private int posT3Y;

    public Asteroide(int posT1X, int posT1Y, char name) {
        this.posT1X = posT1X;
        this.posT1Y = posT1Y;
        this.name = name;
    }

    public Asteroide() {}

    public char getName() {
        return name;
    }

    public int getPosT1X() {
        return posT1X;
    }

    public int getPosT1Y() {
        return posT1Y;
    }

    public int getPosT2X() {
        return posT2X;
    }

    public void setPosT2X(int posT2X) {
        this.posT2X = posT2X;
    }

    public int getPosT2Y() {
        return posT2Y;
    }

    public void setPosT2Y(int posT2Y) {
        this.posT2Y = posT2Y;
    }

    public int getPosT3X() {
        return posT3X;
    }

    public void setPosT3X(int posT3X) {
        this.posT3X = posT3X;
    }

    public int getPosT3Y() {
        return posT3Y;
    }

    public void setPosT3Y(int posT3Y) {
        this.posT3Y = posT3Y;
    }

    public int getMoveT2T1X() {
        return moveT2T1X;
    }

    public void setMoveT2T1X(int moveT2T1X) {
        this.moveT2T1X = moveT2T1X;
    }

    public int getMoveT2T1Y() {
        return moveT2T1Y;
    }

    public void setMoveT2T1Y(int moveT2T1Y) {
        this.moveT2T1Y = moveT2T1Y;
    }

    @Override
    public String toString() {
        return "Asteroide{" +
                ", name='" + name + '\'' +
                " posT1X=" + posT1X +
                ", posT1Y=" + posT1Y +
                " posT2X=" + posT2X +
                ", posT2Y=" + posT2Y +
                ", moveT2T1X=" + moveT2T1X +
                ", moveT2T1Y=" + moveT2T1Y +
                ", posT3X=" + posT3X +
                ", posT3Y=" + posT3Y +
                '}';
    }
}