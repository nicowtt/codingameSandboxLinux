import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        new Player().run();
    }
    public void run() {
        final int TYPE_MONSTER = 0;
        final int TYPE_MY_HERO = 1;
        final int TYPE_OP_HERO = 2;

        Utils utils = new Utils();

        int roundCount = 0;

        Scanner in = new Scanner(System.in);
        int baseX = in.nextInt(); // The corner of the map representing your base
        int baseY = in.nextInt();

        Cell myBase = new Cell(baseX, baseY);
        boolean baseIsOnLeftSide = baseX == 0;
        Cell oppBase = new Cell(baseX == 0 ? 17630 : 0, baseY == 0 ? 9000 : 0);
        int heroesPerPlayer = in.nextInt(); // Always 3



        // game loop
        while (true) {
            roundCount += 1;
            int myHealth = in.nextInt(); // Your base health
            int myMana = in.nextInt(); // Ignore in the first league; Spend ten mana to cast a spell
            int oppHealth = in.nextInt();
            int oppMana = in.nextInt();
            int entityCount = in.nextInt(); // Amount of heros and monsters you can see

            List<Entity> myHeroes = new ArrayList<>(entityCount);
            List<Entity> oppHeroes = new ArrayList<>(entityCount);
            List<Entity> monsters = new ArrayList<>(entityCount);

            for (int i = 0; i < entityCount; i++) {
                int id = in.nextInt();              // Unique identifier
                int type = in.nextInt();            // 0=monster, 1=your hero, 2=opponent hero
                int x = in.nextInt();               // Position of this entity
                int y = in.nextInt();
                Cell position = new Cell(x,y);
                int shieldLife = in.nextInt();      // Ignore for this league; Count down until shield spell fades
                int isControlled = in.nextInt();    // Ignore for this league; Equals 1 when this entity is under a control spell
                int health = in.nextInt();          // Remaining health of this monster
                int vx = in.nextInt();              // Trajectory of this monster
                int vy = in.nextInt();
                int nearBase = in.nextInt();        // 0=monster with no target yet, 1=monster targeting a base
                int threatFor = in.nextInt();       // Given this monster's trajectory, is it a threat to 1=your base, 2=your opponent's base, 0=neither

                Entity entity = new Entity(
                    id, type, x, y, shieldLife, isControlled, health, vx, vy, nearBase, threatFor, position
                );
                switch (type) {
                    case TYPE_MONSTER:
                        monsters.add(entity);
                        break;
                    case TYPE_MY_HERO:
                        myHeroes.add(entity);
                        break;
                    case TYPE_OP_HERO:
                        oppHeroes.add(entity);
                        break;
                }
            }

            // calcule la distance de chaque monstre par rapport à ma base
            for (Entity monster: monsters) {
                int distToBase = utils.distanceFromCell(monster.getCell(), myBase);
                monster.setDistFromBase(distToBase);
            }


            // todo statégie -> un hero à moi prés de la base adverse, dés que je vois une areigné a porter qui ne se dirige
            // todo pas vers la base ennemie -> je fais un control vers la base ; puis un shield et pourquoi pas un wind
            // Pour chacun de mes heros
            for (int i = 0; i < myHeroes.size(); i++) {
                Move move = new Move(baseIsOnLeftSide, myBase, oppBase, myMana, monsters, oppHeroes, myHeroes.get(i));
                // distance to base
                Cell myHeroeCell = new Cell(myHeroes.get(i).getX(), myHeroes.get(i).getY());
                myHeroes.get(i).setCell(myHeroeCell);
                myHeroes.get(i).setDistFromBase(utils.distanceFromCell(myHeroeCell, myBase));

                if (roundCount < 6) { // placement de départ (acroche le plus possible de monstre)
                    System.out.println(move.beginPlace());
                }
                else if (roundCount > 100) {
                    System.out.println(move.attackOpp());
                }
                else {
                    System.out.println(move.goToMonsterClosedFromBase());
                }
            }
        }
    }
}