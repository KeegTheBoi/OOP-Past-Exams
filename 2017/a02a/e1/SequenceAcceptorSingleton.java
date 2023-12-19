package a02a.e1;

import java.util.*;
import java.util.stream.Stream;

public class SequenceAcceptorSingleton {
    private static SequenceAcceptorSingleton instance = null;
    private Iterator<Integer> iter;

    public Iterator<Integer> getIter() {
        return iter;
    }

    public static SequenceAcceptorSingleton powerUp(){
        instance = new SequenceAcceptorSingleton(Stream.iterate(0, i -> i + 1).map(p -> (int)Math.pow(2, p)).iterator());
        return instance;
    }

    public static SequenceAcceptorSingleton flip(){
        instance = new SequenceAcceptorSingleton(Stream.iterate(1, i -> i + 1).map(p -> p % 2).iterator());
        return instance;
    }

    public static SequenceAcceptorSingleton ramble(){
        instance = new SequenceAcceptorSingleton(Stream.iterate(1, i -> i + 1).flatMap(p -> Stream.of(0, p)).iterator());
        return instance;
    }
    static int t;
    public static SequenceAcceptorSingleton fibonacci(){
       
       var s = Stream.iterate(
            new ArrayList<Integer>(List.of(1, 1)), 
            l -> {
                l.add(t = l.stream().skip(l.size() - 2).mapToInt(Integer::intValue).sum());
                return l;
            }
        ).map(l -> l.size() <= 3 ? 1 : l.get(l.size() - 2)).iterator();
        instance = new SequenceAcceptorSingleton(s);
        return instance;
    }

    public static SequenceAcceptorSingleton getInstance() {
        return instance;
    }


    private SequenceAcceptorSingleton(Iterator<Integer> iter) {
        this.iter = iter;
    }

    private SequenceAcceptorSingleton() {
    }

}
