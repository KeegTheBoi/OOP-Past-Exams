package a02a.e2;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LogicImpl implements Logic {
    
    private final Set<Coord> bishops;
    private final Set<Coord> disabled;
    
    private int size;
    private boolean isOv;

    private int counter;

    public LogicImpl(int size) {
        this.size = size;
        bishops = new LinkedHashSet<>();
        disabled = new LinkedHashSet<>();
        counter = 0;
        isOv = false;
    }

    @Override
    public Set<Coord> hit(Coord w) {
        bishops.add(w);
        int originLeft = w.x() - w.y();
        int originRight = w.x() + w.y();
        Set<Coord> selected = this.all().stream()
            .filter(c -> 
                    c.x() + c.y() == originRight || 
                    c.x() - c.y() == originLeft
            ).collect(Collectors.toSet());
        disabled.addAll(selected.stream().filter(e -> !disabled.contains(e)).collect(Collectors.toSet()));
        if((disabled.size()) >= (size * size)) {
            isOv = true;
        }
        selected.remove(w);
        return selected;
    }

    private Set<Coord> all() {
        return IntStream.range(0, size)
            .boxed().flatMap(
                i -> IntStream.range(0, size)
                .mapToObj(k -> new Coord(i, k))
            ).collect(Collectors.toSet());
    }

    @Override
    public boolean isOVer() {
        return this.isOv;
    }

    @Override
    public Set<Coord> getSelected() {
        return this.bishops;
    }
}
