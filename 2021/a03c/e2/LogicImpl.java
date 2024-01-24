package a03c.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogicImpl implements Logic {

    private final Map<Coord, Pair<Player, Direction>> map;
    private final int size;
    private boolean success = true;

    public LogicImpl(int size) {
        this.size = size;
        map = new HashMap<>();
        init();
    }

    private void init() {
        IntStream.range(0, size).boxed().forEach(i -> map.put(new Coord(i, size - 1), new Pair<>(Player.PEDINA, Direction.UP)));
        IntStream.range(0, size).boxed().forEach(i -> map.put(new Coord(i, new Random().nextInt(size - 3)), new Pair<>(Player.BOUNCER, Direction.DOWN)));
    }

    @Override
    public Map<Coord, Pair<Player, Direction>> getMap() {
        return this.map;
    }

    Coord pos;
    @Override
    public boolean hit() {
        IntStream.range(0, size).forEach(i -> map.keySet().stream().filter(c -> c.x() == i).collect(Collectors.toList()).forEach(j -> {
            pos = new Coord(i, j.y());    
            if(map.containsKey(pos) && map.get(pos).getX().equals(Player.PEDINA)) {
                Coord tryPos = new Coord(pos.x(), pos.y() + map.get(pos).getY().getDeltaY());
                Optional<Coord> bounceCoord = Optional.empty();
                if(map.containsKey(tryPos) && map.get(tryPos).getX().equals(Player.BOUNCER)) {
                    bounceCoord = Optional.of(tryPos);
                    tryPos = new Coord(pos.x(), pos.y() + map.get(tryPos).getY().getDeltaY());
                } 
                if(tryPos.y() == size - 1) {
                    map.put(pos, new Pair<>(Player.EMPTY, Direction.UP));
                }
                bounceCoord.ifPresent(f -> {map.remove(pos); pos = f;});
                map.put(tryPos, new Pair<>(Player.PEDINA, map.get(pos).getY()));
                map.remove(pos);
                if(tryPos.y() == 0) {
                    success = false;
                }
                
            }
                      
        }));
        
        return success;
    }

}
