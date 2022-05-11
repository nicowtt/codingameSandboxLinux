import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

class Utils {

    public int distanceFromCell(Cell c, Cell other){
        return abs(c.getX() - other.getX()) + abs(c.getY() - other.getY());
    }

    public List<Entity> getMonsterSortedByDistFromMyBase(List<Entity> monsters) {
        return monsters.stream()
            .sorted(Comparator.comparing(Entity::getDistFromBase))
            .collect(Collectors.toList());
    }

    //    getDistanceFrom = (x, y) => {
    //        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    //    };

    public List<Entity> getMonsterNearAndFocusMyBase(List<Entity> monsters) {
        List<Entity> result = new ArrayList<>();
        for (Entity monster: monsters) {
            if (monster.getNearBase() == 1 && monster.getDirection() == MonsterDirection.MYBASE) {
                result.add(monster);
            }
        }
        return  result;
    }
}
