package a05.e2;

import java.util.Map;
import java.util.Random;
import java.util.function.BiPredicate;

public interface Logic {

    enum Operator {
        AND((a, b) -> a && b, "AND"), OR((a, b)->  a || b, "OR"), XOR((a, b) -> a ^ b, "XOR");

        private final BiPredicate<Boolean, Boolean> predicate;
        private final String symbol;

        public boolean trasform(boolean a, boolean b) {
            return predicate.test(a, b);
        }

        public String getSymbol() {
            return this.symbol;
        }

        private Operator(BiPredicate<Boolean, Boolean> predicate, String symbol) {
            this.predicate = predicate;
            this.symbol = symbol;
        }

        public static Operator getRand() {
            return values()[new Random().nextInt(values().length)];
        }
        
    }

    Map<Coord, Object> getMap();

    boolean hit(Coord c);

    boolean getResult();

    boolean isOver();

}
