import java.util.List;

class Utils {

    public static boolean isBoostPossible(List<MyPodSharing> myPodSharings) {
        boolean canBoost = true;
        for (MyPodSharing myPod: myPodSharings) {
            if (myPod.isBoost()) {
                canBoost = false;
            }
        }
        return canBoost;
    }
}
