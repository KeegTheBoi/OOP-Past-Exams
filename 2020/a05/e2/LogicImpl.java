package a05.e2;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.*;

public class LogicImpl implements Logic {

    private int size;
    private boolean isOv;
    private Map<Coord, Object> map;

    public LogicImpl(int size) {
        this.size = size;
        map = new HashMap<>();
        init();
    }

    private void init() {
        Random rand = new Random();
        this.map.putAll(IntStream.range(0, size)
            .boxed().flatMap(i -> 
                IntStream.range(0, size)
                .mapToObj(l -> new Coord(i, l))
            ).collect(Collectors.toMap(
                    Function.identity(), 
                    g -> rand.nextInt(10)
                )
            )
        );
    }

    @Override
    public void hit(Coord c) {
        Set<Coord> adiaxSet = all().stream().filter(k ->isAjax(k, c)).collect(Collectors.toSet());
        if(map.get(c) instanceof Integer) {
            modifyMap(e -> map.put(e, (Integer)map.get(c)), adiaxSet);
            map.put(c, "X");
        } else if(map.get(c) instanceof String) {
            modifyMap(e -> map.put(
                e, 
                this.map.entrySet().stream()
                    .filter(k -> adiaxSet.contains(k.getKey()))
                    .map(Map.Entry::getValue)
                    .filter(o -> o instanceof Integer)
                    .map(o -> (Integer)o)
                    .reduce(0, Integer::sum)
                )
                , adiaxSet
            );
        }
        
        
    }

    private void modifyMap(Consumer<Coord> action, Set<Coord> adiaxSet) {
        this.map.entrySet().stream()
            .filter(e -> adiaxSet.contains(e.getKey()))
            .filter(o -> o.getValue() instanceof Integer)
            .map(Map.Entry::getKey)
            .forEach(action);
    }

    private boolean isAjax(Coord c1, Coord c2) {
        return Math.abs(c1.x() - c2.x()) <= 1 && Math.abs(c1.y() - c2.y()) <= 1;
    }

    private Set<Coord> all() {
        return IntStream.range(0, size)
            .boxed().flatMap(i -> 
                IntStream.range(0, size)
                .mapToObj(l -> new Coord(i, l))
            ).collect(Collectors.toSet());
    }

    

    @Override
    public boolean isOver() {
        return this.isOv;
    }

    @Override
    public Map<Coord, Object> getMap() {
        return this.map;
    }

    

}
