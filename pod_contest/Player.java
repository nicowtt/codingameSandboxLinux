import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    public static void main(String args[]) {
        new Player().run();
    }

    public void run() {
        Point myPodNextCheckPoint = new Point();
        Move move = new Move();
        List<Pod> myPods = new ArrayList<>();
        Pod myPod0 = new Pod(0, 100);
        myPods.add(myPod0);
        Pod myPod1 = new Pod(0, 100);
        myPods.add(myPod1);
        System.err.println(myPods.get(0).getX());
        List<Checkpoint> checkPoints = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int laps = in.nextInt();
        int checkpointCount = in.nextInt();
        for (int i = 0; i < checkpointCount; i++) {
            Checkpoint checkPoint = new Checkpoint();
            checkPoint.setId(i);
            checkPoint.setRayon(600);
            int checkpointX = in.nextInt(); checkPoint.setX(checkpointX);
            int checkpointY = in.nextInt(); checkPoint.setY(checkpointY);
            checkPoints.add(checkPoint);
        }

        // myPodSharing
        List<MyPodSharing> myPodSharings = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            MyPodSharing myPodSharing = new MyPodSharing(i + 1);
            myPodSharings.add(myPodSharing);
        }

        // game loop
        while (true) {
            for (int i = 0; i < 2; i++) {
                int x = in.nextInt(); myPods.get(i).setX(x);
                int y = in.nextInt(); myPods.get(i).setY(y);
                int vx = in.nextInt(); myPods.get(i).setVitesseX(vx);
                int vy = in.nextInt(); myPods.get(i).setVitesseY(vy);
                int angle = in.nextInt(); myPods.get(i).setAngle(angle);
                int nextCheckPointId = in.nextInt(); myPods.get(i).setNextCheckPointId(nextCheckPointId);
                myPods.get(i).setId(i+1);
                myPods.get(i).setRayon(400);
            }
            for (int i = 0; i < 2; i++) {
                int x2 = in.nextInt(); // x position of the opponent's pod
                int y2 = in.nextInt(); // y position of the opponent's pod
                int vx2 = in.nextInt(); // x speed of the opponent's pod
                int vy2 = in.nextInt(); // y speed of the opponent's pod
                int angle2 = in.nextInt(); // angle of the opponent's pod
                int nextCheckPointId2 = in.nextInt(); // next check point id of the opponent's pod
            }

            // todo pour l'instant mes deux pods vont vers le checkPoint désigné automatiquement.
            // todo regarde pour evité de loupé les check point gràce a la methode de collision(si hors trajectoire refaire le point de check point?? ptet?)
            // todo methode collision a l'air ok -> j'en suis a bounce du tuto

            for (Pod pod: myPods ) {
                System.out.println(move.moveIA1(pod, checkPoints, myPodSharings));
            }
        }
    }
}
