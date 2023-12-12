package a01b.e2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LogicImpl implements Logic {

    private final int size;
    private final Map<Coord, Player> mapPlayer;
    private final Set<Coord> enemies;
    private Coord kingPos;

    public LogicImpl(final int size) {
        this.size = size;
        mapPlayer = new HashMap<>();
        this.enemies = new HashSet<>(3);
        this.init();
    }

    public void init() {
        kingPos = new Coord(size - 1, size - 1);
        this.mapPlayer.put(kingPos, Player.KING);     
        Coord pos;
        for (int i = 0; i < 3; i++) {
            do {
                pos = new Coord(new Random().nextInt(size), new Random().nextInt(size - 1));
            }while(enemies.contains(pos));
            mapPlayer.put(pos, Player.PEDINA);
            this.enemies.add(pos);
        }
        
    }
    @Override
    public boolean hit(Coord c) {
        if(isCorrect(c)) {
            if(!isEatable(c)) {
                moveKing(c);
                return true;
            }
        }
        return false;
    }

    private void moveKing(Coord c) {
        mapPlayer.remove(kingPos);
         if(enemies.contains(c)) {
            enemies.remove(c);
        }
        mapPlayer.put(c, Player.KING);
        kingPos = c;        
    }

    private boolean isCorrect(Coord c) {
        return c.x() - 1 == kingPos.x()  && c.y() == kingPos.y() 
            || c.x() + 1 == kingPos.x() && c.y() == kingPos.y()
            || c.x() + 1 == kingPos.x() && c.y() + 1 == kingPos.y()
            || c.x() - 1 == kingPos.x() && c.y() - 1 == kingPos.y()
            || c.x() == kingPos.x() && c.y()  + 1 == kingPos.y()
            || c.x() == kingPos.x() && c.y()  - 1 == kingPos.y()
            || c.x() + 1 == kingPos.x() && c.y()  - 1 == kingPos.y()
            || c.x()  - 1== kingPos.x() && c.y() + 1 == kingPos.y();

    }

    private boolean isEatable(Coord c) {
        return enemies.stream().anyMatch(i -> new Coord(i.x() + 1 < size ? i.x() + 1 : - 1, i.y() + 1).equals(c) 
            || new Coord(i.x() - 1 >= 0 ? i.x() - 1 : -1, i.y() + 1).equals(c) );
    }

    @Override
    public boolean isOver() {
        return enemies.isEmpty();
    }

    @Override
    public Map<Coord, Player> getMap() {
        return this.mapPlayer;
    }
    
}
