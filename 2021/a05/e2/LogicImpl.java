package a05.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class LogicImpl implements Logic {

    private int size;
    private final Map<Coord, Object> map;
    private int counter = 0;
    private boolean a, b;
    private Operator currOperator;
    private boolean res;
    private boolean isOv;
    private Coord lastPos;

    public LogicImpl(int size) {
        map = new HashMap<>();
        this.size = size;
        init();
    }

    private void init() {
        IntStream.range(0, size).boxed().flatMap(j -> IntStream.range(0, size).mapToObj(i -> new Coord(j, i))).forEach(c -> {
            map.put(c, counter++ % 2 == 0 ? new Random().nextInt(2) == 1 ? true : false : Operator.getRand());
        });
    }

    @Override
    public Map<Coord, Object> getMap() {
        return this.map;
    }

    @Override
    public boolean hit(Coord c) {
        if(!a && !b) {
            if(map.get(c) instanceof Boolean) {
                a = (Boolean)map.get(c);
                this.lastPos = c;
                return true;
            }  
        }
        else if(isAdjax(lastPos, c)){
            if(map.get(c) instanceof Boolean) {
                b = (Boolean)map.get(c);
                System.out.println(a + currOperator.getSymbol() + b);
                a = currOperator.trasform(a, b);
                if(a == false) {
                    isOv = true;
                    return false;
                }
            }
            if(map.get(c) instanceof Logic.Operator) {
                currOperator = (Operator)map.get(c);
            }
            lastPos = c;
            return true;
        }
        return false;
    }

    private boolean isAdjax(Coord c1, Coord c2) {
        return Math.abs(c1.x() - c2.x()) <= 1 && Math.abs(c1.y() - c2.y()) <= 1;
    }

    public boolean getResult() {
        return this.res;
    }

    public boolean isOver() {
        return this.isOv;
    }
    

}
