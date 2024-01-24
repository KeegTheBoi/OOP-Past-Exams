package a01b.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class LogicImpl implements Logic { 
    private enum Ortogonal {
        HORRIZONTAL((c1, c2) -> c1.y() == c2.y()), VERTICAL((c1, c2) -> c1.x() == c2.x());

        private BiPredicate<Coord, Coord> pred;

        public boolean test(Coord c1, Coord c2) {
            return this.pred.test(c1, c2);
        }

        private Ortogonal(BiPredicate<Coord, Coord> pred) {
            this.pred = pred;
        }
    }

    private int size;
    private final Map<Coord, HitType> map;
    private boolean isOv;
    private HitType current = HitType.FIRST;
    private Ortogonal perpend;
    private Coord first, second;

    public LogicImpl(int size) {
        this.size = size;
        map = new HashMap<>();
    }

    @Override
    public void hit(Coord c) {
        if(current == HitType.FIRST) {
            System.out.println("first");
            map.put(c, HitType.FIRST);
            current = HitType.SECOND;
            this.first = c;
        }
        else if(current == HitType.SECOND && (c.x() == first.x() || c.y() == first.y())) {
            System.out.println("second");
            perpend = c.x() == first.x() ? Ortogonal.HORRIZONTAL : Ortogonal.VERTICAL;
            map.put(c, HitType.SECOND);
            current = HitType.THIRD;
            this.second = c;
        }
        else if(current == HitType.THIRD && perpend.test(c, this.first)) {
            System.out.println("third");
            map.put(c, HitType.THIRD);
            drawTriangle(first, second, c);
            current = HitType.FIRST;
            this.isOv = true;
        }

    }

    private void drawTriangle(Coord first2, Coord second2, Coord third) {
        Coord xPos = perpend == Ortogonal.HORRIZONTAL ? third : second2; 
        int xOrigin = first2.x() > xPos.x() ? xPos.x() : first2.x();
        IntStream.range(xOrigin + 1, xOrigin + Math.abs(first2.x() - xPos.x())).forEach(i -> map.put(new Coord(i, xPos.y()), HitType.CONTENT));
        Coord yPos = perpend == Ortogonal.VERTICAL ? third : second2;    
        int yOrigin = first2.y() > yPos.y() ? yPos.y() : first2.y();
        IntStream.range(yOrigin + 1, yOrigin + Math.abs(first2.y() - yPos.y())).forEach(i -> map.put(new Coord(first2.x(), i), HitType.CONTENT));
    }

    @Override
    public boolean isOver() {
        return this.isOv;
    }

    @Override
    public Map<Coord, HitType> getMap() {
        return this.map;
    }

}
