package a03b.e2;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;

public class LogicImpl implements Logic {

    private enum Direction {
        UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

        private static int counter = 1;
        private int deltaX;
        private int deltaY;

        private Direction(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        public static Direction getNext() {
            return values()[counter++ % values().length];
        }

        public static Direction getPrev() {
            return values()[Math.abs(counter-=2) % values().length];
        }
    }

    private int size;
    private Deque<Coord> spiral = new LinkedList<>();
    Direction curr = Direction.UP;
    private boolean isOv;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public Optional<Coord> hit() {
        if(this.spiral.isEmpty()) {
            spiral.add(new Coord(new Random().nextInt(2, size - 2), new Random().nextInt(2, size - 2)));
            return Optional.of(spiral.getLast());
        }
        ;
        return this.advance(spiral.getLast()) ? Optional.of(spiral.getLast()) : Optional.empty();
    }

    private boolean advance(Coord c) {
      
        curr = Direction.getNext();
        Coord tryPos = new Coord(c.x() +curr.deltaX, c.y()+curr.deltaY);
        if(spiral.contains(tryPos)) {
            curr = Direction.getPrev();
            return advance(c);
        }
        if (isBound(tryPos)) {
            this.isOv = true;
            return false;
        }     
        spiral.add(tryPos);  
        return true;    
    }

    private boolean isBound(Coord c) {
        return c.x() < 0 || c.y() >= size || c.y() < 0 || c.x() >= size;
    }

    @Override
    public boolean isOver() {
        return this.isOv;
    }

}
