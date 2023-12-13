package a03b.e2;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class LogicImpl implements Logic {

    private enum Direction {
        UP(-0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

        private static int counter;
        private int deltaX;
        private int deltaY;

        private Direction(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        public static Direction getNext() {
            return values()[counter++ % values().length];
        }
    }

    private int size;
    private Deque<Coord> spiral = new LinkedList<>();
    Direction curr = Direction.UP;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public Coord hit() {
        if(this.spiral.isEmpty()) {
            spiral.add(new Coord(new Random().nextInt(2, size - 2), new Random().nextInt(2, size - 2)));
            return spiral.getLast();
        }
        this.advance(spiral.getLast());
        return spiral.getLast();
    }

    private void advance(Coord c) {
        Coord tryPos = new Coord(c.x() +curr.deltaX, c.y()+curr.deltaY);
        curr = Direction.getNext();
        Coord nextPos = new Coord(c.x() +curr.deltaX, c.y()+curr.deltaY);
        if(spiral.contains(nextPos)) {
            nextPos = tryPos;
        }
        if (isBound(nextPos)) {
            advance(c);
        }
        
        spiral.addLast(nextPos);        
    }

    private boolean isAdiax(Coord c1, Coord c2) {
        return Math.abs(c1.x() - c2.x()) <= 1 && Math.abs(c1.x() - c2.x()) <= 1;
    }

    private boolean isBound(Coord c) {
        return c.x() <= 0 || c.y() >= size || c.y() <= 0 || c.x() >= size;
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOver'");
    }

}
