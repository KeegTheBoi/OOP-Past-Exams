package a04.e2;

import java.util.*;


public class LogicImpl implements Logic, a02a.e2.Logic{

    private final int size;
    private final Map<Coord, Role> map;
    private boolean isOv;
    private Coord lastHuman;
    private Coord lastComp;
    private Random rand = new Random();

    public LogicImpl(int size) {
        this.size = size;
        this.map = new HashMap<Coord, Role>();
        init();
    }

    void init() {
        lastHuman = new Coord(rand.nextInt(size), rand.nextInt(size));
        do {
            lastComp = new Coord(rand.nextInt(size), rand.nextInt(size));
        }while(lastHuman.equals(lastComp));
        map.put(lastHuman, Role.HUMAN);
        map.put(lastComp, Role.COMPUTER);
    }

    @Override
    public boolean hit(Coord c) {
        if(isValid(c)) {
            checkMove(Role.HUMAN, Role.COMPUTER, lastHuman, c);
            if(isOv) {
                return true;
            }
            lastHuman = c;
            if(this.isAdjax(lastHuman, lastComp)) {
                isOv = true;
                return false;
            }
            Coord newPos = getNewComp();
            checkMove(Role.COMPUTER, Role.HUMAN, lastComp, newPos);
            lastComp = newPos;
        }
        return false;
    }

    private Coord getNewComp() {
        Coord newPos;
        do {
            newPos = new Coord(rand.nextInt(size), rand.nextInt(size));
        }while(!isAdjax(newPos, lastComp));
        return newPos;
    }

    private boolean isValid(Coord c) {
        return c.x() == lastHuman.x() 
        || c.y() == lastHuman.y() 
        || lastHuman.x() < c.x() ? c.x() >= lastComp.x() : c.x() <= lastComp.x()
        || lastHuman.y() < c.y() ? c.y() >= lastComp.y() : c.y() <= lastComp.y() ;
    }

    private boolean isAdjax(Coord c1, Coord c2){
        return Math.abs(c1.x() - c2.x()) <= 1 && Math.abs(c1.y() - c2.y()) <= 1;
    }

    private void checkMove(Role p, Role enemy, Coord before, Coord after) {
        map.remove(before);
        if(map.containsKey(after) && map.get(after).equals(enemy)) {
            isOv = true;
            return;
        }
        map.put(after, p);
    }

    @Override
    public boolean isOver() {
        return this.isOv;
    }

    @Override
    public Map<Coord, Role> getMap() {
        return this.map;
    }

}
