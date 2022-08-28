class Pod extends Unit{
    private int id;
    private double angle;
    private int nextCheckPointId;
    private int checked; // nbr de checkPoint passé
    private int timeOut; // nbr de tour avant timeOut
    private Pod partner;
    private Boolean shield;

    public Pod( int checked, int timeOut ) {
        this.id = id;
        this.checked = checked;
        this.timeOut = timeOut;
    }

    public int getId() { return id; }
    public double getAngle() { return angle; }
    public int getNextCheckPointId() { return nextCheckPointId; }
    public int getChecked() { return checked; }
    public int getTimeOut() { return timeOut; }
    public Pod getPartner() { return partner; }
    public Boolean isAlreadyShield() { return shield; }

    public void setId(int id) { this.id = id;}
    public void setAngle(double angle) { this.angle = angle; }
    public void setNextCheckPointId(int nextCheckPointId) { this.nextCheckPointId = nextCheckPointId; }
    public void setChecked(int checked) { this.checked = checked; }
    public void setTimeOut(int timeOut) { this.timeOut = timeOut; }
    public void setPartner(Pod partner) { this.partner = partner; }
    public void setAlreadyShield(Boolean shield) { this.shield = shield; }

    // methods
    /**
     * angle que devrais avoir le pod pour faire face a un point donnée
     * @param p
     * @return
     */
    public double calcAngle(Point p) {
        double d = this.distance(p);
        double dx = (p.getX() - this.getX()) / d;
        double dy = (p.getY() - this.getY()) / d;

        // Trigonométrie simple. On multiplie par 180.0 / PI pour convertir en degré.
        double a = Math.acos(dx) * 180.0 / Math.PI;

        // Si le point qu'on veut est en dessus de nous, il faut décaler l'angle pour qu'il soit correct.
        if (dy < 0) {
            a = 360.0 - a;
        }

        return a;
    }

    /**
     * De combien le pod doit tourné (et dans quel sens) pour être comme demandé par getAngle()
     * @param p
     * @return
     */
    public double diffAngle(Point p) {
        double a = this.calcAngle(p);

        // Pour connaitre le sens le plus proche, il suffit de regarder dans les 2 sens et on garde le plus petit
        // Les opérateurs ternaires sont la uniquement pour éviter l'utilisation d'un operateur % qui serait plus lent
        double right = this.angle <= a ? a - this.angle : 360.0 - this.angle + a;
        double left = this.angle >= a ? this.angle - a : this.angle + 360.0 - a;

        if (right < left) {
            return right;
        } else {
            // On donne un angle négatif s'il faut tourner à gauche
            return -left;
        }
    }

    /**
     * indique de combien le pod doit tourner dans le prochain tour
     * @param p
     */
    public void rotate(Point p) {
        double a = this.diffAngle(p);

        // On ne peut pas tourner de plus de 18° en un seul tour
        if (a > 18.0) {
            a = 18.0;
        } else if (a < -18.0) {
            a = -18.0;
        }

        this.angle += a;

        // L'opérateur % est lent. Si on peut l'éviter, c'est mieux.
        if (this.angle >= 360.0) {
            this.angle = this.angle - 360.0;
        } else if (this.angle < 0.0) {
            this.angle += 360.0;
        }
    }

    /**
     * Application du trust
     * @param thrust
     */
    public void boost(int thrust) {
        // N'oubliez pas qu'un pod qui a activé un shield ne peut pas accélérer pendant 3 tours
        /*
        if (this.shield) {
            return;
        }
        */

        // Conversion de l'angle en radian
        double ra = this.angle * Math.PI / 180.0;

        // Trigonométrie
        this.setVitesseX(this.getVitesseX() + (Math.cos(ra) * thrust));
        this.setVitesseY(this.getVitesseY() + (Math.sin(ra) * thrust));
    }

    /**
     * deplacement du pod
     * @param t -> put 1 if there is no collision on simulation
     */
    public void move(double t) {
        this.setX(this.getX() + (this.getVitesseX() * t));
        this.setY(this.getY() + (this.getVitesseY() * t));
    }

    /**
     * application de la friction et arrondir le resultat
     */
    public void end() {
        this.setX(Math.round(this.getX()));
        this.setY(Math.round(this.getY()));
        this.setVitesseX(Math.round(this.getVitesseX() * 0.85));
        this.setVitesseY(Math.round(this.getVitesseY() * 0.85));

        // N'oubliez pas que le timeout descend de 1 chaque tour. Il revient à 100 quand on passe par un checkpoint
        //this.timeout -= 1;
    }

    /**
     * simulate position on next turn with specific point!
     * @param p
     * @param thrust
     */
    public void play(Point p, int thrust) {
        this.rotate(p);
        this.boost(thrust);
        this.move(1.0);
        this.end();
    }
}
