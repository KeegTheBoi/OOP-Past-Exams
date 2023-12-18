package a03a.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ModelImpl implements Model {

    private final Random randomGRandom;
    private Map<Movement, Pair<Integer, Integer>> mapperTable;
    private boolean end;

    public Map<Movement, Pair<Integer, Integer>> getMapperTable() {
        return this.mapperTable;
    }

    private final int size;

    public int getSize() {
        return size;
    }

    public ModelImpl(int size){
        randomGRandom = new Random();
        mapperTable = new HashMap<>();
        this.size = size;
    }

    public void initTable(){
        Pair<Integer, Integer> newHumanPos;
        Pair<Integer, Integer> newCompPos;
        do{
            newHumanPos = new Pair<>(randomGRandom.nextInt(size), randomGRandom.nextInt(size));
            newCompPos = new Pair<>(randomGRandom.nextInt(size), randomGRandom.nextInt(size));
        }while(newCompPos.equals(newHumanPos));
        mapperTable.put(Movement.HUMAN, newHumanPos);
        mapperTable.put(Movement.COMPUTER, newCompPos);
        this.end = false;
    }

    @Override
    public void computermove() {
        end = mapperTable.get(Movement.COMPUTER).getX() == mapperTable.get(Movement.HUMAN).getX() ||  mapperTable.get(Movement.COMPUTER).getY() == mapperTable.get(Movement.HUMAN).getY();
        Pair<Integer, Integer> position;
        do{
            position = new Pair<>(randomGRandom.nextInt(size), randomGRandom.nextInt(size));
        }while(!checkNewPosValidity(position.getX(), position.getY(), this.mapperTable.get(Movement.COMPUTER)));      
        mapperTable.put(Movement.COMPUTER, position);    
        
    }

    @Override
    public boolean humanmove(int x, int y) {
        var position = new Pair<>(x, y);
        if(checkNewPosValidity(x, y, this.mapperTable.get(Movement.HUMAN))){
            mapperTable.put(Movement.HUMAN, position);
            end = mapperTable.get(Movement.COMPUTER).equals(position);
            return true;
        }
        return false;
    }

    private boolean checkNewPosValidity(int x, int y, Pair<Integer, Integer> prevPos){
        return prevPos.getX().equals(x) || prevPos.getY().equals(y) && !(prevPos.getX().equals(x) && prevPos.getY().equals(y));
    }

    @Override
    public boolean isOver() {
        return this.end;
    }
    
}
