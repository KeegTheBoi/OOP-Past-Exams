package a01b.e2;

import java.util.Map;

public interface Logic {
    enum Player {
        PEDINA("p"), KING("K");

        private String content;

        public String getContent() {
            return content;
        }

        private Player(String str) {
            this.content = str;
        }
    }

    boolean hit(Coord c);
    boolean isOver();
    Map<Coord, Player> getMap();
}
