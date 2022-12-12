class Strat {

    public String saveHouse() {
        if (MyVar.cooldown == 0) {
            if (!Utils.aroundHouseToSafe.isEmpty()) {
                // take fire closed node to safe
                Node closestFireNode = Utils.findNodeClosedFire(Utils.aroundHouseToSafe);
                // update Node for add secure
                final int x = closestFireNode.getX();
                final int y = closestFireNode.getY();
                Utils.allNodes.stream()
                        .filter(n -> n.getX() == x && n.getY() == y)
                        .findFirst()
                        .ifPresent(n -> n.setSecurized(true));
                Utils.removeNodeToSafeAroundHouses(closestFireNode);

                return closestFireNode.getX() + " " + closestFireNode.getY();
            }
        }
        return "WAIT";
    }
}
