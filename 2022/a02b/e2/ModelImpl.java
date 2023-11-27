package a02b.e2;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ModelImpl implements Model {

    private final int size;
    private final Map<Pair<Integer, Integer>, Boolean> mapper;
    private Set<Integer> listAlready;

    public ModelImpl(int size){
        mapper = new HashMap<>();
        this.size = size;
        listAlready = new LinkedHashSet<>();
    }

    @Override
    public boolean hit(int x, int y) {
        var pos = new Pair<>(x, y);
        if(Objects.isNull(this.mapper.get(pos)) || !this.mapper.get(pos)){
            mapper.put(pos, true);
            return true;
        }
        mapper.put(pos, false);
        return false;
    }

    private Set<Pair<Integer, Integer>> all(){
        Set<Pair<Integer, Integer>> outer = new LinkedHashSet<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                outer.add(new Pair<>(j, i));
            }
        }
        return Set.copyOf(outer);
    }

    @Override
    public Set<Pair<Integer, Integer>> getSelectedPos() {
        var first = getSelected(0);
        return first.isEmpty() ? getSelected(-size) : first;
    }

    private Set<Pair<Integer, Integer>> getSelected(int initialVal){
        final Set<Pair<Integer, Integer>> select = new HashSet<>();
        final Map<Integer, Integer> mactherOrigins = new HashMap<>();
        for (int i = initialVal, incr; i < size; i++) {
            incr = 0;
            mactherOrigins.put(i, incr);
            for (Map.Entry<Pair<Integer, Integer>, Boolean> entr: mapper.entrySet()) {
                if(entr.getValue().booleanValue() && entr.getKey().getX() - entr.getKey().getY() == i){
                    mactherOrigins.put(i, ++incr);
                }
            } 
            if(mactherOrigins.get(i) == 3 && !listAlready.contains(i)){
                listAlready.add(i);
                for(Pair<Integer, Integer> val : this.all()){
                    if(val.getX() - val.getY() == i){
                        select.add(val);
                    }
                }
                return select;
            }
        }
        return Collections.emptySet();
    }
}
