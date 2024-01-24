package esame.e2;
import java.util.*;
import java.util.function.UnaryOperator;

public interface Logic {

    public enum Direction {
        UP(c -> new Coord(c.x(), c.y() - 1)), DOWN(c -> new Coord(c.x(), c.y() + 1));

        private UnaryOperator<Coord> oper;

        private Direction(UnaryOperator<Coord> oper) {
            this.oper = oper;
        }

        public Coord getOper(Coord c) {
            return oper.apply(c);
        }

        public Direction getOpp() {
            return this.equals(Direction.UP) ? Direction.DOWN : Direction.UP;
        }
    }
    public boolean hit(Coord c);
    public boolean isOVer();
    public Map<Coord, Direction> getMap();
}
