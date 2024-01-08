package a01d.e2;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



public class LogicImpl implements Logic {
    
    private enum Direction {
        UP(c -> new Coord(c.x(), c.y() - 1)), 
        RIGHT(c -> new Coord(c.x() + 1, c.y())),
        DOWN(c -> new Coord(c.x(), c.y() + 1)),
        LEFT(c ->  new Coord(c.x() - 1, c.y()));

        private UnaryOperator<Coord> trasf;

        private Direction(UnaryOperator<Coord> trasf) {
            this.trasf = trasf;
        }

        public Coord apply(Coord c) {
            return this.trasf.apply(c);
        }
    }

    private static int SQUARE_LENGHT = 5;
    private final Set<Coord> square;
    private final int size;
    private Direction dire;
    private boolean isOver;

    public LogicImpl(final int size) {
        this.size = size;
        square = new LinkedHashSet<>();
    }

    @Override
    public void hit(Coord c) {
        if(square.isEmpty()) {
            createSquare(c);
        } else {
            move(getDirection(c));
        }
    }

    
    private void move(Optional<Direction> direction) {
        direction.ifPresent(p -> {
            dire = p;
            var modified = square.stream().map(dire::apply).collect(Collectors.toSet()); 
            square.clear();
            square.addAll(modified);
        });
    }

    private Optional<Direction> getDirection(Coord c) {
        if(c.y() == 0) {
            return Optional.of(Direction.UP);
        } else if(c.y() == size - 1) {
            return Optional.of(Direction.DOWN);
        } else if(c.x() == 0) {
            return Optional.of(Direction.LEFT);
        } else if(c.x() == size - 1) {
            return Optional.of(Direction.RIGHT);
        } else {
            return Optional.empty();
        }
    }

    private void createSquare(Coord c) {
        int start = c.x() - (int)Math.ceil(SQUARE_LENGHT / 2);
        int end = c.x() + (int)Math.ceil(SQUARE_LENGHT / 2);
        if(outBound(new Coord(start, end)) || outBound(new Coord(c.y() - (int)Math.ceil(SQUARE_LENGHT / 2), c.x() + (int)Math.ceil(SQUARE_LENGHT / 2)))) {
            isOver = true;
            return;
        }
        this.square.addAll(IntStream.rangeClosed(start, end).boxed().flatMap(e -> IntStream.rangeClosed(start, end).mapToObj(j -> new Coord(e, j))).collect(Collectors.toSet()));
    }

    private boolean outBound(Coord c) {
        return !(c.x() >= 0 && c.y() < size && c.x() < size && c.y() >= 0);
    }

    @Override
    public boolean isOver() {
        return this.square.stream().anyMatch(this::outBound) && isOver;
    }

    @Override
    public Set<Coord> getSquare() {
        return Collections.unmodifiableSet(square);
    }

}
