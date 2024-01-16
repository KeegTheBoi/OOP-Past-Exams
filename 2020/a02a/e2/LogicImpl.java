package a02a.e2;

import java.util.HashMap;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;

public class LogicImpl implements Logic {

    private int size;
    private Map<Coord, Role> map;
    private Coord pawn;
    private Random rand = new Random();

    public LogicImpl(int size) {
        this.size = size;
        this.map = new HashMap<>();
        this.init();
    }

    private void init() {
        this.pawn = new Coord(rand.nextInt(size), size - 1);
        while(map.size() < size) {
            this.map.put(new Coord(rand.nextInt(size), rand.nextInt(size)), Role.FLY);
        }
        this.map.put(pawn, Role.PAWN);
    }

    @Override
    public Optional<Coord> hit() {
        return this.optionalOfHit().filter(this::canEat).map(this::eat)
        .or(() -> optionalOfHit().filter(this::isFree))
        .map(this::updatePawn);    
        
    }

    private Coord eat(Coord c) {
        Coord left = Coord.moveLeft(c);
        Coord right = Coord.moveRight(c);
        return setOf(Role.FLY).contains(left) ? left : right; 
    }

    private boolean canEat(Coord coord1) {
        return setOf(Role.FLY).contains(Coord.moveLeft(coord1)) || setOf(Role.FLY).contains(Coord.moveRight(coord1));
    }

    private boolean isFree(Coord coord) {
        return !setOf(Role.FLY).contains(coord);
    }

    private Coord updatePawn(Coord c) {
        this.map.remove(this.pawn);
        this.map.put(c, Role.PAWN);
        return this.pawn = c;
    }

    private Optional<Coord> optionalOfHit() {
        return Optional.of(this.pawn).map(Coord::moveUp).filter(this::inBound);
    }

    private boolean inBound(Coord c) {
        return c.x() >= 0 && c.y() < size && c.x() < size && c.y() >= 0;
    }
    

    private Set<Coord> setOf(Role r) {
        return this.map.entrySet().stream().filter(e -> e.getValue() == r).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    @Override
    public Coord getPawn() {
        return this.pawn;
    }

    @Override
    public Map<Coord, Role> getMap() {
        return this.map;
    }

}
