package a02a.e2;

import java.util.*;

public interface Logic {

    public enum Role {
        PAWN("P"), FLY("*");

        private final String symbol;

        public String getSymbol() {
            return this.symbol;
        }

        private Role(final String symb) {
            this.symbol = symb;
        }
    }

    Optional<Coord> hit();

    Coord getPawn();

    Map<Coord, Role> getMap();

}
