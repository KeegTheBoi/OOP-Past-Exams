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
    private Optional<Boolean> pc;

    private enum Direction {
        VERTICAL((c, v) -> new Coord(c.x(), v)), HORIZZONTAL((c, v) -> new Coord(c.x(), v));

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
        }while(mapper.containsKey(lastHuman));
        mapper.put(lastComp, Player.COMPUTER);
    }

    @Override
    public void hit(Coord c) {
        if(c.x() == lastHuman.x() || c.y() == lastHuman.y()) {
            mapper.remove(lastHuman);
            lastHuman = c;
            if(mapper.containsKey(c) && mapper.get(c).equals(Player.COMPUTER)) {
                isOv = true;
                pc = false;
                return;
            }
            mapper.put(lastHuman, Player.HUMAN);
            if(movePC()) {
                pc = true;
            }
        }
        
    }

    private boolean movePC() {
        Coord newPos = Direction.values()[rand.nextInt(2)].getPos(lastHuman, rand.nextInt(size));
        if(mapper.containsKey(newPos) && mapper.get(newPos).equals(Player.HUMAN)) {
            isOv = true;
            return true;
        }
        return false;
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
