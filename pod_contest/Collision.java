class Collision {
    private Unit a;
    private Unit b;
    double t;

    public Collision(Unit a, Unit b, double t) {
        this.a = a;
        this.b = b;
        this.t = t;
    }

    @Override
    public String toString() {
        return "Collision{" +
               "a=" + a +
               ", b=" + b +
               ", t=" + t +
               '}';
    }
}
