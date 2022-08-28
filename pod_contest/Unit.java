import static java.lang.Math.sqrt;

class Unit extends Point{
    private int id;
    private double rayon;
    private double vitesseX;
    private double vitesseY;

    public Unit() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getRayon() { return rayon; }
    public void setRayon(double rayon) { this.rayon = rayon; }
    public double getVitesseX() { return vitesseX; }
    public void setVitesseX(double vitesseX) { this.vitesseX = vitesseX; }
    public double getVitesseY() { return vitesseY; }
    public void setVitesseY(double vitesseY) { this.vitesseY = vitesseY; }

    //methods
    Collision collision(Unit u) {
        // Distance carré
        double dist = this.distance2(u);

        // Somme des rayons au carré
        double sr = (this.rayon + u.rayon)*(this.rayon + u.rayon);
        System.err.println("sr: " + sr);

        // On prend tout au carré pour éviter d'avoir à appeler un sqrt inutilement. C'est mieux pour les performances

        if (dist < sr) {
            // Les objets sont déjà l'un sur l'autre. On a donc une collision immédiate
            System.err.println("passage collision imediate");
            return new Collision(this, u, 0.0);
        }

        // Optimisation. Les objets ont la même vitesse ils ne pourront jamais se rentrer dedans
        if (this.vitesseX == u.vitesseX && this.vitesseY == u.vitesseY) {
            System.err.println("passage même vitesse");
            return null;
        }

        // On se met dans le référentiel de u. u est donc immobile et se trouve sur le point (0,0) après ça
        this.setX(this.getX() - u.getX());
        this.setY(this.getY() - u.getY());
        Point myp = new Point(this.getX(), this.getY());
        double vx = this.vitesseX - u.vitesseX;
        double vy = this.vitesseY - u.vitesseY;
        Point up = new Point(0, 0);

        // On cherche le point le plus proche de u (qui est donc en (0,0)) sur la droite décrite par notre vecteur de vitesse
        Point p = up.closest(myp, new Point(this.getX() + vx, this.getY() + vy));

        // Distance au carré entre u et le point le plus proche sur la droite décrite par notre vecteur de vitesse
        double pdist = up.distance2(p);
        System.err.println("pdist: " + pdist);

        // Distance au carré entre nous et ce point
        double mypdist = myp.distance2(p);

        // Si la distance entre u et cette droite est inférieur à la somme des rayons, alors il y a possibilité de collision
        if (pdist < sr) {
            // Notre vitesse sur la droite
            double length = sqrt(vx*vx + vy*vy);

            // On déplace le point sur la droite pour trouver le point d'impact
            double backdist = sqrt(sr - pdist);
            p.setX(p.getX() - backdist * (vx / length));
            p.setY(p.getY() - backdist * (vy / length));

            // Si le point s'est éloigné de nous par rapport à avant, c'est que notre vitesse ne va pas dans le bon sens
            if (myp.distance2(p) > mypdist) {
                System.err.println("passage vitesse pas dans le bon sens");
                return null;
            }

            pdist = p.distance(myp);

            // Le point d'impact est plus loin que ce qu'on peut parcourir en un seul tour
            if (pdist > length) {
                System.err.println("passage trop loin pour un tour");
                return null;
            }

            // Temps nécessaire pour atteindre le point d'impact
            double t = pdist / length;
            System.err.println("passage retour collision");
            return new Collision(this, u, t);
        }
        System.err.println("aucune collision avec cette trajectoire!");
        return null;
    }
}