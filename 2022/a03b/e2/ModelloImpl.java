package a03b.e2;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class ModelloImpl implements Modello{

    private final Set<Pair<Integer, Integer>> listEnemy;
    private final Map<Pair<Integer, Integer>, Player> mapper;

    public Map<Pair<Integer, Integer>, Player> getMapper() {
        return this.mapper;
    }

    private final int size;
    private final Random randomGen = new Random();

    public ModelloImpl(int size){
        this.listEnemy = new LinkedHashSet<>();
        mapper = new HashMap<>();
        this.size = size;
    }

    @Override
    public void initMap() {
        for (int i = 0; i < size; i++) {
            var pos = new Pair<>(i, randomGen.nextInt(2));
            var posHuman = new Pair<>(i, size - 1);
            listEnemy.add(pos);
            this.mapper.put(pos, Player.COMPUTER); 
            this.mapper.put(posHuman, Player.HUMAN);
        }
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                var currPos = new Pair<>(j, i);
                if(Objects.isNull(this.mapper.get(currPos))){
                    this.mapper.put(currPos, Player.EMPTY);
                }
            }
        }
    }

    @Override
    public boolean hit(int x, int y) {
        var prevPos = new Pair<>(x, y);
        if(!this.mapper.get(prevPos).equals(Player.HUMAN)){
            return false;
        }
        if(possible(x, y - 1)){
            if(eatable(y - 1, x + 1) || eatable(y - 1, x - 1)){
                this.mapper.put(prevPos, Player.EMPTY);
                this.mapper.put(new Pair<Integer,Integer>(eatable(y - 1, x + 1) ? x + 1 : x - 1, y - 1), Player.HUMAN);
                return true;
            }
            this.mapper.put(prevPos, Player.EMPTY);
            this.mapper.put(new Pair<Integer,Integer>(x, y - 1), Player.HUMAN);
            return true;

        }
        return false;
    }

    private boolean possible(int x, int y){
        return x < size && x >= 0 && y >= 0 && y < size && this.mapper.get(new Pair<>(x, y)).equals(Player.EMPTY);
    }

    private boolean eatable(int y, int x){
        return x < size && x >= 0 && y >= 0 && y < size && this.mapper.get(new Pair<>(x, y)).equals(Player.COMPUTER);
    }
    
}
