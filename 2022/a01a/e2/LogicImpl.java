package a01a.e2;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class LogicImpl implements Logic{

    private boolean isOv;
    private final Set<Coord> selected;

    public LogicImpl(int size) {
        this.selected = new LinkedHashSet<>();
    }

    @Override
    public void hit(Coord c) { 
        if(selected.contains(c)) {
            this.selected.remove(c);
            return;
        }
        this.selected.add(c);
        if(selected.size() >= 3) {
            this.isOv = checkDiagonal(selected.stream().skip(selected.size() - 3).collect(Collectors.toSet()));
        }
    }

    private boolean checkDiagonal(Set<Coord> collect) {
        Coord pos = collect.stream().findAny().get();
        return collect.stream().allMatch(c -> deltaCoords(c) == deltaCoords(pos)) || collect.stream().allMatch(c -> sumCoords(c) == sumCoords(pos));
    }

    private int deltaCoords(Coord c) {
        return Math.abs(c.x() - c.y());
    }

    private int sumCoords(Coord c) {
        return Math.abs(c.x() + c.y());
    }


    @Override
    public boolean isOver() {
        return this.isOv;
    }

    @Override
    public Set<Coord> getSelected() {
        return this.selected;
    }

    

}
