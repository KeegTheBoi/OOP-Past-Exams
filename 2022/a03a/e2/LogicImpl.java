package a03a.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;

public class LogicImpl implements Logic {
    private final Map<Coord, Player> mapper;
    private final int size;
    private boolean isOv;
    private Coord lastHuman;
    private Coord lastComp;
    private Random rand = new Random();

    private enum Direction {
        VERTICAL((c, v) -> new Coord(c.x(), v)), HORIZZONTAL((c, v) -> new Coord(v, c.y()));

        private BiFunction<Coord, Integer, Coord> nextPos;

        private Direction(BiFunction<Coord,Integer, Coord> nextPos) {
            this.nextPos = nextPos;
        }

        public Coord getPos(Coord c, int j) {
            return nextPos.apply(c, j);
        }
    }

    public LogicImpl(int size) {
        this.size = size;
        mapper = new HashMap<>();
        init();
    }

    private void init() {
        lastHuman = new Coord(rand.nextInt(size), rand.nextInt(size));
        mapper.put(lastHuman, Player.HUMAN);
        do {
            lastComp = new Coord(rand.nextInt(size), rand.nextInt(size));       
        }while(lastComp.equals(lastHuman));
        mapper.put(lastComp, Player.COMPUTER);
    }

    @Override
    public void hit(Coord c) {
        if(c.x() == lastHuman.x() || c.y() == lastHuman.y()) {
            if(move(Player.HUMAN, c, lastHuman)) {
                return;
            };
            lastHuman = c;
            if(lastHuman.x() == lastComp.x() || lastHuman.y() == lastComp.y()) {
                isOv = true;
                mapper.remove(lastHuman);
                return;
            }
            Coord newPos = Direction.values()[rand.nextInt(2)].getPos(lastComp, rand.nextInt(size));
            move(Player.COMPUTER, newPos, lastComp);
            lastComp = newPos;
        }
        
    }

    private boolean move(Player p, Coord pos, Coord old) {
        mapper.remove(old);
        if(mapper.containsKey(pos) && mapper.get(pos).equals(p.equals(Player.HUMAN) ? Player.COMPUTER : Player.HUMAN)) {
            isOv = true;
            mapper.remove(p.equals(Player.HUMAN) ? lastComp : lastHuman);
        }
        mapper.put(pos, p);
        return isOv;
    }

    @Override
    public boolean isOVer() {
        return this.isOv;
    }

    @Override
    public Map<Coord, Player> getMap() {
        return this.mapper;
    }

}
