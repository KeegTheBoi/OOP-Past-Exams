package a01b.e1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class FlattenerFactoryImpl implements FlattenerFactory {

    private <X, Y> Flattener<X, Y> generalFlatten(Predicate<List<List<X>>> testing, Function<List<List<X>>, List<Y>> mapper){
        return new Flattener<X,Y>() {

            @Override
            public List<Y> flatten(List<List<X>> list) {
                List<Y> listFlatten = new ArrayList<>();
                List<List<X>> temp = new ArrayList<>();
                for (List<X> listSIn : list) {
                    temp.add(listSIn);
                    if(testing.test(temp)){
                        listFlatten.addAll(mapper.apply(temp));
                        temp.clear();
                    }
                }
                if(!temp.isEmpty()){
                    listFlatten.addAll(mapper.apply(temp));
                }
                return listFlatten;
            }
            
        };
    }

    @Override
    public Flattener<Integer, Integer> sumEach() {
        

        return generalFlatten(new Predicate<List<List<Integer>>>() {

            @Override
            public boolean test(List<List<Integer>> t) {
                
                return true;
            }
            
        }, new Function<List<List<Integer>>,List<Integer>>() {

            @Override
            public List<Integer> apply(List<List<Integer>> t) {
                int sum = 0;
                for (Integer integer : t.get(0)) {
                    sum += integer;
                }
                // TODO Auto-generated method stub
                return Collections.singletonList(sum);
            }
            
        });
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return generalFlatten(new Predicate<List<List<X>>>() {

            @Override
            public boolean test(List<List<X>> t) {
                
                return true;
            }
            
        }, new Function<List<List<X>>,List<X>>() {

            @Override
            public List<X> apply(List<List<X>> t) {
                return new ArrayList<>(t.get(0));
            }
            
        });
    }

    @Override
    public Flattener<String, String> concatPairs() {
        return generalFlatten(new Predicate<List<List<String>>>() {

            @Override
            public boolean test(List<List<String>> t) {
                return t.size() % 2 == 0;
            }
            
        }, new Function<List<List<String>>,List<String>>() {

            @Override
            public List<String> apply(List<List<String>> t) {
                String b = "";
                for (List<String> list : t) {
                    for (String s : list) {
                        b = b.concat(s);
                    }
                }
                return List.of(b);
            }
            
        });
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return generalFlatten(new Predicate<List<List<I>>>() {

            @Override
            public boolean test(List<List<I>> t) {
                return true;
            }
            
        }, new Function<List<List<I>>,List<O>>() {

            @Override
            public List<O> apply(List<List<I>> t) {
                List<O> c = new ArrayList<>();
                for (List<I> list : t) {
                    c.add(mapper.apply(list));
                }
                return c;
            }
            
        });
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        return generalFlatten(new Predicate<List<List<Integer>>>() {

            @Override
            public boolean test(List<List<Integer>> t) {
                
                return false;
            }
            
        }, new Function<List<List<Integer>>,List<Integer>>() {

            @Override
            public List<Integer> apply(List<List<Integer>> t) {
                Map<Integer, List<Integer>> map = new HashMap<>();
                for (List<Integer> list : t) {
                    for (int i = 0; i < list.size(); i++) {
                        if(map.get(i) == null){
                            map.put(i, new ArrayList<>());
                        }
                       
                        map.get(i).add(list.get(i));
                       
                    }
                }
                List<Integer> outer = new ArrayList<>();
                for (List<Integer> lista : map.values()) {
                    outer.add(lista.stream().mapToInt(Integer::intValue).sum());
                }

                return outer;
            }
            
        });
    }
    
}
