package a01b.e2;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;


public class LogicImpl implements Logic {

    private enum Direction {
        LEFT(c -> new Coord(c.x() - 1, c.y())), RIGHT(c -> new Coord(c.x() + 1, c.y()));

        private final UnaryOperator<Coord> oper;

        private Direction(UnaryOperator<Coord> oper) {
            this.oper = oper;
        }

        public Coord apply(Coord c) {
            return oper.apply(c);
        }

    }
    private int size;
    private Map<Coord, Integer> map;
    private Direction dire = Direction.LEFT;

    public LogicImpl(int size) {
        this.size = size;
        this.map = new HashMap<Coord, Integer>();
    }

    @Override
    public void hit(Coord c) {
        if(map.size() < 5) {
            map.put(c, map.size() + 1);
        } else {
            shift();
        }
    }

    private void shift() {
        Optional.of(map).filter(m -> m.keySet().stream().anyMatch(i -> i.x() == 0)).ifPresent(u -> dire = Direction.RIGHT);
        var defCopy = map.entrySet().stream().map(e -> new Pair<>(dire.apply(e.getKey()), e.getValue())).collect(Collectors.toMap(Pair::getX, Pair::getY));
        map.clear();
        map.putAll(defCopy);
    }

    @Override
    public boolean isOver() {
        return map.keySet().stream().anyMatch(this::outBound);
    }

    @Override
    public Map<Coord, Integer> selected() {
        return Collections.unmodifiableMap(map);
    }

    private boolean outBound(Coord coord1) {
        return !(coord1.x() >= 0 && coord1.y() < size && coord1.y() >= 0 && coord1.x() < size);
    }

}
