package a02b.e2;

import java.util.Map;

public interface Logic {
    enum Player{
        LEFTOBST("L"), RIGHTOBST("R"), HUMAN("*");

        private String symb;

        public String getSymb() {
            return symb;
        }

        private Player(String symb) {
            this.symb = symb;
        }
    }

    void  hit();
    Map<Coord, Player> getMap();
    boolean isOver();

}
