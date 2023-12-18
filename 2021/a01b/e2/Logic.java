package a01b.e2;

import java.util.Map;

public interface Logic {

    public enum HitType {
        FIRST("1"), SECOND("2"), THIRD("3"), CONTENT("*");

        
        private String symbol;

        public String getSymbol() {
            return symbol;
        }

        private HitType(String symbol) {
            this.symbol = symbol;
        }
    }
    public void hit(Coord c);
    public boolean isOver();
    public Map<Coord, HitType> getMap();

}
