package a05.e1;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StateFactoryImpl implements StateFactory {

    


    @Override
    public <S, A> State<S, A> fromFunction(Function<S, Pair<S, A>> fun) {
        return new State<S,A>() {

            @Override
            public S nextState(S s) {
                return fun.apply(s).get1();
            }

            @Override
            public A value(S s) {
                return fun.apply(s).get2();
            }

            @Override
            public <B> State<S, B> map(Function<A, B> sfun) {
                return fromFunction(k -> new Pair<S, B>(nextState(k), sfun.apply(value(k))));
            }

            @Override
            public Iterator<A> iterator(S s0) {
                var map = Stream.iterate(new Pair<S, A>(s0, value(s0)), o -> new Pair<S, A>(nextState(o.get1()), value(nextState(o.get1())))).limit(4)
                .collect(Collectors.toMap(s -> s.get1(), v -> v.get2()));
                return map.values().iterator();
            }
            
        };
    }

    @Override
    public <S, A, B> State<S, B> compose(State<S, A> state1, State<S, B> state2) {
        return new State<S,B>() {

            @Override
            public S nextState(S s) {
                return state2.nextState(state1.nextState(s));
            }

            @Override
            public B value(S s) {
                return state2.value(state1.nextState(s));
            }

            @Override
            public <K> State<S, K> map(Function<B, K> fun) {             
                return fromFunction(k -> new Pair<S, K>(nextState(k), fun.apply(value(k))));
            }

            @Override
            public Iterator<B> iterator(S s0) {
                var pairIterator = Stream.iterate(new Pair<>(s0, value(s0)), o -> new Pair<>(nextState(o.get1()), value(nextState(o.get1())))).iterator();
                return Stream.generate(() -> null).takeWhile(x -> pairIterator.hasNext()).map(n -> pairIterator.next().get2()).iterator();
            }
            
        };
    }

    @Override
    public State<Integer, String> incSquareHalve() {
        State<Integer, String> first = fromFunction(u -> new Pair<Integer, String>(u + 1, String.valueOf(u + 1)));
        State<Integer, String> second = fromFunction(u -> new Pair<Integer, String>(u * u, String.valueOf(u * u)));
        State<Integer, String> third = fromFunction(u -> new Pair<Integer, String>(u / 2, String.valueOf(u / 2)));
        return compose(compose(first, second), third);
    }

    @Override
    public State<Integer, Integer> counterOp(CounterOp op) {
        switch(op) {
            case INC:
                return fromFunction(u -> new Pair<Integer, Integer>(u + 1, null));
            case RESET:
                return fromFunction(u -> new Pair<Integer, Integer>(0, null));
            case GET:
                return fromFunction(u -> new Pair<Integer, Integer>(u, u));
            default:
                return null;
        }
    }
    
}
