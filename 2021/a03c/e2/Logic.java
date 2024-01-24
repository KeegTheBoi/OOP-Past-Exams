package a03c.e2;

import java.util.Map;

public interface Logic {
            
    enum Direction {
        NO(0), UP(-1), DOWN(1);

        private int deltaY;
        public int getDeltaY() {
            return deltaY;
        }

        private static int count;

        private Direction(int y) {
            this.deltaY = y;
        }

        public static Direction next() {
            return Direction.values()[count++ % values().length];
        }
    }

    enum Player{
        BOUNCER("O"), PEDINA("*"), EMPTY("");

        private String symbol;

        public String getSymbol() {
            return symbol;
        }

        private Player(String symobl) {
            this.symbol = symobl;
        }
    }

    Map<Coord, Pair<Player, Direction>> getMap();

    boolean hit();

}
