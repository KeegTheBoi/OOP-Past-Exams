package a05.e1;

import java.util.Iterator;
import java.util.function.Function;

public class StateFactoryImpl implements StateFactory {

    @Override
    public <S, A> State<S, A> fromFunction(Function<S, Pair<S, A>> fun) {
        return new State<S,A>() {
            A current;
            @Override
            public S nextState(S s) {
                return fun.apply(s).get1();
            }

            @Override
            public A value(S s) {
                current = fun.apply(s).get2();
                return current;
            }

            @Override
            public <B> State<S, B> map(Function<A, B> func) {
                return fromFunction(s -> new Pair<>(fun.apply(s).get1(), func.apply(fun.apply(s).get2())));
            }

            @Override
            public Iterator<A> iterator(S s0) {
                // TODO Auto-generated method stub
                return null;
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
            public <L> State<S, L> map(Function<B, L> fun) {
                
                return fromFunction(s -> new Pair<S, L>(nextState(s) , fun.apply(value(s))));
            }

            @Override
            public Iterator<B> iterator(S s0) {
                // TODO Auto-generated method stub
                return null;
            }
            
        };
    }

    @Override
    public State<Integer, String> incSquareHalve() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public State<Integer, Integer> counterOp(CounterOp op) {
        // TODO Auto-generated method stub
        return null;
    }

}
