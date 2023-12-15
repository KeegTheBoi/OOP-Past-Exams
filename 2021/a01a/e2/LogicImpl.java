package a01a.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class LogicImpl implements Logic {
    private final Map<Coord, Component> map;
    private final int size;
    private boolean isOv;
    private int counter;
    private Coord first;

    public LogicImpl(int size) {
        map = new HashMap<>();
        this.size = size;
    }

    @Override
    public void hit(Coord c) {
        if(counter++ == 0) {
            map.put(c, Component.FISRT);
            first = c;
        }
        else if (counter == 1){
            map.put(c, Component.SECOND);
            drawRectangle(first, c);
            counter = 0;
        }
    }

    private void drawRectangle(Coord vertix, Coord antiVetrix) {
        IntStream.range(
            vertix.x() > antiVetrix.x() ? antiVetrix.x() : vertix.x(),
            Math.abs(vertix.x() - antiVetrix.x())
        ).boxed().flatMap(x -> IntStream.range(
            vertix.y() > antiVetrix.y() ? antiVetrix.y() : vertix.y(),
            Math.abs(vertix.x() - antiVetrix.x())).
            mapToObj(y -> new Coord(x, y))
        ).forEach(h -> map.put(h, Component.ANY));
    }

    @Override
    public boolean isOver() {
        return isOv;
    }

    @Override
    public Map<Coord, Component> getMap() {
       return this.map;
    }



}
