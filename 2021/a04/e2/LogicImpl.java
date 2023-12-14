package a04.e2;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class LogicImpl implements Logic {
    private final Map<Coord, Operator> map;
    private int x;
    private int y;
    Operator.Operand currentOperand = Operator.Operand.ADD;
    private int size;
    
    public LogicImpl(int size) {
        this.size = size;
        map = new HashMap<>();
        init();
    }

    public void init() {
        while(map.size() < size / 2 * size) {
            Coord pos = new Coord(new Random().nextInt(0, size), new Random().nextInt(0, size));
            if(!map.containsKey(pos)) {
                map.put(pos, new Operator(getRandNumber(10)));
            }
        }

         while(map.size() < size * size) {
            Coord pos = new Coord(new Random().nextInt(0, size), new Random().nextInt(0, size));
            if(!map.containsKey(pos)) {
                map.put(pos, new Operator(Operator.Operand.getRand()));
            }
        }
    }

    @Override
    public void hit(Coord c) {
        if(map.isEmpty()) {
            x = map.get(c).getVal();
        }
        else if(map.get(c).isValue()){          
            y =  map.get(c).getVal();
            System.out.println(x + " " +  currentOperand.name() + " " + y);
            x = currentOperand.getResult(x, y);
        }
        else if (!map.get(c).isValue()) {
            currentOperand = map.get(c).getOperand();
        }
    }

    private int getRandNumber(int i) {
        return new Random().nextInt(i);
    }

    @Override
    public int getResult() {
        return this.x;
    }

    @Override
    public Map<Coord, Operator> getMap() {
        return this.map;
    }

  

}


