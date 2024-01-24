package a01b.e2;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class LogicImpl implements Logic {
    private final int size;
    private boolean isOv;
    private Set<Coord> set;
    private int countEligible, countRemoved = 0;
    private boolean remove;
     
    public LogicImpl(final int size){
        this.size = size;
        this.set = new LinkedHashSet<>();
    }
    
    private enum Diagonal{
       NDX(1, -1), NSX(-1, -1), SDX(1, 1), SSX(-1, 1);
       
       private final int deltaX;
       private final int deltaY;
       
       private Diagonal(int x, int y) {
           this.deltaY = y;
           this.deltaX = x;
       }
    }
    
  
    public Set<Coord> hit(Coord c) {
        countRemoved = 0;
        countEligible = 0;
        System.out.println(set);
        var valid = Arrays.stream(Diagonal.values())
            .map(d -> new Coord(c.x() + d.deltaX, c.y() + d.deltaY))
            .filter(n -> isPerimeter(n)).collect(Collectors.toSet());
        var removed = valid.stream().filter(w -> this.set.contains(w)).toList();
        set.removeAll(removed);
        valid.removeAll(removed);
        set.addAll(valid);
        if (valid.size() == 1 && removed.size() == 3) {
            isOv = true;
        }
        return Collections.unmodifiableSet(set);
    }
    
    private boolean isPerimeter(Coord c) {
        return c.x() >= 0 && c.x() < size && c.y() >= 0 && c.y() < size;
    }

    @Override
    public boolean isOver() {
         return this.isOv;
    }
}