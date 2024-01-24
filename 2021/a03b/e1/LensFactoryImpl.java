package a03b.e1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LensFactoryImpl implements LensFactory {

    private <L, E> Lens<L, E> lensFactor(Function<L, E> getter, BiFunction<L, E, L> setter){
        return new Lens<L,E>() {

            @Override
            public E get(L s) {
                return getter.apply(s);
            }

            @Override
            public L set(E a, L s) {
                return setter.apply(s, a);
            }
            
        };
    }

    @Override
    public <E> Lens<List<E>, E> indexer(int i) {
        return this.lensFactor(o -> o.get(i), (s, a) -> {
            List<E> copyList = new ArrayList<>(s);
            copyList.set(i, a);
            return List.copyOf(copyList);
        });
    }

    @Override
    public <E> Lens<List<List<E>>, E> doubleIndexer(int i, int j) {
        return this.lensFactor(o -> o.get(i).get(j), (s, a) -> {
            List<List<E>> copyList = new ArrayList<>(s.stream().map(ArrayList::new).toList());
            copyList.get(i).set(j, a);
            return List.copyOf(copyList);
        });
    }

    @Override
    public <A, B> Lens<Pair<A, B>, A> left() {
        return this.lensFactor(o -> o.get1(), (s, a) -> {
            return new Pair<A, B>(a, s.get2());
        });
    }

    @Override
    public <A, B> Lens<Pair<A, B>, B> right() {
        return this.lensFactor(o -> o.get2(), (s, a) -> {
            return new Pair<A, B>(s.get1(), a);
        });
    }

    @Override
    public <A, B, C> Lens<List<Pair<A, Pair<B, C>>>, C> rightRightAtPos(int i) {
        return this.lensFactor(o -> o.get(i).get2().get2(), (s, a) -> {
            List<Pair<A, Pair<B, C>>> copyList = new ArrayList<>(s);
            copyList.set(i, new Pair<A,Pair<B,C>>(
                copyList.get(i).get1(), new Pair<B, C>(copyList.get(i).get2().get1(), a)));
            return List.copyOf(copyList);
        });
    }
    
}
