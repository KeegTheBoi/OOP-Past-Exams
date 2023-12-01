package a01a.e2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelImpl implements Model{

    final private int size;

    public int getSize() {
        return size;
    }

    final private Map<Pair<Integer, Integer>,  Boolean> clickedMap = new HashMap<Pair<Integer, Integer>,  Boolean>();

    public ModelImpl(final int size){
        this.size = size;
    }


    @Override
    public boolean handleClick(final int x,final int y) {
        boolean asterisk = true;
        var position = new Pair<Integer, Integer>(x, y);
        if(this.clickedMap.containsKey(position) && this.clickedMap.get(position) == true){
            asterisk = false;
        }
        this.clickedMap.put(position, asterisk);
        return asterisk;
    }

    @Override
    public boolean verifydiagonal() {
        if(this.history().size() == 3){
            return true;
        }
        return false;
    }

    @Override
    public List<Pair<Integer, Integer>> history() {
        
        return null;
    }
    
}
