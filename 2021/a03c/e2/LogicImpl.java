package a03c.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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

    @Override
    public boolean hit() {
        IntStream.range(0, size).forEach(i -> IntStream.range(0, size).forEach(j -> {
            Coord pos = new Coord(i, j);    
            if(map.containsKey(pos) && map.get(pos).getX().equals(Player.PEDINA)) {
                if(pos.y() == size - 1) {
                    map.put(pos, new Pair<>(Player.PEDINA, Direction.UP));
                }
                if(pos.y() == 0) {
                    success = false;
                }
                if(map.containsKey(pos) && map.get(new Coord(pos.x(), pos.y() + map.get(pos).getY().getDeltaY())).getX().equals(Player.BOUNCER)) {
                    map.put(pos, new Pair<>(Player.PEDINA, Direction.DOWN));
                } 
                map.put(new Coord(pos.x(), pos.y() + map.get(pos).getY().getDeltaY()), new Pair<>(Player.PEDINA, map.get(pos).getY()));
                map.remove(pos);
                
            }
                      
        }));
        return success;
    }

}
