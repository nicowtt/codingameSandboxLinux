class MyPodSharing {

    private int myPodId;
    private boolean boost;

    public MyPodSharing(int myPodId) {
        this.myPodId = myPodId;
        this.setBoost(false);
    }

    public int getMyPodId() {
        return myPodId;
    }

    public void setMyPodId(int myPodId) {
        this.myPodId = myPodId;
    }

    public boolean isBoost() {
        return boost;
    }

    public void setBoost(boolean boost) {
        this.boost = boost;
    }
}
