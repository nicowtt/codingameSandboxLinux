import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract class Utils {

    public static char[][] map;
    public static List<Node> allNodes;

    public static List<Node> aroundHouseToSafe;

    private static char WALL = '#';
    private static char HOUSE = 'X';
    private static char TREE = '.';

    private static char FIRE = 'f';

    private static char SECURISED = 's';

    public static void createMap(List<String> lines, int column, int row) {
        map = new char[column][row];
        allNodes = new ArrayList<>();
        int nodeId = 0;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                map[x][y] = lines.get(y).charAt(x);
                Node node = new Node(nodeId, x ,y);
                nodeId++;
                if (map[x][y] == WALL) {
                    node.setBlock(true);
                } else if (map[x][y] == HOUSE) {
                    node.setHouse(true);
                } else if (map[x][y] == TREE) {
                    node.setTree(true);
                }
                allNodes.add(node);
            }
        }
    }
    public static void displayMap() {
        String line = "";
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                line += map[x][y];
            }
            System.err.println(line);
            line = "";
        }
    }

    public static Node findNodeByCoordinate(int x, int y) {
        List<Node> nodes = allNodes.stream()
                            .filter(n-> n.getX() == x && n.getY() == y)
                            .collect(Collectors.toList());
        if (!nodes.isEmpty()) {
            return nodes.get(0);
        }
        return new Node();
    }

    public static boolean isHousesOnMap() {
        return allNodes.stream().anyMatch(Node::isHouse);
    }

    public static int distBetweenTwoNode(Node one, Node two) {
        return Math.abs(two.getY() - one.getY()) + Math.abs(two.getX() - one.getX());
    }

    public static Node findHouseNodeClosedByBeginFire(int fireStartX, int fireStartY) {
        Node fire = findNodeByCoordinate(fireStartX, fireStartY);
        System.err.println("fire on: " + fire.toString());
        Node closedFireHouse = new Node();
        int value = Integer.MAX_VALUE;

        for (Node node: allNodes) {
            if (node.isHouse() &&
                distBetweenTwoNode(fire, node) < value
            ) {
                value = distBetweenTwoNode(fire, node);
                closedFireHouse = node;
            }
        }
        return closedFireHouse;
    }

    public static Node findHouseNodeClosedFire() {
        List<Node> fires = allNodes.stream().filter(Node::isOnFire).collect(Collectors.toList());
        List<Node> houses = allNodes.stream().filter(Node::isHouse).collect(Collectors.toList());

        Node closedFireHouse = new Node();
        int value = Integer.MAX_VALUE;

        for (Node fire: fires) {
            for (Node house: houses) {
                if (distBetweenTwoNode(fire, house) < value) {
                    value = distBetweenTwoNode(fire, house);
                    closedFireHouse = house;
                }
            }
        }

        return closedFireHouse;
    }

    public static Node findNodeClosedFire(List<Node> nodeList) {
        List<Node> fires = allNodes.stream().filter(Node::isOnFire).collect(Collectors.toList());

        Node closedFireNode = new Node();
        int value = Integer.MAX_VALUE;

        // todo to many time here for test 8
        for (Node fire: fires) {
            for (Node node: nodeList) {
                if (distBetweenTwoNode(fire, node) < value) {
                    value = distBetweenTwoNode(fire, node);
                    closedFireNode = node;
                }
            }
        }

        return closedFireNode;
    }

    public static List<Node> findAroundNode(Node node) {
        List<Node> aroundList = new ArrayList<>();

        // northNode
        aroundList.add(findNodeByCoordinate(node.getX(),node.getY() - 1));
        // eastNode
        aroundList.add(findNodeByCoordinate(node.getX() + 1,node.getY()));
        // southNode
        aroundList.add(findNodeByCoordinate(node.getX(), node.getY() + 1));
        // westNode
        aroundList.add(findNodeByCoordinate(node.getX() -1, node.getY()));
        
        return aroundList;
    }

    public static List<Node> findNodeAroundHouseNotSecureHouseOrOnFire(Node house) {
        List<Node> result = new ArrayList<>();
        List<Node> nodeAroundHouse = findAroundNode(house);

        for (Node node: nodeAroundHouse) {
            if (!node.isSecurized()
                && !node.isHouse()
                && !node.isOnFire()
            ) {
                result.add(node);
            }
        }

        return result;
    }

    public static void removeNodeToSafeAroundHouses(Node node) {
        List<Node> result = aroundHouseToSafe.stream()
                .filter(n -> n.getId() != node.getId())
                .collect(Collectors.toList());

        aroundHouseToSafe = new ArrayList<>(result);
    }
}
