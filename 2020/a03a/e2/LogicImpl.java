package a03a.e2;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogicImpl implements Logic {

    
    private final Random rand = new Random();
    private Coord rook;
    private final int size;

    public LogicImpl(final int size) {
        this.size = size;
        this.rook = new Coord(rand.nextInt(size), rand.nextInt(size));
    }

    @Override
    public Set<Coord> hit(Coord c) {
        this.rook = c;
        return all().stream().filter(p -> (this.rook.x() == p.x() || this.rook.y() == p.y())).collect(Collectors.toSet());
    }

    private Set<Coord> all() {
        return IntStream.range(0, size).boxed().flatMap(i ->IntStream.range(0, size).mapToObj(j -> new Coord(i, j))).collect(Collectors.toSet());
    }

    @Override
    public Coord rook() {
        return this.rook;
    }

}
