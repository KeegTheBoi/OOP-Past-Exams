package a01a.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class LogicImpl implements Logic {
    private final Map<Coord, Component> map;
    private final int size;
    private boolean isOv;
    private int counter = -1;
    private Coord first;

    public LogicImpl(int size) {
        map = new HashMap<>();
        this.size = size;
    }

    @Override
    public void hit(Coord c) {  
        ++counter;
        if(counter == 0) {
            map.put(c, Component.FIRST);
            first = c;
        }
        else if (counter == 1){
            map.put(c, Component.ANY);
            drawRectangle(first, c);
            if(map.size() == size * size) {
                isOv = true;
            }
            counter = -1;
        }
    }

    private void drawRectangle(Coord vertix, Coord antiVetrix) {
        int startX = vertix.x() > antiVetrix.x() ? antiVetrix.x() : vertix.x();
        int startY = vertix.y() > antiVetrix.y() ? antiVetrix.y() : vertix.y();
        IntStream.rangeClosed(
            startX,
            startX + Math.abs(vertix.x() - antiVetrix.x())
        ).boxed().flatMap(x -> IntStream.rangeClosed(
            vertix.y() > antiVetrix.y() ? antiVetrix.y() : vertix.y(),
            startY +Math.abs(vertix.y() - antiVetrix.y())).
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
