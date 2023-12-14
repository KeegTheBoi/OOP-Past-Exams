package a03a.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class LogicImpl implements Logic{

    private final int size;
    private static String symbol = "*";
    private final Map<Coord, String> mapper;
    private boolean isOv;
    private Coord lastVert;

    public LogicImpl(int size) {
        this.size = size;
        mapper = new HashMap<>();
        lastVert = new Coord(-1, -1);
    }

    @Override
    public boolean hit(Coord c) {
        if(lastVert.equals(new Coord(-1, -1))) {
            this.lastVert = c;
        }
        if(this.isValid(c)) {
            this.lastVert = c;
            drawRectangle(c);
            return true;
        }
        return false;
    }

    private void drawRectangle(Coord vertix) {
        Coord oppositeVertix = new Coord(size - vertix.x() - 1, size - vertix.y() - 1);       
        updateMap(vertix.x(), oppositeVertix.x() + 1, x -> new Pair<>(new Coord(x, oppositeVertix.y()), new Coord(x, vertix.y())));
        updateMap(vertix.y() + 1, oppositeVertix.y(), y -> new Pair<>(new Coord(vertix.x(), y), new Coord(oppositeVertix.x(), y)));
        if(Math.abs(oppositeVertix.x() - vertix.x()) <= 2 || Math.abs(oppositeVertix.y() - vertix.y()) <= 2) {
            this.isOv = true;
            return;
        }
    }

    private void updateMap(int start, int end, Function<Integer, Pair<Coord, Coord>> newCoord){
        IntStream.range(start, end)
            .forEach(i -> {
                mapper.put(newCoord.apply(i).getX(), symbol);
                mapper.put(newCoord.apply(i).getY(), symbol);
            }
        );
    }

    private boolean isValid(Coord c ) {
        return c.x() < this.size / 2 && c.y() < this.size / 2 && c.x() >= this.lastVert.x() && c.y() >= this.lastVert.y();
    }

    
    @Override
    public Map<Coord, String> getMap() {
        return this.mapper;
    }

    @Override
    public boolean isOver() {
        return this.isOv;
    }
    
}
