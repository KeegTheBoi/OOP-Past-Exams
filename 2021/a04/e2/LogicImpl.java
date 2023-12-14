package a04.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class LogicImpl implements Logic {
    private final Map<Coord, Operand> mapper;
    private int x;
    private int y;
    enum Operand{
        NUMBER(), ADD(Integer::sum), MINUS((x, y) -> x - y), MULTIPLY((x, y) -> x - y);

        private BiFunction<Integer, Integer, Integer> trasf;

        private Operand(BiFunction<Integer, Integer, Integer> trasf) {
            this.trasf = trasf;
        }

        private Operand() {
        
        }
    }

    private int size;
    
    public LogicImpl(int size) {
        this.size = size;
        mapper = new HashMap<>();
    }

    @Override
    public Optional<Integer> hit(Coord c) {
        if(mapper.get(c).equals(Operand.NUMBER)){
            x = 
        }
    }

}
