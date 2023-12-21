package esame.e2;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class LogicImpl implements Logic{

    private int size;
    private Map<Coord, Direction> map;
    private boolean isOv;

    public LogicImpl(int size) {
        this.size = size;
        map = new HashMap<>();
    }

    @Override
    public boolean hit(Coord c) {
        if(map.size() < 5) {
            map.put(c, Direction.UP);
            return true;
        } else {
            moveAll();
            return false;
        }
    }

    private void moveAll() {
        Predicate<Map.Entry<Coord, Direction>> pred = k -> isValid(k.getValue().getOper(k.getKey()));
        var defCopy = Map.copyOf(map);
        move(pred, defCopy, e -> e.getValue()); 
        move(pred.negate(), defCopy, e -> e.getValue().getOpp());

    }

    private boolean isValid(Coord oper) {
        return oper.x() >= 0 && oper.y() >= 0 && oper.x() < size && oper.y() < size;
    }

    private void move(Predicate<Map.Entry<Coord, Direction>> predicate, Map<Coord, Direction> defMap, Function<Map.Entry<Coord, Direction>, Direction> mapper ) {
        map.entrySet().stream()
            .filter(predicate)
            .peek(e -> 
                map.put(
                    mapper.apply(e).getOper(e.getKey()),
                    mapper.apply(e)
                )
            ).map(Map.Entry::getKey)
            .peek(map::remove)
            .forEach(System.out::println);
    }

    @Override
    public boolean isOVer() {
        return isOv;
    }

    @Override
    public Map<Coord, Direction> getMap() {
        return this.map;
    }

}
