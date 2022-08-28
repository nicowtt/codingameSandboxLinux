import java.util.List;

class Move {

    /**
     * Try to take best vector for destination
     * @param myPod
     * @return
     */
    public String moveIA1(Pod myPod, List<Checkpoint> checkpoints, List<MyPodSharing> myPodSharings ) {

        int trust = 0;
        String bestTrust = " ";
        double dist = Double.POSITIVE_INFINITY;
        Checkpoint checkPoint = checkpoints.stream()
            .filter(c -> c.getId() == myPod.getNextCheckPointId())
            .findFirst()
            .get();

        // timeOut
        int timeOut = myPod.getTimeOut();
        myPod.setTimeOut(timeOut - 1);


        // calc difference angle between myNextCheckPoint and me
        double diffAngle = myPod.diffAngle(checkPoint);
        System.err.println("myPod n°" + myPod.getId() );
        System.err.println("Angle: " + myPod.getAngle());
        System.err.println("vise checkPoint id: " + myPod.getNextCheckPointId());
        System.err.println("Diff Angle: " + diffAngle);
        System.err.println("timeOut: " + myPod.getTimeOut());
        // test collision avec le prochain checkpoint
        Collision colWithCheckPoint = myPod.collision(checkPoint);
        if (colWithCheckPoint != null) {
            System.err.println("Collision to checkpoint: " + colWithCheckPoint.toString());
            myPod.setTimeOut(100);
            int checked = myPod.getChecked();
            myPod.setChecked(checked + 1);
            System.err.println("nbr de checkPoint passés: " + myPod.getChecked());
        }

        System.err.println("-----------------------------------------");

        // simulate with 10 level of trust -> better distance to checkpoint
        for (int i = 10; i <= 100; i+=10) {
            myPod.play(checkPoint, i);
            double simuDistance = myPod.distance2(checkPoint);
            if (simuDistance < dist) {
                dist = simuDistance;
                trust = i;
            }
        }
        bestTrust+= trust;

        // add only one boost
        if (myPod.distance(checkPoint) > 4000 && Utils.isBoostPossible(myPodSharings)) {
            myPodSharings.get(myPod.getId() - 1).setBoost(true);
            return (int)checkPoint.getX() + " " + (int)checkPoint.getY() + " BOOST";
        } else {
            return (int)checkPoint.getX() + " " + (int)checkPoint.getY() + bestTrust;
        }
    }
}
