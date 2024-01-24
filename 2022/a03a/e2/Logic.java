package a03a.e2;

import java.util.Map;

public interface Logic {

    enum Player {
        COMPUTER("O"), HUMAN("*");

        
        private String symbol;

        public String getSymbol() {
            return symbol;
        }

        private Player(String symbol){
            this.symbol = symbol;
        }
    }


    public void hit(Coord c);
    public boolean isOVer();
    public Map<Coord, Player> getMap();
}
