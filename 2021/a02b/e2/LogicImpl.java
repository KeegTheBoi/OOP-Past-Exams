package a02b.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.swing.text.html.Option;

public class LogicImpl implements Logic {

    private enum Direction {
        UP(0, -1), LEFT(-1, 0), RIGHT(1, 0);

        private int x;

        public int getX() {
            return this.x;
        }

        public int getY() {
            return y;
        }

        private int y;

        private Direction(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private final int size;
    private final Map<Coord, Player> mapper;
    private Direction cuurDir = Direction.UP;
    private Coord lastPos;
    private boolean iOv;

    public LogicImpl(final int size) {
        this.size = size;
        this.mapper = new HashMap<>();
        this.initMap();
    }

    private void initMap() {
        final Random rand = new Random();
        boolean left = true;
        while(mapper.size() < 20) {
            Coord pos = new Coord(rand.nextInt(size), rand.nextInt(size));
            if(isValid(pos)) {
                if(left) {
                    mapper.put(pos, Player.LEFTOBST);
                    left = false;
                }
                else {
                    mapper.put(pos, Player.RIGHTOBST);
                    left = true;
                }              
            }
        }
        do {
            this.lastPos = new Coord(rand.nextInt(size), size - 1);
        }while(this.mapper.get(lastPos) == Player.LEFTOBST || this.mapper.get(lastPos) == Player.RIGHTOBST);
        mapper.put(this.lastPos, Player.HUMAN);
    }

    private boolean isValid(Coord c) {
        return c.x() >= 0 && c.x() < this.size && c.y() >= 0 && c.y() < this.size && !mapper.keySet().contains(c);
    }

    @Override
    public void hit() {
        Coord newPos = new Coord(lastPos.x() + cuurDir.getX(), lastPos.y() + cuurDir.getY());
        if(isBound(newPos)) {
            this.iOv = true;
            return;
        }
        mapper.remove(lastPos);
        this.isObstacle(newPos).ifPresent(m -> this.cuurDir = m);
        lastPos = newPos;
        mapper.put(lastPos, Player.HUMAN);
    }

    private Optional<Direction> isObstacle(final Coord c){
        if(mapper.containsKey(c)) {
            if(mapper.get(c) == Player.LEFTOBST){
                return Optional.of(Direction.LEFT);
            }
            if (mapper.get(c) == Player.RIGHTOBST) {
                return Optional.of(Direction.RIGHT);
            }
        }
        return Optional.empty();
        
    }

    private boolean isBound(Coord c) {
        return c.x() < 0 || c.x() >= size || c.y() < 0;
    }

    @Override
    public Map<Coord, Player> getMap() {
        return this.mapper;
    }

    @Override
    public boolean isOver() {
        return this.iOv;
    }
    
}
