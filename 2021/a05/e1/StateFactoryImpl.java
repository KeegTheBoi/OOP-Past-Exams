package a05.e1;

import java.util.Iterator;
import java.util.function.Function;
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
            public <B> State<S, B> map(Function<A, B> func) {
                return fromFunction(s -> new Pair<>(fun.apply(s).get1(), func.apply(value(s))));
            }

            @Override
            public Iterator<A> iterator(S s0) {
                return Stream.iterate(s0, s -> nextState(s)).map(this::value).iterator();
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
                
                return fromFunction(s -> new Pair<>(state2.nextState(state1.nextState(s)), fun.apply(value(s))));
            }

            @Override
            public Iterator<B> iterator(S s0) {
                return Stream.iterate(s0, s -> nextState(s)).map(this::value).iterator();
            }
            
        };
    }

    @Override
    public State<Integer, String> incSquareHalve() {
        return compose(
                compose(
                    fromFunction(s -> new Pair<Integer, String>(s + 1, String.valueOf(s + 1))),
                    fromFunction(s -> new Pair<Integer, String>((int)Math.pow(s, 2), String.valueOf(Math.pow(s, 2))))
                ),
                fromFunction(s -> new Pair<Integer, String>(s / 2, String.valueOf(s / 2)))
            );
    }

    @Override
    public State<Integer, Integer> counterOp(CounterOp op) {
        // TODO Auto-generated method stub
        return null;
    }

}
