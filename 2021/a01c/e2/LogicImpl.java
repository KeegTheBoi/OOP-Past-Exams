package a01c.e2;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class LogicImpl implements Logic {

    public enum Ortogonal {
        HORRIZONTAL((c1, c2) -> c1.y() == c2.y()), VERTICAL((c1, c2) -> c1.x() == c2.x());

        private BiPredicate<Coord, Coord> pred;

        private Ortogonal(BiPredicate<Coord, Coord> pred) {
            this.pred = pred;
        }

        public boolean test(Coord c1, Coord c2) {
            return this.pred.test(c1, c2);
        }
    }

    private final Set<Coord> snake;
    private Ortogonal curr;
    private Coord last;
    private boolean exit = false;

    public LogicImpl() {
        snake = new LinkedHashSet<>();
    }

    @Override
    public void hit(Coord c) {
        if(snake.isEmpty()) {
            snake.add(c);
            this.last = c;
        }
        else if((areHorizontal(this.last, c) || areVertical(this.last, c)) && !exit) {
            drawSegment(c);
            exit = true;
            return;
        }
        if(exit) {
            if(curr.test(c, this.last)) {
                drawSegment(c);
            }
        }
    }

    private boolean areHorizontal(Coord c1, Coord c2) {
        return c1.y() == c2.y();
    }

    private boolean areVertical(Coord c1, Coord c2) {
        return c1.x() == c2.x();
    }

    private void drawSegment(Coord c) {
        int start, end;
        curr = areHorizontal(this.last, c) ? Ortogonal.VERTICAL : Ortogonal.HORRIZONTAL;
        if(areHorizontal(this.last, c)) {
            start = Integer.min(this.last.x(), c.x());
            end = Integer.max(this.last.x(), c.x());
            IntStream.rangeClosed(start, end).boxed().map(i -> new Coord(i, c.y())).forEach(snake::add);
        }
        else {
            start = Integer.min(this.last.y(), c.y());
            end = Integer.max(this.last.y(), c.y());
            IntStream.rangeClosed(start, end).boxed().map(i -> new Coord(c.x(), i)).forEach(snake::add);
        }
        this.last = c;
        
    }

    @Override
    public Set<Coord> getSet() {
        return this.snake;
    }

}
