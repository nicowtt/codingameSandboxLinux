import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class Move {

    private boolean baseIsOnLeftSide;
    private Cell myBase;
    private Cell oppBase;
    private int myMana;
    private List<Entity> monsters;
    private List<Entity> oppHeroes;
    private Entity myHero;

    // not use


    public Move(boolean baseIsOnLeftSide, Cell myBase, Cell oppBase, int myMana, List<Entity> monsters, List<Entity> oppHeroes, Entity myHero) {
        this.baseIsOnLeftSide = baseIsOnLeftSide;
        this.myBase = myBase;
        this.oppBase = oppBase;
        this.myMana = myMana;
        this.monsters = monsters;
        this.oppHeroes = oppHeroes;
        this.myHero = myHero;
    }

    // methods
    Utils utils = new Utils();
    final Cell firstDef = new Cell(6000, 1000);
    final Cell secondDef = new Cell(1000, 6000);
    final Cell thirdDef = new Cell(5000, 4000);

    final Cell firstDefInv = new Cell(16000, 3000);
    final Cell secondDefInv = new Cell(12000, 7000);
    final Cell thirdDefInv = new Cell(13000, 4000);

    public String beginPlace() {
        if (baseIsOnLeftSide) {
            if (myHero.getId() == 0) { myHero.setTarget(new Entity(firstDef));}
            if (myHero.getId() == 1) { myHero.setTarget(new Entity(secondDef));}
            if (myHero.getId() == 2) { myHero.setTarget(new Entity(thirdDef));}
        } else {
            if (myHero.getId() == 3) { myHero.setTarget(new Entity(firstDefInv));}
            if (myHero.getId() == 4) { myHero.setTarget(new Entity(secondDefInv));}
            if (myHero.getId() == 5) { myHero.setTarget(new Entity(thirdDefInv));}
        }

        if (myHero.getTarget() != null) {
            return String.format("MOVE %d %d %d", myHero.getTarget().getCell().getX() , myHero.getTarget().getCell().getY(), myHero.getId());
        } else {
            return "WAIT";
        }
    }

    public String goToMonsterClosedFromBase() { // defend
        boolean priorityFocus = false;
        myHero.setMode(HeroMode.DEFENSE);

        // Liste avec les monstres dans ma base et focus ma base
        List<Entity> monsterIsInAndFocusMyBase = utils.getMonsterNearAndFocusMyBase(monsters);

        // Liste classé par distance monstre-base
        List<Entity> monsterClosedMyBase = utils.getMonsterSortedByDistFromMyBase(monsters);

        if (!monsterIsInAndFocusMyBase.isEmpty() && myHero.getId() == 0 || myHero.getId() == 3) {
            this.getMonsterNearMyBase(monsters, false);
            priorityFocus = true;
        }

        if (!monsterClosedMyBase.isEmpty() && !priorityFocus) {
            this.getMonsterNearMyHero(monsters, true);
        }

        if (myHero.getTarget() != null) {
            if ((utils.distanceFromCell(myHero.getTarget().getCell(), myBase) < 5500) && (utils.distanceFromCell(myHero.getCell(), myHero.getTarget().getCell()) <= 1280) && myMana > 10) {
                return String.format("SPELL WIND %d %d %d", oppBase.getX() , oppBase.getY(), myHero.getId());
            } else {
                // return "MOVE " + myHero.getTarget().getCell().getX() + " " + myHero.getTarget().getCell().getY() + " ";
                // return "MOVE " + myHero.getTarget().getCell().getX() + " " + myHero.getTarget().getCell().getY() + " " + "go " + myHero.getTarget().getId();
                return "MOVE " + myHero.getTarget().getCell().getX() + " " + myHero.getTarget().getCell().getY() + " " + "id " + myHero.getId();

            }
        } else {
            return this.beginPlace();
        }
    }

    public String attackOpp() {
        myHero.setMode(HeroMode.ATTACK);
        if (myHero.getId() == 1 || myHero.getId() == 4) { // attack
            int myHeroToOppBase = utils.distanceFromCell(myHero.getCell(), oppBase);

            if (myHeroToOppBase > 7000) {
                // move to oppBase
                return "MOVE " + oppBase.getX() + " " + oppBase.getY() + " GO OPPBASE! ";
            }
            if (myHeroToOppBase < 7000) {

                // check if opp hero is in the place and control direction mybase
                for (Entity oppHero: oppHeroes) {
                    if ((utils.distanceFromCell(myHero.getCell(), oppHero.getCell()) < 2200) && myMana > 60) {
                        return "SPELL CONTROL " + oppHero.getId() + " " + myBase.getX() + " " + myBase.getY() + " CONTROL! ";
                    }
                }


                // check if one monster is controlled
                for (Entity monster: monsters) {
                    if (utils.distanceFromCell(monster.getCell(), oppBase) < 4000) {
                        return "SPELL SHIELD " + monster.getId() + " SHIELD! ";
                    }
                }

                for (Entity monster: monsters) {
                    //                    if (monster.getShieldLife() == 0) {
                    //                        // relance le shield
                    //                        return "SPELL SHIELD " + monster.getId() + " SHIELD! ";
                    //                    }
                    //                    if (monster.getShieldLife() > 0) {
                    //                        // suis le monstre
                    //                        return "MOVE " + monster.getCell().getX() + " " + monster.getCell().getY() + " FOLLOWING! ";
                    //                    }
                    //                    if (monster.getIsControlled() == Controlled.YES) {
                    //                        return "SPELL SHIELD " + monster.getId() + " SHIELD! ";
                    //                    }
                    // todo repousse l'adversaire avec un control

                    if ((monster.getDirection() == MonsterDirection.NOBASE || monster.getDirection() == MonsterDirection.MYBASE) &&
                        utils.distanceFromCell(myHero.getCell(), monster.getCell()) < 2200) {
                        System.err.println("control monster: " + monster.getId());
                        return "SPELL CONTROL " + monster.getId() + " " + oppBase.getX() + " " + oppBase.getY() + " CONTROL! ";
                    }

                    if ((monster.getDirection() == MonsterDirection.OPPBASE) && myMana > 60) {
                        return "SPELL WIND " + oppBase.getX() + " " + oppBase.getY() + " " + myHero.getId();
                    }
                }

            }
        }
        return this.goToMonsterClosedFromBase();
    }

    private void getMonsterNearMyHero(List<Entity> monsters, boolean focus) {
        int closedDistHeroMonster = Integer.MAX_VALUE;
        int monsterIndex = -1;

        for (int i = 0; i < monsters.size(); i++) {
            System.err.println("monster " + monsters.get(i).getId() + " controlled: "  + monsters.get(i).getIsControlled());

            if (!monsters.get(i).isTargetedByMyHero() && monsters.get(i).getIsControlled() == Controlled.NO ) { // n'est pas sous l'effet d'un control
                int distHeroMonsterTemp = utils.distanceFromCell(myHero.getCell(), monsters.get(i).getCell());
                if (distHeroMonsterTemp < closedDistHeroMonster ) {
                    closedDistHeroMonster = distHeroMonsterTemp;
                    this.myHero.setTarget(monsters.get(i));
                    monsterIndex = i;
                }
            }
        }
        if (monsterIndex != -1 && focus) {
            monsters.get(monsterIndex).setTargetedByMyHero(true);
        }
    }

    private void getMonsterNearMyBase(List<Entity> monsters, boolean focus) {
        int closedDistMyBaseMonster = Integer.MAX_VALUE;
        int monsterIndex = -1;

        for (int i = 0; i < monsters.size(); i++) {
            if (!monsters.get(i).isTargetedByMyHero()) {
                int distMyBaseMonsterTemp = utils.distanceFromCell(myBase, monsters.get(i).getCell());
                if (distMyBaseMonsterTemp < closedDistMyBaseMonster ) {
                    closedDistMyBaseMonster = distMyBaseMonsterTemp;
                    this.myHero.setTarget(monsters.get(i));
                    monsterIndex = i;
                }
            }
        }
        if (monsterIndex != -1 && focus) {
            monsters.get(monsterIndex).setTargetedByMyHero(true);
        }
    }
}

enum HeroMode {
    ATTACK,
    DEFENSE
};