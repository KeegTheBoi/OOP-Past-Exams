package a04.e2;

import java.util.Map;

public interface Logic {
    enum Role {
        COMPUTER("K"), HUMAN("R");

        private String symobl;

        public String getSymobl() {
            return symobl;
        }

        private Role(String symobol) {
            this.symobl = symobol;
        }
    }

    public boolean hit(Coord c);
    public boolean isOver();
    public Map<Coord, Role> getMap();
} 
